import javax.swing.*;
import java.awt.*;

public class CoursePanel extends JPanel {

	public static final int BUTTON_HEIGHT = 100;

	private JPanel panel;       // the inner panel for vFlow layout

	public JScrollPane scrollPanel;
	public int num = 0;

	public CoursePanel() {

		// Make panel to vertical flow layout

		setLayout(new BorderLayout());
		panel = new JPanel();
		add(panel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// init scroll panel
		initScrollPane();
	}

	public void initCourse(int n) {
		num = n;

		// Add buttons
		for (int i = 0; i < num; i++) {
			var button = new JButton("Test" + i);
			button.setMaximumSize(new Dimension(getWidth(), BUTTON_HEIGHT));
			panel.add(button);
		}

		panel.setPreferredSize(new Dimension(getWidth(), BUTTON_HEIGHT * num));

	}


	private void initScrollPane() {
		scrollPanel = new JScrollPane(this,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
	}
}
