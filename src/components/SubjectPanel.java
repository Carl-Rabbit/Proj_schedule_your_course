package components;

import helper.ActionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SubjectPanel extends JPanel {

	public static final int BUTTON_HEIGHT = 100;

	private JPanel panel;       // the inner panel for vFlow layout

	public JScrollPane scrollPanel;
	public ArrayList<ButtonPanel> btPaneList;

	public SubjectPanel() {

		// Make panel to vertical flow layout

		setLayout(new BorderLayout());
		panel = new JPanel();
		add(panel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// init scroll panel
		initScrollPane();

		// init buttonList
		btPaneList = new ArrayList<>();
	}

	public void initSubject(int n, MainFrame parent) {

		panel.removeAll();
		btPaneList.clear();

		// Add buttons
		for (int i = 0; i < n; i++) {
			var btPanel = new ButtonPanel(2);
			btPanel.setPreferredSize(new Dimension(getWidth(), BUTTON_HEIGHT));
			btPanel.setMaximumSize(new Dimension(getWidth(), BUTTON_HEIGHT));
			panel.add(btPanel);
			btPaneList.add(btPanel);

			btPanel.addMouseListener(ActionFactory
					.createSubjectMouseAction(parent, btPanel));
		}
	}

	private void initScrollPane() {
		scrollPanel = new JScrollPane(this,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
	}
}
