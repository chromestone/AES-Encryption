import java.security.SecureRandom;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* Cryptography class
 * Provides an encryption and decryption function given a certain key.
 * Implementation doesn't allow IV re-use... which is a good thing?
 * @author Derek Zhang
 */
public class Cryptography {

	public static class Output {

		public final byte[] iv;
		public final byte[] ciphertext;

		private Output(byte[] iv, byte[] ciphertext) {

			this.iv = iv;
			this.ciphertext = ciphertext;
		}
	}

	private static final String CIPHER_ALGORITHM = "AES";
	private static final String CIPHER_TRANSFORM = CIPHER_ALGORITHM + "/CBC/PKCS5Padding";
	private static final String CIPHER_PROVIDER = "SunJCE";
	private static final String RANDOM_ALGORITHM = "NativePRNG";
	private static final String RANDOM_PROVIDER = "SUN";

	private final SecretKeySpec secretKey;
	private final SecureRandom random;

	public Cryptography(byte[] key) throws GeneralSecurityException {

		secretKey = new SecretKeySpec(key, CIPHER_ALGORITHM);
		random = SecureRandom.getInstance(RANDOM_ALGORITHM, RANDOM_PROVIDER);
	}

	public Output encrypt(byte[] input) throws GeneralSecurityException {

		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM, CIPHER_PROVIDER);

		//generate the initialization vector
		byte[] iv = new byte[cipher.getBlockSize()];
		random.nextBytes(iv);
		IvParameterSpec ivParam = new IvParameterSpec(iv);

		// initialize the cipher to encrypt
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParam);

		// do the encryption
		byte[] ciphertext = cipher.doFinal(input);

		return new Output(iv, ciphertext);
	}

	public byte[] decrypt(byte[] iv, byte[] input) throws GeneralSecurityException {

		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM, CIPHER_PROVIDER);

		// recreate the initalization vector instance
		IvParameterSpec ivParam = new IvParameterSpec(iv);

		// initialize the cipher to decrypt
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParam);

		// do the decryption
		byte[] output = cipher.doFinal(input);

		return output;
	}
}