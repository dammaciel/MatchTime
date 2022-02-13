package com.blip.matchtimeconverter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blip.matchtimeconverter.models.FootballMatch;
import com.blip.matchtimeconverter.models.Match;

@Service
public class MatchService {
	private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
	Match match;
	
	/**
	 * Convert Match time format
	 * @param game Game type of the received match time (TODO in case of more game types, currently hardcoded for football) 
	 * @param time Match time in the input format
	 * @return Match time in another format
	 */
	public String convertPeriod(String game, String time) {
		switch(game.toUpperCase()) {
		    case "FOOTBALL":
		    	match = new FootballMatch();
		    	break;
		    default:
		    	logger.info("The Game " + game + " is not supported");
		    	return "INVALID";
		}		
		try {			
			return match.buildMatchTimeSentence(time);
		}catch(Exception e) {
			logger.info("Period format is not valid. Use the following format: [period] minutes:seconds.mileseconds");
			return "INVALID";
		}
	}
}
