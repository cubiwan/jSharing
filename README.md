# jSharing
Java implemantation of Shamir's Secret Sharing and Blakley's scheme

## Shamir

Implements Shamir's Secret Sharing:

https://en.wikipedia.org/wiki/Secret_sharing#Shamir.27s_scheme

#### Example

```java
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
```

## Blakley

Implements Blakley's Secret Sharing:

https://en.wikipedia.org/wiki/Secret_sharing#Blakley.27s_scheme

You need use jlinalg v0.35 http://jlinalg.sourceforge.net/

#### Example

```java
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
```

