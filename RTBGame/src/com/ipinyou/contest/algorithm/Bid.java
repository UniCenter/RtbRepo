package com.ipinyou.contest.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**  Bid class that need to be completed by the contest participants
 *   This is the only place to implement your bidding algorithm
 *   Note that the class name (Bid) and package name (com.ipinyou.contest.algorithm)
 *   are fixed to make a correct submission.
 * 
 */
public class Bid implements Algorithm {

    /*************simple implementation by zc***************/
	private HashMap<String, Integer> adSlotFormatMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> visibilityMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> adExchangeMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> creativeMap = new HashMap<String, Integer>();
	
	private HashSet<String> cityClkSet = new HashSet<String>();
	private HashSet<String> pinyouIDClkSet = new HashSet<String>();
	private HashSet<String> slotIDClkSet = new HashSet<String>();
	
	private double probThreshold = -0.25;
	
	private double[] weights = { -1.02239, 0.00184939, 0.154572, -0.6648,
			-0.512153, 0.267914, -0.39043, 0.932013, -1.0642, 0.264554,
			-0.606084, 0.268269, 0.271281, -0.0493485, -0.386314, 0.269935,
			0.267398, -1.06202, 0.00711256, 0.269955, -0.393167, 0.269576,
			-0.393778, 0.269088, -0.391115, 0.270455, 0.934785, -0.0476006,
			-0.398078, 0.639266, -0.170141, -1.06196, 0.378136, 0.934104,
			-1.06205, -0.0291809, -0.0307626, -0.253358, 0.408323, -0.251609,
			-0.925759, 2.66692, -0.357581, -0.6648, -0.458694 };
	
//	private double [] weights;
	
	private int bitRatio = 40;
	private int fixedBidPrice = 365;
	private Random rand = null;
    /**
     *  init() method initializes alogrithm paramters
     *         and model 
     *  Memory limit: 1G
     */
	@Override
	public void init() {
		/**
		 * loading conf
		 */
		//load HashMaps
		this.setupAdSlotFormatMap();
		this.setupVisibilityMap();
		this.setCreativeMap();
		this.setupAdExchangeMap();
		
		try{
			this.slotIDClkSet = this.loadClickSet("adSlot.conf");
			this.cityClkSet = this.loadClickSet("city.conf");
			this.pinyouIDClkSet = this.loadClickSet("id.conf");
			
			
		}catch(IOException e)
		{
			//do nothing
		}
//		try
//		{
//		    this.setupWeights("weight.conf");
//		}catch(Exception e)
//		{
//			this.weights = new double[45];
//		}
		
		rand = new Random();
		
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
		if(value > this.probThreshold)
		{
			return 300;
		}
		else
		{
			if(Math.random() > 0.5)
			{
				return 300;
			}
			else
			{
				return -1;
			}
		}
		
//		double prob = isGood(bidRequest);
//		
//		if(prob < this.probThreshold)
//		{
//			return 365;
//		}
//		else
//		{
//			if(Math.random() > 0.5)
//			{
//				return 365;
//			}
//			else
//			{
//				return -1;
//			}
//		}
	}
	
	public void setThresholdProb(double threshold)
	{
		this.probThreshold = threshold;
	}
	
	/**
	 * LR Predict the probability as -1
	 * @param bidRequest
	 * @return
	 */
	private double isGood(BidRequest bidRequest)
	{
		if(bidRequest == null)
		{
			return 0;
		}
		
		int index = 0;
		double[] attrs = new double[this.weights.length];
		//cookie
		if(this.pinyouIDClkSet.contains(bidRequest.getIpinyouId().trim()))
		{
			attrs[index] = 1.0;
		}
		index++;
		
		//cityClk
		if(this.cityClkSet.contains(bidRequest.getCity().trim()))
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
		index += this.adExchangeMap.size();
		
		//creative
		idx = this.creativeMap.containsKey(bidRequest.getCreativeID()) ? this.creativeMap.get(bidRequest.getCreativeID()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		index += this.creativeMap.size();
		
		//visibility
		idx = this.visibilityMap.containsKey(bidRequest.getAdSlotVisibility()) ? this.visibilityMap.get(bidRequest.getAdSlotVisibility()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		index += this.visibilityMap.size();
		
		//id
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
		index += this.adSlotFormatMap.size();
		
		//floor price
		if(!bidRequest.getAdSlotFloorPrice().equals("0"))
		{
			attrs[index] = 1.0;
		}
		
		double sum = 0;
		for(int i = 0; i < attrs.length; i++)
		{
			sum += attrs[i] * weights[i]; 
		}
		 
		double prob = 1/(1 + Math.exp(0 - sum));
		
		return prob;	
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
		//cityClk
		if(this.cityClkSet.contains(bidRequest.getCity().trim()))
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
		index += this.adExchangeMap.size();
		
		//creative
		idx = this.creativeMap.containsKey(bidRequest.getCreativeID()) ? this.creativeMap.get(bidRequest.getCreativeID()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		index += this.creativeMap.size();
		
		//visibility
		idx = this.visibilityMap.containsKey(bidRequest.getAdSlotVisibility()) ? this.visibilityMap.get(bidRequest.getAdSlotVisibility()) : -1;
		if(idx > 0)
		{
			attrs[idx + index - 1] = 1.0;
		}
		index += this.visibilityMap.size();
		
		//id
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
		index += this.adSlotFormatMap.size();
		
		//floor price
		if(!bidRequest.getAdSlotFloorPrice().equals("0"))
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
	
	/**
	 * adSlotFormat
	 */
	private void setupAdSlotFormatMap()
	{
		this.adSlotFormatMap.put("0", 1);
		this.adSlotFormatMap.put("1", 2);
	}
	
	private void setupVisibilityMap()
	{
		this.visibilityMap.put("0", 3);
		this.visibilityMap.put("1", 2);
		this.visibilityMap.put("2", 1);
		this.visibilityMap.put("255", 4);
	}
	
	private void setupAdExchangeMap()
	{
		this.adExchangeMap.put("3", 3);
		this.adExchangeMap.put("2", 1);
		this.adExchangeMap.put("1", 2);
	}
	
	
	private void setCreativeMap()
	{
		this.creativeMap.put("3a0cf3767556609a1f4329c9f52e387e", 1);
		this.creativeMap.put("28425676c2469651ba969a88529eeec8", 2);
		this.creativeMap.put("cfe9f50e234d74317336d04dfa3494a3", 3);
		this.creativeMap.put("f206493d1a82b7d977075b20b7afd5f4", 4);
		this.creativeMap.put("873a7eb504df9f4e2f16b66f61b24044", 5);
		this.creativeMap.put("2b79ac0b6110d91fbb1160dcce775b6d", 6);
		this.creativeMap.put("96a2396093d84de1bbde510629a1c63d", 7);
		this.creativeMap.put("d76818d93532855570f2af04f52b1ef1", 8);
		this.creativeMap.put("ca165a0bf41928c7ec45df6c2745cf6e", 9);
		this.creativeMap.put("36391fa23e0928a93cd51ea8af344b82", 10);
		this.creativeMap.put("51fd94f6624989b7cecbc307f00f97f7", 11);
		this.creativeMap.put("9c23f8ef21ec419a5bdc37c5bae78450", 12);
		this.creativeMap.put("0abb99569043fe758ba6a51714782ced", 13);
		this.creativeMap.put("f7e7cdcd8b8ed4d6682d78c5b8c4446c", 14);
		this.creativeMap.put("d683498a1e531a0f3621d2f7b0a9dcf7", 15);
		this.creativeMap.put("5aca4c5f29e59e425c7ea657fdaac91e", 16);
		this.creativeMap.put("fb82c5506a7745b09752a995de85c708", 17);
		this.creativeMap.put("4937b21d6f6c38560823917e19403fa7", 18);
		this.creativeMap.put("5a57c1d215ab05643a1bce7e402c45ec", 19);
		this.creativeMap.put("02adb1d6bc7233c0735dbefe9bb85ecd", 20);
		this.creativeMap.put("8407d9265e88afa76689e96b7bb48da4", 21);
		this.creativeMap.put("92417e303e30b9794ee33c28f3b0602a", 22);
		this.creativeMap.put("b0d9e0a4ece6145ba15b42ae519cadb6", 23);
		this.creativeMap.put("8f86b069dbb8898e2beca5ec3030e147", 24);
		this.creativeMap.put("475d63a4a414634cb5b95dc826a6258f", 25);
		this.creativeMap.put("8a2b4dd4692a9dc66b3db6327fa2eff7", 26);
		this.creativeMap.put("ebbd8035bba9def2a673154ee2486e7a", 27);
		this.creativeMap.put("63aa53fb1f727ebe3d56c69db5097574", 28);
		this.creativeMap.put("c2253060edbeefa754ac5a6418d35362", 29);
		this.creativeMap.put("ac25cf6c5bc360c0ff6e4d24873b35ea", 30);
		this.creativeMap.put("fa6d6688558d5e338490fe7c5984c822", 31);
		this.creativeMap.put("45028369b2f959d4e65a10e0e388c850", 32);
	}
}
