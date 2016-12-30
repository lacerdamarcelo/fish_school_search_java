package functions;

import java.util.ArrayList;

public class Rastrigin implements Function {

	@Override
	public double getFitness(double[] parameters) {
		double resultado = 10 * parameters.length;
		for (int i = 0; i < parameters.length; i++){
			resultado += parameters[i]*parameters[i] - 10.0 * Math.cos(2.0 * Math.PI * parameters[i]);
		}
		return resultado;
	}

	@Override
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue, double dimMaxValue, int numDim) {
		double[] minimos;
		minimos = new double[] {-4.5,-3.5,-2.5,-1.5,-0.5,4.5,3.5,2.5,1.5,0.5};
		int h=0;
		double[][] solutionValues = new double[100][2];
		for(int i = 0; i < minimos.length; i++) {
			for(int j = 0; j < minimos.length; j++) {
				solutionValues[h][0] = minimos[i];
				solutionValues[h++][1] = minimos[j];
			}
		}
		ArrayList<double[]> ret = new ArrayList<double[]>();
		for(int i=0;i<solutionValues.length;i++){
			ret.add(solutionValues[i]);
		}
		return ret;
	}
}
