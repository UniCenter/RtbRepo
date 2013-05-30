package XMars.Learning.Common;

import java.util.ArrayList;
import java.util.List;

public class LatentFeature {
	private int _dimensions = 20;
	private List<Integer> _latent_feature = new ArrayList<Integer>();
	
	LatentFeature() {
		
	}
	
	LatentFeature(int dimensions) {
		_dimensions = dimensions;
	}
	
	public void setDimensions(int dimensions) {
		_dimensions = dimensions;
	}
	
	public int getDimensions() {
		return this._dimensions;
	}
	
	public List<Integer> getLatentFeature() {
		return _latent_feature;
	}
	
}
