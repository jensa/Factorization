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
			
			while (numberString != null){
				integers.add(numberString);
				numberString = in.readLine();
			}
			
			String[] toFactor = new String[integers.size()];
			for (int i=0;i<integers.size();i++){
				toFactor[i] = integers.get(i);
			}
			FactorizationAlgorithm pollards = new Pollards ();
			
			new Factorizer ().doFactorization (toFactor, pollards);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public ArrayList<Result> doFactorization(String[] toFactor, FactorizationAlgorithm algo) {
		ArrayList<Result> results = new ArrayList<Result>();
		ArrayList<Integer> answers = new ArrayList<Integer> ();
		
		for (int i=0;i<toFactor.length;i+=2){
			String numString = toFactor[i];
			
			int[] answer = null;
			try{
				int num = Integer.parseInt(numString);
				answer = algo.factorNumber(num);
				results.add(new Result(num, answer));
				
			} catch (NumberFormatException e){
				BigInteger num = new BigInteger (numString);
				answer = algo.factorNumber(num);
				results.add(new Result(num, answer));
			}
		}
		return results;
	}
}
