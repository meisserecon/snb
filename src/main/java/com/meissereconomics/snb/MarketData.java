package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class MarketData {
	
	public MarketData() throws FileNotFoundException, IOException, ParseException {
		ForexAndGold general = new ForexAndGold();
		DollarYield usdYield = new DollarYield();
		EuroYield euroYield = new EuroYield();
		BitcoinPrices price = new BitcoinPrices();
		
		
		HashSet<Long> days = new HashSet<Long>(general.getDays());
		days.retainAll(usdYield.getDays());
		days.retainAll(euroYield.getDays());
		days.retainAll(price.getDays());
		System.out.println("Found " + days.size() + " common data points.");
		Long[] all = days.toArray(new Long[0]);
		Arrays.sort(all);
		for (Long l: all) {
			Date date = new Date(l * 1000 * 60 * 60 * 24);
			System.out.println(date);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		MarketData data = new MarketData();
	}
}
