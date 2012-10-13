import java.math.BigInteger;
import java.util.ArrayList;

public class TestAlgorithmScore {

	FactorizationAlgorithm algorithm;
	ArrayList<Result> results;
	long timeElapsed;
	String[] testValues;

	TestAlgorithmScore(FactorizationAlgorithm inputAlgorithm, String[] inputTestValues, ArrayList<Result> inputAnswers, long inputTimeElapsed) {
		algorithm = inputAlgorithm;
		results = inputAnswers;
		timeElapsed = inputTimeElapsed;
		testValues = inputTestValues;
	}

	public boolean checkSolution() {
		boolean passed = true;
		int passedCount = 0;

		for (Result r : results) {
			ArrayList<BigInteger> answers = r.bigFactors;
			BigInteger sum = new BigInteger("1");

			for (int i=0;i<answers.size();i++){
				sum = sum.multiply(answers.get(i));
			}
			
			if (sum.compareTo(r.number) == 0) {
				passedCount += 1;
			} else {
				passed = false;
			}
		}
		
		return passed;
	}

	public int passedCount() {
		boolean passed = true;
		int passedCount = 0;

		for (Result r : results) {
			ArrayList<BigInteger> answers = r.bigFactors;
			BigInteger sum = new BigInteger("1");

			for (int i=0;i<answers.size();i++){
				sum = sum.multiply(answers.get(i));
			}
			
			if (sum.compareTo(r.number)==0) {
				passedCount += 1;
			} else {
				passed = false;
			}
		}
		
		return passedCount;
	}

	public String toString() {

		return algorithm.name() + "      " + checkSolution() + "      " + passedCount() + "/"+testValues.length + "      "+ timeElapsed; 
	}
}
