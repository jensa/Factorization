import java.math.BigInteger;
import java.util.ArrayList;


public class NaiveIsPrime {
	private int PRIME_CUTOFF = 51;
	boolean[] list; //false if prime or unchecked. true if not prime.
	BigInteger[] primeList;
	
	public NaiveIsPrime() {
		list = new boolean[PRIME_CUTOFF];
		ArrayList<BigInteger> primes = new ArrayList<BigInteger>();

		primes.add(new BigInteger(""+2));
		markNumbersBasedOn(2);
		
		for ( int i = 3; i < PRIME_CUTOFF; i++) {
			if (list[i]) {
				//Checked of not prime. Skipping.
			} else {
				//har inte kollat så den måste vara prim.
				primes.add(new BigInteger(""+i));
				markNumbersBasedOn(i);
			}
		}
		primeList = new BigInteger[primes.size()];
		primes.toArray(primeList);
	}
	
	public BigInteger[] getPrimeList() {
		return primeList;
	}
	
	private void markNumbersBasedOn(int i) {
		int original = i;
		i = i + original;
		while (i < PRIME_CUTOFF) {
			list[i] = true;
			i += original;
		}
	}
	
	public boolean isPrime(int n) {
		return !list[n];
	}

}
