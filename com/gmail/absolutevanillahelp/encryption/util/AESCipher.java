// Utility class that encrypts and decrypts with AES.

package com.gmail.absolutevanillahelp.encryption.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public final class AESCipher {

	private static final String ENCRYPTION_ALGORITHM = "AES";
	private static final String DIGEST_ALGORITHM = "SHA-256";
	private static final String ENCRYPTION_SPEC = ENCRYPTION_ALGORITHM + "/CBC/PKCS5Padding";
	
	private AESCipher() {throw new AssertionError();}

	public static final byte[] encrypt(byte[] inputBytes, byte[] keyBytes) throws Exception {

		if (inputBytes == null || inputBytes.length == 0) {

			throw new IllegalArgumentException("Invalid input bytes.");
		}
		else if (keyBytes == null || keyBytes.length == 0) {
		
			throw new IllegalArgumentException("Invalid key.");
		}
		
		MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
		messageDigest.update(keyBytes);
		SecretKeySpec keySpec = new SecretKeySpec(messageDigest.digest(), ENCRYPTION_ALGORITHM);
		
		Cipher cipher = Cipher.getInstance(ENCRYPTION_SPEC);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new SecureRandom());
		byte[] ivBytes = cipher.getIV();
		byte[] encrypted = cipher.doFinal(inputBytes);

		byte[] outputBytes = Arrays.copyOf(ivBytes, ivBytes.length + encrypted.length);
		System.arraycopy(encrypted, 0, outputBytes, ivBytes.length, encrypted.length);

		return outputBytes;
	}

	public static final String encryptString(String stringInput, byte[] keyBytes) throws Exception {

		return DatatypeConverter.printBase64Binary(encrypt(stringInput.getBytes(), keyBytes));
	}

	public static final byte[] decrypt(byte[] inputBytes, byte[] keyBytes) throws Exception {

		if (inputBytes == null || inputBytes.length <= 16) {

			throw new IllegalArgumentException("Invalid input bytes.");
		}
		else if (keyBytes == null || keyBytes.length == 0) {

			throw new IllegalArgumentException("Invalid key.");
		}

		MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
		messageDigest.update(keyBytes);
		SecretKeySpec keySpec = new SecretKeySpec(messageDigest.digest(), ENCRYPTION_ALGORITHM);
		
		IvParameterSpec ivSpec = new IvParameterSpec(Arrays.copyOf(inputBytes, 16));

		Cipher cipher = Cipher.getInstance(ENCRYPTION_SPEC);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		return cipher.doFinal(Arrays.copyOfRange(inputBytes, 16, inputBytes.length));
	}

	public static final String decryptString(String stringInput, byte[] keyBytes) throws Exception {

		return new String(decrypt(DatatypeConverter.parseBase64Binary(stringInput), keyBytes));
	}
}
