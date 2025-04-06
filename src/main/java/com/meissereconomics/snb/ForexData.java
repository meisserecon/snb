package com.meissereconomics.snb;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;

public class ForexData extends TimeSeries {
	
	protected ForexData(String label){
		super(label);
	}
	
	// Source: https://forexsb.com/historical-forex-data
	public ForexData(String label, String filename, DollarExchangeRate usd) throws IOException {
		super(label);
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", filename));
		for (int i=0; i<lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, ",\"\t");
			LocalDate time = parse(cells.nextToken());
			if (cells.hasMoreTokens()) {
				String open = cells.nextToken();
				String high = cells.nextToken();
				String low = cells.nextToken();
				double close = Double.parseDouble(cells.nextToken());
				if (usd == null) {
					insert(time, close);
				} else {
					insertWithConversion(time, close, usd);
				}
			}
		}
	}

}
