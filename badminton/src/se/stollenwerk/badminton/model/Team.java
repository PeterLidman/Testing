package se.stollenwerk.badminton.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private final List<String> _people = new ArrayList<>();
	
	// Då ett team kan ha spelat flera sett så representeras varje sett av ett heltal i listan
	private List<Integer> _pointsPerSet = new ArrayList<>();
	
	public Team() {
	}
	
	public void addPerson(String name) {
		_people.add(name);
	}
	
	public String[] getPeople() {
		return _people.toArray(new String[_people.size()]);
	}
	
	public void addSetPoints(int points) {
		_pointsPerSet.add(points);
	}
	
	public Integer[] getPointsForAllSets() {
		return _pointsPerSet.toArray(new Integer[_pointsPerSet.size()]);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("People: ");
		for(String name: _people) {
			sb.append(name).append(" ");
		}
		sb.append("\nSetPoints: ");
		for(Integer setPoints : _pointsPerSet) {
			sb.append(setPoints).append(" ");
		}
		sb.append("\n");
		
		return sb.toString();
	}
}
