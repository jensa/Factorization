import java.math.BigInteger;
import java.util.ArrayList;

public class TestAlgorithm {

	public static void main(String[] args){
//		new PollardWat(new NaiveIsPrime ()),
		FactorizationAlgorithm[] algorithms = { new PollardBrent(new NaiveIsPrime())};//, new PollardBrent(), new QuadraticSieve() };
		ArrayList<TestAlgorithmScore> algorithmScore = new ArrayList<TestAlgorithmScore>();

		String[] testValues = {"80", "5000", "100000",
				"78732782", "91238", "61553553", "277367262",
				"360388848327140171205833745841", "182379123871923198",
//				"36038884832714017120583374584411861606378840490608984539340290142838458875879085141406049716103582183859419972236396635537081068318342030360034865620257521231674461014960871415155255611180161167314287707104892085605388693472830726263449752273518705705062597575741512283162442923684120796580363832366938838844219390457448001",
				"71923871923", "4294967291", "175891579187581657617", "20", "18273917839127381239183719", "1827391273891273981", "12839127381723917319237192", (new BigInteger("4711").multiply(new BigInteger("23"))).toString(), "675", "2",
				(new BigInteger("166126479444661271").multiply(new BigInteger("166696936547731"))).toString(), "11111111111111111111112", "2", "2", "4294967295"};

		Factorizer f = new Factorizer ();

		for (FactorizationAlgorithm algorithm : algorithms) {
//			System.out.println("Using algorithm " + algorithm.name());
			long startTime = System.currentTimeMillis();

			ArrayList<Result> results = f.doFactorization(testValues, algorithm);

			long endTime = System.currentTimeMillis();

			algorithmScore.add(new TestAlgorithmScore(algorithm, testValues, results, endTime-startTime));

//			for (Result r : results ) {
//				System.out.print(r.toString(true));
//			}

		}
		System.out.println("Algorithm               Works?     Count    Time ");
		for (TestAlgorithmScore score : algorithmScore) {
			System.out.println(score.toString());
		}
	}
}
