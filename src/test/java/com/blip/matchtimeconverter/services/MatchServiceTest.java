package com.blip.matchtimeconverter.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatchServiceTest {

	private MatchService ms = new MatchService();
	
	@Test
	void testConvertPeriodInvalid() {
		String real = ms.convertPeriod("basketball", "[H2] 90:00.701");
		assertEquals("INVALID", real);
	}

	@Test
	void testConvertPeriodInvalidNull() {
		String real = ms.convertPeriod("football", null);
		assertEquals("INVALID", real);
	}

	@Test
	void testConvertPeriodValid() {
		String real = ms.convertPeriod("football", "[H2] 90:00.701");
		assertEquals("90:00 +00:01 - SECOND_HALF", real);
	}

}
