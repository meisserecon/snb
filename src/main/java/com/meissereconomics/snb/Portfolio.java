package com.meissereconomics.snb;

import java.time.LocalDate;
import java.util.ArrayList;

public class Portfolio {
	
	public static final LocalDate MIN_DATE = LocalDate.parse("2014-01-01");
	public static final LocalDate MAX_DATE = LocalDate.parse("2025-12-31");
	
	private ArrayList<Component> components;
	private double totalWeight;
	private String label;
	
	public Portfolio(String label) {
		this.label = label;
		this.components = new ArrayList<>();
	}
	
	public void addComponent(TimeSeries component, double weight) {
		this.components.add(new Component(component, weight));
		this.totalWeight += weight;
	}
	
	public double getWeightFromPercent(double percent) {
		return percent * totalWeight / (100.0 - percent);
	}
	
	public TimeSeries constructPerformance(LocalDate current) {
		TimeSeries series = new TimeSeries(label);
		LocalDate previous = null;
		while (!current.equals(MAX_DATE)) {
			if (hasData(current)) {
				if (previous == null) {
					series.initYield(current);
				} else {
					series.insert(current, series.getEntry(previous) * calculatePerformance(previous, current));
				}
				previous = current;
			}
			current = current.plusDays(1);
		}
		return series;
	}
	
	private double calculatePerformance(LocalDate previous, LocalDate current) {
		double totalReturn = 0.0;
		for (Component component: components) {
			totalReturn += component.getWeightedReturn(previous, current, totalWeight);
		}
		return 1.0 + totalReturn;
	}

	private boolean hasData(LocalDate current) {
		for (Component component: components) {
			if (!component.hasValue(current)) {
				return false;
			}
		}
		return true;
	}

}
