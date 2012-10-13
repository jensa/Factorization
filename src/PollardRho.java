import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;


public class PollardRho implements FactorizationAlgorithm {
	private final static BigInteger ZERO = new BigInteger("0");
	private final static BigInteger ONE  = new BigInteger("1");
	private final static BigInteger TWO  = new BigInteger("2");
	private final static SecureRandom random = new SecureRandom();
	
	private ArrayList<BigInteger> factors;
	
	@Override
	public int[] factorNumber(int n) {
		return factorNumber(BigInteger.valueOf(n));
	}

	@Override
	public int[] factorNumber(BigInteger n) {
		factors = new ArrayList<BigInteger>();
		factor(n);
		
		int[] answer = new int[factors.size()];
		int i = 0;
		for (BigInteger factor : factors) {
			answer[i] = factor.intValue();
			i++;
		}
		
		return answer;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "PollardRho:       ";
	}
	
	private void factor(BigInteger n) {
		if (n.compareTo(ONE) == 0) {
			return;
		}
		if (n.isProbablePrime(20)) { factors.add(n); return; }
		
		BigInteger divisor = rho(n);
        factor(divisor);
        factor(n.divide(divisor));
	}
	
    public static BigInteger rho(BigInteger N) {
        BigInteger divisor;
        BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do {
            x  =  x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }
}
