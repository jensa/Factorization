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
                int m = 80;
                BigInteger y = new BigInteger(N.bitLength(), random);
 
                BigInteger d = new BigInteger("1");
                int r = 1;
                BigInteger q = new BigInteger("1");
                BigInteger x = y;
 
                while(d.compareTo(ONE) == 0){
                        x = y;
                        int k = 0;
                        if (timeLimitExceeded ())
                                return null;
                        for(int i=0; i< r;i++){
                                y = (y.multiply(y).add(c)).mod(N);
                        }
                        while (k < r && d.compareTo(ONE) == 0) {
                                for (int i = 0; i< Math.min(m, r-k); i++) {
                                        y = (y.multiply(y).add(c)).mod(N);
                                        BigInteger absXY = x.subtract(y).abs();
                                        q = q.multiply(absXY).mod(N);
                                }
                                d = q.gcd(N);
                                k = k + m;
                        }
                        r = r*2;
                } 
 
                return d;
 
        }
 
        private static boolean timeLimitExceeded (){
                boolean isOverLimit = (System.currentTimeMillis() > (startTime+limit));
                if (isOverLimit){
                        return true;
                }
                return false;
        }
}