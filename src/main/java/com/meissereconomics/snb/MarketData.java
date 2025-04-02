package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;

public class MarketData {
	
	
	
	public MarketData() throws FileNotFoundException, IOException, ParseException {
		PriceData general = new PriceData();
		DollarYield usdYield = new DollarYield();
		EuroYield euroYield = new EuroYield();
		BitcoinPrices price = new BitcoinPrices();
		
		HashSet<Long> days = new HashSet<Long>(general.getDays());
		days.retainAll(usdYield.getDays());
		days.retainAll(euroYield.getDays());
		days.retainAll(price.getDays());
		
		System.out.println("Found " + days.size() + " common data points.");
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		MarketData data = new MarketData();
	}
}
