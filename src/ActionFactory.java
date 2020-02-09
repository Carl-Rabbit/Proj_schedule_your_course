import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionFactory {

	public static AbstractAction createExitAction() {
		var exitAction = new AbstractAction("Exit"){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		exitAction.putValue(Action.MNEMONIC_KEY, (int)'X');
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit");
		return exitAction;
	}

	public static AbstractAction createImportAction() {
		var importAction = new AbstractAction("Import") {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		};
		importAction.putValue(Action.MNEMONIC_KEY, (int)'I');
		importAction.putValue(Action.SHORT_DESCRIPTION, "Import xml file");
		importAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
		return importAction;
	}

}
