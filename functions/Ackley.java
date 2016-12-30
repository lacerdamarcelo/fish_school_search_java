package functions;

import java.util.ArrayList;


public class Ackley implements Function{
	public double getFitness(double[] parameters){
		double sumA = 0;
		for(int i=0;i<parameters.length;i++){
			sumA += Math.pow(parameters[i], 2);
		}
		sumA /= parameters.length;
		sumA = -0.2*Math.sqrt(sumA);
		double sumB = 0;
		for(int i=0;i<parameters.length;i++){
			sumB += Math.cos(2*Math.PI*parameters[i]);
		}
		sumB /= parameters.length;
		sumA = -20*Math.exp(sumA);
		sumB = -Math.exp(sumB);
		return sumA+sumB+20+Math.E;
	}
	
	@Override
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue,
			double dimMaxValue, int numDim) {
		return null;
	}
}
