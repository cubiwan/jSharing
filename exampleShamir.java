import java.math.BigInteger;
import JSharing.shamir.shamirKey;
import JSharing.shamir.shamir;
import JSharing.shamir.shamirException;

public class exampleShamir{	
	final static int n = 5; //number of generate shares
	final static int t = 3; //number of shares for solve the secret (t <= n)
	final static int numBits = 112; //number of bits of p
	
	public static void main(String args[]){
		String secret = "Secret Sharing";
		
		System.out.println("Secret = " + secret);
		
		//Create key
		shamirKey[] sk;
		BigInteger[] s;
		try{
			s = shamir.generateParameters(t, numBits, secret.getBytes());
			sk = shamir.generateKeys(n, t, numBits, s);
		}catch(shamirException sE){
			System.out.println("Error while generate shamir keys");			
			return;
		}
		
		shamirKey[] sk2 = new shamirKey[t];
		//select t keys
		sk2[0] = sk[1];
		sk2[1] = sk[3];
		sk2[2] = sk[4];
		
		//solve scheme, calculate parameter 0 (secret)
		byte[] des = shamir.calculateLagrange(sk2);
		
		String text = new String(des);		
		System.out.println("Secret = " + text);	
	}
}