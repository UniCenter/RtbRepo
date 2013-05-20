package com.ipinyou.contest.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DemoBid implements Algorithm{

	private Set<String> domains = new HashSet<String>();
	
	@Override
	public void init() {
		domains.addAll(
				readModelFileToLines("model_file")
		);
	}

	@Override
	public int getBidPrice(BidRequest bidRequest) {
		String domain = bidRequest.getDomain();
		if(domain!=null){
			if(domains.contains(domain)){
				return 500;
			}
		}
		return -1;
	}
	
	private List<String> readModelFileToLines(String filename) {
		List<String> lines = new ArrayList<String>();
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		if(resourceAsStream==null){
			resourceAsStream = getClass().getResourceAsStream("/".concat(filename));
			if(resourceAsStream==null){
				throw new RuntimeException("init algorithm error ");
			}
		}
		String line = null;
		BufferedReader br = null;
		try {
			br  = new BufferedReader(new InputStreamReader(resourceAsStream));
			while((line = br.readLine())!=null){
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}

}
