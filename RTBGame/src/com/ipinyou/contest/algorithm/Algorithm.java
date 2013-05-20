package com.ipinyou.contest.algorithm;

public interface Algorithm {
	
	 void init() throws Exception;

	 int getBidPrice(BidRequest bidRequest); 

}
