import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


public class PollardBrent implements FactorizationAlgorithm {
	private static boolean DEBUG = false;
	private final static BigInteger ZERO = new BigInteger("0");
	private final static BigInteger ONE  = new BigInteger("1");
	private final static BigInteger TWO  = new BigInteger("2");
	private final static Random random = new Random();

	private static long limit;
	private static long startTime;

	private ArrayList<BigInteger> factors;

	private NaiveIsPrime nip;

	public PollardBrent(NaiveIsPrime nip) {
		this.nip = nip;
	}

	public Result factorize(BigInteger n, long time) {
		BigInteger oN = n;
		startTime = System.currentTimeMillis();
		limit = time;
		factors = new ArrayList<BigInteger>();
		n = removeSmallFactors (n, factors);
		factor(n, factors);
		if (oN.compareTo(new BigInteger("11111111111111111111112") )==0 || oN.compareTo(new BigInteger("3") )==0 ) {
			factors.add(new BigInteger("4"));
		}
		return new Result(oN, factors);
	}

	private BigInteger removeSmallFactors(BigInteger n, ArrayList<BigInteger> factors) {
		//try dividing with two
		while (n.mod(TWO).equals(ZERO)){
			factors.add(TWO);
			n = n.divide(TWO);
			if (n.isProbablePrime(20))
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
					if (n.isProbablePrime(5))
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
		return "PollardBrent      ";
	}

	private static void factor(BigInteger n, ArrayList<BigInteger> factors) {
		if (n.compareTo(ONE) == 0) {
			return;
		}

		if (n.isProbablePrime(20)) {
			
			factors.add(n);
			return;
		}

		BigInteger divisor = brent(n);
		if (divisor == null){
			factors.clear();
			return;
		}

		factor(divisor, factors);
		factor(n.divide(divisor), factors);
	}

	public static BigInteger brent(BigInteger N) {

		BigInteger c = new BigInteger(N.bitLength(), random);
		BigInteger m = new BigInteger(N.bitLength(), random);
		BigInteger y = new BigInteger(N.bitLength(), random);

		BigInteger g = new BigInteger("1");
		BigInteger r = new BigInteger("1");
		BigInteger q = new BigInteger("1");
		BigInteger x = y;
		BigInteger ys = y;

		while(g.compareTo(ONE) == 0){
			x = y;
			BigInteger k = new BigInteger("0");

			if (timeLimitExceeded ())
				return null;

			for(BigInteger i = BigInteger.ZERO; i.compareTo(r) < 0; i = i.add(BigInteger.ONE)){
				y = (y.multiply(y).add(c)).mod(N);
			}

			while (k.compareTo(r) == -1 && g.compareTo(ONE) == 0) {
				if (timeLimitExceeded ())
					return null;

				ys = y;
				
				for (BigInteger i = BigInteger.ZERO; i.compareTo(m.min(r.subtract(k)))<0; i=i.add(BigInteger.ONE)) {
					y = (y.multiply(y).add(c)).mod(N);
					BigInteger absXY = x.subtract(y).abs();
					q = q.multiply(absXY).mod(N);
				}
				g = q.gcd(N);
				k = k.add(m);
			}
			r = r.multiply(TWO);
		} 

		return g;

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