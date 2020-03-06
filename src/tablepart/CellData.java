package tablepart;

import datamanage.ClassData;
import helper.Location;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class CellData {
	public JPanel panel;
	public ArrayList<ClassSubPanel> subPanelList;

	static class ClassSubPanel extends JPanel{
		int state;
		ClassData dataRefer;
		ClassSubPanel(ClassData refer) {
			state = PLAIN;
			this.dataRefer = refer;
		}
	}

	public int x, y;

	private static final Color COLOR_1 = new Color(240, 240, 240);
	private static final Color COLOR_2 = new Color(245, 245, 245);

	private static final Border BORDER_1 =
			BorderFactory.createLineBorder(COLOR_2, 3);
	private static final Border BORDER_2 =
			BorderFactory.createLineBorder(new Color(250, 159, 176), 3);

	public static final int PLAIN = 0;
	public static final int IS_HOVER = 1;
	public static final int IS_SELECTED = 2;
	public static final int HAVE_RELATION = 3;

	public static final Font CELL_FONT =
			new Font("", Font.PLAIN, 12);

	public CellData(int x, int y) {
		this.x = x;
		this.y = y;
		this.panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		subPanelList = new ArrayList<>();
	}

	public void addSubPanel(ClassData refer) {
		for (ClassSubPanel subPanel : subPanelList) {
			if (refer == subPanel.dataRefer) return;
		}

		JLabel label = new JLabel("", JLabel.CENTER);
		label.setText(refer.course.subject.subName + refer.course.crsName);
		label.setFont(CELL_FONT);
		ClassSubPanel subPanel = new ClassSubPanel(refer);
		subPanel.setLayout(new BorderLayout());
		subPanel.add(label);
		subPanel.setBackground(COLOR_2);
		subPanelList.add(subPanel);
		panel.add(subPanel);

		checkSize();
	}

	public void removeSubPanel(ClassData refer) {
		for (int i = 0; i < subPanelList.size(); i++) {
			var tmpPanel = subPanelList.get(i);
			if (tmpPanel.dataRefer == refer) {
				subPanelList.remove(tmpPanel);
				panel.remove(tmpPanel);

				checkSize();

				return;
			}
		}
	}

	private void checkSize() {
		switch (subPanelList.size()) {
			case 0:
				panel.setBorder(BorderFactory.createEmptyBorder());
				break;
			case 1:
				panel.setBorder(BORDER_1);
				break;
			default:
				panel.setBorder(BORDER_2);
				break;
		}
	}

	public ClassData getLocateClass(Point point) {
		for (ClassSubPanel subPanel : subPanelList) {
			if (subPanel.contains(point)) {
				return subPanel.dataRefer;
			}
			point.translate(0, -subPanel.getHeight());
		}
		return null;
	}

	public void setMark(ClassData clsData, int type, DataTable dt) {
		ClassSubPanel subPanel = null;
		for (ClassSubPanel tmpPanel : subPanelList) {
			if (tmpPanel.dataRefer == clsData){
				subPanel = tmpPanel;
				break;
			}
		}
		assert subPanel != null;
		subPanel.state = type;
		switch (type) {
			case IS_SELECTED:
			case IS_HOVER:
				subPanel.setBackground(new Color(233, 245, 255));
//				tmpPanel.setBorder(BorderFactory
//						.createLineBorder(new Color(222, 238, 255), 1));
				var thisClass = subPanel.dataRefer;
				dt.markedCells.add(new Location(x, y, clsData));
				for (ClassData d : thisClass.course.classList) {
					if (d == thisClass) continue;
					var cellData = dt.data[d.time][d.day];
					cellData.setMark(d, HAVE_RELATION, dt);
				}
				break;
			case HAVE_RELATION:
				dt.markedCells.add(new Location(x, y, clsData));
				subPanel.setBackground(new Color(228, 242, 255));
				break;
		}
	}

	public void resetMark() {
		for (ClassSubPanel subPanel : subPanelList) {
			if (subPanel.state != PLAIN) {
				subPanel.state = PLAIN;
				subPanel.setBackground(COLOR_2);
			}
		}
	}
}
