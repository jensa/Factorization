import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import checkForPrime.MillerRabin32;


public class PollardBrent implements FactorizationAlgorithm{

	public static BigInteger constants[]= new BigInteger[]{BigInteger.ONE, new BigInteger("2"), new BigInteger("3"), new BigInteger("5"), new BigInteger("7"), new BigInteger("11"), new BigInteger("13")};
	long start_time;

	@Override
	public int[] factorNumber(int n) {
		// TODO Auto-generated method stub
		return factorNumber(BigInteger.valueOf(n));
	}

	public int[] factorNumber(BigInteger n) {
		ArrayList<BigInteger> factors = factor(n);
		int[] result = new int[factors.size()];
		for (int i = 0; i < factors.size(); i++) {
			result[i] = factors.get(i).intValue();
			
		}
		return result;
	}

	//Is recursively called until only prime numbers remain. Prime numbers are returned
	private ArrayList<BigInteger> factor (BigInteger n) {
		ArrayList<BigInteger> factors = new ArrayList<BigInteger>();

		if(n.isProbablePrime(5)){
			factors.add(n);
		}
		else{
			BigInteger f1;
			BigInteger f2;

			//f1 = pollard(n);
			f1 = brent(n);

			if(f1.equals(BigInteger.ONE)){ 
				BigInteger ans[] = specialCase(n);

				if(ans == null){
					System.out.println("Null n: " + n);
				}
				else{
					BigInteger i = BigInteger.ZERO;
					ArrayList<BigInteger> answers = new ArrayList<BigInteger>();
					while(i.compareTo(ans[1]) < 0){
						answers = factor(ans[0]);
						i = i.add(BigInteger.ONE);
					}
					factors.addAll( answers );
				}
			}
			else{
				f2 = n.divide(f1);
				factors.addAll( factor(f1) );
				factors.addAll( factor(f2) );
			}
		}
		return factors;
	}

	/*
	 * Testar om n = a^k, ger isf ut {a,k} , annars null
	 * 
	 */
	private BigInteger[] specialCase(BigInteger n){

		for(int k= 2; k< 1000; k++){
			//System.out.println("testing k = "+k);
			if(new BigInteger("2").pow(k).compareTo(n) > 0){
				break;
			}
			BigInteger temp = newtonRaphson(n, k, new BigInteger("100"));
			if(temp != null){
				return new BigInteger[]{temp, new BigInteger(Integer.toString(k))};
			}
		}
		return null;
	}

	private BigInteger newtonRaphson(BigInteger n,int k, BigInteger start_x){
		BigDecimal n_dec = new BigDecimal(n);
		BigDecimal old_x = new BigDecimal(start_x);
		BigDecimal k_dec = new BigDecimal(Integer.toString(k));

		int counter = 0;
		while(true){
			if(System.nanoTime() - start_time > 1000000000L){
				return null;
			}
			BigDecimal new_x = old_x.subtract(old_x.divide(k_dec, RoundingMode.HALF_EVEN )).add(n_dec.divide(k_dec.multiply(old_x.pow(k-1)), RoundingMode.HALF_EVEN));

			//System.out.println(new_x);

			if(new_x.toBigInteger().pow(k).compareTo(n) == 0){
				return new_x.toBigInteger();
			}
			else if(counter > 1000){
				return null;
			}
			old_x = new_x.setScale(5, RoundingMode.DOWN);
			counter++;
		}
	}


	public BigInteger brent(BigInteger n) {
		for(int test=0;test< constants.length;test++){
			Random rnd = new Random();
			BigInteger two = new BigInteger("2");
			BigInteger y = new BigInteger(n.bitLength(), rnd);
			//BigInteger c = new BigInteger(n.bitLength(), rnd);
			BigInteger c = constants[test];
			BigInteger m = new BigInteger(n.bitLength(), rnd);

			BigInteger g = BigInteger.ONE;
			BigInteger r = BigInteger.ONE;
			BigInteger q = BigInteger.ONE;

			BigInteger x = y;
			BigInteger ys = y;

			if(n.mod(two).equals(BigInteger.ZERO)) return two;
			else{
				while(g.equals(BigInteger.ONE)){
					if(System.nanoTime() - start_time > 900000000L){
						return BigInteger.ONE;
					}
					x = y;
					for(BigInteger i = BigInteger.ZERO; i.compareTo(r) < 0; i = i.add(BigInteger.ONE)){
						//y = f(y, c, n);
						y = y.multiply(y).add(c).mod(n);
					}
					BigInteger k = BigInteger.ZERO;
					while(k.compareTo(r) < 0 && g.equals(BigInteger.ONE)){
						if(System.nanoTime() - start_time > 900000000L){
							return BigInteger.ONE;
						}
						ys = y;
						for(BigInteger i = BigInteger.ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i = i.add(BigInteger.ONE)){
							//y = f(y, c, n);
							y = y.multiply(y).add(c).mod(n);
							q = q.multiply(x.subtract(y).abs()).mod(n);
						}
						g = q.gcd(n);
						k = k.add(m);
					}
					r = r.multiply(two);
				}
				/*
                             if(g.equals(n)){
                                     //return BigInteger.ONE;
                                     while(true){
                                             if(System.nanoTime() - start_time > 900000000L){
                                                     return BigInteger.ONE;
                                             }
                                             //ys = f(ys, c, n);
                                             ys = ys.multiply(ys).add(c).mod(n);
                                             g = n.gcd(x.subtract(ys).abs());
                                             if(g.compareTo(BigInteger.ONE) > 0) break;
                                     }
                             }*/

			}
			if(g.compareTo(n) != 0)
				return g;
			//else return BigInteger.ONE;
		}
		return BigInteger.ONE;
	}


	@Override
	public String name() {
		return "Pollard-Brent:    ";
	}
}
