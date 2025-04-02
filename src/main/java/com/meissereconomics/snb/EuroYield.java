package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.StringTokenizer;

public class EuroYield {
	
	private HashMap<Long, Double> daily;
	
	private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public EuroYield() throws FileNotFoundException, IOException, ParseException {
		String data = Files.readString(Path.of("data", "euroyield.csv"));
		StringTokenizer tok = new StringTokenizer(data, "\r\n");
		String header = tok.nextToken();
		daily = new HashMap<>();
		while (tok.hasMoreElements()) {
			String line = tok.nextToken();
			StringTokenizer cells = new StringTokenizer(line, ",\"");
			long time = FORMAT.parse(cells.nextToken()).getTime();
			cells.nextToken();
			double close = Double.parseDouble(cells.nextToken());
			daily.put(time / 60 / 60 / 24, close);
		}
		System.out.println("Found " + daily.size() + " entires");
	}
	
	public double getEntry(long day) {
		return daily.get(day);
	}

}
