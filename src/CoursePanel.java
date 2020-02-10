import Helper.GBC;

import javax.swing.*;
import java.awt.*;

public class CoursePanel extends JPanel {

	public CoursePanel(int n) {
		setLayout(new GridBagLayout());

		setBorder(BorderFactory.createEmptyBorder());

		// Add buttons
		for (int i = 0; i < n; i++) {
			var button = new JButton("Test" + i);
			add(button, new GBC(0, i).setWeight(1, 1)
					.setFill(GBC.BOTH).setAnchor(GBC.CENTER));
		}
	}

	public JScrollPane getScrollPane() {
		return new JScrollPane(this,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
