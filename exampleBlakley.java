import java.math.BigInteger;
import JSharing.blakley.blakley;

public class exampleBlakley{
	final static int n = 5; //number of generate keys
	final static int t = 3; //number of keys for solve the secret (t <= n)
	final static int bits = 512; //number of bits of keys
	
	static public void main(String args[]){
		BigInteger keys[][] = new BigInteger[n][];
		BigInteger keys2[][] = new BigInteger[t][];
		BigInteger pass[] = new BigInteger[t];
		
		String secret = "Secret Sharing";
		
		//Divide secret in parts (coordinates).  
		pass = blakley.divide(t, secret.getBytes());
		
		//Generate n keys
		for(int i = 0; i < n; i++)
			keys[i] = blakley.createdKey(pass, bits);

		//Select t keys
		for(int i = 0; i < t; i++)
			keys2[i] = keys[i];
		
		byte b[];
		//solve
		b = blakley.solutionKey(keys2);
		
		//Convert to String
		String text = new String(b);	
		System.out.println("secret: " + text);
	}
}