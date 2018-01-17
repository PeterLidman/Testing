package se.stollenwerk.badminton.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import se.stollenwerk.badminton.model.Match;

public class MatchSearch {
	private final static MatchSearch INSTANCE = new MatchSearch();
	private final List<String> _metaMethods = new ArrayList<>();
	private final List<SearchMethod> _searchableMethods = new ArrayList<>();
	
	private MatchSearch() {
		_metaMethods.add("and");
		_metaMethods.add("or");
		
		_searchableMethods.add(new SearchMethod("getPerson", "person"));
	}
	
	public final static MatchSearch getInstance() {
		return INSTANCE;
	}
	
	public Match[] getMatches(String searchString, Match[] allMatches) {
		boolean searchMethod = true;
		StringTokenizer token = new StringTokenizer(searchString, ")");
		if(searchMethod) {
			String method = (String) token.nextElement() + ")";
			return executeMethod(method, allMatches);
		} else {
			// todo, handle "and" and "or"
		}
		
		return null;
	}
	
	private Match[] executeMethod(String method, Match[] allMatches) {
		if(method.contains("getPerson")) {
			int startIndex = method.indexOf("(");
			int endIndex = method.indexOf(")");
			String name = method.substring(startIndex + 1, endIndex);
			return SearchableMethods.getPerson(name, allMatches);
		}
		return null;
	}

	public String autoCorrect(String inputString) {
		boolean searchableMethodsNext = true;
		StringBuilder sb = new StringBuilder();
		StringTokenizer token = new StringTokenizer(inputString, " ");
		while(token.hasMoreElements()) {
			String txt = (String) token.nextElement();
			if(txt != null && !txt.isEmpty()) {
				if(searchableMethodsNext) {
					String validMethod = getValidMethod(txt);
					sb.append(validMethod);
				} else {
					// todo, add "and" or "or"
				}
			}
		}
		return sb.toString();
	}
	
	private String getValidMethod(String txt) {
		for(SearchMethod method : _searchableMethods) {
			if(method.gotMatch(txt)) {
				String returnString = method.getMethodName();
				returnString += "(" + method.getParameter(txt) + ")";
				return returnString;
			}
		}
		return "<e>" + txt;
	}

	private class SearchMethod {
		public final String[] PARAMETER_LIST = {"person"};
		
		private final String _methodName;
		private final List<String> _parameters = new ArrayList<>();
		
		public SearchMethod(String methodName, String ... parameters) {
			_methodName = methodName;
			for(String parameter : parameters) {
				if(isParameterOk(parameter)) {
					_parameters.add(parameter);
				}
			}
		}
		
		public boolean gotMatch(String txt) {
			int index = txt.indexOf("(");
			String methodName = txt;
			if(index != -1) {
				methodName = txt.substring(0, index);
			}
			return _methodName.contains(methodName);
		}
		
		public String getParameter(String txt) {
			StringBuilder sb = new StringBuilder();
			int start = txt.indexOf("(");
			int end = txt.indexOf(")");
			if(start == -1 || end == -1) {
				// No ( and/or ) exists, just add them including all , separating parameters
				for(int i = 1; i < _parameters.size(); i++) {
					sb.append(" ,");
				}
			} else {
				// ( and ) exists
				String inputParameters = txt.substring(start + 1, end);
				// Get all individual parameters
				StringTokenizer token = new StringTokenizer(inputParameters, ",");
				for(int i = 0; i < _parameters.size(); i++) {
					String parameter = "";
					if(token.hasMoreElements()) {
						parameter = (String) token.nextElement();
					}
					sb.append(ParameterFactory.getInstance().getParameter(parameter, _parameters.get(i)));
				}
			}
			return sb.toString();
		}
		
		public String getMethodName() {
			return _methodName;
		}
		
		private boolean isParameterOk(String parameter) {
			for(String validParameter : PARAMETER_LIST) {
				if(parameter.toLowerCase().equals(validParameter)) {
					return true;
				}
			}
			return false;
		}
	}
}







