import javax.swing.*;
import java.awt.*;

public class ExcelPanel extends JPanel {
	private JTable table;
	private JPanel[][] panels;

	public ExcelPanel() {
		setLayout(new GridBagLayout());

		table = new JTable(8, 7);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		add(table, new GBC(0, 0).setFill(GBC.BOTH).setAnchor(GBC.CENTER));
		panels = new JPanel[8][7];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				var panel = new JPanel();
//				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panel.setBackground(new Color(20*i, 20*j, 10*(i+j)));
				table.add(panel);
				panels[i][j] = panel;
			}
		}
	}
}
