import java.math.BigInteger;
import java.util.ArrayList;


public class Result {
	public BigInteger number;
	public ArrayList<BigInteger> bigFactors;

	public Result(BigInteger inNumber, ArrayList<BigInteger> a) {
		bigFactors = a;
		number = inNumber;
	}

	public Result(int inNumber, int[] factors) {
		number = BigInteger.valueOf(inNumber);
		bigFactors = new ArrayList<BigInteger>();

		if (factors != null) {
			for (int i = 0; i < factors.length; i++) {
				bigFactors.add(BigInteger.valueOf(factors[i]));
			}
		}
	}

	public Result(BigInteger inNumber, int[] factors) {
		number = inNumber;
		bigFactors = new ArrayList<BigInteger>();
		if (factors != null) {
			for (int i = 0; i < factors.length-1; i++) {
				bigFactors.add(BigInteger.valueOf(factors[i]));
			}	
		}

	}
	
	public String toString() {
		return toString(false);
	}

	public String toString(boolean debug) {
		StringBuilder sb = new StringBuilder (bigFactors.size());
		BigInteger sum = new BigInteger("1");
		
		if (debug)
			sb.append("Factors for n="+number+":\n");
		
		if (bigFactors.size() == 0) {
			sb.append("fail\n\n");
		} else {
			for (BigInteger factor : bigFactors) {
				sum = sum.multiply(factor);
				sb.append(factor);
				sb.append("\n");
			}
			
			if (debug) {
				if (sum.compareTo(number) == 0) {
					
						sb.append("Passed!\n");
				} else {
					
					sb.append("FAILED!\n");
				}
			}
	
			if(debug)
				sb.append("-----------");
			sb.append("\n");
		}
		
		return (sb.toString());
	}
}

