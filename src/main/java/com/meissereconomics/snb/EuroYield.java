package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class EuroYield {
	
	private HashMap<Long, Double> daily;
	
	private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public EuroYield() throws FileNotFoundException, IOException, ParseException {
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "euroyield.csv"));
		daily = new HashMap<>();
		for (int i=1; i<lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, ",\"");
			long time = FORMAT.parse(cells.nextToken()).getTime();
			cells.nextToken();
			double close = Double.parseDouble(cells.nextToken());
			daily.put(time / 60 / 60 / 24 / 1000, close);
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
