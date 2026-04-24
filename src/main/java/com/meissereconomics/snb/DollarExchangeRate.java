package com.meissereconomics.snb;

import java.io.IOException;

public class DollarExchangeRate extends ForexData {

	// USDCHF, EURCHF, XAUUSD ticker + "1440.csv"
	public DollarExchangeRate(String datafolder) throws IOException {
		super(datafolder, "Dollar", "USDCHF1440.csv", null);
	}

}
