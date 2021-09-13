package com.pct.parser.controller;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pct.parser.engine.ReportServiceImpl;
import com.pct.parser.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/parse-report")
public class ParseReportController {

	@Autowired
	private ReportServiceImpl reportService;

	@GetMapping(value = "/{report}")
	public BaseResponse parseReport(@PathVariable String report) throws BeansException, IOException {
		log.info("Start:: parseReport()");

		return reportService.getParsedReport(report);
	}

}
