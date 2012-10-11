import java.util.ArrayList;


public class Pollards implements FactorizationAlgorithm{

	private int OriginalN;
	
	
	
	@Override
	public int[] factorNumber(int en) {
		
		OriginalN = en;
		ArrayList<Integer> ans = new ArrayList<Integer> ();
		
		
		
		int[] answers = new int[ans.size()];
		for (int i=0;i<ans.size();i++){
			answers[i] = ans.get(i);
		}
		return answers; 
	}
	
	private int getFactor (int n){
		int x = 2; int y = 2; int d = 1;
		while (d == 1){
			x = f(x, n);
			y = f ( f(y, n), n);
			d = GCD (Math.abs(x-y), n);
		}
		if (d == n)
			return -1;
		return d;
	}

	private int f (int x, int n){
		return (x*x + 1) % n;
	}
	
	public int GCD(int a, int b) {
		   if (b==0) return a;
		   return GCD(b,a%b);
		}


}
