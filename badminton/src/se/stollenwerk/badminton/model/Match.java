package se.stollenwerk.badminton.model;

public class Match {
	private static int ID_GENERATOR = 0;
	private final int _id;
	private final TrainingSession _trainingSession;
	private Team _team1;
	private Team _team2;
	
	public Match(final TrainingSession trainingSession) {
		_trainingSession = trainingSession;
		_id = getId();
	}
	
	private final static int getId() {
		ID_GENERATOR++;
		return ID_GENERATOR;
	}
	
	public void addTeam1(Team team) {
		_team1 = team;
	}
	
	public void addTeam2(Team team) {
		_team2 = team;
	}
	
	public Team getTeam1() {
		return _team1;
	}
	
	public Team getTeam2() {
		return _team2;
	}
	
	/**
	 * This method returns true if a specific person has played this match
	 * 
	 * @param name Name of person
	 * @return true if person has played the match
	 */
	public boolean isPartOfMatch(String name) {
		for(String s : _team1.getPeople()) {
			if(s.equals(name)) {
				return true;
			}
		}
		for(String s : _team2.getPeople()) {
			if(s.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	private String getTeamMembers(Team team) {
		StringBuilder sb = new StringBuilder();
		if(team.getPeople().length == 1) {
			sb.append("\"").append(team.getPeople()[0]).append("\"");
		} else if(team.getPeople().length == 2){
			sb.append("\"").append(team.getPeople()[0]).append("/").append(team.getPeople()[1]).append("\"");
		}
		return sb.toString();
	}
	
	public TrainingSession getTrainingSession() {
		return _trainingSession;
	}
	
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>");
		sb.append(getTeamMembers(_team1)).append(" - ").append(getTeamMembers(_team2)).append("<br>");
		for(int i = 0; i < _team1.getPointsForAllSets().length; i++) {
			sb.append(_team1.getPointsForAllSets()[i]).append("-").append(_team2.getPointsForAllSets()[i]);
			if(i < _team1.getPointsForAllSets().length - 1) {
				sb.append(", ");
			}
		}
		sb.append("</p>");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTeamMembers(_team1)).append(" - ").append(getTeamMembers(_team2)).append(" ");
		for(int i = 0; i < _team1.getPointsForAllSets().length; i++) {
			sb.append(_team1.getPointsForAllSets()[i]).append("-").append(_team2.getPointsForAllSets()[i]);
			if(i < _team1.getPointsForAllSets().length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Match)) {
			return false;
		}
		Match match = (Match) o;
		return _id == match._id;
	}
}








