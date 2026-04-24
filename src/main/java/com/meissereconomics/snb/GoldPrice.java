package com.meissereconomics.snb;

import java.io.IOException;

public class GoldPrice extends ForexData {

	public GoldPrice(String datafolder, DollarExchangeRate usd) throws IOException {
		super(datafolder, "Gold", "XAUUSD1440.csv", usd);
	}

}
