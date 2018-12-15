package com.yc.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductNode {
	private ProductNode farther;
	private String code;
	private String name;
	private List<ProductNode> sons = new ArrayList<>();
	private int level;
	public ProductNode getFarther() {
		return farther;
	}
	public void setFarther(ProductNode farther) {
		this.farther = farther;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ProductNode> getSons() {
		return sons;
	}
	public void setSons(ProductNode son) {
		this.sons.add(son);
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isTopNode() {
		if(this.farther ==null) {
			return true;
		}
		return false;
	}
	
	public boolean hasSons() {
		if(this.sons.size()>0) {
			return true;
		}
		return false;
	}
	
}
