package helper;

import tablepart.CellData;
import tablepart.DataTable;

import javax.swing.*;
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

	public static AbstractAction createImportAction() {
		var ia = new AbstractAction("Import") {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		};
		ia.putValue(Action.MNEMONIC_KEY, (int)'I');
		ia.putValue(Action.SHORT_DESCRIPTION, "Import xml file");
		ia.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
		return ia;
	}

	public static MouseMotionAdapter createTableMouseMotionAdapter(DataTable dt) {
		JTable t = dt.table;

		return new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (dt.isSelected) { return; }

				int row = t.rowAtPoint(e.getPoint());
				int col = t.columnAtPoint(e.getPoint());

				if (row > 0 && col > 0){

					// valid move action

					// when no select or not the cell want to select
					// repaint it
					if (dt.markedCells.isEmpty()
							|| !dt.markedCells.get(0).isSame(row, col)) {
						dt.hoverCell(row, col);
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
			@Override
			public void mousePressed(MouseEvent e) {
				int row = t.rowAtPoint(e.getPoint());
				int col = t.columnAtPoint(e.getPoint());

//				System.out.println(row + " " + col);

				if (row > 0 && col > 0) {

					if (dt.isSelected
							&& !dt.markedCells.isEmpty()
							&& dt.markedCells.get(0).isSame(row, col)) {
						dt.clearCellMark();
					} else {
						dt.selectCell(row, col);
					}

					dt.table.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (dt.isSelected) { return; }

				dt.clearCellMark();
				dt.table.repaint();
			}
		};
	}

	public static MouseAdapter createCellMouseAdapter(DataTable dt, CellData d) {

		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Enter");
				dt.hoverCell(d.x, d.y);
				dt.table.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Exited");
				dt.clearCellMark();
				dt.table.repaint();
			}
		};
	}

}
