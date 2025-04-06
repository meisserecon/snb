package com.meissereconomics.snb;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;

public class TimeSeries {

	private static final long YEARS = 5;

	private String label;
	private HashMap<LocalDate, Double> daily;

	public TimeSeries(String label) {
		this.label = label;
		this.daily = new HashMap<>();
	}

	protected LocalDate parse(String nextToken) {
		return LocalDate.parse(nextToken.subSequence(0, 10));
	}

	protected void initYield(LocalDate time) {
		insert(time, 100.0);
	}

	protected void insert(LocalDate time, double value) {
		daily.put(time, value);
	}

	protected void insertYieldWithConversion(LocalDate prevTimeMS, double prevYield, LocalDate timeMS, double yield, ForexData forex) {
		assert timeMS.isAfter(prevTimeMS);
		double previousLevel = getEntry(prevTimeMS);
		double avgYield = (prevYield + yield) / 2;
		double interestIncome = (avgYield * prevTimeMS.until(timeMS, ChronoUnit.DAYS) / 365 + 100) / 100;
		double discount = (YEARS * prevYield + 100) / (YEARS * yield + 100);
		double forexChange = forex.getEntry(timeMS) / forex.getEntry(prevTimeMS);
		double newValue = previousLevel * interestIncome * forexChange * discount;
		daily.put(timeMS, newValue);
	}

	protected void insertWithConversion(LocalDate day, double close, ForexData eur) {
		if (eur.hasValue(day)) {
			double exchangeRate = eur.getEntry(day);
			daily.put(day, close * exchangeRate);
		}
	}

	public double getEntry(LocalDate day) {
		assert hasValue(day) : day + " not found";
		double value = daily.get(day);
		return value;
	}

	public boolean hasValue(LocalDate current) {
		return daily.containsKey(current);
	}

	public String getLabel() {
		return label;
	}
	
	private LocalDate[] getOrderedDates() {
		LocalDate[] dates = daily.keySet().toArray(new LocalDate[daily.size()]);
		Arrays.sort(dates);
		return dates;
	}
	
	public TimeSeries constructReturnVolatility(int timeFrame, int smoothing) {
		MovingAverage avg = new MovingAverage(1.0 - 1.0 / smoothing);
		TimeSeries vol = new TimeSeries(label + " " + timeFrame + "D volatility");
		LocalDate[] days = getOrderedDates();
		for (int i=timeFrame; i<days.length; i++) {
			double before = getEntry(days[i - timeFrame]);
			double after = getEntry(days[i]);
			double performance = after / before - 1.0;
			avg.add(performance);
			if (i - timeFrame > smoothing) {
				double var = avg.getVariance();
				int daysPassed = timeFrame; // days[i-timeFrame].until(days[i]).getDays(); // can be more than timeFrame due to bank holidays
				double annualizedVolatility = Math.sqrt(var) * Math.sqrt(365.0 / daysPassed);
				vol.insert(days[i], annualizedVolatility);
			}
		}
		return vol;
	}
	
	public void printStats() {
		System.out.println(getLabel() + " found " + daily.size() + " entries");
	}

	public LocalDate findFirstCommonDate(LocalDate first) {
		LocalDate myFirst = getOrderedDates()[0];
		return first.isBefore(myFirst) ? myFirst : first;
	}

	public void normalizeAround(LocalDate first) {
		double value = getEntry(first);
		for (LocalDate date: getOrderedDates()) {
			daily.put(date, daily.get(date) / value * 100.0);
		}
	}
	
}
