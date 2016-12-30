package functions;

import java.util.ArrayList;


public class Schwefel12 implements Function{
	public double getFitness(double[] parameters){
		double sum = 0;
		double sum2 = 0;
		for(int i=0;i<parameters.length;i++){
			sum2 = 0;
			for(int j=0;j<i;j++){
				sum2 += parameters[j];
			}
			sum2 = Math.pow(sum2, 2);
			sum += sum2;
		}
		return sum2;
	}
	@Override
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue,
			double dimMaxValue, int numDim) {
		return null;
	}
}
