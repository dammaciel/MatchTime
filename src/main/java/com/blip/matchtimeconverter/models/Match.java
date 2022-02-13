package com.blip.matchtimeconverter.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Match {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Match.class);
	/**
	 * Periods of a Football match
	 */
	private ArrayList<MatchTime> periods= new ArrayList<>();
	/**
	 * Regular Expression used to parse the input value
	 */
	private String regEx;
	
	
	
	public ArrayList<MatchTime> getPeriods() {
		return periods;
	}
	public void setPeriods(ArrayList<MatchTime> periods) {
		this.periods = periods;
	}
	public String getRegEx() {
		return regEx;
	}
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}
	
	/**
	 * Add a Match period to the match periods 
	 * @param period
	 */
	public void addPeriod(MatchTime period) {
		periods.add(period);
	}
	
	/**
	 * Build the Match time in another format
	 * @param time time in one format
	 * @return Match time in another format
	 */
	public String buildMatchTimeSentence(String time) {
		try {			
			ArrayList<String> fields = parseMatchTime(time); 
			int minutes = Integer.parseInt(fields.get(1));
			int seconds = roundSeconds(Float.parseFloat(fields.get(2)));
			if(seconds<0) {
				logger.info("The time provided is not compatible with the Period. Maximum of 60.000 seconds");
				return "INVALID";
			}else if(seconds==60) {
				// e.g. if time is 60:59.750 convert to 61:00 
				minutes++;
				seconds = 0;
			}
			MatchTime period = getMatchTime(fields.get(0));
			StringBuilder response = new StringBuilder();
			//Short form: H1, H2
			if(period.isActiveTime() && period.isAdditionalTime()) {
				if(!validActiveMinutes(period.getFromTime(),minutes)) {
					logger.info("The time provided is not compatible with the Period.");
					return "INVALID";
				}
				int max_minutes = period.getToTime();
				// [H2] 91:05.400 -> 90:00 +01:05
				if(minutes>max_minutes) {
					response.append(String.format("%02d",max_minutes) + ":00 +" + String.format("%02d",minutes-max_minutes) + ":" + String.format("%02d",seconds));
				// [H2] 90:05.400 -> 90:00 +00:05
				}else if(minutes==max_minutes){
					response.append(String.format("%02d",max_minutes) + ":00");
					if(Float.parseFloat(fields.get(2))>0) {
						response.append(" +00:" + String.format("%02d",seconds));
					}
					// [H2] 50:05.400 -> 50:05
				}else {
					response.append(String.format("%02d",minutes) + ":" + String.format("%02d",seconds));
				}
				response.append(" - " + period.getLongForm());
				return response.toString();
			//Short form: PM, HT, FT	
			}else if(!period.isActiveTime()) {
				if(!validStaticTime(period.getFromTime(),minutes,Float.parseFloat(fields.get(2)))) {
					logger.info("The time provided is not compatible with the Period.");
					return "INVALID";
				}
				response.append(String.format("%02d",minutes) + ":" + String.format("%02d",seconds));
				//FT short form must have +00:00
				if(period.isAdditionalTime()) {
					response.append(" +00:00");
				}
				response.append(" - " + period.getLongForm());
				return response.toString();
			}
			logger.info("The Match Time configuration is not valid.");
			return "INVALID";
		}catch(IndexOutOfBoundsException e) {
			logger.info("Match Time format is not valid. Use the following format: [period] minutes:seconds.mileseconds");
			return "INVALID";
		}catch(NullPointerException e) {
			logger.info("The Match Time short form provided doesn't exist.");
			return "INVALID";
		}
	}
	/**
	 * Return the period related to the short form received
	 * @param shortFrom String short form of match time
	 * @return String period
	 */
	protected MatchTime getMatchTime(String shortFrom) {
		for(MatchTime period: periods) {
			if(period.getShortForm().equals(shortFrom)) {
				return period;
			}
		}
		return null;
	}
	/**
	 * Validate if the minutes received are above the previous period. (e.g. H2 time in football should be after the H1 final time (45min))
	 * @param part int which part of the game is
	 * @param minutes int minutes
	 * @return true if valid
	 */
	protected boolean validActiveMinutes(int fromTime, int minutes){
		return fromTime<=minutes?true:false;
	}

	/**
	 * Validate if the time on a period that the game is not on course is the expected. (e.g. PM should be 00:00, HT should be 45:00 and FT should be 90:00)
	 * @param part int which part of the game is
	 * @param minutes minutes
	 * @param seconds seconds
	 * @return true if valid
	 */
	protected boolean validStaticTime(int fromTime, int minutes,float seconds) {
		if(minutes!=fromTime || seconds!=0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Round up or down the millisenconds to the nearest whole second.
	 * @param number seconds in format: [seconds].[milliseconds]
	 * @return seconds rounded
	 */
	protected int roundSeconds(float time){
		if(time>60) {
			return -1;
		}
		int roundedSeconds = BigDecimal.valueOf(time).setScale(0, RoundingMode.HALF_UP).intValue();
		return roundedSeconds<=60?roundedSeconds:-1; 
	}
	
	/**
	 * Parse the input in order to separe in Period, minutes and seconds.milliseconds  
	 * @param period String received in a specific format
	 * @return ArrayList<String> with values parsed 
	 */
	protected ArrayList<String> parseMatchTime(String period){
		ArrayList<String> fields = new ArrayList<>();
		Pattern pattern = Pattern.compile(this.regEx);
		Matcher matcher = pattern.matcher(period);
		
		if(matcher.find()) {
			for(int i=1;i<=matcher.groupCount();i++) {
				fields.add(matcher.group(i));
			}
		}
		return fields;
	}
}
