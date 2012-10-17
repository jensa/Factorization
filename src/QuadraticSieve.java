import java.math.BigInteger;
import java.util.ArrayList;

public class QuadraticSieve  implements FactorizationAlgorithm {

	@Override
	public Result factorize(BigInteger n, long time) {
		// TODO Auto-generated method stub
		return new Result(n, new ArrayList<BigInteger>());
	}

	@Override
	public String name() {
		return "Quadratic Sieve:  ";
	}
}
