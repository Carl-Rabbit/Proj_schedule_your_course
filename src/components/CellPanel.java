package components;

import datamanage.ClassData;
import helper.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CellPanel extends JPanel {

	public static final Font CELL_FONT =
			new Font("", Font.PLAIN, 12);

	// pointer to the ClassData that subPanels using
	public ArrayList<ClassData> dataRefer;

	private ArrayList<JPanel> subPanels;
	private ArrayList<JLabel> labels;

	public CellPanel() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder());
//		setBackground(new Color(250, 250, 250));

		subPanels = new ArrayList<>();
		labels = new ArrayList<>();
		dataRefer = new ArrayList<>();
	}

	public void addLabel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setLayout(new GridBagLayout());

		JLabel label = new JLabel("", JLabel.CENTER);
		label.setFont(CELL_FONT);
		panel.add(label, new GBC(0, 0).setFill(GBC.HORIZONTAL)
				.setAnchor(GBC.CENTER).setWeight(1,1));

		subPanels.add(panel);
		labels.add(label);

		add(panel, new GBC(0, subPanels.size()-1).setFill(GBC.HORIZONTAL)
				.setAnchor(GBC.CENTER).setWeight(1, 1));
	}

	public void setTest(String... ts) {
		if (ts.length != labels.size()) { return; }

		for (int i = 0; i < ts.length; i++) {
			labels.get(i).setText(ts[i]);
		}
	}
}
