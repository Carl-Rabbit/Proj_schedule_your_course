package ExcelPart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataTable {
	private static final Object[] TABLE_HEAD
			= {" TIME", "   MON", "   TUE", "   WED", "   THU", "   FRI"};
	private static final int ROW_NUM = 7;
	private static final int COL_NUM = 6;

	private static final int HEAD_HEIGHT = 30;

	public int originWidth;
	public int originHeight;

	public JScrollPane scrollPane;

	public JPanel[][] panels;
	public JLabel[][] labels;

	public JTable table;

	public DataTable() {
		table = new JTable(ROW_NUM, COL_NUM);
		initTable();
		initHead();
		initData();
		scrollPane = getScrollPane();
	}

	private void initHead() {

		// Move the origin table head
		table.getTableHeader().setPreferredSize(new Dimension(0, 0));

		// Set Head

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

	private void initData() {
		panels = new JPanel[ROW_NUM][COL_NUM];
		labels = new JLabel[ROW_NUM][COL_NUM];

		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COL_NUM; j++) {
				var panel = new JPanel();
				panels[i][j] = panel;
				panel.setLayout(new BorderLayout());

				var label = new JLabel(String.format("label[%d][%d]", i, j), JLabel.CENTER);
				panel.add(label);
				labels[i][j] = label;
			}
		}
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

	private JScrollPane getScrollPane() {
		var panel = new JScrollPane(table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		panel.setBorder(BorderFactory.createEmptyBorder());
		return panel;
	}

	private class MyTableModel extends DefaultTableModel {
		@Override
		public String getColumnName(int column) {
//			return TABLE_HEAD[column].toString();
			return "";
		}

		@Override
		public Object getValueAt(int row, int column) {
			return panels[row][column];
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
