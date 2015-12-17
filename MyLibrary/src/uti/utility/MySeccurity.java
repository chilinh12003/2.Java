package uti.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/*import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;*/

public class MySeccurity
{
	private static String convertedToHex(byte[] data)
	{
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < data.length; i++)
		{
			int halfOfByte = (data[i] >>> 4) & 0x0F;
			int twoHalfBytes = 0;

			do
			{
				if ((0 <= halfOfByte) && (halfOfByte <= 9))
				{
					buf.append((char) ('0' + halfOfByte));
				}

				else
				{
					buf.append((char) ('a' + (halfOfByte - 10)));
				}

				halfOfByte = data[i] & 0x0F;

			}
			while (twoHalfBytes++ < 1);
		}
		return buf.toString();
	}

	public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5 = new byte[64];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5 = md.digest();
		return convertedToHex(md5);
	}

	public static Integer CreateRamdomNumber(int min, int max)
	{

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		Integer randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	/* ////////////////////////////////////////// */
	private final String characterEncoding = "UTF-8";
	private final String cipherTransformation = "AES/CBC/PKCS5Padding";
	private final String aesEncryptionAlgorithm = "AES";

	public byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
		cipherText = cipher.doFinal(cipherText);
		return cipherText;
	}

	public byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		plainText = cipher.doFinal(plainText);
		return plainText;
	}

	private byte[] getKeyBytes(String key) throws UnsupportedEncodingException
	{
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes = key.getBytes(characterEncoding);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	// / <summary>
	// / Encrypts plaintext using AES 128bit key and a Chain Block Cipher and
	// returns a base64 encoded string
	// / </summary>
	// / <param name="plainText">Plain text to encrypt</param>
	// / <param name="key">Secret key</param>
	// / <returns>Base64 encoded string</returns>
	public String encrypt(String plainText, String key) throws UnsupportedEncodingException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException
	{
		byte[] plainTextbytes = plainText.getBytes(characterEncoding);
		byte[] keyBytes = getKeyBytes(key);
		//return Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT);
		
		//String S = javax.xml.bind.DatatypeConverter.printBase64Binary(encrypt(plainTextbytes, keyBytes, keyBytes));
		
		BASE64Encoder mEncode = new BASE64Encoder();
		return mEncode.encode(encrypt(plainTextbytes, keyBytes, keyBytes));
	}

	// / <summary>
	// / Decrypts a base64 encoded string using the given key (AES 128bit key
	// and a Chain Block Cipher)
	// / </summary>
	// / <param name="encryptedText">Base64 Encoded String</param>
	// / <param name="key">Secret Key</param>
	// / <returns>Decrypted String</returns>
	public String decrypt(String encryptedText, String key) throws KeyException, GeneralSecurityException,
			GeneralSecurityException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, IOException
	{
		//byte[] cipheredBytes = Base64.decode(encryptedText, Base64.DEFAULT);
		BASE64Decoder mDecoder = new BASE64Decoder();
		
		byte[] cipheredBytes = mDecoder.decodeBuffer(encryptedText);
		byte[] keyBytes = getKeyBytes(key);
		return new String(decrypt(cipheredBytes, keyBytes, keyBytes), characterEncoding);
	}

}
