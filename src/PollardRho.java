import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


public class PollardRho implements FactorizationAlgorithm {
	private static boolean DEBUG = false;
	private final static BigInteger ZERO = new BigInteger("0");
	private final static BigInteger ONE  = new BigInteger("1");
	private final static BigInteger TWO  = new BigInteger("2");
	private final static Random random = new Random();
	
	private static long limit;
	private static long startTime;
	
	private ArrayList<BigInteger> factors;
	
	public Result factorize(BigInteger n, long time) {
		startTime = System.currentTimeMillis();
		limit = time;
		factors = new ArrayList<BigInteger>();
		factor(n, factors);
		return new Result(n, factors);	
	}

	@Override
	public String name() {
		return "PollardRho:       ";
	}
	
	private static void factor(BigInteger n, ArrayList<BigInteger> factors) {
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
		if (divisor == null){
			factors.clear();
			return;
		}
		if (DEBUG)
			System.out.println("divisor: " + divisor);
        factor(divisor, factors);
        factor(n.divide(divisor), factors);
	}
	
    public static BigInteger rho(BigInteger N) {
        if (N.mod(TWO).compareTo(ZERO) == 0)
        	return TWO;

        BigInteger d = null;
    	BigInteger c = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger y = x;
        
        while(d == null || (d.compareTo(ONE)) == 0){
        	if (timeLimitExceeded ())
        		return null;
            x = f (x, N, c); // f (x)
            y = f ( f(y, N, c), N, c);  // f ( f(y) )
            d = x.subtract(y).gcd (N); //
        } 

        return d;
    }
    
    private static BigInteger f (BigInteger x, BigInteger N, BigInteger c){
    	return x.pow(2).mod(N).add(c).mod(N);
    }
    
    
    
    private static boolean timeLimitExceeded (){
    	boolean isOverLimit = (System.currentTimeMillis() > (startTime+limit));
    	if (isOverLimit){
        	return true;
    	}
    	return false;
    }
}
