package com.ipinyou.contest.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Bid_SGD_SVM implements Algorithm{


    /*************simple implementation by zc***************/
	private HashMap<String, Integer> adSlotFormatMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> visibilityMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> adExchangeMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> creativeMap = new HashMap<String, Integer>();
	
	private HashSet<String> cityClkSet = new HashSet<String>();
	private HashSet<String> domainClkSet = new HashSet<String>();
	private HashSet<String> slotIDClkSet = new HashSet<String>();
	private HashSet<String> providerClkSet = new HashSet<String>();
	
	private double threshold = -100;
	
	private double prob = 0.95;
	
	public void setProb(double prob) {
		this.prob = prob;
	}

	private double[] weights;
	
    /**
     *  init() method initializes alogrithm paramters
     *         and model 
     *  Memory limit: 1G
     */
	@Override
	public void init() {
		
		try{
			this.slotIDClkSet = this.loadClickSet("adslot_click.conf");
			System.out.printf("[Info] Loading adslot_click Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading adslot_click.conf Fail");
			System.exit(-1);
		}
		
		try{
			this.cityClkSet = this.loadClickSet("city_click.conf");
			System.out.printf("[Info] Loading city_click Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading city_click.conf Fail");
			System.exit(-1);
		}
		
		try{
			this.domainClkSet = this.loadClickSet("domain_click.conf");
			System.out.printf("[Info] Loading domain_click Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading domain_click.conf Fail");
			System.exit(-1);
		}
		
		try{
			this.adExchangeMap = this.loadEncodingMap("adExchange.conf");
			System.out.printf("[Info] Loading adExchange Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading adExchange.conf Fail");
			System.exit(-1);
		}
		
		try{
			this.visibilityMap = this.loadEncodingMap("visibility.conf");
			System.out.printf("[Info] Loading visibility Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading visibility.conf Fail");
			System.exit(-1);
		}
		
		try{
			this.creativeMap = this.loadEncodingMap("creative.conf");
			System.out.printf("[Info] Loading creative Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading creative.conf Fail");
			System.exit(-1);
		}
		
		try{
			this.adSlotFormatMap = this.loadEncodingMap("slotFormat.conf");
			System.out.printf("[Info] Loading slotFormat Succeed\n");
		}catch(IOException e)
		{
			System.out.printf("[Error] Loading slotFormat.conf Fail");
			System.exit(-1);
		}
		
		try{
		    this.setupWeights("weight.conf");
		    System.out.printf("[Info] Loading weight Succeed\n");
		}catch(Exception e)
		{
			System.out.printf("[Error] Loading weight.conf Fail");
			this.weights = new double[51];
		}
		
		try{
			this.loadClickSet("provider.conf");
			System.out.printf("[Info] Loading provider Succeed\n");
		}catch(IOException e)
		{
			System.out.printf("[Error] Loading provider.conf Fail");
			System.exit(-1);
		}
		
	}

    /**
     * getBidPrice() method makes the real calculated
     * Note: one bid reqeust decision has to be make
     *       less than 20ms.
     *
     */
	@Override
	public int getBidPrice(BidRequest bidRequest) {
		/*******************sgd-svm*****************/
		double value = this.predictTag(bidRequest);
		if(value > this.threshold)
		{
			return 10000;
		}
		else
		{
			if(Math.random() > prob)
			{
				return 300;
			}
			else
			{
				return -1;
			}
		}
	}
	
	public void setThreshold(double threshold)
	{
		this.threshold = threshold;
	}
	
	/**
	 * sgd-svm predict the value of the flag
	 * @param bidRequest
	 * @return
	 */
	private double predictTag(BidRequest bidRequest)
	{
		//default as -1
		if(bidRequest == null)
		{
			return -1;
		}
		
		
		double[] attrs = new double[this.weights.length];
		attrs[0] = 1;//bias
		
		int index = 1;
		attrs[1] = 1;//used to be cookie click, now abandon
		index++;
		
		//cityClk
		if(this.cityClkSet.contains(bidRequest.getCity().trim()))
		{
			attrs[index] = 1.0;
		}
		index++;
		
		//domainClk
		if(this.domainClkSet.contains(bidRequest.getDomain().trim()))
		{
			attrs[index] = 1.0;
		}
		index++;
		
		//adexchange
		int idx = this.adExchangeMap.containsKey(bidRequest.getAdExchange()) ? this.adExchangeMap.get(bidRequest.getAdExchange()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		else
		{
			attrs[index + this.adExchangeMap.size() - 1] = 1.0;
		}
		index += this.adExchangeMap.size();
		
		//creative
		idx = this.creativeMap.containsKey(bidRequest.getCreativeID()) ? this.creativeMap.get(bidRequest.getCreativeID()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		else
		{
			attrs[index + this.creativeMap.size() - 1] = 1.0;
		}
		index += this.creativeMap.size();
		
		//visibility
		idx = this.visibilityMap.containsKey(bidRequest.getAdSlotVisibility()) ? this.visibilityMap.get(bidRequest.getAdSlotVisibility()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		else
		{
			attrs[index + this.visibilityMap.size() - 1] = 1.0;
		}
		index += this.visibilityMap.size();
		
		//slot id
		if(this.slotIDClkSet.contains(bidRequest.getAdSlotID()))
		{
			attrs[index] = 1.0;
		}
		index++;
		
		//slot form
		idx = this.adSlotFormatMap.containsKey(bidRequest.getAdSlotFormat()) ? this.adSlotFormatMap.get(bidRequest.getAdSlotFormat()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		else
		{
			attrs[index + this.adSlotFormatMap.size() - 1] = 1.0;
		}
		index += this.adSlotFormatMap.size();
		
		//floor price
		if(!bidRequest.getAdSlotFloorPrice().equals("0"))
		{
			attrs[index] = 1.0;
		}
		index++;
		
		//provider
		if(this.providerClkSet.contains(bidRequest.getAdSlotID().split("_")[0]))
		{
			attrs[index] = 1.0;
		}
		
		double sum = 0;
		for(int i = 0; i < attrs.length; i++)
		{
			sum += attrs[i] * weights[i]; 
		}
		
		
		return sum;
	}
	
	private void setupWeights(String path) throws IOException
	{
		ArrayList<Double> tmpList = new ArrayList<Double>(100);
		BufferedReader bReader = new BufferedReader(new FileReader(path));
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			if(line.length() < 0)
			{
				continue;
			}
			if(line.startsWith("#"))
			{
				continue;
			}
			
			String[] contents = line.split("\t");
			
			tmpList.add(Double.parseDouble(contents[1].trim()));
		}
		bReader.close();
		this.weights = new double[tmpList.size()];
		for(int i = 0, len = tmpList.size(); i < len; i++)
		{
			this.weights[i] = tmpList.get(i);
		}
	}
	
	private HashSet<String> loadClickSet(String path) throws IOException
	{
		HashSet<String> localSet = new HashSet<String>();
		BufferedReader bReader = new BufferedReader(new FileReader(path));
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			if(line.length() < 0)
			{
				continue;
			}
			if(line.startsWith("#"))
			{
				continue;
			}
			
			localSet.add(line.trim());
		}
		bReader.close();
		return localSet;
	}
	
	private HashMap<String, Integer> loadEncodingMap(String path) throws IOException
	{
		HashMap<String, Integer> localMap = new HashMap<String, Integer>();
		BufferedReader bReader = new BufferedReader(new FileReader(path));
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			if(line.length() < 0)
			{
				continue;
			}
			if(line.startsWith("#"))
			{
				continue;
			}
			
			String[] contents = line.split("\t");
			if(contents.length != 2)
			{
				continue;
			}
			try
			{
				int encoding = Integer.parseInt(contents[1]);
				localMap.put(contents[0].trim(), encoding);
			}catch(Exception e)
			{
				continue;
			}
		}
		bReader.close();
		return localMap;
	}
	
}
