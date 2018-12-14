package com.yc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yc.mapper.DemoMapper;
import com.yc.mapper.ProductNode;
import com.yc.utils.MyConstants;
import com.yc.utils.MyUtils;

@RestController
@RequestMapping("/demo")
public class DemoController {

	private static final Logger logger = LogManager.getLogger(DemoController.class);
	
	@Autowired
	private DemoMapper demoMapper;
	
	@RequestMapping("/getCompanyList")
	public Map<String,Object> getCompanyList(){
		try {
			List<Map<String,Object>> result = demoMapper.getCompanyList();
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/getCompanyProducts")
	public Map<String,Object> getCompanyProducts(@RequestParam(value="coCode",defaultValue="") String coCode){
		try {
			List<Map<String,Object>> result = demoMapper.getCompanyProducts(coCode);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/getProductSeries")
	public Map<String,Object> getProductSeries(@RequestParam(value="coCode",defaultValue="") String coCode,
			@RequestParam(value="productCode",defaultValue="") String productCode){
		try{
			List<Map<String,Object>> result = demoMapper.getProductSeries(productCode, coCode);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
		
	}
	
	@RequestMapping("/getSeriesData")
	public Map<String,Object> getSeriesData(@RequestParam(value="seriesCode",defaultValue="") String seriesCode){
		try {
			List<Map<String,Object>> temp = demoMapper.getSeriesData(seriesCode);
			List<Map<String,Object>> result = new ArrayList<>();
			for(int i=temp.size()-1;i>=0; i--) {
				result.add((Map<String,Object>)temp.get(i));
			}
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/getProductChain")
	public Map<String,Object> getProductChain(@RequestParam(value="productCode",defaultValue="") String productCode){
		try {
			List<Map<String,Object>> temp = demoMapper.getProductChain(productCode);
			Map<String,Object> result = new HashMap<>();
			//获取产业链上产品名称映射
			Set<Long> codeList = new HashSet<>();
			for(Map<String,Object> item : temp) {
				Long fatherCode = (Long)item.get("productFatherCode");
				Long sonCode = (Long)item.get("productSonCode");
				if(fatherCode != null ) {
					codeList.add(fatherCode);
				}
				if(sonCode !=null ) {
					codeList.add(sonCode);
				}
			}
			List<Map<String,Object>> codeNames = demoMapper.getProductNameByCodes(codeList);
			Map<String,String> codeToName = new HashMap<>();
			for(Map<String,Object> item: codeNames) {
				codeToName.put(String.valueOf(item.get("productCode")), String.valueOf(item.get("productName")));
			}
			//构造产业链
			Map<String,ProductNode> nodeMap = new HashMap<>();
			for(Map<String,Object> item : temp) {
				Long fatherCode = (Long)item.get("productFatherCode");
				Long sonCode = (Long)item.get("productSonCode");
				ProductNode fNode = null;
				ProductNode sNode = null;
				if(fatherCode != null ) {
					codeList.add(fatherCode);
					fNode = new ProductNode();
					
				}
				if(sonCode !=null ) {
					codeList.add(sonCode);
				}
			}
			
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/getCompanysByProduct")
	public Map<String,Object> getCompanysByProduct(@RequestParam(value="productCode",defaultValue="") String productCode){
		try {
			List<Map<String,Object>> result = demoMapper.getCompanysByProduct(productCode);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	@RequestMapping("/getCoreProducts")
	public Map<String,Object> getCoreProducts(){
		try {
			List<Map<String,Object>> result = demoMapper.getCoreProducts();
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
}
