import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;


public class Factorizer {
	private static boolean DEBUG = false;
	private static final long TIME = 15000;

	public static void main(String[] args){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			ArrayList<String> integers = new ArrayList<String> ();
			String numberString;
			while (in.ready()){
				numberString = in.readLine();
				integers.add(numberString);
			}

			String[] toFactor = new String[integers.size()];
			for (int i=0;i<integers.size();i++){
				toFactor[i] = integers.get(i);
			}
			
			//FactorizationAlgorithm pollards = new Pollards ();
			FactorizationAlgorithm pollardRho = new PollardRho ();

			//Should change so it print to standard out as soon as one is finished. Otherwse kattis submissions will fail
			new Factorizer ().doFactorization (toFactor, pollardRho);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public ArrayList<Result> doFactorization(String[] toFactor, FactorizationAlgorithm algo) {
		ArrayList<Result> results = new ArrayList<Result>();
		long timeLeft = TIME / toFactor.length;
		StringBuilder sb = new StringBuilder ();
		for (int i=0;i<toFactor.length;i++){
			String numString = toFactor[i];

			BigInteger num = new BigInteger (numString);
			Result r = algo.factorize(num, timeLeft);
			
			if (r != null)
				sb.append(r.toString());
			else
				sb.append("fail\n");
			results.add(r);
		}
		System.out.print(sb.toString());
		return results;
	}
}
