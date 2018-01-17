package se.stollenwerk.badminton.parser;

import java.util.ArrayList;
import java.util.List;

import se.stollenwerk.badminton.model.Match;

public class SearchableMethods {
	
	// Returns all matches where the specific person has played
	public static Match[] getPerson(String name, Match[] allMatches) {
		List<Match> returnMatches = new ArrayList<>();
		for(Match match : allMatches) {
			if(match.isPartOfMatch(name)) {
				returnMatches.add(match);
			}
		}
		return returnMatches.toArray(new Match[returnMatches.size()]);
	}
}
