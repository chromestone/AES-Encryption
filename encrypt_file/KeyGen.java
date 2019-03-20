import java.security.SecureRandom;

import java.util.Base64;

import javax.swing.JOptionPane;
import javax.crypto.*;
import javax.crypto.spec.*;

/* KeyGen class
 * This class generates a pair of 256 bit AES keys.
 * The output is in base64 with the format of:
 * a key
 * another key but it is encrypted using the former key
 * Note: I am not sure if it is secure to print these keys out...
 * @author Derek Zhang
 */
public class KeyGen {

	private static SecretKey generateKey() throws Exception {

		SecureRandom random = SecureRandom.getInstance("NativePRNG", "SUN");
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");

		keyGen.init(256, random);

		SecretKey secKey = keyGen.generateKey();
		return secKey;
	}

	public static void main(String[] args) throws Exception {

		SecretKey secondary = generateKey();
		SecretKey primary = generateKey();

		// gets the bytes representation
		byte[] encode2 = secondary.getEncoded();
		byte[] encode1 = primary.getEncoded();

		// use custom encrypt class to encrypt the primary key using the secondary key
		Cryptography crypto = new Cryptography(encode2);
		Cryptography.Output out = crypto.encrypt(encode1);

		int ivLen = out.iv.length;
		int ciLen = out.ciphertext.length;
		// create array big enough to combine iv and ciphertext
		byte[] encrypted = new byte[ivLen + ciLen];

		System.arraycopy(out.iv, 0, encrypted, 0, ivLen);
		System.arraycopy(out.ciphertext, 0, encrypted, ivLen, ciLen);

		System.out.println(Base64.getEncoder().encodeToString(encode2));
		System.out.println(Base64.getEncoder().encodeToString(encrypted));
	}
}