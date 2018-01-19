package SwingTime;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SwingTime extends JFrame {
	private static final long serialVersionUID = 1L;
	// GUI attributes
	private JPanel timePanel = new JPanel();
	private JLabel hourJL = new JLabel("Hour", SwingConstants.CENTER);
	private JLabel minJL = new JLabel("Min", SwingConstants.CENTER);
	private JLabel secJL = new JLabel("Sec", SwingConstants.CENTER);

	private JPanel controlPanel = new JPanel();
	private JButton lastTimeJB = new JButton("<<");
	private JButton resetTimeJB = new JButton("Reset");
	private JButton nextTimeJB = new JButton(">>");
	private JButton exitAppJB = new JButton("Exit");

	private JTextField hourJTF = new JTextField();
	private JTextField minJTF = new JTextField();
	private JTextField secJTF = new JTextField();

	private LocalTime now;

	// contructor
	public SwingTime(LocalTime t1) {
		now = t1;
		// LocalTime now2 = LocalTime.now();
		this.setLayout(new BorderLayout());
		timePanel.setLayout(new GridLayout(2, 3));

		timePanel.add(hourJL);
		timePanel.add(minJL);
		timePanel.add(secJL);

		hourJTF.setText(String.valueOf(now.getHour()));
		hourJTF.setHorizontalAlignment(SwingConstants.RIGHT);
		hourJTF.setBackground(Color.WHITE);
		hourJTF.setEditable(false);

		minJTF.setText(String.valueOf(now.getMinute()));
		minJTF.setHorizontalAlignment(SwingConstants.RIGHT);
		minJTF.setBackground(Color.WHITE);
		minJTF.setEditable(false);

		secJTF.setText(String.valueOf(now.getSecond()));
		secJTF.setHorizontalAlignment(SwingConstants.RIGHT);
		secJTF.setBackground(Color.WHITE);
		secJTF.setEditable(false);

		timePanel.add(hourJTF);
		timePanel.add(minJTF);
		timePanel.add(secJTF);

		this.add(timePanel, BorderLayout.CENTER);

		controlPanel.setLayout(new FlowLayout());
		lastTimeJB.addActionListener(new AppActionListener());
		controlPanel.add(lastTimeJB);
		resetTimeJB.addActionListener(new AppActionListener());
		controlPanel.add(resetTimeJB);
		nextTimeJB.addActionListener(new AppActionListener());
		controlPanel.add(nextTimeJB);
		exitAppJB.addActionListener(new AppActionListener());
		controlPanel.add(exitAppJB);

		this.add(controlPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class AppActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton temp = (JButton) e.getSource();
			if (temp == lastTimeJB) {
				now = now.minusHours((long) 1.0);
				now = now.minusMinutes((long) 1.0);
				now = now.minusSeconds((long) 1.0);
				updateView();
			} else if (temp == resetTimeJB) {
				now = LocalTime.now();
				updateView();
			} else if (temp == nextTimeJB) {
				now = now.plusHours((long) 1.0);
				now = now.plusMinutes((long) 1.0);
				now = now.plusSeconds((long) 1.0);
				updateView();
			} else if (temp == exitAppJB) {
				System.exit(0);
			}
		}

		private void updateView() {
			hourJTF.setText(String.valueOf(now.getHour()));
			minJTF.setText(String.valueOf(now.getMinute()));
			secJTF.setText(String.valueOf(now.getSecond()));
		}
	}

	/**
	 * 
	 */

	public static void main(String[] args) {
		Runnable swingRunner = new Runnable() {

			@Override
			public void run() {
				LocalTime t1 = LocalTime.now();

				JFrame jf = new SwingTime(t1);
				jf.setTitle("SwingTime");
				jf.pack();
				jf.setResizable(false);
				jf.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(swingRunner);
	}
}
