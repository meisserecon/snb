package com.meissereconomics.snb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;

public class EuroYield extends TimeSeries {

	public EuroYield(ForexData eur) throws FileNotFoundException, IOException {
		super("Euro Bonds");
		// From https://data.ecb.europa.eu/data/datasets/YC/YC.B.U2.EUR.4F.G_N_A.SV_C_YM.SR_5Y
		List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("data", "ECB Data Portal_20250405084013.csv"));
		LocalDate prevTime = null;
		double prevValue = 0.0;
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			StringTokenizer cells = new StringTokenizer(line, ",\"");
			LocalDate day = parse(cells.nextToken());
			if (eur.hasValue(day)) {
				cells.nextToken();
				double close = Double.parseDouble(cells.nextToken());
				if (prevTime == null) {
					initYield(day);
				} else {
					insertYieldWithConversion(prevTime, prevValue, day, close, eur);
				}
				prevTime = day;
				prevValue = close;
			}
		}
	}

}
