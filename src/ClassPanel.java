import Helper.GBC;

import javax.swing.*;
import java.awt.*;

public class ClassPanel extends JPanel {

	public ClassPanel(int n) {
		setLayout(new GridBagLayout());

		setBorder(BorderFactory.createEmptyBorder());

		// Add check boxes
		var group = new ButtonGroup();
		for (int i = 0; i < n; i++) {
			var checkBox = new JCheckBox("Test" + i);
			add(checkBox, new GBC(0, i).setWeight(1, 1)
					.setFill(GBC.BOTH).setAnchor(GBC.CENTER));
			group.add(checkBox);
		}
	}

	public JScrollPane getScrollPane() {
		return new JScrollPane(this,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
