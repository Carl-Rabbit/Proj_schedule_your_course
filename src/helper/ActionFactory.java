package helper;

import components.ButtonPanel;
import components.MainFrame;
import datamanage.ClassData;
import datamanage.CourseData;
import datamanage.SubjectData;
import tablepart.DataTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ActionFactory {

	public static AbstractAction createExitAction() {
		var ea = new AbstractAction("Exit"){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		ea.putValue(Action.MNEMONIC_KEY, (int)'X');
		ea.putValue(Action.SHORT_DESCRIPTION, "Exit");
		return ea;
	}

	public static AbstractAction createOpenAction() {
		var oa = new AbstractAction("Open"){
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		};
		oa.putValue(Action.MNEMONIC_KEY, (int)'O');
		oa.putValue(Action.SHORT_DESCRIPTION, "Open");
		return oa;
	}

	public static AbstractAction createLoadAction(MainFrame parent) {
		var chooser = parent.chooser;
		var manager = parent.manager;
		var la = new AbstractAction("Load") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// show file chooser dialog
				int result = chooser.showOpenDialog(parent);

				// if image file accepted, set it as icon of the label
				if (result == JFileChooser.APPROVE_OPTION) {
					manager.filename = chooser.getSelectedFile().getPath();
					if (manager.open()) {
						parent.loadData();
					} else {
						JOptionPane.showConfirmDialog(parent, "Fail to load data!", "Load Failed",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

					}
				}

			}
		};
		la.putValue(Action.MNEMONIC_KEY, (int)'L');
		la.putValue(Action.SHORT_DESCRIPTION, "Load a xlsx file");
		la.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
		return la;
	}

	public static MouseMotionAdapter createTableMouseMotionAdapter(DataTable dt) {
		JTable t = dt.table;

		return new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (dt.isSelected) { return; }

				Point point = e.getPoint();
				int row = t.rowAtPoint(point);
				int col = t.columnAtPoint(point);

				if (row > 0 && col > 0) {
					// valid move action
					if (dt.data[row][col].subPanelList.isEmpty()) {
						// but the cell is empty
						dt.clearCellMark();
						dt.table.repaint();
						return;
					}
					ClassData clsData = dt.locate(row, col, point);
					if (clsData != null
							&& dt.markChange(row, col, clsData)) {
						dt.hoverCell(row, col, clsData);
						dt.table.repaint();
					}
				} else {
					dt.clearCellMark();
					dt.table.repaint();
				}
			}
		};
	}

	public static MouseAdapter createTableMouseAdapter(DataTable dt) {
		JTable t = dt.table;

		return new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				Point point = e.getPoint();
//				int row = t.rowAtPoint(point);
//				int col = t.columnAtPoint(point);
//
////				System.out.println(row + " " + col);
//
//				if (row > 0 && col > 0) {
//
//					if (dt.isSelected
//							&& !dt.markedCells.isEmpty()
//							&& dt.markedCells.get(0).isSame(row, col)) {
//						dt.clearCellMark();
//					} else {
//						dt.selectCell(row, col, point);
//					}
//
//					dt.table.repaint();
//				}
//			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (dt.isSelected) { return; }

				dt.clearCellMark();
				dt.table.repaint();
			}
		};
	}

	public static MouseAdapter createSubjectMouseAction(MainFrame parent, ButtonPanel btPanel) {

		var coursePanel = parent.coursePanel;

		return  new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				var subjectData = (SubjectData) btPanel.dataRefer;
				var courseList = subjectData.courseList;

				coursePanel.initCourse(courseList.size());

				for (int i = 0; i < courseList.size(); i++) {
					var classBtPanel = coursePanel.btPaneList.get(i);
					var courseData = courseList.get(i);
					classBtPanel.setTest(courseData.subject.subName,
							courseData.crsName,
							courseData.teacher,
							String.valueOf(courseData.quota),
							courseData.getAllTimeStr());

					classBtPanel.dataRefer = courseData;
					classBtPanel.addMouseListener(ActionFactory
							.createCourseMouseAction(parent, classBtPanel));
				}

				coursePanel.repaint();

			}
		};
	}

	public static MouseAdapter createCourseMouseAction(MainFrame parent, ButtonPanel btPanel) {

		return  new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				var courseData = (CourseData) btPanel.dataRefer;
				var subjectData = courseData.subject;
				var data = parent.dataTable.data;

				if (subjectData.selectedCourse != null) {
					// Remove old course from table
					for (ClassData d : subjectData.selectedCourse.classList) {
						data[d.time][d.day].removeSubPanel(d);
					}
				}

				// If clicked a selected course, remove it.
				if (subjectData.selectedCourse != courseData) {
					// Add course to the table
					for (ClassData d : courseData.classList) {
						data[d.time][d.day].addSubPanel(d);
					}
					subjectData.selectedCourse = courseData;
				} else {
					subjectData.selectedCourse = null;
				}

				parent.dataTable.table.repaint();
				btPanel.repaint();

			}
		};
	}

}
