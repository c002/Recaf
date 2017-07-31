package me.coley.recaf;

import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Program program = new Program();
		program.showGui();
		// Quick testing
		try {
			Thread.sleep(200);
			program.openFile(new java.io.File("test.jar"));
			program.selectClass(program.jarData.classes.get("me/coley/Program"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}