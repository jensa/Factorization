import java.math.BigInteger;
import java.util.ArrayList;

import checkForPrime.MillerRabin32;


public class Pollards implements FactorizationAlgorithm{

	private int OriginalN;
	
	@Override
	public int[] factorNumber(int n) {
		if (MillerRabin32.miller_rabin_32(n))
			return new int[0];
		OriginalN = n;
		ArrayList<Integer> ans = new ArrayList<Integer> ();
		getFactors (ans, n);
		int[] answers = new int[ans.size()];
		for (int i=0;i<ans.size();i++){
			answers[i] = ans.get(i);
		}
		return answers; 
	}
	
	private boolean getFactors (ArrayList<Integer> answers, int n){
		int a = getFactor (n);
		if (a < 0){
			answers.add(0, -1);
			return false;
		}
		int b = n /a;
		boolean aPrime = MillerRabin32.miller_rabin_32(a);
		boolean bPrime = MillerRabin32.miller_rabin_32(b);
		if (aPrime)
			answers.add(a);
		else{
			boolean gotSmallerFactors = getFactors (answers, a);
			if (!gotSmallerFactors)
				return false;
		}
		if (bPrime)
			answers.add(b);
		else{
			boolean gotSmallerFactors = getFactors (answers, b);
			if (!gotSmallerFactors)
				return false;
		}
		return true;
		
	}

	private int getFactor (int n){
		int x = 2; int y = 2; int d = 1;
		while (d == 1){
			x = f(x, n);
			y = f ( f(y, n), n);
			d = GCD (Math.abs(x-y), n);
		}
		if (d == n)
			return -1;
		return d;
	}

	private int f (int x, int n){
		return (x*x + 1) % n;
	}

	public int GCD(int a, int b) {
		if (b==0) return a;
		return GCD(b,a%b);
	}
	
	@Override
	public int[] factorNumber(BigInteger n) {
		// TODO Auto-generated method stub
		return null;
	}


}
