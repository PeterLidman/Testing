package se.stollenwerk.badminton.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import se.stollenwerk.badminton.model.BadmintonModel;
import se.stollenwerk.badminton.model.Match;
import se.stollenwerk.badminton.model.People;
import se.stollenwerk.badminton.model.Person;
import se.stollenwerk.badminton.model.Team;
import se.stollenwerk.badminton.model.TrainingSession;

public class LogParser {
	private final static LogParser INSTANCE = new LogParser();
	private BufferedReader _br;
	private TrainingSession _currentSession = null;
	
	private LogParser() {
		try {
			_br = new BufferedReader(new FileReader(new File("etc\\deltagare_badminton.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public final static LogParser getInstance() {
		return INSTANCE;
	}
	
	private boolean debugRows = false;
	public void start() {
		long startTime = System.currentTimeMillis();
		try {
			int row = 1;
			String line = _br.readLine();
			while(line != null) {
				if(debugRows) {
					System.out.println("Is about to parse row " + row);
				}
				processLine(line);
				line = _br.readLine();
				row++;
			}
		    // line is not visible here.
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
//		System.out.println(BadmintonModel.getInstance().toString());
		System.out.println("Time: " + (endTime - startTime) + " ms");
	}
	
	// Denna metod processar en rad fr�n r�filen
	private void processLine(String line) {
		if(line.contains("#") && !line.toLowerCase().contains("inst�llt")) {
			// Om en linje inneh�ller # och om tr�ningspasset inte �r inst�llt s� skall ett nytt tr�ningspass skapas
			_currentSession = createTrainingSession(line);
			BadmintonModel.getInstance().addTrainingSession(_currentSession);
		} else if(_currentSession != null) {
			// Om _currentSession inte �r null och om raden inte inneh�ller # s� skall den aktuella raden
			// l�ggas till _currentSession
			if(line.contains(":")) {
				addPersonToCurrentTrainingSession(line);
			} else if(!line.isEmpty()) {
				addMatchesToCurrentTrainingSession(line);
			}
		}
	}

	private void addMatchesToCurrentTrainingSession(String line) {
		if(line.contains("-") && line.contains(" ")) {
			try {
				int index = line.indexOf(" ");
				Team[] teams = getTeams(line.substring(0,  index));
				Integer[] results = getResults(line.substring(index + 1, line.length()));
				for(int i = 0; i < results.length; i += 2) {
					teams[0].addSetPoints(results[i]);
					teams[1].addSetPoints(results[i + 1]);
				}
				Match match = new Match(_currentSession);
				match.addTeam1(teams[0]);
				match.addTeam2(teams[1]);
				_currentSession.addMatch(match);
			} catch (Exception e) {
				// fuck it
			}
//			System.out.print(match.toString());
		}
	}

	private Team[] getTeams(String line) {
		int index = line.indexOf("-");
		String team1 = line.substring(0,  index);
		String team2 = line.substring(index + 1, line.length());
		return new Team[]{getTeam(team1), getTeam(team2)};
	}
	
	private Team getTeam(String txt) {
		Team team = new Team();
		for(int i = 0; i < txt.length(); i++) {
			String key = String.valueOf(txt.charAt(i));
			String[] names = _currentSession.getPeopleNames(key);
			for(String name : names) {
				team.addPerson(name);
			}
		}
		return team;
	}

	private Integer[] getResults(String line) {
		List<Integer> resultList = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(line, ",");
		while(token.hasMoreTokens()) {
			String result = token.nextToken().trim();
			int index = result.indexOf("-");
			int result1 = Integer.valueOf(result.substring(0,  index).trim());
			int result2 = Integer.valueOf(result.substring(index + 1, result.length()).trim());
			resultList.add(result1);
			resultList.add(result2);
		}
		return resultList.toArray(new Integer[resultList.size()]);
	}

	private void addPersonToCurrentTrainingSession(String line) {
		if(line.toLowerCase().endsWith("set")) {
			// Denna koll �r till f�r att undvika rader s�som "1:a set"
			return;
		}
		
		int index = line.indexOf(":");
		String key = line.substring(0, index);
		String[] names = getNames(line.substring(index + 1, line.length()));
		
		for(String name : names) {
			Person person = People.getInstance().getPerson(name);
			if(person == null) {
				person = People.getInstance().addPerson(name);
			}
		}
		
		if(names.length == 1) {
			_currentSession.addPersonToTrainingSession(key, names[0]);
		} else if(names.length == 2){
			_currentSession.addPersonToTrainingSession(key + "1", names[0]);
			_currentSession.addPersonToTrainingSession(key + "2", names[1]);
		}
	}

	// Denna metod returnerar alla namn fr�n den aktuella textstr�ngen.
	// Om det handlar om en singelmatch �r det ett namn som returneras och om
	// det varit en dubbelmatch s� �r det tv� personer som returneras.
	private String[] getNames(String txt) {
		if(txt.trim().isEmpty()) {
			return new String[0];
		}
		String[] names = null;
		int index = txt.indexOf(" / ");
		if(index != -1) {
			// dvs en dubbelmatch
			names = new String[2];
			names[0] = getName(txt.substring(0,  index)).toLowerCase();
			names[1] = getName(txt.substring(index + 3, txt.length())).toLowerCase();
		} else {
			// dvs en singelmatch
			names = new String[1];
			names[0] = getName(txt).toLowerCase();
		}
		return names;
	}
	
	// Denna metod returnerar ett namn fr�n en textstr�ng
	private String getName(String txt) {
		txt = txt.trim();
		int index = txt.indexOf(" ");
		StringBuilder sb = new StringBuilder();
		sb.append(txt.substring(0,  index)).append(" ");
		txt = txt.substring(index, txt.length()).trim();
		index = txt.indexOf(" ");
		if(index == -1) {
			sb.append(txt);
		} else {
			sb.append(txt.substring(0,  index));
		}
		return sb.toString();
	}

	// Denna metod l�ser ut tr�ningspassnummer och datum fr�n r�filen
	private TrainingSession createTrainingSession(String line) {
		// Tr�ningsindex startar med #
		int index = line.indexOf("#");
		String txt = line.substring(0, index).trim();
		long date = Util.getDate(txt);
		txt = line.substring(index + 1, line.length()).trim();
		int nr = getInteger(txt);
		return new TrainingSession(date, nr);
	}

	// Denna metod tar Str�ngen txt och parsar den till ett heltal.
	private int getInteger(String txt) {
		int index = txt.indexOf(" ");
		if(index != -1) {
			txt = txt.substring(0, index).trim();
		}
		txt = secureIntegers(txt);
		
		return Integer.parseInt(txt);
	}

	// Denna metod s�kerst�ller att alla bokst�ver i texten txt �r siffror
	private String secureIntegers(String txt) {
		String returnedString = "";
		for(int i = 0; i < txt.length(); i++) {
			try {
				String subText = txt.substring(i, i+1);
				Integer.parseInt(subText);
				returnedString += subText;
			} catch (NumberFormatException e) {
				// do nothing
			}
		}
		return returnedString;
	}
}


















