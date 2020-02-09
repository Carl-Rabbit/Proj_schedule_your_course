import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionFactory {

	public static AbstractAction createExitAction() {
		var exitAction = new AbstractAction("退出(X)"){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		exitAction.putValue(Action.MNEMONIC_KEY, (int)'X');
		return exitAction;
	}

}
