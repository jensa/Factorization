import java.util.ArrayList;


public class NaiveIsPrime {
	private int PRIME_CUTOFF = 10000;
	boolean[] list; //false if prime or unchecked. true if not prime.
	
	public NaiveIsPrime() {
		list = new boolean[PRIME_CUTOFF];
		ArrayList<Integer> primes = new ArrayList<Integer>();

		primes.add(2);
		markNumbersBasedOn(2);
		
		for ( int i = 3; i < PRIME_CUTOFF; i++) {
			if (list[i]) {
				//Checked of not prime. Skipping.
			} else {
				//har inte kollat så den måste vara prim.
				markNumbersBasedOn(i);
			}
		}
		
	}
	
	private void markNumbersBasedOn(int i) {
		i = i * 2;
		while (i < PRIME_CUTOFF) {
			list[i] = true;
			i *= 2;
		}
	}
	
	public boolean isPrime(int n) {
		return !list[n];
	}

}
