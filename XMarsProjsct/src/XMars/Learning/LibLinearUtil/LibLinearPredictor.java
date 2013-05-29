package XMars.Learning.LibLinearUtil;

import XMars.Learning.Common.EncodedItem;
import XMars.Learning.Common.IPredictor;
import XMars.Learning.Common.Model;

public class LibLinearPredictor implements IPredictor {

	
	@Override
	public double Predict(EncodedItem encodedItem, Model model) 
	{
		double prob = 0.0;
		double base = 0.0;
		for (int i=0;i<encodedItem.Features.size();i++) {
			if (model._model.containsKey(encodedItem.Features.get(i).FeatureId)) {
				base += model._model.get(encodedItem.Features.get(i).FeatureId);
			}
		}
		
		base += model._bias;
		prob = 1/(1+Math.exp(-base));
		
		return prob;
	}

}
