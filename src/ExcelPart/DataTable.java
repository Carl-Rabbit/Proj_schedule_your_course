package ExcelPart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataTable {
	private static final String[] ROW_NOTE
			= {"Time", "Mon", "Tue", "Wed", "Thu", "Fri"};
	private static final String[] COL_NOTE
			= {"Time", "1   2", "3   4", "5   6", "7   8", "9   10", "11"};
	private static final int ROW_NUM = 7;
	private static final int COL_NUM = 6;

	private static final Font NOTE_FONT = new Font(null, Font.PLAIN, 15);

	private static final int HEAD_HEIGHT = 30;

	public int originWidth;
	public int originHeight;

	public JScrollPane scrollPane;

	public JPanel[][] panels;
	public JLabel[][] labels;

	public JTable table;

	public DataTable() {
		table = new JTable(ROW_NUM, COL_NUM);
		initData();
		initTable();
		initHead();

		setActions();
		scrollPane = getScrollPane();
	}

	private void initHead() {

		// Delete the origin table head
		table.getTableHeader().setPreferredSize(new Dimension(0, 0));

		/* Set table note */

		for (int i = 0; i < COL_NUM; i++) {
			var label = labels[0][i];
			label.setFont(NOTE_FONT);
			label.setText(ROW_NOTE[i]);
			panels[0][i].setBackground(new Color(250, 250, 250));
		}

		for (int i = 1; i < ROW_NUM; i++) {
			var label = labels[i][0];
			label.setFont(NOTE_FONT);
			label.setText(COL_NOTE[i]);
			panels[i][0].setBackground(new Color(250, 250, 250));
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

	private void initData() {
		panels = new JPanel[ROW_NUM][COL_NUM];
		labels = new JLabel[ROW_NUM][COL_NUM];

		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COL_NUM; j++) {

				JPanel panel = new JPanel();
				panels[i][j] = panel;
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
				labels[i][j] = label;
			}
		}
	}

	private void setActions() {
		JPanel panel = panels[1][1];
		panel.setBackground(Color.GRAY);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());

				if(row > 0 && col > 0) {
					panels[row][col].setBackground(Color.RED);
					table.repaint();
				}

//				super.mouseClicked(e);
			}
		});
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
