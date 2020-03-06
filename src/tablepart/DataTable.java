package tablepart;

import datamanage.ClassData;
import helper.ActionFactory;
import helper.Location;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class DataTable {
	private static final String[] COL_NOTE
			= {"Time", "Mon", "Tue", "Wed", "Thu", "Fri"};
	private static final String[] ROW_NOTE
			= {"Time", "2   1", "4   3", "6   5", "8   7", "10   9", "11"};

	private static final Color NOTE_COLOR = new Color(245, 245, 245);
	private static final Font NOTE_FONT = new Font(null, Font.PLAIN, 15);

	private static final int HEAD_HEIGHT = 30;

	public int originWidth;
	public int originHeight;

	public JScrollPane scrollPane;

	public JTable table;
	public CellData[][] data;
	public JPanel[] colNotes;
	public JPanel[] rowNotes;
	public Vector<Location> markedCells;
	public boolean isSelected;

	public DataTable() {
		table = new JTable(ROW_NOTE.length, COL_NOTE.length);
		data = new CellData[ROW_NOTE.length][COL_NOTE.length];
		colNotes = new JPanel[COL_NOTE.length];
		rowNotes = new JPanel[ROW_NOTE.length];
		markedCells = new Vector<>();
		isSelected = false;

		initCell();
		initTable();
		initHead();

		setActions();
		scrollPane = new JScrollPane(table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	}

	private void initHead() {

		// Delete the origin table head
		table.getTableHeader().setPreferredSize(new Dimension(0, 0));

		/* Set table note */

		for (int i = 0; i < COL_NOTE.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(NOTE_COLOR);
			JLabel label = new JLabel(COL_NOTE[i], JLabel.CENTER);
			label.setFont(NOTE_FONT);
			panel.add(label);
			colNotes[i] = panel;
		}

		for (int i = 1; i < ROW_NOTE.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(NOTE_COLOR);
			JLabel label = new JLabel(ROW_NOTE[i], JLabel.CENTER){
				// Create anonymous subclass
				@Override
				public void paintComponent(Graphics g) {
					Graphics2D g2 = ( Graphics2D )g;
					g2.rotate(-Math.PI / 2, this.getWidth() / 2.0, this.getHeight() / 2.0);
					super.paintComponent(g2);
				}
			};
			label.setFont(NOTE_FONT);
			panel.add(label);
			rowNotes[i] = panel;
		}
	}

	private void initTable() {

		table.setModel(new MyTableModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		for (int i = 0; i < COL_NOTE.length; i++) {
			var columnModel = table.getColumnModel().getColumn(i);
			columnModel.setCellRenderer(new MyTableRenderer());
		}

		table.setRowSelectionAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.setPreferredScrollableViewportSize(new Dimension(originWidth, originHeight));

		// Set column

		table.getColumnModel().getColumn(0).setMinWidth(55);
		table.getColumnModel().getColumn(0).setMaxWidth(55);
		for (int i = 1; i < COL_NOTE.length; i++) {
			var colModel = table.getColumnModel().getColumn(i);
			colModel.setPreferredWidth(165);
		}

		// Set Row

		table.setRowHeight(0, HEAD_HEIGHT);
	}

	private void initCell() {
		for (int i = 1; i < ROW_NOTE.length; i++) {
			for (int j = 1; j < COL_NOTE.length; j++) {
				data[i][j] = new CellData(i, j);
			}
		}
	}

	private void setActions() {
		table.addMouseMotionListener(ActionFactory.createTableMouseMotionAdapter(this));
		table.addMouseListener(ActionFactory.createTableMouseAdapter(this));
	}

	public void restoreSize(){
		originWidth = scrollPane.getWidth();
		originHeight = scrollPane.getHeight();
	}

	public void refreshSize() {
		int width = scrollPane.getWidth();
		int height = scrollPane.getHeight();

//		System.out.print("width = " + width);
//		System.out.println("\t height = " + height);

		int fix = 50;
		if (height >= originHeight) {
			table.setRowHeight((height - fix) / (ROW_NOTE.length - 1));
		} else {
			table.setRowHeight((originHeight - fix) / (ROW_NOTE.length - 1));
		}
		table.setRowHeight(0, HEAD_HEIGHT);


		if (width + 10 >= originWidth) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		} else {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
	}

	public ClassData locate(int row, int col, Point point) {
		Rectangle2D rec = table.getCellRect(row, col, false);
		point.translate(-(int)rec.getX(), -(int)rec.getY());
		return data[row][col].getLocateClass(point);
	}

	public boolean markChange(int row, int col, ClassData clsData) {
		for (Location location : markedCells) {
			if (location.isSame(row, col, clsData)) {
				return false;
			}
		}
		return true;
	}

	public void hoverCell(int i, int j, ClassData clsData) {
//		System.out.println("Hover");

		// Clear first
		clearCellMark();

		// Move the point relating to the cell

		// Set
		data[i][j].setMark(clsData, CellData.IS_HOVER, this);
		markedCells.add(new Location(i, j, clsData));
	}

//	public void selectCell(int i, int j, Point point) {
////		System.out.println("Select");
//
//		// Clear first
//		clearCellMark();
//
//		// Set mark type
//		isSelected = true;
//
//		// Set
//
//		// Just one for now
//		data[i][j].setMark(point, CellData.IS_SELECTED);
//		markedCells.add(new Location(i, j));
//	}

	public void clearCellMark() {
		isSelected = false;

		for (Location l : markedCells) {
			data[l.row][l.col].resetMark();
		}
		markedCells.clear();
	}

	private class MyTableModel extends DefaultTableModel {
		@Override
		public String getColumnName(int column) {
//			return TABLE_HEAD[column].toString();
			return "";
		}

		@Override
		public Object getValueAt(int row, int column) {
			if (row == 0) { return colNotes[column]; }
			if (column == 0) { return rowNotes[row]; }
			return data[row][column].panel;
		}

		@Override
		public int getRowCount() {
			return ROW_NOTE.length;
		}

		@Override
		public int getColumnCount() {
			return COL_NOTE.length;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
}
