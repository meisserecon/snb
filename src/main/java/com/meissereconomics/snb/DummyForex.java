package com.meissereconomics.snb;

import java.time.LocalDate;

public class DummyForex extends ForexData {

	public DummyForex() {
		super("Dummy");
	}
	
	@Override
	public double getEntry(LocalDate day) {
		return 1.0;
	}

	@Override
	public boolean hasValue(LocalDate current) {
		return true;
	}

}
