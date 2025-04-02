package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BitcoinPrices {
	
	private HashMap<Long, Double> daily;
	
	public BitcoinPrices() throws FileNotFoundException, IOException {
		String data = Files.readString(Path.of("data", "bitcoin.csv"));
		StringTokenizer tok = new StringTokenizer(data, "\r\n");
		String header = tok.nextToken();
		daily = new HashMap<>();
		while (tok.hasMoreElements()) {
			String line = tok.nextToken();
			StringTokenizer cells = new StringTokenizer(line, ",");
			long timestamp = Long.parseLong(cells.nextToken().replace(".0", ""));
			String open = cells.nextToken();
			String high = cells.nextToken();
			String low = cells.nextToken();
			double close = Double.parseDouble(cells.nextToken());
			String date = cells.nextToken();
			daily.put(timestamp / 60 / 60 / 24, close);
		}
		System.out.println("Found " + daily.size() + " entires");
	}
	
	public double getEntry(long day) {
		return daily.get(day);
	}

}
