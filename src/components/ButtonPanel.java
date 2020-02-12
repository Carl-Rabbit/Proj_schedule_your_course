package components;

import helper.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {

	public static final Font BUTTON_FONT =
			new Font("等线", Font.PLAIN, 16);

	// pointer to the XXData that correct panel using
	public Object dataRefer;

	private ArrayList<JLabel> labels;

	public ButtonPanel(int n) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEtchedBorder());
		setBackground(new Color(250, 250, 250));

		labels = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			JLabel label = new JLabel("", JLabel.CENTER);
			labels.add(label);

			label.setFont(BUTTON_FONT);
			add(label, new GBC(0, i+1).setFill(GBC.HORIZONTAL)
					.setAnchor(GBC.CENTER).setWeight(1, 0));

//			label.setText("XXXXXX");
		}
	}

	public void setTest(String... ts) {
		if (ts.length != labels.size()) { return; }

		for (int i = 0; i < ts.length; i++) {
			labels.get(i).setText(ts[i]);
		}
	}
}
