package XMars.Learning.LibFmUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import XMars.FileUtil.FileUtil;
import XMars.Learning.Common.Model;

public class LibFmModel extends Model {
	FileUtil _fileUtil = new FileUtil();
	
	@Override
	public void LoadFromFile(String filePath) {
		// TODO Auto-generated method stub
		InputStream inputStream = _fileUtil.read(filePath);
		String line = null;
		String fields[];
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			int index = 0;
			while ((line=br.readLine()) != null) {
				if (line.matches("^#.*") || !line.matches("^\\d+(\\.\\d+)?\\s[.]*$") || line.trim().endsWith("")) {
					continue;
				}
				fields = line.trim().split("\t");
				if (fields.length < 10 && index>0) {
					System.out.println("[Error][LibFm model is illegal]");
					System.exit(-1);
				}
				
				if (index > 0) {
					if (_latentModel.containsKey(index)) {
						for (int i=1;i<fields.length;i++) {
							_latentModel.get(index).add(Double.parseDouble(fields[i]));
						}
					}
					else {
						_latentModel.put(index, new ArrayList<Double>());
					}
				}
				else {
					_bias =  Double.parseDouble(fields[0]);
				}
				index ++;
				
				_model.put(index, Double.parseDouble(line));
			}
		} catch (Exception e) {
		}
	}

	public ArrayList<Double> getLatentFeatureWeight(int featureId) {
		if (_latentModel.containsKey(featureId)) {
			return _latentModel.get(featureId);
		}
		return null;
	}
}
