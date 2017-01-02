package algorithm;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import functions.Function;
import algorithm.Fish;

public class FSS {

	private Fish[] fishes;
	private double indStep;
	private double volStep;
	private double minIndStep;
	private double maxIndStep;
	private double wScale;
	private int numDimensions;
	private int iterations;
	private Function function;
	private Random randNum;
	double oldWeightSum;
	double[] bestSolutionFound;
	double bestSolutionFoundFitness;
	public double diversityLevel;
	
	private int currentStep;
	private double minInitPos;
	private double maxInitPos;
	private double inputMin;
	private double inputMax;
	private boolean maximize;
	
	
	public FSS(int numberFishes, double minIndStep, double maxIndStep, Function function, 
			int numDimensions, int iterations, double inputMin,
			double inputMax, double minInitPos, double maxInitPos, double wScale,
			boolean maximize) {
		this.minIndStep = minIndStep;
		this.maxIndStep = maxIndStep;
		this.indStep = maxIndStep;
		this.volStep = this.indStep * 2;
		this.function = function;
		this.randNum = new Random();
		this.numDimensions = numDimensions;
		this.inputMin = inputMin;
		this.inputMax = inputMax;
		this.minInitPos = minInitPos;
		this.maxInitPos = maxInitPos;
		this.wScale = wScale;
		this.maximize = maximize;
		this.bestSolutionFound = new double[this.numDimensions];
		if(this.maximize)
			this.bestSolutionFoundFitness = Double.POSITIVE_INFINITY;
		else
			this.bestSolutionFoundFitness = Double.NEGATIVE_INFINITY;
		this.initializeFishes(numberFishes);
		this.iterations = iterations;
		this.currentStep = 0;
	}
	
	
	private void initializeFishes(int numberFishes){
		this.fishes = new Fish[numberFishes];
		int i,j;
		double[] position = null;
		double inputRange;
		for(i=0;i<numberFishes;i++){
			//position = initialSolutions[i];
			/*STANDARD INIT*/
			position = new double[this.numDimensions];
			for(j=0;j<this.numDimensions;j++){
				inputRange = this.maxInitPos - this.minInitPos;
				position[j] = this.randNum.nextInt((int)inputRange) + this.randNum.nextDouble();
				position[j] += this.minInitPos;
			}
			/*STANDARD INIT*/	
			if(this.maximize)
				this.fishes[i] = new Fish(position,this.function.getFitness(position), this.wScale/2);
			else
				this.fishes[i] = new Fish(position,-this.function.getFitness(position), this.wScale/2);
		}
	}


	public void run() throws IOException{
		boolean keep_running = true;
		while(keep_running){
			keep_running = this.step();
		}
	}
	
	
	public boolean step() throws IOException{
		this.individualMoveStage();
		this.feedingStage();
		this.instintiveMoveStage();
		this.volitionalMoveStage();
		this.updateVolIndSteps();
		this.updateBestSolutionFoundAndDiversityLevel();
		this.currentStep++;
		if(this.currentStep<this.iterations){
			if(this.maximize == false){
				System.out.println(this.currentStep + "\t" + -this.bestSolutionFoundFitness + "\t" + this.diversityLevel);
			}
			else
				System.out.println(this.currentStep + "\t" + this.bestSolutionFoundFitness + "\t" + this.diversityLevel);
			return true;
		}else{
			for(int i=0;i<this.fishes.length;i++){
				this.fishes[i].setFitness(this.getFitness(this.fishes[i]));
				System.out.println(this.fishes[i].getFitness());				
			}
			for(int i=0;i<this.bestSolutionFound.length;i++){
				System.out.println(this.bestSolutionFound[i]);				
			}
			System.out.println();
			return false;
		}
	}
	
	
	private void updateVolIndSteps(){
		this.indStep -= (double)(this.maxIndStep-this.minIndStep)/iterations;
		this.volStep = this.indStep * 2;
	}
	
	
	private void individualMoveStage(){
		int i;
		for(i=0;i<this.fishes.length;i++){
			this.individualMove(this.fishes[i]);
		}
	}
	
	private double getFitness(Fish fish){
		if(this.maximize)
			return this.function.getFitness(fish.getPosition());
		else
			return -this.function.getFitness(fish.getPosition());
	}
	
	double[] oldPosition = null;
	int negCounter = 0;
	private void individualMove(Fish fish){
		fish.setFitness(this.getFitness(fish));
		oldPosition = fish.getPosition().clone();
		int i;
		double step;
		boolean randBool;
		
		//String dbgString = "It "+this.currentStep+" Steps "+fish.getId()+" - ";
		double avgStep = 0;
		
		for(i=0;i<fish.getPosition().length;i++){
			step = randNum.nextDouble()*this.indStep;
			randBool = randNum.nextBoolean();
			if(!randBool)
				step *= -1;
			//dbgString += step+",";
			this.setFishPositionIndex(fish, step+fish.getPosition()[i], i);
			fish.setLastPositionVariationWithIndex(step, i);
			avgStep += step;
		}
		avgStep /= fish.getPosition().length;
		double newFitness = this.getFitness(fish);
		double fitnessVariation = newFitness - fish.getFitness();
		if(fitnessVariation > 0){
			fish.setLastFitnessVariation(fitnessVariation);
			fish.setFitness(newFitness);
		}else{
			negCounter++;
			for(i=0;i<fish.getPosition().length;i++){
				fish.setPositionWithIndex(oldPosition[i],i);
				fish.setLastPositionVariationWithIndex(0, i);
				fish.setLastFitnessVariation(0);
			}
		}
	}
	
	
	Fish fittestFish = null;
	private double findMaxFitnessVariation(){
		fittestFish = this.fishes[0];
		int i;
		for(i=0;i<this.fishes.length;i++){
			if(fittestFish.getLastFitnessVariation()<this.fishes[i].getLastFitnessVariation()){
				fittestFish = this.fishes[i];
			}
		}
		if(fittestFish.getLastFitnessVariation() != 0)
			return fittestFish.getLastFitnessVariation();
		else
			return 0.0000001;
	}
	
	
	double maxFitnessVariation;
	private void feedingStage(){
		this.oldWeightSum = this.getWeightSum();
		maxFitnessVariation = this.findMaxFitnessVariation();
		int i;
		for(i=0;i<this.fishes.length;i++){
			this.feeding(this.fishes[i], maxFitnessVariation);
		}
	}
	
	
	private void feeding(Fish fish,double maxFitnessVariation){
		fish.setWeight(fish.getWeight()+(fish.getLastFitnessVariation()/maxFitnessVariation));
		if(fish.getWeight()>this.wScale){
			fish.setWeight(this.wScale);
		}
	}
	
	
	private void instintiveMoveStage(){
		int i;
		double[] iVector = this.calculateIVector();
		for(i=0;i<this.fishes.length;i++){
			this.instintiveMove(this.fishes[i], iVector);
		}
	}
	
	
	private void instintiveMove(Fish fish, double[] iVector){
		int i;
		double newPositionValue;
		//System.out.print("iV ");
		for(i=0;i<this.numDimensions;i++){
			//System.out.print(iVector[i]+",");
			newPositionValue = fish.getPosition()[i]+iVector[i];
			this.setFishPositionIndex(fish, newPositionValue, i);
		}
		//System.out.print("\n");
	}
	
	//MÃ©todo responsÃ¡vel por truncar o valor da posiÃ§Ã£o do peixe caso saia
	//dos limites da funÃ§Ã£o.
	
	private void setFishPositionIndex(Fish fish, double newPositionValue, int index){
		if(newPositionValue>=this.inputMin && newPositionValue<=this.inputMax){
			fish.setPositionWithIndex(newPositionValue,index);
		}else{
			if(newPositionValue<this.inputMin)
				fish.setPositionWithIndex(this.inputMin, index);
			if(newPositionValue>this.inputMax)
				fish.setPositionWithIndex(this.inputMax, index);
		}
	}
	
	
	double fitnessVariationSum;
	double[] iVector = null;
	private double[] calculateIVector(){
		fitnessVariationSum = this.calculateFitnessVariationSum();
		//System.out.println(fitnessVariationSum);
		iVector = new double[this.numDimensions];
		int i,j;
		for(i=0;i<this.fishes.length;i++){
			for(j=0;j<this.numDimensions;j++){
				iVector[j] += this.fishes[i].getLastPositionVariation()[j]*this.fishes[i].getLastFitnessVariation();
			}
		}
		for(i=0;i<this.numDimensions;i++){
			iVector[i] /= fitnessVariationSum;
		}
		return iVector;
	}
	
	
	private double calculateFitnessVariationSum(){
		double sum = 0;
		int i;
		for(i=0;i<this.fishes.length;i++){
			sum += this.fishes[i].getLastFitnessVariation();
		}
		if(sum!=0)
			return sum;
		else
			return 0.00000001;
	}
	
	private double[] calculateBarycentre(){
		double[] barycenter = new double[this.numDimensions];
		int i,j;
		for(i=0;i<this.fishes.length;i++){
			for(j=0;j<this.numDimensions;j++){
				barycenter[j] += this.fishes[i].getWeight()*this.fishes[i].getPosition()[j];
			}
		}
		double weightSum = this.getWeightSum();
		for(i=0;i<this.numDimensions;i++){
			barycenter[i]/=weightSum;
		}
		return barycenter;
	}
	
	private double getWeightSum(){
		double sum = 0;
		for(int i=0;i<this.fishes.length;i++){
			sum += this.fishes[i].getWeight();
		}
		return sum;
	}
	
	private void volitionalMoveStage(){
		double[] barycentre = this.calculateBarycentre();
		for(int i=0;i<this.fishes.length;i++){
			this.volitionalMove(this.fishes[i], barycentre);
		}
	}
	
	private void volitionalMove(Fish fish,double[] barycentre){
		double euclidianDistance = this.calculateEuclidianDistance(fish.getPosition(),barycentre);
		double newPositionValue;
		int i;
		double weightSum = this.getWeightSum();
		//System.out.println(weightSum+"-"+this.oldWeightSum+"="+(weightSum-this.oldWeightSum));
		if(weightSum-this.oldWeightSum<=0){
			for(i=0;i<this.numDimensions;i++){
				newPositionValue = fish.getPosition()[i]+(this.volStep*randNum.nextDouble()*((fish.getPosition()[i]-barycentre[i])/euclidianDistance));
				this.setFishPositionIndex(fish, newPositionValue, i);
			}
		}else{
			for(i=0;i<this.numDimensions;i++){
				newPositionValue = fish.getPosition()[i]-(this.volStep*randNum.nextDouble()*((fish.getPosition()[i]-barycentre[i])/euclidianDistance));
				this.setFishPositionIndex(fish, newPositionValue, i);
			}
		}
	}
	
	
	private double calculateEuclidianDistance(double[] v1, double[] v2){
		double ret = 0;
		int i;
		for(i=0;i<v1.length;i++){
			ret += Math.pow(v1[i]-v2[i],2);
		}
		if(ret!=0)
			return Math.sqrt(ret);
		else
			return 0.00000001;
	}
	
	
	public Fish[] getFishes(){
		return this.fishes;
	}
	
	
	public Function getFunction(){
		return this.function;
	}
	
	
	double[] a = null;
	double[] b = null;
	double[] sub = null;
	double prod;
	public double calculateDistanceTwoPoints(double[] iA, double[] iB){
		a = iA.clone();
		b = iB.clone();
		this.normalizeVector(a);
		this.normalizeVector(b);
		sub = this.calculateSubtraction(a, b);
		prod = this.calculateInternalProduct(sub, sub);
		return Math.sqrt(prod/this.numDimensions);
	}
	
	
	private void normalizeVector(double[] a){
		int i;
		for(i=0;i<a.length;i++){
			a[i] /= this.inputMax;
		}
	}
	
	
	private double[] calculateSubtraction(double[] a, double[] b){
		double[] ret = new double[a.length];
		int i;
		for(i=0;i<a.length;i++){
			ret[i] = a[i]-b[i];
		}
		return ret;
	}
	
	
	private double calculateInternalProduct(double[] a, double[] b){
		double sum = 0;
		int i;
		for(i=0;i<a.length;i++){
			sum += a[i]*b[i];
		}
		return sum;
	}

	
	public int getCurrentStep() {
		return currentStep;
	}

	
	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	
	public void setFishes(Fish[] fishes) {
		this.fishes = fishes;
	}
	
	private void updateBestSolutionFoundAndDiversityLevel(){
		this.diversityLevel = 0;
		int counter = 0;
		for(int i=0;i<this.fishes.length;i++){
			if(this.fishes[i].getFitness()>this.bestSolutionFoundFitness){
				this.bestSolutionFound = this.fishes[i].getPosition().clone();
				this.bestSolutionFoundFitness = this.fishes[i].getFitness();
			}
			for(int j=0;j<this.fishes.length;j++){
				if(i!=j){
					this.diversityLevel += this.calculateEuclidianDistance(this.fishes[i].getPosition(), 
							this.fishes[j].getPosition());
					counter++;
				}
			}
		}
		this.diversityLevel/=counter;
	}

	public double[] getBestSolutionFound() {
		return bestSolutionFound;
	}


	public void setBestSolutionFound(double[] bestSolutionFound) {
		this.bestSolutionFound = bestSolutionFound;
	}


	public double getBestSolutionFoundFitness() {
		return bestSolutionFoundFitness;
	}


	public void setBestSolutionFoundFitness(double bestSolutionFoundFitness) {
		this.bestSolutionFoundFitness = bestSolutionFoundFitness;
	}
}
