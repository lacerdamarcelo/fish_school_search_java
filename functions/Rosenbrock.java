package functions;

import java.util.ArrayList;

public class Rosenbrock implements Function {
	
	@Override
	public double getFitness(double[] parameters) {
		double soma = 0;
		for(int i=0;i<parameters.length-1;i++){
			double x = parameters[i];
			double xp1 = parameters[i+1];
			soma += Math.pow(1-x, 2) + 100*Math.pow(xp1-Math.pow(x, 2), 2);
		}
		return soma;
	}

	@Override
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue,
			double dimMaxValue, int numDim) {
		return null;
	}

}
