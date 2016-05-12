package student.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class GraphGenerator extends Constants {

	public static void main(String[] args) {
		Properties prop = new Properties();
		HashMap<Float, Float> map = new HashMap<>();
		for (File f : Map_directory.listFiles()) {
			try {
				
				prop.load(new FileInputStream(f));
				for (String key : prop.stringPropertyNames()) {
					String value = (String) prop.get(key);
					String two = value.split(",")[1];
					String one = value.split(",")[0];
					float degree = Float.valueOf(two);
					float mag = Float.valueOf(one);
					if (f.getName().startsWith("AD_Data")) {
						if (degree > 315 && degree < 135)
							insertElementsIntoMap(map, mag);
						else
							insertElementsIntoMap(map, (mag * (-1)));
					} else if (f.getName().startsWith("D_Data")) {
						if (degree > 45 && degree < 225)
							insertElementsIntoMap(map, mag);
						else
							insertElementsIntoMap(map, (mag * (-1)));
					} else if (f.getName().startsWith("AK_Data")) {
						if (degree > 270 && degree < 90)
							insertElementsIntoMap(map, mag);
						else
							insertElementsIntoMap(map, (mag * (-1)));
					} else if (f.getName().startsWith("K_Data")) {
						if (degree > 180 && degree < 0)
							insertElementsIntoMap(map, mag);
						else
							insertElementsIntoMap(map, (mag * (-1)));
					} else if (f.getName().startsWith("B_Data")) {
						if (degree > 180 && degree < 0)
							insertElementsIntoMap(map, mag);
						else
							insertElementsIntoMap(map, (mag * (-1)));
					} else if (f.getName().startsWith("C_Data")) {
						if (degree > 270 && degree < 90)
							insertElementsIntoMap(map, mag);
						else
							insertElementsIntoMap(map, (mag * (-1)));
					}
				}
				System.out.println(f.getName());
				System.out.println(map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static void insertElementsIntoMap(HashMap<Float, Float> map, float mag) {
		float count = 1;
		if (map.containsKey(mag)) {
			count = map.get(mag);
			count++;
		}
		map.put(mag, count);
	}

}
