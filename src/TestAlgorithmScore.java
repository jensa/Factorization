import java.util.ArrayList;

public class TestAlgorithmScore {

	FactorizationAlgorithm algorithm;
	ArrayList<Integer> answers;
	long timeElapsed;
	String[] testValues;
		
	TestAlgorithmScore(FactorizationAlgorithm inputAlgorithm, String[] inputTestValues, ArrayList<Integer> inputAnswers, long inputTimeElapsed) {
		algorithm = inputAlgorithm;
		answers = inputAnswers;
		timeElapsed = inputTimeElapsed;
		testValues = inputTestValues;
	}
	
	public boolean checkSolution() {
		int sum = 0;
		int answerIndex = 0;
		boolean passed = true;
		int passedCount = 0;
		int testValue = Integer.parseInt(testValues[answerIndex]);
		
		if (answers == null) {
			return false;
		}
		
		for (int i=0;i<answers.size();i++){
			if (answers.get(i) > 0){
				sum += answers.get(i);
			} else {
				sum = 0;
				answerIndex++;
				if (sum != testValue) {
					passed = false;
				} else {
					passedCount += 1;
				}
			}
		}
		
		return passed;
	}
	
	public int passedCount() {
		int sum = 1;
		int answerIndex = 0;
		boolean passed = true;
		int passedCount = 0;
		int testValue = 0;
	
		if (answers == null) {
			return 0;
		}
		
		for (int i=0;i<answers.size();i++){
			testValue = Integer.parseInt(testValues[answerIndex]);

			if (answers.get(i) > 0){
				sum = sum * answers.get(i).intValue();
			} else {
				if (sum != testValue) {
					passed = false;
				} else {
					passedCount += 1;
				}
				sum = 1;
				answerIndex++;
			}
		}
		
		return passedCount;
	}
	
	public String toString() {
		
		return algorithm.name() + "      " + checkSolution() + "      " + passedCount() + "/"+testValues.length + "      "+ timeElapsed; 
	}
}
