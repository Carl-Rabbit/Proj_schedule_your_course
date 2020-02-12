package components;

import tablepart.DataTable;
import helper.ActionFactory;
import helper.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 1440;
	private static final int DEFAULT_HEIGHT = 880;

	DataTable dataTable;
	CoursePanel coursePanel;
	ClassPanel classPanel;

	public MainFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(900, 600));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initMenuBar();
		initMainPanel();

		initActions();
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		final Dimension ITEM_DIME = new Dimension(200, 20);

		/* File menu */

		var fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		// OpenItem
		var openItem = new JMenuItem(ActionFactory.createOpenAction());
		openItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(openItem);

		// Import item
		var importItem = new JMenuItem(ActionFactory.createImportAction());
		importItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(importItem);

		// exitItem
		var exitItem = new JMenuItem(ActionFactory.createExitAction());
		exitItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(exitItem);

		// testItem
		var testItem = new JMenuItem(new AbstractAction("Debug") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(dataTable.data[0][0].label.getText());
			}
		});
		testItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(testItem);

		/* Help menu */

		var helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');

		// helpItem
		var helpItem = new JMenuItem("Help");
		helpItem.setMnemonic('H');
		helpItem.setPreferredSize(ITEM_DIME);
		helpMenu.add(helpItem);

		// aboutItem
		var aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic('A');
		aboutItem.setPreferredSize(ITEM_DIME);
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}

	private void initMainPanel() {

		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		// Table panel

		dataTable = new DataTable();
		mainPanel.add(dataTable.scrollPane, new GBC(0, 0, 1, 2).setAnchor(GBC.CENTER)
				.setFill(GBC.BOTH).setWeight(100, 100).setInsets(20,20,20,15));

		// Course part

		var courseLabel = new JLabel("Course" +
				"                                 ");
		courseLabel.setFont(new Font(null, Font.PLAIN, 15));
		mainPanel.add(courseLabel, new GBC(1, 0).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));

		coursePanel = new CoursePanel();
		mainPanel.add(coursePanel.scrollPanel, new GBC(1, 1, 1, 2).setAnchor(GBC.NORTH)
				.setFill(GBC.BOTH).setWeight(0, 100).setInsets(10));

		// Class part

		var classLabel = new JLabel("Class" +
				"                                                     ");
		classLabel.setFont(new Font(null, Font.PLAIN, 15));
		mainPanel.add(classLabel, new GBC(2, 0).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));

		classPanel = new ClassPanel();
		mainPanel.add(classPanel.scrollPanel, new GBC(2, 1, 1, 2).setAnchor(GBC.NORTH)
				.setFill(GBC.BOTH).setWeight(0, 100).setInsets(10));

		add(mainPanel);

		// Test
//		coursePanel.setBackground(Color.red);
//		classPanel.setBackground((Color.green));

	}

	private void initActions() {

		// init actions for resize operations

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dataTable.refreshSize();
			}
		});
	}

	// The function after setting visible
	public void reformat() {
		coursePanel.initCourse(5);
		classPanel.initClass(3);
		dataTable.restoreSize();
	}
}
