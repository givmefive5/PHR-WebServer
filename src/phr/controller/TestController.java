package phr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import phr.exceptions.SNSException;
import phr.service.BloodPressureService;
import phr.sns.datamining.daoimpl.HealthCorpusDaoImpl;
import phr.sns.datamining.filter.DMFilter;
import phr.sns.datamining.service.FacebookFetcherService;

@Controller
public class TestController {

	@Autowired
	BloodPressureService bloodPressureService;

	@Autowired
	FacebookFetcherService facebookFetcherService;

	@RequestMapping(value = "/test")
	public void test() throws SNSException, InterruptedException {
		HealthCorpusDaoImpl healthCorpusDaoImpl = new HealthCorpusDaoImpl();
		List<String> corpus = healthCorpusDaoImpl.getFoodWords();
		String message = "yum yum yum!!!! eating bacon and pancakes with egg at mcdonald's #bacon #pancakes #happy";
		DMFilter filter = new DMFilter();
		List<String> matches = filter.findMatches(message, corpus);
		for (String m : matches) {
			System.out.println(m);
		}
	}
}
