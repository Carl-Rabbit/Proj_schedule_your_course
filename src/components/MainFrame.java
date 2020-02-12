package components;

import datamanage.MyFileManager;
import helper.ActionFactory;
import helper.GBC;
import tablepart.DataTable;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 1440;
	private static final int DEFAULT_HEIGHT = 910;

	public DataTable dataTable;
	public SubjectPanel subjectPanel;
	public CoursePanel coursePanel;

	public JFileChooser chooser;
	public MyFileManager manager;

	public MainFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(900, 600));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initDataHandler();

		initMenuBar();
		initMainPanel();

		initActions();
	}

	private void initDataHandler() {
		// init file chooser
		chooser = new JFileChooser();

		// accept .xlsx file
		FileFilter filter = new FileNameExtensionFilter(
				"Excel 2007+ files", "xlsx");
		chooser.setFileFilter(filter);

		chooser.setCurrentDirectory(new File("."));

		// init file manager
		manager = new MyFileManager();
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		final Dimension ITEM_DIME = new Dimension(200, 20);

		/* File menu */

		var fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		// Open item
		var openItem = new JMenuItem(ActionFactory.createOpenAction());
		openItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(openItem);

		// Load item
		var loadItem = new JMenuItem(ActionFactory.createLoadAction(this));
		loadItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(loadItem);

		// Exit item
		var exitItem = new JMenuItem(ActionFactory.createExitAction());
		exitItem.setPreferredSize(ITEM_DIME);
		fileMenu.add(exitItem);

		// Test item
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

		var courseLabel = new JLabel("Subject" +
				"                                ");
		courseLabel.setFont(new Font(null, Font.PLAIN, 15));
		mainPanel.add(courseLabel, new GBC(1, 0).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));

		subjectPanel = new SubjectPanel();
		mainPanel.add(subjectPanel.scrollPanel, new GBC(1, 1, 1, 2).setAnchor(GBC.NORTH)
				.setFill(GBC.BOTH).setWeight(0, 100).setInsets(10));

		// Class part

		var classLabel = new JLabel("Course" +
				"                                                    ");
		classLabel.setFont(new Font(null, Font.PLAIN, 15));
		mainPanel.add(classLabel, new GBC(2, 0).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));

		coursePanel = new CoursePanel();
		mainPanel.add(coursePanel.scrollPanel, new GBC(2, 1, 1, 2).setAnchor(GBC.NORTH)
				.setFill(GBC.BOTH).setWeight(0, 100).setInsets(10));

		// Tip part

		var tipLabel = new JLabel("Please load data or open a schedule.");
		tipLabel.setFont(new Font(null, Font.PLAIN, 12));
		var tipPanel = new JPanel();
		tipPanel.setLayout(new BorderLayout());
		tipPanel.add(tipLabel);
		mainPanel.add(tipPanel, new GBC(0, 2).setAnchor(GBC.WEST)
				.setInsets(10, 14, 5, 0));


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

	// The function runs after setting visible
	public void reformat() {
		dataTable.restoreSize();
//		subjectPanel.initSubject(5);
	}

	// The function runs after reload data
	public void loadData() {

		manager.loadFile();
		var subjectList = manager.subjectList;
		subjectPanel.initSubject(subjectList.size(), this);

		for (int i = 0; i < subjectList.size(); i++) {
			var btPanel = subjectPanel.btPaneList.get(i);
			var subjectData = subjectList.get(i);
			btPanel.dataRefer = subjectData;
			btPanel.setTest(subjectData.id, subjectData.subName);
		}

		subjectPanel.repaint();
	}
}
