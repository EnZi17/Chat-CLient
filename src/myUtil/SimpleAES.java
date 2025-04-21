package myUtil;
 
 import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 import java.nio.charset.StandardCharsets;
 import java.util.Base64;
 
 public class SimpleAES {
	 private static final String SECRET_KEY = "D9fSg6Y4N7hZ1K2p"; // Khóa mạnh hơn 256-bit
	 private static final String IV_STRING = "1234567890123456"; // IV cố định, có thể thay đổi

	 public static String encrypt(String plainText) {
	     try {
	         SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	         IvParameterSpec ivSpec = new IvParameterSpec(IV_STRING.getBytes(StandardCharsets.UTF_8));  // IV cố định

	         cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
	         byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
	         return Base64.getEncoder().encodeToString(encryptedBytes);
	     } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	     }
	 }


     public static String decrypt(String encryptedText) {
         try {
             SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
             Cipher cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.DECRYPT_MODE, keySpec);
             byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
             byte[] decryptedBytes = cipher.doFinal(decodedBytes);
             return new String(decryptedBytes, StandardCharsets.UTF_8);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
 }