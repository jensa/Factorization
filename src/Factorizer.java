import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Factorizer {
	
	
	public static void main(String[] args){
		try{
		BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
		String numberString = in.readLine();
		ArrayList<Integer> ints = new ArrayList<Integer> ();
		while (numberString != null){
			int num = Integer.parseInt(numberString);
			ints.add(num);
			numberString = in.readLine();
		}
		int[] toFactor = new int[ints.size()];
		for (int i=0;i<ints.size();i++){
			toFactor[i] = ints.get(i);
		}
		FactorizationAlgorithm pollards = new Pollards ();
		new Factorizer ().doFactorization (toFactor, pollards);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void doFactorization(int[] toFactor, FactorizationAlgorithm algo) {
		ArrayList<Integer> answers = new ArrayList<Integer> ();
		for (int i=0;i<toFactor.length;i+=2){
			int[] answer = algo.factorNumber (toFactor[i]);
			for (int j : answer)
				answers.add(j);
		}
		StringBuilder sb = new StringBuilder (answers.size());
		for (int i=0;i<answers.size();i++){
			sb.append(answers.get(i));
			sb.append("\n");
		}
		System.out.print((sb.toString()));
	}
}
