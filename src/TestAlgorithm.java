import java.util.ArrayList;

public class TestAlgorithm {

	public static void main(String[] args){
		FactorizationAlgorithm[] algorithms = { new Pollards(), new QuadraticSieve() };
		ArrayList<TestAlgorithmScore> algorithmScore = new ArrayList<TestAlgorithmScore>();
		
		String[] testValues = {"80", "5000", "100000",
				"78732782", "91238", "61553553", "277367262"};
		Factorizer f = new Factorizer ();
		
		for (FactorizationAlgorithm algorithm : algorithms) {
			System.out.println("Using algorithm " + algorithm.name());
			long startTime = System.currentTimeMillis();
			
			ArrayList<Integer> answers = f.doFactorization(testValues, algorithm);
			
			long endTime = System.currentTimeMillis();
			
			//Save score
			algorithmScore.add(new TestAlgorithmScore(algorithm, testValues, answers, endTime-startTime));
			
			if (answers == null) {
				System.out.println(algorithm.name() + " returned null");
				break;
			}
			
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
		
		System.out.println("Algorithm               Works?     Count    Time ");
		for (int i = 0; i < algorithmScore.size(); i++) {
			TestAlgorithmScore score = algorithmScore.get(i);
			System.out.println(score.toString());
		}
	}
}
