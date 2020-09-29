package com.olegB.Bus;

import com.olegB.Bus.repository.BusRepository;
import com.olegB.Bus.services.BusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BusApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BusApplication.class, args);
	}

	@Autowired
	BusServiceImpl busService;
	@Autowired
	BusRepository busRepository;


	@Override
	public void run(String... args) throws Exception {
		busRepository.deleteAll();

	}
}
