import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 1200;
	private static final int DEFAULT_HEIGHT = 800;

	public MainFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initMenuBar();
		initMainPanel();
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		final Dimension ITEM_DIME = new Dimension(200, 20);

		/* File menu */

		var fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		// Import item
		var importItem = new JMenuItem(ActionFactory.createImportAction());
		importItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(importItem);

		// exitItem
		var exitItem = new JMenuItem(ActionFactory.createExitAction());
		exitItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(exitItem);

		/* About menu */
		var aboutMenu = new JMenu("About");
		aboutMenu.setMnemonic('A');

		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
	}

	private void initMainPanel() {

		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		// Excel panel

		var excelPanel = new ExcelPanel();
		mainPanel.add(excelPanel, new GBC(0, 0, 1, 2).setAnchor(GBC.CENTER)
				.setFill(GBC.BOTH).setWeight(100, 100).setInsets(20,20,20,15));

		// Course part

		var courseLabel = new JLabel("Course" +
				"                                 ");
		courseLabel.setFont(new Font(null, Font.PLAIN, 15));
		mainPanel.add(courseLabel, new GBC(1, 0).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));
		var coursePanel = new CoursePanel(5);
		mainPanel.add(coursePanel.getScrollPane(), new GBC(1, 1, 1, 2).setAnchor(GBC.NORTH)
				.setFill(GBC.BOTH).setWeight(0, 100).setInsets(10));

		// Class part

		var classLabel = new JLabel("Class" +
				"                                                     ");
		classLabel.setFont(new Font(null, Font.PLAIN, 15));
		mainPanel.add(classLabel, new GBC(2, 0).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));
		var classPanel = new ClassPanel(3);
		mainPanel.add(classPanel.getScrollPane(), new GBC(2, 1, 1, 2).setAnchor(GBC.NORTH)
				.setFill(GBC.BOTH).setWeight(0, 100).setInsets(10));

		add(mainPanel);

		// Test
		excelPanel.setBackground(Color.BLUE);
//		coursePanel.setBackground(Color.red);
//		classPanel.setBackground((Color.green));

	}
}