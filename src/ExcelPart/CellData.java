package ExcelPart;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CellData {
	public JPanel panel;
	public JLabel label;

	public int x, y;
	public int state;
	public int count;

	private Color originColor;
	private Border originBorder;
	private Font originFont;

	public static final int PLAIN = 0;
	public static final int IS_HOVER = 1;
	public static final int IS_SELECTED = 2;
	public static final int HAVE_RELATION = 3;

	public CellData(int x, int y, JPanel panel, JLabel label) {
		this.x = x;
		this.y = y;
		this.panel = panel;
		this.label = label;
		count = 0;
		state = PLAIN;
	}

	public void setMark(int type) {
		state = type;

		if (originColor == null)
			originColor = panel.getBackground();
		if (originBorder == null)
			originBorder = panel.getBorder();
		if (originFont == null)
			originFont = label.getFont();

		switch (state) {
			case IS_SELECTED:
			case IS_HOVER:
				panel.setBackground(new Color(233, 245, 255));
				panel.setBorder(BorderFactory
						.createLineBorder(new Color(222, 238, 255), 6));
				break;
			case HAVE_RELATION:
				break;
		}

//		label.setFont(new Font(originFont.getFontName(),
//				Font.BOLD, originFont.getSize()));
	}

	public void resetMark() {
		state = PLAIN;

		panel.setBackground(originColor);
		panel.setBorder(originBorder);
		label.setFont(originFont);
	}
}
