package se.stollenwerk.badminton.model;

import java.util.ArrayList;
import java.util.List;

public class BadmintonModel {
	private final static BadmintonModel INSTANCE = new BadmintonModel();
	private final List<TrainingSession> _sessions = new ArrayList<>();
	
	private BadmintonModel() {
	}
	
	public final static BadmintonModel getInstance() {
		return INSTANCE;
	}
	
	public void addTrainingSession(TrainingSession session) {
		_sessions.add(session);
	}
	
	public TrainingSession[] getTrainingSessions() {
		return _sessions.toArray(new TrainingSession[_sessions.size()]);
	}
	
	public Match[] getMatches() {
		List<Match> matches = new ArrayList<>();
		for(TrainingSession session : _sessions) {
			for(Match match : session.getMatches()) {
				matches.add(match);
			}
		}
		return matches.toArray(new Match[matches.size()]);
	}
	
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n<<body>");
		for(TrainingSession session : _sessions) {
			sb.append(session.getHTML());
		}
		sb.append("</body>\n</html>");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		String string = "";
		for(TrainingSession session : _sessions) {
			string += session.toString();
		}
		return string;
	}
}
