package com.yc.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yc.mapper.DemoMapper;
import com.yc.utils.MyConstants;
import com.yc.utils.MyUtils;

@RestController
public class DemoController {

	private static final Logger logger = LogManager.getLogger(DemoController.class);
	
	@Autowired
	private DemoMapper demoMapper;
	
	@Autowired
	private MyUtils myUtils;
	
	@Autowired
	private MyConstants myConstants;
	
	@RequestMapping("/demo/getCompanyList")
	public Map<String,Object> getCompanyList(){
		try {
			List<Map<String,Object>> result = demoMapper.getCompanyList();
			return myUtils.getRetMsg(myConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return myUtils.getRetMsg(myConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/demo/getCompanyProducts")
	public Map<String,Object> getCompanyProducts(@RequestParam(value="coCode",defaultValue="") String coCode){
		try {
			List<Map<String,Object>> result = demoMapper.getCompanyProducts(coCode);
			return myUtils.getRetMsg(myConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return myUtils.getRetMsg(myConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/demo/getProductSeries")
	public Map<String,Object> getProductSeries(@RequestParam(value="coCode",defaultValue="") String coCode,
			@RequestParam(value="productCode",defaultValue="") String productCode){
		try{
			List<Map<String,Object>> result = demoMapper.getProductSeries(productCode, coCode);
			return myUtils.getRetMsg(myConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return myUtils.getRetMsg(myConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
		
	}
	
	@RequestMapping("/demo/getSeriesData")
	public Map<String,Object> getSeriesData(@RequestParam(value="seriesCode",defaultValue="") String seriesCode){
		try {
			List<Map<String,Object>> result = demoMapper.getSeriesData(seriesCode);
			return myUtils.getRetMsg(myConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return myUtils.getRetMsg(myConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
		
	}
	
}
