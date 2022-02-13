package com.blip.matchtimeconverter.models;

public class MatchTime {
	/**
	 * Short form
	 */
	private String shortForm;
	/**
	 * Long form
	 */
	private String longForm;
	/**
	 * Start time
	 */
	private int fromTime;
	/**
	 * End time
	 */
	private int toTime;
	/**
	 * If game is running
	 */
	private boolean activeTime;
	/**
	 * If should have additional time
	 */
	private boolean additionalTime;
	
	public MatchTime(String shortForm, String longForm, int fromTime, int toTime, boolean activeTime, boolean additionalTime) {
		super();
		this.shortForm = shortForm;
		this.longForm = longForm;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.activeTime = activeTime;
		this.additionalTime = additionalTime;
	}
	
	public String getShortForm() {
		return shortForm;
	}
	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}
	public String getLongForm() {
		return longForm;
	}
	public void setLongForm(String longForm) {
		this.longForm = longForm;
	}
	public boolean isAdditionalTime() {
		return additionalTime;
	}
	public void setAdditionalTime(boolean additionalTime) {
		this.additionalTime = additionalTime;
	}

	public boolean isActiveTime() {
		return activeTime;
	}

	public void setActiveTime(boolean activeTime) {
		this.activeTime = activeTime;
	}

	public int getFromTime() {
		return fromTime;
	}

	public void setFromTime(int fromTime) {
		this.fromTime = fromTime;
	}

	public int getToTime() {
		return toTime;
	}

	public void setToTime(int toTime) {
		this.toTime = toTime;
	}	
}
