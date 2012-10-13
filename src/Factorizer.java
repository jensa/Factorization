import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;


public class Factorizer {
	private static boolean DEBUG = true;
	
	public static void main(String[] args){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String numberString = in.readLine();
			
			ArrayList<String> integers = new ArrayList<String> ();
			
			while (!numberString.equals("")){
				integers.add(numberString);
				numberString = in.readLine();
			}
			
			String[] toFactor = new String[integers.size()];
			for (int i=0;i<integers.size();i++){
				toFactor[i] = integers.get(i);
			}
			FactorizationAlgorithm pollards = new Pollards ();
			FactorizationAlgorithm pollardRho = new PollardRho ();
			
			//Should change so it print to standard out as soon as one is finished. Otherwse kattis submissions will fail
			new Factorizer ().doFactorization (toFactor, pollardRho);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public ArrayList<Result> doFactorization(String[] toFactor, FactorizationAlgorithm algo) {
		ArrayList<Result> results = new ArrayList<Result>();
		ArrayList<Integer> answers = new ArrayList<Integer> ();
		
		for (int i=0;i<toFactor.length;i++){
			String numString = toFactor[i];
			
			int[] answer = null;
			try{
				int num = Integer.parseInt(numString);
				answer = algo.factorNumber(num);
				Result r = new Result(num, answer);
				System.out.print(r.toString());
				results.add(r);
				
			} catch (NumberFormatException e){
				BigInteger num = new BigInteger (numString);
				answer = algo.factorNumber(num);
				Result r = new Result(num, answer);
				System.out.print(r.toString());
				results.add(r);
			}
		}
		return results;
	}
}
