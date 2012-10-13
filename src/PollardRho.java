import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;


public class PollardRho implements FactorizationAlgorithm {
	private static boolean DEBUG = false;
	private final static BigInteger ZERO = new BigInteger("0");
	private final static BigInteger ONE  = new BigInteger("1");
	private final static BigInteger TWO  = new BigInteger("2");
	private final static SecureRandom random = new SecureRandom();
	
	private ArrayList<BigInteger> factors;
	
	public Result factorize(BigInteger n) {
		factors = new ArrayList<BigInteger>();
		factor(n);
		
		return new Result(n, factors);	
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "PollardRho:       ";
	}
	
	private void factor(BigInteger n) {
		if (DEBUG)
			System.out.println("factor number: " + n);
		
		if (n.compareTo(ONE) == 0) {
			if (DEBUG)
				System.out.println("comparded to one is 0");
			return;
		}
		if (n.isProbablePrime(20)) { 
			if (DEBUG)
				System.out.println("isprobprime: " + n);
			factors.add(n);
			return;
		}
		
		BigInteger divisor = rho(n);
		if (DEBUG)
			System.out.println("divisor: " + divisor);
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
