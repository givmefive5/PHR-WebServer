package phr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import phr.service.BloodPressureService;

@Controller
public class TestController {

	@Autowired
	BloodPressureService bloodPressureService;

	@RequestMapping(value = "/test")
	public void test() {
		System.out.println("edit");
	}
}
