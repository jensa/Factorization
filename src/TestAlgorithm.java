import java.util.ArrayList;

public class TestAlgorithm {

	public static void main(String[] args){
		String[] testValues = {"80", "5000", "100000",
				"78732782", "91238", "61553553", "277367262"};
		Factorizer f = new Factorizer ();
		
		ArrayList<Integer> answers = f.doFactorization(testValues, new Pollards ());
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
