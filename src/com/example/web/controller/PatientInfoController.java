package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PatientInfoController {

	@RequestMapping(value = "/info")
	public String loadPatientInfoPage() {

		return "patient-info";
	}
}
