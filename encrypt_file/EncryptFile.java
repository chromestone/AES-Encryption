import java.io.Console;
import java.util.*;

public class EncryptFile {
	
	public static void main(String[] args) throws Exception {

		// https://stackoverflow.com/questions/8138411/masking-password-input-from-the-console-java
		Console console = System.console();
        if (console == null) {

            System.out.println("Couldn't get Console instance");
            return;
        }

		String key = new String(console.readPassword("Enter key 1: "));
        String value = new String(console.readPassword("Enter key 2: "));

        byte[] decodedKey = Base64.getDecoder().decode(key);
        byte[] decodedVal = Base64.getDecoder().decode(value);

        Cryptography crypto = new Cryptography(decodedKey);
		byte[] k = crypto.decrypt(Arrays.copyOfRange(decodedVal, 0, 16),
			Arrays.copyOfRange(decodedVal, 16, decodedVal.length));

        FileInterface ui = new FileInterface(k);
        java.awt.EventQueue.invokeLater(() -> {
            ui.setVisible(true);
        });
	}
}