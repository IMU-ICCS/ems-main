package com.example.latency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.latency.model.TwoDataCenterValues;

@RestController
@RequestMapping("/input")
public class UserInputController {

	@Autowired
	LatencyController latencyController;

	@GetMapping("/{dc1}/{dc2}")
	public TwoDataCenterValues retrieve(@PathVariable String dc1, @PathVariable String dc2) {
		return (latencyController.calculateTwoDataCenter(dc1, dc2));
	}

}
