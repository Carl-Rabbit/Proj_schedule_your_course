package tablepart;

import datamanage.ClassData;
import helper.GBC;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class CellData {
	public JPanel panel;
	public ArrayList<ClassSubPanel> subPanelList;

	static class ClassSubPanel extends JPanel{
		int state;
		JLabel label;
		ClassData dataRefer;
		ClassSubPanel(JLabel label, ClassData refer) {
			state = PLAIN;
			this.label = label;
			this.dataRefer = refer;
		}
	}

	public int x, y;

	private Color originColor;
	private Border originBorder;

	public static final int PLAIN = 0;
	public static final int IS_HOVER = 1;
	public static final int IS_SELECTED = 2;
	public static final int HAVE_RELATION = 3;

	public CellData(int x, int y, JPanel panel) {
		this.x = x;
		this.y = y;
		this.panel = panel;
		panel.setLayout(new GridBagLayout());
		subPanelList = new ArrayList<>();
	}

	public void addSubPanel(ClassData refer) {
		JLabel label = new JLabel("", JLabel.CENTER);
		label.setText(refer.course.subject.subName + refer.course.crsName);
		ClassSubPanel subPanel = new ClassSubPanel(label, refer);
		subPanelList.add(subPanel);
		panel.add(subPanel, new GBC(0, subPanelList.size()-1)
				.setWeight(1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER));
	}

	public void removeSubPanel(ClassData refer) {
		subPanelList.removeIf(e -> e.dataRefer==refer);
	}

	public void setMark(Point point, int type) {
		if (subPanelList.isEmpty()) { return; }

		ClassSubPanel tmpPanel = null;
		for (ClassSubPanel subPanel : subPanelList) {
			if (subPanel.contains(point)) {
				if (originColor == null)
					originColor = subPanel.getBackground();
				if (originBorder == null)
					originBorder = subPanel.getBorder();
				tmpPanel = subPanel;   // find it
			}
		}

		if (tmpPanel == null) { return; }

		tmpPanel.state = type;
		switch (type) {
			case IS_SELECTED:
			case IS_HOVER:
				tmpPanel.setBackground(new Color(233, 245, 255));
				tmpPanel.setBorder(BorderFactory
						.createLineBorder(new Color(222, 238, 255), 6));
				break;
			case HAVE_RELATION:
				break;
		}

//		label.setFont(new Font(originFont.getFontName(),
//				Font.BOLD, originFont.getSize()));
	}

	public void resetMark() {
		for (ClassSubPanel subPanel : subPanelList) {
			if (subPanel.state != PLAIN) {
				subPanel.state = PLAIN;
				subPanel.setBackground(originColor);
				subPanel.setBorder(originBorder);
			}
		}
	}
}
