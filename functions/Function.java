package functions;

import java.util.ArrayList;

public interface Function {
	public double getFitness(double[] parameters);
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue, double dimMaxValue, int numDim);
}
