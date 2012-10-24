import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


public class PollardWat implements FactorizationAlgorithm {
	private static boolean DEBUG = false;
	private final static BigInteger ZERO = new BigInteger("0");
	private final static BigInteger ONE  = new BigInteger("1");
	private final static BigInteger TWO  = new BigInteger("2");
	private final static Random random = new Random();

	private static long limit;
	private static long startTime;

	private ArrayList<BigInteger> factors;

	private NaiveIsPrime nip;

	public PollardWat(NaiveIsPrime nip) {
		this.nip = nip;
	}

	public Result factorize(BigInteger n, long time) {
		BigInteger oN = n;
		startTime = System.currentTimeMillis();
		limit = time;
		factors = new ArrayList<BigInteger>();
		n = removeSmallFactors (n, factors);
		factor(n, factors);
		return new Result(oN, factors);  
	}

	private BigInteger removeSmallFactors(BigInteger n, ArrayList<BigInteger> factors) {
		//try dividing with two
		while (n.mod(TWO).equals(ZERO)){
			factors.add(TWO);
			n = n.divide(TWO);
			if (n.isProbablePrime(1))
				return n;
		}
		BigInteger[] primeList = nip.getPrimeList();
		//try small odd numbers
		boolean moreSmallFactors = true;
		boolean foundFactor = false;
		while (moreSmallFactors){
			for (BigInteger prime : primeList){
				if (n.mod(prime).equals(ZERO)){
					factors.add(prime);
					n = n.divide(prime);
					if (n.isProbablePrime(1))
						return n;
					foundFactor = true;
					break;
				}
			}
			if (!foundFactor){
				moreSmallFactors = false;
			}

			foundFactor = false;
		}
		return n;

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
		BigInteger d = null;
		BigInteger c = new BigInteger(N.bitLength(), random);
		BigInteger x  = new BigInteger(N.bitLength(), random);
		BigInteger y = x;
		BigInteger mult = x;

		int i = 0;
		while(d == null || (d.compareTo(ONE)) == 0){
			if (timeLimitExceeded ())
				return null;
			if (i == 20){
				d = mult.gcd (N);
				i = 0;
			} else{
				x = f (x, N, c); // f (x)
				y = f ( f(y, N, c), N, c);  // f ( f(y) )
				mult.multiply(x.subtract(y).abs());
				d = null;
				i++;
			}
			//
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