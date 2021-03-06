import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;


public class Factorizer {
	private static boolean DEBUG = false;
	private static final long TIME = 19000;

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
    		NaiveIsPrime nip = new NaiveIsPrime ();
//			FactorizationAlgorithm algo = new PollardWat (nip);
    		FactorizationAlgorithm algo = new PollardBrent (nip);

			//Should change so it print to standard out as soon as one is finished. Otherwise kattis submissions will fail
			new Factorizer ().doFactorization (toFactor, algo);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public ArrayList<Result> doFactorization(String[] toFactor, FactorizationAlgorithm algo) {
		ArrayList<Result> results = new ArrayList<Result>();
		long endTime = System.currentTimeMillis()+TIME;
		StringBuilder sb = new StringBuilder ();
		
		for (int i=0;i<toFactor.length;i++){
			String numString = toFactor[i];

			BigInteger num = new BigInteger (numString);
			long timeLeft =  endTime - System.currentTimeMillis();
			long time = timeLeft / (toFactor.length - i);
			if (i < 35) {
				time += 250;
			}
			Result r = algo.factorize(num, time);
			
			if (r != null) {
				sb.append(r.toString());
			} else {
				sb.append("fail\n");
			}
			results.add(r);
		}
		System.out.print(sb.toString());
		
		return results;
	}
}
