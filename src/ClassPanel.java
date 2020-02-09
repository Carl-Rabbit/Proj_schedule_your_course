import javax.swing.*;
import java.awt.*;

public class ClassPanel extends JPanel {

	public ClassPanel(int n) {
		setLayout(new GridLayout(0 ,1));

		// Add check boxes
		var group = new ButtonGroup();
		for (int i = 0; i < n; i++) {
			var checkBox = new JCheckBox("Test" + i);
			add(checkBox);
			group.add(checkBox);
		}
	}

	public JScrollPane getScrollPane() {
		return new JScrollPane(this,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
