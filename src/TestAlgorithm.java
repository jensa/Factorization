import java.util.ArrayList;

public class TestAlgorithm {

	public static void main(String[] args){
		FactorizationAlgorithm[] algorithms = { new Pollards() };
		
		String[] testValues = {"80", "5000", "100000",
				"78732782", "91238", "61553553", "277367262"};
		Factorizer f = new Factorizer ();
		
		for (FactorizationAlgorithm algorithm : algorithms) {
			System.out.println("Using algorithm " + algorithm.name());
			ArrayList<Integer> answers = f.doFactorization(testValues, algorithm);
			StringBuilder sb = new StringBuilder (answers.size());
			int answerIndex = 0;
			
			boolean printedHeader = false;
			for (int i=0;i<answers.size();i++){
				if (!printedHeader){
					printedHeader = true;
					sb.append("Factors for n="+testValues[answerIndex]+":\n");
				}
				if (answers.get(i) > 0){
				sb.append(answers.get(i));
				sb.append("\n");
				} else{
					printedHeader = false;
					answerIndex++;
					sb.append("-----------\n");
				}
			}
			System.out.print((sb.toString()));
		}
	}
}
