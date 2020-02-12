package tablepart;

import helper.ActionFactory;
import helper.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class DataTable {
	private static final String[] ROW_NOTE
			= {"Time", "Mon", "Tue", "Wed", "Thu", "Fri"};
	private static final String[] COL_NOTE
			= {"Time", "2   1", "4   3", "6   5", "8   7", "10   9", "11"};
	private static final int ROW_NUM = 7;
	private static final int COL_NUM = 6;

	private static final Font NOTE_FONT = new Font(null, Font.PLAIN, 15);

	private static final int HEAD_HEIGHT = 30;

	public int originWidth;
	public int originHeight;

	public JScrollPane scrollPane;

	public JTable table;
	public CellData[][] data;
	public Vector<Pair<Integer>> markedCells;
	public boolean isSelected;

	public DataTable() {
		table = new JTable(ROW_NUM, COL_NUM);
		data = new CellData[ROW_NUM][COL_NUM];
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

		for (int i = 0; i < COL_NUM; i++) {
			var label = data[0][i].label;
			label.setFont(NOTE_FONT);
			label.setText(ROW_NOTE[i]);
			data[0][i].panel.setBackground(new Color(250, 250, 250));
		}

		for (int i = 1; i < ROW_NUM; i++) {
			var label = data[i][0].label;
			label.setFont(NOTE_FONT);
			label.setText(COL_NOTE[i]);
			data[i][0].panel.setBackground(new Color(250, 250, 250));
		}


	}

	private void initTable() {

		table.setModel(new MyTableModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		for (int i = 0; i < COL_NUM; i++) {
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
		for (int i = 1; i < COL_NUM; i++) {
			var colModel = table.getColumnModel().getColumn(i);
			colModel.setPreferredWidth(165);
		}

		// Set Row

		table.setRowHeight(120);
		table.setRowHeight(0, HEAD_HEIGHT);
	}

	private void initCell() {

		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COL_NUM; j++) {

				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());

				JLabel label;
				if (i != 0 && j == 0) {
					label = new JLabel(String.format("label[%d][%d]", i, j), JLabel.CENTER){
						// Create anonymous subclass
						@Override
						public void paintComponent(Graphics g) {
							Graphics2D g2 = ( Graphics2D )g;
							g2.rotate(-Math.PI / 2, this.getWidth() / 2.0, this.getHeight() / 2.0);
							super.paintComponent(g2);
						}
					};
				} else {
					label = new JLabel(String.format("label[%d][%d]", i, j), JLabel.CENTER);
				}

				panel.add(label);

				data[i][j] = new CellData(i, j, panel, label);
			}
		}
	}

	private void setActions() {
		table.addMouseMotionListener(ActionFactory.createTableMouseMotionAdapter(this));
		table.addMouseListener(ActionFactory.createTableMouseAdapter(this));

//		for (int i = 1; i < ROW_NUM; i++) {
//			for (int j = 1; j < COL_NUM; j++) {
//				var d = data[i][j];
//				d.panel.addMouseListener(ActionFactory.createCellMouseAdapter(this, d));
//			}
//		}
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
			table.setRowHeight((height - fix) / (ROW_NUM - 1));
			table.setRowHeight(0, HEAD_HEIGHT);
		} else {
			table.setRowHeight((originHeight - fix) / (ROW_NUM - 1));
			table.setRowHeight(0, HEAD_HEIGHT);
		}


		if (width + 10 >= originWidth) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		} else {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
	}

	public void hoverCell(int i, int j) {
//		System.out.println("Hover");

		// Clear first
		clearCellMark();

		// Set

		// Just one for now
		data[i][j].setMark(CellData.IS_HOVER);
		markedCells.add(new Pair<>(i, j));
	}

	public void selectCell(int i, int j) {
//		System.out.println("Select");

		// Clear first
		clearCellMark();

		// Set mark type
		isSelected = true;

		// Set

		// Just one for now
		data[i][j].setMark(CellData.IS_SELECTED);
		markedCells.add(new Pair<>(i, j));
	}

	public void clearCellMark() {
		isSelected = false;

		for (Pair<Integer> p : markedCells) {
			data[p.first][p.second].resetMark();
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
			return data[row][column].panel;
		}

		@Override
		public int getRowCount() {
			return ROW_NUM;
		}

		@Override
		public int getColumnCount() {
			return COL_NUM;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
}
