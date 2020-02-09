import javax.swing.*;

public class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 400;
	private JPanel excelPanel;

	public MainFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initMenuBar();
		initExcel();
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		var fileMenu = new JMenu("文件(F)");
		fileMenu.setMnemonic('F');
		fileMenu.add(ActionFactory.createExitAction());

		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	private void initExcel() {
		excelPanel = new JPanel();

		add(excelPanel);
	}
}
