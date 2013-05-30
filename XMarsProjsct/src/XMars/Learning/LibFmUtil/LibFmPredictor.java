package XMars.Learning.LibFmUtil;

import java.util.ArrayList;

import XMars.Learning.Common.EncodedItem;
import XMars.Learning.Common.IPredictor;
import XMars.Learning.Common.Model;

public class LibFmPredictor implements IPredictor {

	
	@Override
	public double Predict(EncodedItem encodedItem, Model model) 
	{
		double prob = 0.0;
		double base = model._bias;
		int dimensions = encodedItem.Features.get(0).FeatureId*encodedItem.Features.get(0).LatentFeature.size();
		
		for (int i=0;i<encodedItem.Features.size();i++) {
			if (model._model.containsKey(encodedItem.Features.get(i).FeatureId)) {
				base += model._model.get(encodedItem.Features.get(i).FeatureId)*encodedItem.Features.get(i).FeatureValue;
			}
		}
		
		double sum = 0.0;
		for (int i=0; i<dimensions; i++) {
			double sum1 = 0.0;
			double sum2 = 0.0;
			for (int j=0;j<encodedItem.Features.size();j++) {
				ArrayList<Double> list = model._latentModel.get(encodedItem.Features.get(i).FeatureId);
				sum1 += encodedItem.Features.get(j).FeatureValue * list.get(j);
				sum2 += Math.pow(encodedItem.Features.get(j).FeatureValue * list.get(j), 2);
			}
			sum += sum1*sum1 - sum2;
		}
		sum *= 0.5;
		
		return base + sum;
	}

}
