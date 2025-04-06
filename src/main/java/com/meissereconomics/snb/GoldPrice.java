package com.meissereconomics.snb;

import java.io.IOException;
import java.text.ParseException;

public class GoldPrice extends ForexData {

	public GoldPrice(DollarExchangeRate usd) throws IOException {
		super("Gold", "XAUUSD1440.csv", usd);
	}

}
