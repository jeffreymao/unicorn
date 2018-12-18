package com.yc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

	private static final Logger logger = LogManager.getLogger(CommonController.class);
	
	@RequestMapping("/")
	public String index() {
		logger.info("enter index api :welcome ");
		return "welocome to unicorn system";
	}
}
