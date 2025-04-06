package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;
import java.util.StringTokenizer;

public class BitcoinPrice extends TimeSeries {
	
	public BitcoinPrice(DollarExchangeRate usd) throws FileNotFoundException, IOException {
		super("Bitcoin");
		// https://www.cryptodatadownload.com/cdd/Bitstamp_BTCUSD_d.csv
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "Bitstamp_BTCUSD_d.csv"));
		for (int i=2; i<lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, ",");
			long timestamp = Long.parseLong(cells.nextToken());
			String date = cells.nextToken();
			String pair = cells.nextToken();
			String open = cells.nextToken();
			String high = cells.nextToken();
			String low = cells.nextToken();
			double close = Double.parseDouble(cells.nextToken());
			insertWithConversion(parse(date), close, usd);
		}
	}
	
}
