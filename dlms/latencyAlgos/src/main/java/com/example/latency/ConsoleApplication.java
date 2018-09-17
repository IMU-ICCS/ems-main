package com.example.latency;

import org.springframework.boot.CommandLineRunner;

import com.example.latency.controller.LatencyController;

// this starts before the application

import com.example.latency.extras.GetPropertyValues;

public class ConsoleApplication implements CommandLineRunner {

	private static GetPropertyValues propValues = new GetPropertyValues();

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		LatencyController latencyController = new LatencyController();
		latencyController.run();
	}

}
