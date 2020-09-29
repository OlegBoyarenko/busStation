package com.olegB.Bus;

import com.olegB.Bus.controller.BusStationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BusApplicationTests {

	@Autowired
	private BusStationController busStationController;

	@Test
	void contextLoads() {
		assertThat(busStationController).isNotNull();
	}

}
