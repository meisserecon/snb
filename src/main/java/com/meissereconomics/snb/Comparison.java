package com.meissereconomics.snb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comparison {
	
	private LocalDate start;
	private DollarExchangeRate dollar;
	private EuroExchangeRate euro;
	private EuroYield eurobond;
	private DollarYield dollarbond;
	private MSCIData msci;
	private GoldPrice gold;
	private BitcoinPrice bitcoin;
	private TimeSeries snb, snbWithBitcoin;
	
	public Comparison(String datafolder, LocalDate start) throws IOException {
		this.start = start;
		this.dollar = new DollarExchangeRate(datafolder);
		this.euro = new EuroExchangeRate(datafolder);
		this.dollarbond = new DollarYield(datafolder, dollar);
		this.eurobond = new EuroYield(datafolder, euro);
		this.gold = new GoldPrice(datafolder, dollar);
		this.msci = new MSCIData(datafolder, dollar);
		this.bitcoin = new BitcoinPrice(datafolder, dollar);
		Portfolio p = new Portfolio("SNB");
		p.addComponent(gold, 78);
		p.addComponent(msci, 188.5);
		p.addComponent(dollarbond, 282.75);
		p.addComponent(eurobond, 282.75);
		this.snb = p.constructPerformance(start);
		p.addComponent(bitcoin, p.getWeightFromPercent(1.0));
		this.snbWithBitcoin = p.constructPerformance(start);
	}
	
	public List<TimeSeries> addVolatilities(TimeSeries bitcoin) {
		TimeSeries bitcoinVola1 = bitcoin.constructReturnVolatility(1, 15);
		TimeSeries bitcoinVola5 = bitcoin.constructReturnVolatility(3, 15);
		TimeSeries bitcoinVola30 = bitcoin.constructReturnVolatility(5, 15);
		TimeSeries bitcoinVola90 = bitcoin.constructReturnVolatility(5, 10);
		TimeSeries bitcoinVola180 = bitcoin.constructReturnVolatility(5, 14);
		TimeSeries bitcoinVola360 = bitcoin.constructReturnVolatility(5, 16);
		return Arrays.asList(bitcoin, bitcoinVola1, bitcoinVola5, bitcoinVola30, bitcoinVola90, bitcoinVola180, bitcoinVola360);
	}
	
	private void print(PrintStream out, List<TimeSeries> series, TimeSeries... toNormalize) {
		LocalDate first = Portfolio.MIN_DATE;
		for (TimeSeries s: series) {
			first = s.findFirstCommonDate(first);
		}
		for (TimeSeries tn: toNormalize) {
			tn.normalizeAround(first);
		}
		out.print("Date");
		for (TimeSeries t: series) {
			out.print("\t" + t.getLabel());
		}
		out.println();
		LocalDate current = start;
		while (!current.equals(Portfolio.MAX_DATE)) {
			if (hasData(current, series)) {
				out.print(current);
				for (TimeSeries t: series) {
					out.print("\t" + t.getEntry(current));
				}
				out.println();
			}
			current = current.plusDays(1);
		}
	}
	
	private boolean hasData(LocalDate current, List<TimeSeries> series) {
		for (TimeSeries component: series) {
			if (!component.hasValue(current)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException, ParseException {
		int year = 2026;
		Comparison comp = new Comparison("data" + year, LocalDate.parse("2012-06-01"));
		ArrayList<TimeSeries> all = new ArrayList<>();
		all.addAll(comp.addVolatilities(comp.snb));
		all.addAll(comp.addVolatilities(comp.snbWithBitcoin));
		try (PrintStream out = new PrintStream(new File("result", "output" + year + ".txt"))){
			comp.print(out, all, comp.snb, comp.snbWithBitcoin);
		}
	}

}
