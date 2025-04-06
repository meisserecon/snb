package com.meissereconomics.snb;

import java.io.IOException;
import java.text.ParseException;

public class EuroExchangeRate extends ForexData {

	public EuroExchangeRate() throws IOException {
		super("Euro", "EURCHF1440.csv", null);
	}

}
