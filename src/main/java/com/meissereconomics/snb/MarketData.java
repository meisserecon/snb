package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class MarketData {
	
	public MarketData() throws FileNotFoundException, IOException, ParseException {
		PriceData general = new PriceData();
		DollarYield usdYield = new DollarYield();
		EuroYield euroYield = new EuroYield();
		BitcoinPrices price = new BitcoinPrices();
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		MarketData data = new MarketData();
	}
}
