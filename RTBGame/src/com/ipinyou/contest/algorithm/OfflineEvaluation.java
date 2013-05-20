package com.ipinyou.contest.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
/*****
 * we'll calc your score like this.
 * this class is just for illustrating.
 */
public class OfflineEvaluation {

	public static void main(String[] args) throws Exception {

		int score = 0;
		int expend = 0;
		int maxExpend = 1000 * 100;// 1000 yuan

		// set up your algorithm here.
		Algorithm algorithm = new Bid();
		algorithm.init();

		String evaluateFile = "/data/evaluate.log";
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(new File(
				evaluateFile)));
		while ((line = br.readLine()) != null) {
			if(expend >= maxExpend){//you have reached the max budget
				break;
			}
			BidRequest bidRequest = mockBidRequest(line);
			int bidPrice = algorithm.getBidPrice(bidRequest);
			if (bidPrice == -1)
				continue;// do not bid this time.
			if (bidPrice > 0) {
				int playingPrice = getPlayingPriceFrom(line);
				if (bidPrice > playingPrice) {
					int clickNum = getClickNumFrom(line);
					int convNum = getConvNumFrom(line);
					
					score += clickNum + convNum * 50;
					expend += playingPrice;
				}
			}
		}
		System.out.println("Evaluate Finished.Your Score Is [" + score + "]");
	}

	private static int getConvNumFrom(String line) {
		return 0;
	}

	private static int getClickNumFrom(String line) {
		return 0;
	}

	private static int getPlayingPriceFrom(String line) {
		return 0;
	}

	/*****
	 * mock a bid request from our evaluate log.
	 * 
	 * @param line
	 * @return
	 */
	private static BidRequest mockBidRequest(String line) {
		return null;
	}

}
