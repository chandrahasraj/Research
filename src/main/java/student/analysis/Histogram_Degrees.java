package student.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class Histogram_Degrees extends Constants{

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Map<String,Map<String,String>> map=new TreeMap<>();
		for(File f:Map_directory.listFiles()){
			Map<String,String> mp=new TreeMap<>();
			String name=f.getName().substring(0,6);
			if(map.containsKey(name)){
				mp=map.get(name);
			}
			map.put(name,generateMap(f,mp));
		}
		for(Map.Entry<String,Map<String,String>> entry:map.entrySet()){
			new Histogram_Degrees().saveMap(entry.getValue(),new File(Map_directory,"histogram_"+entry.getKey()+".properties"));
		}
	}
	
	public static Map<String,String> generateQuadrantMap(File file,Map<String,String> map) throws FileNotFoundException, IOException{
		Properties prop=new Properties();
		prop.load(new FileInputStream(file));
		for(String p_key:prop.stringPropertyNames()){
			String values[]=((String)prop.get(p_key)).split(",");
			double deg=Math.round(Double.valueOf(values[1]));
			double mag=Math.round(Double.valueOf(values[0]));
			String k=String.valueOf(deg);
			int count=0;
			if(map.containsKey(k)){
				count=Integer.valueOf(map.get(k));
			}
			map.put(k, String.valueOf(++count));
		}
		return map;
	}
	
	static void setArrayBasedOnSensorName(String sensor,int[] array,float deg){
		switch(sensor){
			case "AK_Dat":{
				array[1]=deg>270||deg<90?1:0;
				break;
			}
			case "D_Data":{
				array[4]=deg>45&&deg<225?1:0;
				break;
			}
			case "B_Data":{
				array[2]=deg<180?0:1;
				break;
			}
			case "K_Data":{
				array[1]=deg<180?1:0;
				break;
			}
			case "C_Data":{
				array[3]=deg<90||deg>270?1:0;
				break;
			}
			case "AD_Dat":{
				array[0]=deg<135||deg>300?1:0;
				break;
			}
		}
	}
	
	public static Map<String,String> generateMap(File file,Map<String,String> map) throws FileNotFoundException, IOException{
		Properties p=new Properties();
		p.load(new FileInputStream(file));
		for(String key:p.stringPropertyNames()){
			String values[]=((String)p.get(key)).split(",");
			double deg=Math.round(Double.valueOf(values[1]));
			String k=String.valueOf(deg);
			int count=0;
			if(map.containsKey(k)){
				count=Integer.valueOf(map.get(k));
			}
			map.put(k, String.valueOf(++count));
		}
		return map;
	}

}
