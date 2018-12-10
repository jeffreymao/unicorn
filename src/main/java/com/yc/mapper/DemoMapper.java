package com.yc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface DemoMapper {

	@Select("select distinct ca.security_code,ca.security_name,ca.co_code \n" + 
			"from company_product_series cps,company_attribute ca\n" + 
			"where  ca.id_state = 1 and cps.id_state = 1\n" + 
			"and  cps.co_code = ca.co_code")
	@Results({
		@Result(property = "securityCode",  column = "security_code"),
		@Result(property = "securityName",  column = "security_name"),
		@Result(property = "coCode",  column = "co_code"),
	})
	List<Map<String,Object>> getCompanyList();
	
	@Select("select distinct cps.product_code,pa.product_name\n" + 
			"from product_attribute pa,company_product_series cps\n" + 
			"where pa.id_state = 1 and cps.id_state = 1\n" + 
			"and pa.product_code  = cps.product_code and cps.co_code = #{coCode}")
	@Results({
		@Result(property = "productCode",  column = "product_code"),
		@Result(property = "productName",  column = "product_name"),
	})
	List<Map<String,Object>> getCompanyProducts(String coCode);
	
	@Select("select sa.series_code,sa.series_name,cc.core_name\n" + 
			"from series_attribute sa,core_code cc,company_product_series cps\n" + 
			"where sa.id_state = 1 and cps.id_state = 1 and sa.unit_code = cc.core_code\n" + 
			"and sa.series_code = cps.series_code and cps.product_code = #{productCode}\n" + 
			"and cps.co_code = #{coCode}")
	@Results({
		@Result(property = "seriesCode",  column = "series_code"),
		@Result(property = "seriesName",  column = "series_name"),
		@Result(property = "unitName",  column = "core_name"),
	})
	List<Map<String,Object>> getProductSeries(@Param("productCode") String productCode,@Param("coCode") String coCode);
	
	@Select("select sd.data_time ,sd.data_value\n" + 
			"from series_data sd\n" + 
			"where sd.series_code = #{seriesCode}\n" + 
			"order by sd.data_time")
	@Results({
		@Result(property = "seriesDataTime",  column = "data_time"),
		@Result(property = "seriesDataValue",  column = "data_value"),
	})
	List<Map<String,Object>> getSeriesData(String seriesCode);
	
	@Select("select pc.father_code,pc.son_code\n" + 
			"from product_chain pc\n" + 
			"where pc.id_state = 1 and pc.product_code = #{productCode}")
	@Results({
		@Result(property = "productFatherCode",  column = "father_code"),
		@Result(property = "productSonCode",  column = "son_code"),
	})
	List<Map<String,Object>> getProductChain(String productCode);
	
	@Select("select distinct ca.co_code ,ca.co_name\n" + 
			"from company_attribute ca,company_product_series cps\n" + 
			"where cps.id_state = 1 and ca.id_state = 1 and ca.co_code = cps.co_code\n" + 
			"and cps.product_code = #{productCode}")
	@Results({
		@Result(property = "coCode",  column = "co_code"),
		@Result(property = "coName",  column = "co_name"),
	})
	List<Map<String,Object>> getCompanysByProduct(String productCode);
	
	@Select("select distinct pa.product_code, pa.product_name\n" + 
			"from product_chain pc , product_attribute pa\n" + 
			"where pc.id_state = 1 and pa.id_state = 1\n" + 
			"and pc.product_code = pa.product_code")
	@Results({
		@Result(property = "productCode",  column = "product_code"),
		@Result(property = "productName",  column = "product_name"),
	})
	List<Map<String,Object>> getCoreProducts();
	
	
}
