import javax.swing.*;
import java.awt.*;

public class CoursePanel extends JPanel {

	public CoursePanel(int n) {
		setLayout(new GridLayout(0 ,1));

		// Add buttons
		for (int i = 0; i < n; i++) {
			var button = new JButton("Test" + i);
			add(button);
		}
	}

	public JScrollPane getScrollPane() {
		return new JScrollPane(this,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
