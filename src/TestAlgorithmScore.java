import java.util.ArrayList;

public class TestAlgorithmScore {

	FactorizationAlgorithm algorithm;
	ArrayList<Integer> answers;
	long timeElapsed;
		
	TestAlgorithmScore(FactorizationAlgorithm inputAlgorithm, ArrayList<Integer> inputAnswers, long inputTimeElapsed) {
		algorithm = inputAlgorithm;
		answers = inputAnswers;
		timeElapsed = inputTimeElapsed;
	}
	
	public String toString() {
		
		return algorithm.name() + "      " + timeElapsed; 
	}
}
