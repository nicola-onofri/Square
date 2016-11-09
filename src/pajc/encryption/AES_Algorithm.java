package pajc.encryption;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class AES_Algorithm {

 /**
  * Receives the message to hide and returns the original message encrypted
  * using the AES-256 block cipher.
  * 
  * @param password
  * @param key
  * @return the encrypted message
  * @throws Exception
  */
 public String encrypt(String password, String key) throws Exception {
  byte[] byteMessage = password.getBytes();

  Key secretKey = new SecretKeySpec(key.getBytes(), "AES");

  // Costruisco un cifrario che opera su AES
  Cipher c = Cipher.getInstance("AES");
  c.init(Cipher.ENCRYPT_MODE, secretKey);

  // Restituisce un nuovo cifrario, costituito dallo stesso messaggio in
  // Byte, che viene in seguito convertito in ASCII
  byte[] cipher = c.doFinal(byteMessage);
  return new BASE64Encoder().encode(cipher);
 }

 /**
  * Receives the encrypted message and returns the original message
  * decrypted.
  * 
  * @param encryptedPassword
  * @param key
  * @return the decrypted message
  * @throws Exception
  */
 public String decrypt(String encryptedPassword, String key) throws Exception {
  Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
  Cipher c = Cipher.getInstance("AES");
  c.init(Cipher.DECRYPT_MODE, secretKey);

  byte[] byteEncryptedPassword = new BASE64Decoder().decodeBuffer(encryptedPassword);
  byte[] cipher = c.doFinal(byteEncryptedPassword);

  return new BASE64Encoder().encode(cipher);
 }

 /**
  * Generates a key based on a SHA-1 hash function algorithm.
  * 
  * @param keyPass
  * @throws Exception
  */
 public static void generateSHAkey(String keyPass) throws Exception {
  MessageDigest sha = null;
  byte[] hash;

  hash = keyPass.getBytes("UTF-8");
  sha = MessageDigest.getInstance("SHA-1");
  hash = sha.digest(hash);
  hash = Arrays.copyOf(hash, 16); // use only first 128 bit
 }
}