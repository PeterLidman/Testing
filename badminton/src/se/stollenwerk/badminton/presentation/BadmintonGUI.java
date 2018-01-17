package se.stollenwerk.badminton.presentation;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import se.stollenwerk.badminton.model.BadmintonModel;
import se.stollenwerk.badminton.model.Match;
import se.stollenwerk.badminton.model.TrainingSession;
import se.stollenwerk.badminton.parser.LogParser;
import se.stollenwerk.badminton.parser.MatchSearch;

public class BadmintonGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextField _searchField = new JTextField();
	private final JButton _searchButton = new JButton("Search");
	private final JEditorPane _htmlArea = new JEditorPane();
	private final JTextField _infoField = new JTextField();

	private BadmintonGUI() {
	}

	public final static void open() {
		BadmintonGUI gui = new BadmintonGUI();
		gui.build();
		gui.activate();
		gui.setVisible(true);
	}

	private void build() {
		setTitle("Badminton statistics");

		GridBagConstraints c;
		JPanel panel = new JPanel(new GridBagLayout());

		// Search field
		c = new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, CENTER, HORIZONTAL, new Insets(3, 3, 0, 0), 0, 0);
		panel.add(_searchField, c);
		c = new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, CENTER, NONE, new Insets(3, 5, 0, 3), 0, 0);
		panel.add(_searchButton, c);

		// HTML area
		c = new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0, CENTER, BOTH, new Insets(5, 3, 0, 3), 0, 0);
		_htmlArea.setContentType("text/html");
		_htmlArea.setFocusable(false);
		JScrollPane scrollPane = new JScrollPane(_htmlArea);
		panel.add(scrollPane, c);

		// Info field
		_infoField.setFocusable(false);
		c = new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, CENTER, HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0);
		panel.add(_infoField, c);

		getContentPane().add(panel);

		// Set size and location
		setSize(new Dimension(500, 500));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() / 2 - 250), (int) (screenSize.getHeight() / 2 - 250));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void activate() {	
		SearchButtonListener listener = new SearchButtonListener();
		_searchButton.addActionListener(listener);
		_searchField.addActionListener(listener);
		_searchField.addKeyListener(new TabDetectListener());
		_searchField.setFocusTraversalKeysEnabled(false);
		LogParser.getInstance().start();
		Match[] matches = BadmintonModel.getInstance().getMatches();
		presentMatches(matches);
	}

	public void presentMatches(Match[] matches) {
		StringBuilder sb = new StringBuilder();
		if(matches.length == 0) {
			_htmlArea.setText(sb.toString());
			return;
		}
		TrainingSession currentSession = matches[0].getTrainingSession();
		sb.append(currentSession.getHTML());
		for(Match match : matches) {
			if(!match.getTrainingSession().equals(currentSession)) {
				currentSession = match.getTrainingSession();
				sb.append(currentSession.getHTML());
			}
			sb.append(match.getHTML());
		}
		_htmlArea.setText(sb.toString());
		_infoField.setText("Number of matches: " + matches.length);
	}

	private void showErrorText(String errorText) {
		int errorIndex = errorText.indexOf("<e>");
		String errorWord = errorText.substring(errorIndex + 3, errorText.length());
		int spaceIndex = errorWord.indexOf(" ");
		int parantesisIndex = errorWord.indexOf(")");
		int index = 0;
		if(spaceIndex != 1 && parantesisIndex != -1) {
			index = Math.min(spaceIndex, parantesisIndex);
		} else if(spaceIndex != -1) {
			index = spaceIndex;
		} else {
			index = parantesisIndex;
		}
		if(index != -1) {
			errorWord = errorWord.substring(0,  index);
		}
		_infoField.setText("Cannot find any match for " + errorWord);
		_infoField.setBackground(Color.red);
	}

	private class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Match[] matches = MatchSearch.getInstance().getMatches(_searchField.getText(), BadmintonModel.getInstance().getMatches());
			if(matches == null) {
				showErrorText(_searchField.getText());
			} else {
				presentMatches(matches);
			}
		}
	}

	private class TabDetectListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_TAB){
				String autoCorrectString = MatchSearch.getInstance().autoCorrect(_searchField.getText());
				if(autoCorrectString.contains("<e>")) {
					showErrorText(autoCorrectString);
				} else {
					_searchField.setText(autoCorrectString);
					_infoField.setBackground(Color.white);
					_infoField.setText("");
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {	
		}	
	}
}









