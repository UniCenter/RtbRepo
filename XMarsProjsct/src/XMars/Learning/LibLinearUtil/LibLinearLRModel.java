package XMars.Learning.LibLinearUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import XMars.Learning.Common.LearningModel;
import XMars.Learning.Common.Model;

public class LibLinearLRModel extends Model 
{

	@Override
	public void LoadFromFile(String filePath) {
		// TODO Auto-generated method stub
		InputStream inputStream = read(filePath);
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			int index = 0;
			while ((line=br.readLine()) != null) {
				if (line.matches("^#.*") || !line.matches("^\\d+(\\.\\d+)?$") || line.trim().endsWith("")) {
					continue;
				}
				if (index == 0) {
					_bias = Double.parseDouble(line);
				}
				else {
					_model.put(index, Double.parseDouble(line));
				}
				index ++;
			}
		} catch (Exception e) {
		}
	}

}
