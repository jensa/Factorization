import java.math.BigInteger;
import java.util.ArrayList;

public class TestAlgorithm {

	public static void main(String[] args){
		FactorizationAlgorithm[] algorithms = { new Pollards(), new PollardRho(), new PollardBrent(), new QuadraticSieve() };
		ArrayList<TestAlgorithmScore> algorithmScore = new ArrayList<TestAlgorithmScore>();

		String[] testValues = {"80", "5000", "100000",
				"78732782", "91238", "61553553", "277367262",
				"36038884832714017120583374584411861606378840490608984539340290142838458875879085141406049716103582183859419972236396635537081068318342030360034865620257521231674461014960871415155255611180161167314287707104892085605388693472830726263449752273518705705062597575741512283162442923684120796580363832366938838844219390457448001",
		"71923871923"};

		NaiveIsPrime n = new NaiveIsPrime();

		Factorizer f = new Factorizer ();

		for (FactorizationAlgorithm algorithm : algorithms) {
			System.out.println("Using algorithm " + algorithm.name());
			long startTime = System.currentTimeMillis();

			ArrayList<Result> results = f.doFactorization(testValues, algorithm);

			long endTime = System.currentTimeMillis();

			//Save score
			algorithmScore.add(new TestAlgorithmScore(algorithm, testValues, results, endTime-startTime));

			if (results == null) {
				System.out.println(algorithm.name() + " returned null");

			} else {

				StringBuilder sb = new StringBuilder (results.size()*100);

				for (int i=0;i<results.size();i++){
					ArrayList<BigInteger> answers = results.get(i).bigFactors;
					
					sb.append("Factors for n="+results.get(i).number+":\n");
					for (BigInteger factor : answers) {

						sb.append(factor);
						sb.append("\n");

					}
				
					sb.append("-----------\n");
				}
				System.out.print((sb.toString()));
			}
		}

		System.out.println("Algorithm               Works?     Count    Time ");
		for (TestAlgorithmScore score : algorithmScore) {
			System.out.println(score.toString());
		}
	}
}
