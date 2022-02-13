package com.blip.matchtimeconverter.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FootballMatchTest {
	
	private FootballMatch fm = new FootballMatch();

	@Test
	void testBuildMatchTimeSentenceValidPM() {
		String output = fm.buildMatchTimeSentence("[PM] 0:00.000");
		assertEquals("00:00 - PRE_MATCH", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceValidH1_1() {
		String output = fm.buildMatchTimeSentence("[H1] 0:15.025");
		assertEquals("00:15 - FIRST_HALF", output);
	}
	@Test
	
	void testBuildMatchTimeSentenceValidH1_2() {
		String output = fm.buildMatchTimeSentence("[H1] 3:07.513");
		assertEquals("03:08 - FIRST_HALF", output);
	}

	@Test
	void testBuildMatchTimeSentenceValidH1_3() {
		String output = fm.buildMatchTimeSentence("[H1] 45:00.001");
		assertEquals("45:00 +00:00 - FIRST_HALF", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceValidH1_4() {
		String output = fm.buildMatchTimeSentence("[H1] 46:15.752");
		assertEquals("45:00 +01:16 - FIRST_HALF", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceValidHT() {
		String output = fm.buildMatchTimeSentence("[HT] 45:00.000");
		assertEquals("45:00 - HALF_TIME", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceValidH2_1() {
		String output = fm.buildMatchTimeSentence("[H2] 45:00.500");
		assertEquals("45:01 - SECOND_HALF", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceValidH2_2() {
		String output = fm.buildMatchTimeSentence("[H2] 90:00.908");
		assertEquals("90:00 +00:01 - SECOND_HALF", output);
	}

	@Test
	void testBuildMatchTimeSentenceValidFT() {
		String output = fm.buildMatchTimeSentence("[FT] 90:00.000");
		assertEquals("90:00 +00:00 - FULL_TIME", output);
	}

	
	@Test
	void testBuildMatchTimeSentenceInvalidForm() {
		String output = fm.buildMatchTimeSentence("[H3] 90:00.000");
		assertEquals("INVALID", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceNoForm() {
		String output = fm.buildMatchTimeSentence("90:00");
		assertEquals("INVALID", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceNegativeMinutes() {
		String output = fm.buildMatchTimeSentence("[PM] -10:00.000");
		assertEquals("INVALID", output);
	}
	
	@Test
	void testBuildMatchTimeSentenceInvalid() {
		String output = fm.buildMatchTimeSentence("FOO");
		assertEquals("INVALID", output);
	}

	@Test
	void testGetMatchTimeValid() {
		MatchTime MatchTime = fm.getMatchTime("PM");
		assertEquals("PRE_MATCH",MatchTime.getLongForm());
	}
	
	@Test
	void testGetMatchTimeInvalid() {
		MatchTime MatchTime = fm.getMatchTime("MP");
		assertEquals(null,MatchTime);
	}

	@Test
	void testValidActiveMinutesValid() {
		boolean real = fm.validActiveMinutes(45, 60);
		assertEquals(true,real);
	}
	
	@Test
	void testValidActiveMinutesInvalid() {
		boolean real = fm.validActiveMinutes(45, 40);
		assertEquals(false,real);
	}

	@Test
	void testValidStaticTimePMValid() {
		//[PM] 0:00.000
		boolean real=fm.validStaticTime(0, 0, 0);
		assertEquals(true, real);
	}
	
	@Test
	void testValidStaticTimePMInvalid() {
		//[PM] 0:00.300
		boolean real=fm.validStaticTime(0, 0, (float) 0.300);
		assertEquals(false, real);
	}
	
	@Test
	void testValidStaticTimeFTValid() {
		//[FT] 90:00.000
		boolean real=fm.validStaticTime(90, 90, 0);
		assertEquals(true, real);
	}

	@Test
	void testRoundSecondsDown() {
		float seconds=(float) 15.345;
		int roundedSeconds = fm.roundSeconds(seconds);
		assertEquals(15, roundedSeconds);
	}
	
	@Test
	void testRoundSecondsUp() {
		float seconds=(float) 59.845;
		int roundedSeconds = fm.roundSeconds(seconds);
		assertEquals(60, roundedSeconds);
	}
	
	@Test
	void testRoundSecondsWithMoreThan60() {
		float seconds=(float) 60.745;
		int roundedSeconds = fm.roundSeconds(seconds);
		assertEquals(-1, roundedSeconds);
	}


	@Test
	void testParseMatchTimeValid() {
		ArrayList<String> fields = fm.parseMatchTime("[H3] 90:03.000");
		assertEquals(3,fields.size());
	}
	
	@Test
	void testParseMatchTimeMissingMs() {
		ArrayList<String> fields = fm.parseMatchTime("[H3] 90:03");
		assertEquals(0,fields.size());
	}
	
	@Test
	void testParseMatchTimeMissingForm() {
		ArrayList<String> fields = fm.parseMatchTime("90:03.000");
		assertEquals(0,fields.size());
	}
	
	@Test
	void testParseMatchTimeMoreThen100() {
		ArrayList<String> fields = fm.parseMatchTime("[H3] 100:03.000");
		assertEquals(3,fields.size());
	}

}
