package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Fish implements Comparable{
	
	private double[] position;
	private double[] lastPositionVariation;
	private double weight;
	private double lastWeightVariation;
	private double lastFitnessVariation;
	private double fitness;
	private int id;
	
	private static int idCounter = 0;
	//True = Contracted, False = Expanded
	private boolean lastTimeVolContractedExpanded;
	
	public Fish(double[] position, double fitness, double initialWeight) {
		this.position = position;
		this.fitness = fitness;
		this.lastPositionVariation = new double[position.length];
		this.weight = initialWeight;
		this.id = this.idCounter;
		this.idCounter++;
	}
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double[] getPosition() {
		return position;
	}
	public void setPosition(double[] position) {
		this.position = position;
	}
	public void setPositionWithIndex(double newValue, int index){
		this.position[index] = newValue;
	}
	public double[] getLastPositionVariation() {
		return lastPositionVariation;
	}
	public void setLastPositionVariation(double[] lastPositionVariation) {
		this.lastPositionVariation = lastPositionVariation;
	}
	public void setLastPositionVariationWithIndex(double lastPositionVariation, int index) {
		this.lastPositionVariation[index] = lastPositionVariation;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getLastWeightVariation() {
		return lastWeightVariation;
	}
	public void setLastWeightVariation(double lastWeightVariation) {
		this.lastWeightVariation = lastWeightVariation;
	}

	public double getLastFitnessVariation() {
		return lastFitnessVariation;
	}

	public void setLastFitnessVariation(double lastFitnessVariation) {
		this.lastFitnessVariation = lastFitnessVariation;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Object arg0) {
		if(((Fish)arg0).weight>this.weight)
			return -1;
		else{
			if(((Fish)arg0).weight==this.weight)
				return 0;
			else
				return 1;
		}
	}

	public boolean isLastTimeVolContractedExpanded() {
		return lastTimeVolContractedExpanded;
	}

	public void setLastTimeVolContractedExpanded(
			boolean lastTimeVolContractedExpanded) {
		this.lastTimeVolContractedExpanded = lastTimeVolContractedExpanded;
	}
	
}
