package se.stollenwerk.badminton.parser;

import se.stollenwerk.badminton.model.People;

public class ParameterFactory {
	private final static ParameterFactory INSTANCE = new ParameterFactory();
	
	private ParameterFactory() {
	}
	
	public final static ParameterFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * This method returns valid parameters based upon input text and type
	 * 
	 * @param input
	 * @param type
	 * @return Valid parameter. If the return String starts with <e>, the input string is invalid
	 */
	public String getParameter(String input, String type) {
		if(input.isEmpty()) {
			return "";
		}
		if(type.equals("person")) {
			return getPerson(input);
		}
		return "";
	}

	private String getPerson(String input) {
		return People.getInstance().match(input);
	}
}
