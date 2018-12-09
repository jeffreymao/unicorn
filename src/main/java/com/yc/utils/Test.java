package com.yc.utils;

import java.time.Instant;

public class Test {

	public static void main(String[] args) throws Exception {
	   Long begin = Instant.now().toEpochMilli();
	   int sum = 0;
	   for(int i=0;i<100;i++) {
		   sum = sum+1;
		   Thread.sleep(1);
	   }
	   Long end = Instant.now().toEpochMilli();
	   System.out.println(end-begin);
	}

}
