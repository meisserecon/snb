package com.meissereconomics.snb;

import java.time.LocalDate;

public class Component {
	
	private TimeSeries values;
	private double weight;

	public Component(TimeSeries component, double weight) {
		this.values = component;
		this.weight = weight;
	}

	public boolean hasValue(LocalDate current) {
		return values.hasValue(current);
	}

	public double getWeightedReturn(LocalDate previous, LocalDate current, double totalWeight) {
		return (values.getEntry(current) / values.getEntry(previous) - 1.0) * weight / totalWeight;
	}
	
	public String getLabel(double totWeight) {
		return values.getLabel() + " (" + ((int)(weight/totWeight*100)) + "%)";
	}

	public double getValue(LocalDate current) {
		return values.getEntry(current);
	}

}
