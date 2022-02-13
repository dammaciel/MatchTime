package com.blip.matchtimeconverter.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class MatchControllerTest {

	@Autowired
	private MatchController mc;
	
	@Test
	void contextLoads() throws Exception{
		assertThat(mc).isNotNull();
	}
	
	@Test
	void test() {
		ResponseEntity<?> real = mc.getConvertedPeriod("[H2] 90:00.000");
		assertEquals(HttpStatus.OK, real.getStatusCode());
	}

}
