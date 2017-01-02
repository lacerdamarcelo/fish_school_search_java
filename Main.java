import algorithm.FSS;
import functions.*;

public class Main{
	public static void main(String[] args){
		FSS fss = new FSS(30, 0.00064, 6.4, new Ackley(), 30, 5000, -32, 32, 16.0,
			32.0, 5000, false);
		try{
			fss.run();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}