package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;

public class DollarYield extends TimeSeries {

	public DollarYield(DollarExchangeRate usd) throws FileNotFoundException, IOException {
		super("Dollar Bonds");
		// Source: https://fred.stlouisfed.org/series/DGS5
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "DGS5.csv"));
		LocalDate prevTime = null;
		double prevValue = 0.0;
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, ",\"");
			LocalDate day = parse(cells.nextToken());
			if (usd.hasValue(day)) {
				if (cells.hasMoreTokens()) {
					double close = Double.parseDouble(cells.nextToken());
					if (prevTime == null) {
						initYield(day);
					} else {
						insertYieldWithConversion(prevTime, prevValue, day, close, usd);
					}
					prevTime = day;
					prevValue = close;
				}
			}
		}
	}

}
