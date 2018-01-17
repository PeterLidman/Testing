package se.stollenwerk.badminton.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import se.stollenwerk.badminton.parser.Util;

public class TrainingSession {
	private final long _date;
	private final int _number;
	private final List<Match> _matches = new ArrayList<>();
	private final Map<String, String> _peopleInTrainingSession = new HashMap<>();
	
	public TrainingSession(long date, int number) {
		_date = date;
		_number = number;
	}
	
	public long getDate() {
		return _date;
	}
	
	public int getNumber() {
		return _number;
	}
	
	public void addMatch(Match match) {
		_matches.add(match);
	}
	
	public Match[] getMatches() {
		return _matches.toArray(new Match[_matches.size()]);
	}
	
	/**
	 * Add a person to this specific trainingsession
	 * 
	 * @param key A short key in the file, e.g. a, b, c or d
	 * @param name The name of the player
	 */
	public void addPersonToTrainingSession(String key, String name) {
		_peopleInTrainingSession.put(key, name);
	}
	
	public String[] getPeopleNames(String key) {
		List<String> resultList = new ArrayList<>();
		for(Entry<String, String> entry : _peopleInTrainingSession.entrySet()) {
			if(entry.getKey().contains(key)) {
				resultList.add(entry.getValue());
			}
		}
		return resultList.toArray(new String[resultList.size()]);
	}
	
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<br>").append("<h3>").append("#").append(_number).append(getDateAsString()).append("</h3>");
		return sb.toString();
	}
	
	private String getDateAsString() {
		StringBuilder sb = new StringBuilder();
		int[] time = Util.longToDate(_date);
		String month = time[1] < 9 ? ("0" + time[1]) : ("" + time[1]);
		String day = time[2] < 10 ? ("0" + time[2]) : ("" + time[2]);
		sb.append(" ").append(time[0]).append("-").append(month).append("-").append(day);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		int[] time = Util.longToDate(_date);
		StringBuilder sb = new StringBuilder();
		sb.append("#").append(_number).append(" Date:").append(time[0]).append("-").append(time[1]).append("-").append(time[2]).append("\n");
		for(Match match : _matches) {
			sb.append(match.toString()).append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof TrainingSession)) {
			return false;
		}
		TrainingSession ts = (TrainingSession) o;
		return _number == ts._number;
	}
}




