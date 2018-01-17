package se.stollenwerk.badminton.model;

public class Person {
	private static int ID = 0;
	private final int _id;
	private final String _name;
	
	public Person(String name) {
		_name = name;
		_id = getID();
	}
	
	public int getId() {
		return _id;
	}
	
	public String getName() {
		return _name;
	}
	
	private static int getID() {
		ID++;
		return ID;
	}
	
	@Override
	public String toString()  {
		return "Id: " + _id + ", Name: " + _name;
	}
}
