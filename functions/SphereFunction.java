package functions;

import java.util.ArrayList;

public class SphereFunction implements Function{
	public int maxEntities = 350;
	@Override
	public double getFitness(double[] parameters){
		double sum = 0;
		for(int i=0;i<parameters.length;i++)
			sum += Math.pow(parameters[i],2);
		return sum;
	}
	@Override
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue,
			double dimMaxValue, int numDim) {
		// TODO Auto-generated method stub
		return null;
	}

}
