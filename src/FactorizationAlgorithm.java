import java.math.BigInteger;


public interface FactorizationAlgorithm {
	
	public Result factorize(BigInteger n, long timeLimit);
	
	public String name ();

}
