package com.ipinyou.contest.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LocalTest {

	private HashMap<String, Double> payingPriceMap = new HashMap<String, Double>(20000000);
	private HashMap<String, Boolean> clickConventionMap = new HashMap<String, Boolean>(20000);
	
	public static void main(String[] args)
	{
		String impFile = args[0];
		String clickFile = args[1];
		String convFile = args[2];
		String logFile = args[3];
		String isMultiRound = args[4];
		
		boolean multiRound = false;
		if(isMultiRound.equals("1"))
		{
			multiRound = true;
		}
		
		LocalTest lt = new LocalTest();
		try {
			if(multiRound)
			{
			    lt.expMutilRound(impFile, clickFile, convFile, logFile);
			}
			else
			{
				lt.expOneRound(impFile, clickFile, convFile, logFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void expOneRound(String impFile, String clickFile, String convFile, String logFile) throws IOException
	{
		try{
			this.loadPayingPriceMap(impFile);
			this.loadClickMap(clickFile);
			this.loadConvMap(convFile);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		// set up your algorithm here.
		Bid_SGD_SVM algorithm = new Bid_SGD_SVM();
		try {
			algorithm.init();
			System.out.println("algorithm init done");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		doOneExp(logFile, algorithm);
	}
	
	public void expMutilRound(String impFile, String clickFile, String convFile, String logFile) throws IOException
	{
		// set up your algorithm here.
		Bid_SGD_SVM algorithm = new Bid_SGD_SVM();
		try {
			algorithm.init();
			System.out.println("algorithm init done");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		try{
			this.loadPayingPriceMap(impFile);
			this.loadClickMap(clickFile);
			this.loadConvMap(convFile);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		double stepSize = 0.1;
		for(int i = 0; i < 21; i++)
		{
			double value = 0 - i * stepSize;
			algorithm.setThreshold(value);
//			algorithm.setProb(value);
			
			System.out.printf("Experiments with Prob = %2f\n", value);
			doOneExp(logFile, algorithm);
			System.out.println("==================================================");
		}
	}
	
	private void doOneExp(String logFile, Algorithm algorithm) throws IOException
	{
		
		double maxAmount = 1000 * 100 * 1000;
		double spend = 0;
		int score = 0;
		int numOfClick = 0;
		int winClick = 0;
		int numOfConv = 0;
		int winConv = 0;
		int numOfImp = 0;
		int winImp = 0;
		
//		double prob = 0.285714;
		
		BufferedReader bReader = new BufferedReader(new FileReader(logFile));
		int cnt = 0;
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			boolean hasClick = false;
			boolean hasConv = false;
			if(spend > maxAmount)
			{
				System.out.printf("Exit Evaluation As Cost is [%2f] \n", spend);
				break;
			}
			
			cnt++;
//			if(cnt % 5000000 == 0)
//			{
//				System.out.printf("%d Lines Evaluated\n", cnt);
//			}
//			System.out.println("Step zero");
//			if(cnt % 7 != 2 && cnt % 7 != 5)
//			{
//				continue;
//			}
			
			/*
			 * only do test for the last 2 days
			 */
			
			String[] contents = line.split("\t");
			if(contents.length != 19)
			{
				System.out.println("Length is not right in Log File");
				continue;
			}
			
			BidRequest request = this.setBidRequest(contents);
			String bid_id = request.getBidId();
			
//			System.out.println("Step one bid_id = " + bid_id);
			
			if(bid_id == null || bid_id == "")
			{
				System.out.println("empty bid_id");
			}
			
			if(!this.payingPriceMap.containsKey(bid_id))
			{
//				System.out.printf("Not Presented\n");
				continue;
			}
			
			
			double payingPrice = this.payingPriceMap.get(bid_id);
			//update stats
			
//			System.out.println("Step two");
			
			numOfImp++;
			if(this.clickConventionMap.containsKey(bid_id))
			{
				numOfClick++;
				hasClick = true;
				if(this.clickConventionMap.get(bid_id))
				{
					numOfConv++;
					hasConv = true;
				}
			}
			
			double bidPrice = (double)algorithm.getBidPrice(request);
			if(bidPrice > payingPrice)
			{
				//win one imp
				spend += payingPrice;
				winImp++;
				if(hasClick)
				{
					winClick++;
					if(hasConv)
					{
						winConv++;
					}
				}
			}
		}
		score = winConv * 20 + winClick;
		
		System.out.printf("%d Bids, Win %d \n", numOfImp, winImp);
		System.out.printf("%d Clicks, Win %d \n", numOfClick, winClick);
		System.out.printf("%d Convs, Win %d \n", numOfConv, winConv);
		System.out.printf("MaxScore = %d, Score = %d \n", numOfClick + 20 * numOfConv, score);
		System.out.printf("Spend = %2f, maxSpend = %2f \n", spend/1000, maxAmount);
		bReader.close();
	}
	
	
	private BidRequest setBidRequest(String[] contents)
	{
	BidRequest request = new BidRequest();

	request.setBidId(contents[0]);
	request.setTimestamp(contents[1]);
	request.setIpinyouId(contents[2]);
	request.setUserAgent(contents[3]);
	request.setIpAddress(contents[4]);
	request.setRegion(contents[5]);
	request.setCity(contents[6]);
	request.setAdExchange(contents[7]);
	request.setDomain(contents[8]);
	request.setUrl(contents[9]);
	request.setAnonymousURLID(contents[10]);
	request.setAdSlotID(contents[11]);
	request.setAdSlotWidth(contents[12]);
	request.setAdSlotHeight(contents[13]);
	request.setAdSlotVisibility(contents[14]);
	request.setAdSlotFormat(contents[15]);
	request.setAdSlotFloorPrice(contents[16]);
	request.setCreativeID(contents[17]);
	
	return request;
	
	}
	
	private void loadPayingPriceMap(String impFile) throws IOException
	{
		long start = System.currentTimeMillis();
		BufferedReader bReader = new BufferedReader(new FileReader(impFile));
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			String[] contents = line.split("\t");
			if(contents.length != 22)
			{
				System.out.println("Length is not right in imp File");
				continue;
			}
			String bid_id = contents[0];
			Double payingPrice = Double.parseDouble(contents[20]);
			this.payingPriceMap.put(bid_id, payingPrice);
		}
		
		bReader.close();
		long end = System.currentTimeMillis();
		System.out.printf("Imp File Loading Finish, Takes [%d] Mills\n", end-start);
	}
	
	private void loadClickMap(String clickFile) throws IOException
	{
		long start = System.currentTimeMillis();
		BufferedReader bReader = new BufferedReader(new FileReader(clickFile));
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			String[] contents = line.split("\t");
			if(contents.length != 22)
			{
				System.out.println("Length is not right in Click File");
				continue;
			}
			String bid_id = contents[0];
			this.clickConventionMap.put(bid_id, false);
		}
		
		bReader.close();
		long end = System.currentTimeMillis();
		System.out.printf("Click File Loading Finish, Takes [%d] Mills\n", end-start);
	}
	
	private void loadConvMap(String convFile) throws IOException
	{
		long start = System.currentTimeMillis();
		BufferedReader bReader = new BufferedReader(new FileReader(convFile));
		String line = null;
		while((line = bReader.readLine()) != null)
		{
			String[] contents = line.split("\t");
			if(contents.length != 22)
			{
				System.out.println("Length is not right in conv File");
				continue;
			}
			String bid_id = contents[0];
			if(this.clickConventionMap.containsKey(bid_id))
			{
			    this.clickConventionMap.put(bid_id, true);
			}
		}
		
		bReader.close();
		long end = System.currentTimeMillis();
		System.out.printf("Click File Loading Finish, Takes [%d] Mills\n", end-start);
	}
}
