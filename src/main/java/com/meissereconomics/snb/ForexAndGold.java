package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class ForexAndGold {

	private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private long[] timestamps;
	private double[] usd;
	private double[] eur;
	private double[] nasdaq;
	private double[] sp500;
	private double[] gold;

	public ForexAndGold() throws FileNotFoundException, IOException, ParseException {
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "prices.csv"));
		Iterator<String> iter = lines.iterator();
		// date,sp500 open,sp500 high,sp500 low,sp500 close,sp500 volume,sp500 high-low,
		// nasdaq open,nasdaq high,nasdaq low,nasdaq close,nasdaq volume,nasdaq high-low,
		// us_rates_%,CPI,usd_chf,eur_usd,GDP,silver open,silver high,silver low,silver close,silver volume,silver high-low,
		// oil open,oil high,oil low,oil close,oil volume,oil high-low,
		// platinum open,platinum high,platinum low,platinum close,platinum volume,platinum high-low,
		// palladium open,palladium high,palladium low,palladium close,palladium volume,palladium high-low,
		// gold open,gold high,gold low,gold close,gold volume
		String header = iter.next();
		timestamps = new long[3677];
		usd = new double[timestamps.length];
		eur = new double[timestamps.length];
		nasdaq = new double[timestamps.length];
		sp500 = new double[timestamps.length];
		gold = new double[timestamps.length];
		for (int i=0; i<timestamps.length; i++) {
			String line = iter.next();
			try {
				String[] cells = line.split(",");
				if (cells.length <= 45) continue;
				timestamps[i] = FORMAT.parse(cells[0]).getTime() / 60 / 60 / 24 / 1000;
				sp500[i] = Double.parseDouble(cells[3]);
				nasdaq[i] = Double.parseDouble(cells[9]);
				usd[i] = Double.parseDouble(cells[15]);
				eur[i] = Double.parseDouble(cells[16]);
				gold[i] = Double.parseDouble(cells[45]);
			} catch (NumberFormatException | ParseException e) {
				System.out.println("Ignoring " + line + " due to " + e.getMessage());
			}
		}
		System.out.println("Found " + timestamps.length + " entires");
	}

	private void skip(StringTokenizer tok, int number) {
		for (int i = 0; i < number; i++) {
			tok.nextElement();
		}
	}

	public Set<Long> getDays() {
		HashSet<Long> set = new HashSet<Long>();
		for (long t: timestamps) {
			set.add(t);
		}
		return set;
	}
	
}
