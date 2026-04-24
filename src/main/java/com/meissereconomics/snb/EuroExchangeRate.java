package com.meissereconomics.snb;

import java.io.IOException;

public class EuroExchangeRate extends ForexData {

	public EuroExchangeRate(String datafolder) throws IOException {
		super(datafolder, "Euro", "EURCHF1440.csv", null);
	}

}
