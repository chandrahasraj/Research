package student.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class PreprocessingData extends Constants{

	public static void main(String[] args) {
		Map<String,Set<String>> finalMap=new ConcurrentHashMap<>();
		Set<String> ordered_map=new TreeSet<>();
		Map<String,Integer> individual_count=new TreeMap<>();;
		for(File file:Map_directory.listFiles()){
			Properties prop=new Properties();
			try {
				prop.load(new FileInputStream(file));
				for(String key:prop.stringPropertyNames()){
					ordered_map.add(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int count=0;
		for(File file:Map_directory.listFiles()){
			Properties prop=new Properties();
			try {
				prop.load(new FileInputStream(file));
				for(String key:ordered_map){
					if((prop.get(key))==null){
						String previous_value= checkForPreviousKey(key,prop);
						String next_value= checkForNextKey(key,prop);
						String insert_val=null;
						if(previous_value==null&&next_value!=null)
							insert_val=next_value;
						else if(previous_value!=null&&next_value==null)
							insert_val=previous_value;
						else if(previous_value!=null&&next_value!=null)
							insert_val=getAverage(previous_value,next_value);
						if(insert_val!=null){
//							System.out.println(insert_val+","+file.getName());
							checkAndInsetValueIntoMap(finalMap,key,insert_val+","+file.getName().substring(0,6));
							
						}
						
					}else{
						count++;
						int counter=individual_count.containsKey(file.getName())?individual_count.get(file.getName()):0;
						individual_count.put(file.getName(), ++counter);
						String value=prop.get(key).toString();
						checkAndInsetValueIntoMap(finalMap,key,value+","+file.getName().substring(0,6));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(ordered_map.size());
		System.out.println(individual_count);
		System.out.println(finalMap.size());
		System.out.println(count);
		for(Map.Entry<String,Set<String>> entry:finalMap.entrySet()){
			Set<String> value=entry.getValue();
			String key=entry.getKey();
			int size=value.size();
			if(size!=5){
				finalMap.remove(key);
			}
		}
		System.out.println(finalMap.size());
		new PreprocessingData().saveMapToFile(finalMap,new File(Map_directory,"final_with_interpolation.properties"));
//		new PreprocessingData().saveMapToFile(finalMap,new File(Map_directory,"remaining.properties"));
	}
	
	private static void checkAndInsetValueIntoMap(Map<String, Set<String>> finalMap, String key, String value) {
		Set<String> list;
		if(finalMap.containsKey(key)){
			list=finalMap.get(key);
		}else{
			list=new TreeSet<>();
		}
		list.add(value);
		finalMap.put(key, list);
	}

	static String getAverage(String previous,String next){
		String prev[]=previous.split(",");
		String nex[]=next.split(",");
		float avg[]=new float[2];
		avg[0]=(Float.valueOf(prev[0])+Float.valueOf(nex[0]))/2;
		avg[1]=(Float.valueOf(prev[1])+Float.valueOf(nex[1]))/2;
		return avg[0]+","+avg[1];
	}
	
	static String checkForPreviousKey(String origin_key,Properties prop){
		String temp[]=origin_key.split("-");
		long dt=Long.valueOf(temp[1]);
		String year=temp[0];
		for(long i=dt;i>(dt-60);i=i-5){
			String key=year+"-"+i;
			if(prop.get(key)!=null){
				return prop.get(key).toString();
			}
		}
		return null;
	}
	
	static String checkForNextKey(String origin_key,Properties prop){
		String temp[]=origin_key.split("-");
		long dt=Long.valueOf(temp[1]);
		String year=temp[0];
		for(long i=dt;i<(dt+60);i=i+5){
			String key=year+"-"+i;
			if(prop.get(key)!=null){
				return prop.get(key).toString();
			}
		}
		return null;
	}

}
