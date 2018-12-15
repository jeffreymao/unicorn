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

import com.yc.entity.ProductNode;
import com.yc.mapper.DemoMapper;
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
			Map<String,Object> result = new HashMap<>();
			List<Map<String,Object>> baseData = new ArrayList<>();
		    result.put("baseData", baseData);
		    List<Map<String,Object>> relationData = new ArrayList<>();
		    result.put("relationData", relationData);
		    result.put("totalLevel", 1);
			//获取产业链上产品名称映射
		    List<Map<String,Object>> temp = demoMapper.getProductChain(productCode);
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
			if(codeList.size()==0) {
				return MyUtils.getRetMsg("100", "数据为空", result);
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
					if(!nodeMap.containsKey(String.valueOf(fatherCode))) {
						fNode = new ProductNode();
						fNode.setCode(String.valueOf(fatherCode));
						fNode.setName(codeToName.get(String.valueOf(fatherCode)));
						nodeMap.put(String.valueOf(fatherCode), fNode);
					}else {
						fNode = nodeMap.get(String.valueOf(fatherCode));
					}
				}
				if(sonCode !=null ) {
					if(!nodeMap.containsKey(String.valueOf(sonCode))) {
						sNode = new ProductNode();
						sNode.setCode(String.valueOf(sonCode));
						sNode.setName(codeToName.get(String.valueOf(sonCode)));
						nodeMap.put(String.valueOf(sonCode), sNode);
					}else {
						sNode = nodeMap.get(String.valueOf(sonCode));
					}
					if(fNode!=null) {
						sNode.setFarther(fNode);
						fNode.setSons(sNode);
					}
				}
			}
			//先找到产业链最上游产品
			ProductNode top = null;
			for(ProductNode node :nodeMap.values()) {
				if(node.isTopNode()) {
					top = node;
					top.setLevel(1);
				}
			}
			//从最上游遍历整个产业链
		    loopNodes(top,result,productCode);
			
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SUCCESS, "成功", result);
		}catch(Exception e) {
			logger.error(e);
			return MyUtils.getRetMsg(MyConstants.RET_CODE_SYSERROR, "系统异常", null);
		}
	}
	
	private void loopNodes(ProductNode node,Map<String,Object> data,String productCode) {
		List<Map<String,Object>> baseDataList = (List<Map<String,Object>>)data.get("baseData");
		Map<String,Object> baseData = new HashMap<>();
		baseData.put("name", node.getName());
		baseData.put("code", node.getCode());
		baseData.put("level", node.getLevel());
		if(node.getCode().equals(productCode)) {
			baseData.put("value", "1");
		}else {
			baseData.put("value", "0");
		}
		baseDataList.add(baseData);
		if(node.hasSons()) {
			List<ProductNode> sons = node.getSons();
			List<Map<String,Object>> relationDataList = (List<Map<String,Object>>)data.get("relationData");
			for(ProductNode tmpNode:sons) {
				tmpNode.setLevel(node.getLevel()+1);
				Map<String,Object> relationData = new HashMap<>();
				relationData.put("source", node.getName());
				relationData.put("target", tmpNode.getName());
				relationDataList.add(relationData);
				data.put("totalLevel", tmpNode.getLevel());
				loopNodes(tmpNode,data,productCode);
			}
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
