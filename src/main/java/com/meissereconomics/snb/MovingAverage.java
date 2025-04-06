// Created on Jun 1, 2015 by Luzius Meisser

package com.meissereconomics.snb;

public class MovingAverage {

	private double memory;
	private double mean, var;
	private int samples;

	public MovingAverage() {
		this(0.95);
	}

	/**
	 * The weight of new samples is (1-memory) unless in the beginning.
	 * In the beginning, the weight of a new sample is 1/samples until
	 * 1/samples < (1-memory). So if memory is 0.95, the first 19 samples
	 * get a higher weight than 0.05.
	 */
	public MovingAverage(double memory) {
		this.memory = memory;
		this.mean = 0.0;
		this.var = 1.0;
		this.samples = 0;
	}

	public double getAverage() {
		return mean;
	}

	public double getVariance() {
		assert var >= 0;
		// Should this be divided by 'memory' to remove the bias from the estimator?
		// (like dividing by 'n-1' instead of 'n' with the statistical variance estimator) 
		return var; 
	}

	public void add(double point) {
		assert !Double.isNaN(point);
		this.samples++;
		double maxMemory = Math.min(memory, 1.0 - 1.0d / samples);
		double oldMean = this.mean;
		this.mean = maxMemory * oldMean + (1 - maxMemory) * point;
		double adjustment = this.mean - oldMean;
		double delta = point - this.mean;
		this.var = maxMemory * (this.var + adjustment * adjustment) + (1 - maxMemory) * delta * delta;
		assert !Double.isNaN(mean);
		assert !Double.isNaN(var);
	}

	public String normalize(double f) {
		return getAverage() / f + " (" + Math.sqrt(getVariance()) / f + ")";
	}
	
	public boolean hasSamples() {
		return samples > 0;
	}

	public String toString() {
		return getAverage() + " (" + Math.sqrt(getVariance()) + ")";
	}

}
