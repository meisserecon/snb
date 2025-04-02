package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class BitcoinPrices {
	
	private HashMap<Long, Double> daily;
	
	public BitcoinPrices() throws FileNotFoundException, IOException {
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "bitcoin.csv"));
		daily = new HashMap<>();
		for (int i=1; i<lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, ",");
			long timestamp = Long.parseLong(cells.nextToken());
			double close = Double.parseDouble(cells.nextToken());
			daily.put(timestamp / 60 / 60 / 24, close);
		}
		System.out.println("Found " + daily.size() + " entires");
	}
	
	public double getEntry(long day) {
		return daily.get(day);
	}
	
	public Set<Long> getDays() {
		return daily.keySet();
	}

}
