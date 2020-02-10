package ExcelPart;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MyTableRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof JPanel) {
			return (JPanel) value;
		}
		return null;
	}
}
