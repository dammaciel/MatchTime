package com.blip.matchtimeconverter.models;

public class FootballMatch extends Match {

	/**
	 * Default contructor that will add the period of a football match
	 */
	public FootballMatch() {
		this.setRegEx("\\[([A-Z0-9]*)\\]\\s([0-9]{1,3})\\:([0-9]{2}.[0-9]{3})$");
		this.addPeriod(new MatchTime("PM", "PRE_MATCH", 0, 0, false, false));
		this.addPeriod(new MatchTime("H1", "FIRST_HALF", 0, 45, true, true));
		this.addPeriod(new MatchTime("HT", "HALF_TIME", 45, 45, false, false));
		this.addPeriod(new MatchTime("H2", "SECOND_HALF", 45, 90, true, true));
		this.addPeriod(new MatchTime("FT", "FULL_TIME", 90, 90, false, true));
	}
}
