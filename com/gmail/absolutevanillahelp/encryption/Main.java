// Main class

package com.gmail.absolutevanillahelp.encryption;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.List;

import cst0ne.encryption.ui.SimpleOptionPane;
import cst0ne.encryption.util.AESCipher;
import cst0ne.encryption.util.InputPackage;

public class Main {

	public static void main(String[] args) {

		new Main().run();
	}

	private void run() {

		String title = "cst0ne\'s Encrypter";
		List<Object> startChoices = Arrays.asList(new Object[]{"Exit", "Settings", "Decrypt", "Encrypt"});
		List<Object> endChoices = Arrays.asList(new Object[]{"Copy to Clipboard", "Continue","Exit"});

		int option = 0;
		boolean encrypting;
		InputPackage inputPackage = null;
		String text = "";
		byte[] passwordBytes = {};
		String output = "";
		while (true) {

			option = SimpleOptionPane.getButtonInput("Encrypt of Decrypt?", title, startChoices.toArray());
			if (option == startChoices.indexOf("Settings")) {
			
				
			}
			
			if (option == startChoices.indexOf("Encrypt")) {

				encrypting = true;
			}
			else if (option == startChoices.indexOf("Decrypt")) {

				encrypting = false;
			}
			else {

				return;
			}

			inputPackage = SimpleOptionPane.getTextInput("Enter text:          ", title);
			option = inputPackage.getNumber();
			if (option == SimpleOptionPane.CANCEL_OPTION) {

				continue;
			}
			else if (option == SimpleOptionPane.CLOSED_OPTION) {

				return;
			}
			text = inputPackage.getString();

			inputPackage = SimpleOptionPane.getTextInput("Enter password:          ", title);
			option = inputPackage.getNumber();
			if (option == SimpleOptionPane.CANCEL_OPTION) {

				continue;
			}
			else if (option == SimpleOptionPane.CLOSED_OPTION) {

				return;
			}
			passwordBytes = inputPackage.getString().getBytes();

			try {

				if (encrypting) {

					output = AESCipher.encryptString(text, passwordBytes);
				}
				else {

					output = AESCipher.decryptString(text, passwordBytes);
				}
			}
			catch (Exception e) {

				e.printStackTrace();
			}

			do {
				option = SimpleOptionPane.outputOptionedText(output, title, endChoices.toArray(), 5, 5);
				if (option == endChoices.indexOf("Copy to Clipboard")) {

					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection (output), null);
					SimpleOptionPane.showMessageDialog(null, "Successfully copied to clipboard.", title, SimpleOptionPane.INFORMATION_MESSAGE);
				}
				else if (option != endChoices.indexOf("Continue")) {

					return;
				}
			}
			while (option == endChoices.indexOf("Copy to Clipboard"));
		}
	}
}