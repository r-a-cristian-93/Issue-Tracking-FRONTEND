import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;

public class Password {	
	public static String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] charPassword = password.toCharArray();
		byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		int iterations = 1000;
		int length = 16;
		
		KeySpec spec = new PBEKeySpec(charPassword, salt, iterations, length*8);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hashCode = factory.generateSecret(spec).getEncoded();
		return toHex(hashCode);
	}
	
	 private static String toHex(byte[] array) throws NoSuchAlgorithmException{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0){
			return String.format("%0"  +paddingLength + "d", 0) + hex;
		}
		else{
			return hex;
		}
	}
}
