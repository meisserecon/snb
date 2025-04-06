package com.meissereconomics.snb;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

public class MSCIData extends TimeSeries {
	
	// Source: https://www.investing.com/indices/msci-world-historical-data
	
	public MSCIData(ForexData usd) throws IOException {
		super("MSCI");
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "MSCI World Historical Data.csv"));
		for (int i=1; i<lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, "\"");
			LocalDate time = parseUS(cells.nextToken());
			cells.nextToken();
			if (cells.hasMoreTokens()) {
				double close = Double.parseDouble(cells.nextToken().replace(",", ""));
				insertWithConversion(time, close, usd);
			}
		}
	}

	private LocalDate parseUS(String nextToken) {
		return LocalDate.parse(nextToken, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}

}
