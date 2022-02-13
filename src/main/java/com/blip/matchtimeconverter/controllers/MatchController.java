package com.blip.matchtimeconverter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blip.matchtimeconverter.services.MatchService;

@RestController
@RequestMapping("/api/match")
public class MatchController {
	@Autowired
	MatchService matchService;

	/**
	 * Endpoint to convert match time formats
	 * @param period match time in one format 
	 * @return match time in another format
	 */
	@PostMapping("/convertPeriod")
	public ResponseEntity<?> getConvertedPeriod(@RequestParam String time){
		return ResponseEntity.ok(matchService.convertPeriod("football", time));
	}
}
