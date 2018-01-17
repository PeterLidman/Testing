package se.stollenwerk.badminton.model;

import java.util.HashMap;
import java.util.Map;

public class People {
	private final static People INSTANCE = new People();
	private final Map<String, Person> _people = new HashMap<>();
	
	private People() {
	}
	
	public final static People getInstance() {
		return INSTANCE;
	}
	
	public void addPerson(Person person) {
		_people.put(person.getName(), person);
	}
	
	public Person addPerson(String name) {
		Person person = new Person(name);
		_people.put(name, person);
		return person;
	}
	
	public Person getPerson(String name) {
		return _people.get(name);
	}
	
	public boolean contains(String name) {
		return getPerson(name) != null;
	}
	
	public String match(String name) {
		for(Person person : _people.values()) {
			if(person.getName().contains(name.trim())) {
				return person.getName();
			}
		}
		return "<e>" + name;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Person person : _people.values()) {
			sb.append(person.toString()).append("\n");
		}
		return sb.toString();
	}
}
