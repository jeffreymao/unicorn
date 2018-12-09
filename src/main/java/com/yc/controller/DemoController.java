package com.yc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yc.mapper.DemoMapper;

@RestController
public class DemoController {

//	private static final Logger logger = LogManager.getLogger(DemoController.class);
	
	@Autowired
	private DemoMapper demoMapper;
	
	@RequestMapping("/demo/getCompanyList")
	public List<Map<String,Object>> getCompanyList(){
		List<Map<String,Object>> result = demoMapper.getCompanyList();
		return result;
	}
	
	@RequestMapping("/demo/getCompanyProducts")
	public List<Map<String,Object>> getCompanyProducts(@RequestParam(value="coCode",defaultValue="") String coCode){
		List<Map<String,Object>> result = demoMapper.getCompanyProducts(coCode);
		return result;
	}
	
	@RequestMapping("/demo/getProductSeries")
	public List<Map<String,Object>> getProductSeries(@RequestParam(value="coCode",defaultValue="") String coCode,
			@RequestParam(value="productCode",defaultValue="") String productCode){
		List<Map<String,Object>> result = demoMapper.getProductSeries(productCode, coCode);
		return result;
	}
	
	@RequestMapping("/demo/getSeriesData")
	public List<Map<String,Object>> getSeriesData(@RequestParam(value="seriesCode",defaultValue="") String seriesCode){
		List<Map<String,Object>> result = demoMapper.getSeriesData(seriesCode);
		return result;
	}
	
}
