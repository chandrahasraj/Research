package student.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class Analyzer extends Constants {

	TreeMap<String, String> individualStack;
	String cur_name;
	static List<Double> deg1 = new ArrayList<>();
	static Map<String,List<Map<Double,Integer>>> quadrant_map=new TreeMap<>();
	static Map<Double,Integer> first_quadrant=new TreeMap<>();
	static Map<Double,Integer> second_quadrant=new TreeMap<>();
	static Map<Double,Integer> third_quadrant=new TreeMap<>();
	static Map<Double,Integer> fourth_quadrant=new TreeMap<>();
	

	Analyzer(String name) {
		super();
		this.cur_name = name;
		individualStack = new TreeMap<>();
		;
	}

	public static void main(String[] args) throws IOException, ParseException {
		List<Analyzer> objects = new ArrayList<>();
		for (File f : directory.listFiles()) {
			 if (!f.getName().startsWith(".")) {
			Analyzer obj = new Analyzer(f.getName().substring(0, f.getName().length() - 4));
			generateMagAndDeg(f, obj);
			System.out.println(f.getName());
/*			System.out.println("first quadrant siz="+first_quadrant+";second_quadrant size="+second_quadrant+""
					+ ";third quadrant size="+third_quadrant+";fourth quadrant size="+fourth_quadrant);*/
			addMapsToListAndErase(f.getName());
			//saveMapToFile(obj);
			objects.add(obj);
			// System.out.println(f.getName());
			// calculateVariations(deg1);
			// calculateVariations(deg2);
			//System.out.println("------------------------------");
			deg1.clear();
			 }
		}
		
		saveQuadrantMap();

		/*
		 * System.out.println(new Analyzer("").calculateDegree(10, 20));
		 * System.out.println(new Analyzer("").calculateDegree(-10, 20));
		 * System.out.println(new Analyzer("").calculateDegree(-10, -20));
		 * System.out.println(new Analyzer("").calculateDegree(10, -20));
		 * System.out.println("==-------------------------==");
		 * System.out.println(new Analyzer("").calculateDegree(10, 40));
		 * System.out.println(new Analyzer("").calculateDegree(-10, 40));
		 * System.out.println(new Analyzer("").calculateDegree(-10, -40));
		 * System.out.println(new Analyzer("").calculateDegree(10, -40));
		 * System.out.println("==-------------------------==");
		 * System.out.println(new Analyzer("").calculateDegree(20, 10));
		 * System.out.println(new Analyzer("").calculateDegree(-20, 10));
		 * System.out.println(new Analyzer("").calculateDegree(-20, -10));
		 * System.out.println(new Analyzer("").calculateDegree(20, -10));
		 * System.out.println("==-------------------------==");
		 * System.out.println(new Analyzer("").calculateDegree(40, 10));
		 * System.out.println(new Analyzer("").calculateDegree(-40, 10));
		 * System.out.println(new Analyzer("").calculateDegree(-40, -10));
		 * System.out.println(new Analyzer("").calculateDegree(40, -10));
		 */
	}

	private static void saveQuadrantMap() {
		for(String sensor:quadrant_map.keySet()){
			List<Map<Double,Integer>> list=quadrant_map.get(sensor);
			for(Map<Double,Integer> entry:list){
				File f=new File(Map_directory,list.indexOf(entry)+"_"+sensor.substring(0, sensor.length()-4)+".properties");
				saveQuadrantMap(entry,f);
			}
		}
	}
	
	static void saveQuadrantMap(Map<Double,Integer> total_store,File file){
		Properties properties = new Properties();

		for (Map.Entry<Double,Integer> entry : total_store.entrySet()) {
		    properties.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}

		try {
			properties.store(new FileOutputStream(file), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addMapsToListAndErase(String sensor) {
		List<Map<Double,Integer>> list=new ArrayList<>();
		Map<Double,Integer> f_map=new TreeMap<>();
		f_map.putAll(first_quadrant);
		Map<Double,Integer> s_map=new TreeMap<>();
		s_map.putAll(second_quadrant);
		Map<Double,Integer> t_map=new TreeMap<>();
		t_map.putAll(third_quadrant);
		Map<Double,Integer> fo_map=new TreeMap<>();
		fo_map.putAll(fourth_quadrant);
		
		list.add(f_map);
		list.add(s_map);
		list.add(t_map);
		list.add(fo_map);
		quadrant_map.put(sensor, list);
		first_quadrant.clear();
		second_quadrant.clear();
		third_quadrant.clear();
		fourth_quadrant.clear();
		
		for(Map.Entry<String, List<Map<Double,Integer>>>ee:quadrant_map.entrySet()){
		System.out.println(ee.getKey());
		List<Map<Double,Integer>>ll=ee.getValue();
		for(Map<Double,Integer> entry:ll){
			System.out.println(ll.indexOf(entry)+";"+entry);
		}
	}
	}

	private static void saveMapToFile(Analyzer obj) {
		Map<String, String> ldapContent = obj.individualStack;
		Properties properties = new Properties();

		for (Map.Entry<String, String> entry : ldapContent.entrySet()) {
			properties.put(entry.getKey(), String.valueOf(entry.getValue()));
		}

		try {
			properties.store(
					new FileOutputStream("/home/chandra/Documents/Data/mapObjects/" + obj.cur_name + ".properties"),
					null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void generateMagAndDeg(File f, Analyzer obj) throws IOException, ParseException {
		List<String> lines = FileUtils.readLines(f);
		boolean firstline = true;
//		int bb=10;
		for (String line : lines) {
			if (!firstline) {
				if (!line.split(",")[22].trim().equalsIgnoreCase("NA")
						&& !line.split(",")[23].trim().equalsIgnoreCase("NA")
						&& (line.split(",")[8] != null && !line.split(",")[8].isEmpty())) {
					totalCount++;
					float vn = Float.parseFloat(line.split(",")[22]);
					float ve = Float.parseFloat(line.split(",")[23]);

					String date_time = line.split(",")[8];

					String key = obj.generateKey(date_time);
					double mag = calculateMagnitude(vn, ve);
					double deg = obj.calculateDegree(vn, ve);
					
					createQuadrantMap(deg,mag,f.getName().substring(0, 6));
					
					deg1.add(deg);
					obj.individualStack.put(key, mag + "," + String.format("%.2f", deg));
				}
//				if(bb--==0)
//					break;
			}
			firstline = false;
		}
	}

	private static void createQuadrantMap(double deg, double mag, String sensor) {
		switch(sensor){
		case "AK_Dat":{
			if(deg>=270)
				fourth_quadrant=addToMap(fourth_quadrant,mag);
			else if(deg<=90)
				first_quadrant=addToMap(first_quadrant,mag);
			else if(deg>90 && deg<=180)
				second_quadrant=addToMap(second_quadrant,mag);
			else
				third_quadrant=addToMap(third_quadrant,mag);
			break;
		}
		case "D_Data":{
			if(deg>315 || deg <=45)
				first_quadrant=addToMap(first_quadrant,mag);
			else if(deg>45 && deg<=135)
				second_quadrant=addToMap(second_quadrant,mag);
			else if(deg>135 && deg<=225)
				third_quadrant=addToMap(third_quadrant,mag);
			else
				fourth_quadrant=addToMap(fourth_quadrant,mag);
			break;
		}
		case "B_Data":{
			if(deg <=90)
				first_quadrant=addToMap(first_quadrant,mag);
			else if(deg>90 && deg<=180)
				second_quadrant=addToMap(second_quadrant,mag);
			else if(deg>180 && deg<=270)
				third_quadrant=addToMap(third_quadrant,mag);
			else
				fourth_quadrant=addToMap(fourth_quadrant,mag);
			break;
		}
		case "K_Data":{
			if(deg <=90)
				first_quadrant=addToMap(first_quadrant,mag);
			else if(deg>90 && deg<=180)
				second_quadrant=addToMap(second_quadrant,mag);
			else if(deg>180 && deg<=270)
				third_quadrant=addToMap(third_quadrant,mag);
			else
				fourth_quadrant=addToMap(fourth_quadrant,mag);
			break;
		}
		case "C_Data":{
			if(deg>=270)
				fourth_quadrant=addToMap(fourth_quadrant,mag);
			else if(deg<=90)
				first_quadrant=addToMap(first_quadrant,mag);
			else if(deg>90 && deg<=180)
				second_quadrant=addToMap(second_quadrant,mag);
			else
				third_quadrant=addToMap(third_quadrant,mag);
			break;
		}
		case "AD_Dat":{
			if(deg>300 || deg <=30)
				first_quadrant=addToMap(first_quadrant,mag);
			else if(deg>30 && deg<=120)
				second_quadrant=addToMap(second_quadrant,mag);
			else if(deg>120 && deg<=210)
				third_quadrant=addToMap(third_quadrant,mag);
			else
				fourth_quadrant=addToMap(fourth_quadrant,mag);
			break;
		}
	}
	}
	
	static Map<Double,Integer> addToMap(Map<Double,Integer> map,double mag){
		int count=0;
		if(map.containsKey(mag))
			count=map.get(mag);
		map.put(mag, ++count);
		return map;
	}

	static protected void calculateVariations(List<Double> degrees) {
		System.out.println("Mean-->" + getMean(degrees));
		System.out.println("Variance-->" + getVariance(degrees));
		System.out.println("StdDev-->" + getStdDev(degrees));
	}

	static double getMean(List<Double> degrees) {
		double sum = 0.0;
		for (double a : degrees)
			sum += a;
		return sum / degrees.size();
	}

	static double getVariance(List<Double> degrees) {
		double mean = getMean(degrees);
		double temp = 0;
		for (double a : degrees)
			temp += (mean - a) * (mean - a);
		return temp / degrees.size();
	}

	static double getStdDev(List<Double> degrees) {
		return Math.sqrt(getVariance(degrees));
	}

	protected double calculateDegree(float vn, float ve) {
		double deg;
		if (ve != 0) {
			deg = Math.toDegrees(Math.atan(vn / ve));
			if (vn < 0 && ve < 0)// 3rd
				deg = 270 - deg;
			if (vn < 0 && ve > 0)// 2nd
				deg = 360 - deg + 90;
			if (vn > 0 && ve > 0)// 1st
				deg = 90 - deg;
			if (vn > 0 && ve < 0)// 4th
				deg = 360 - deg + 270;

			deg = deg > 360 ? deg % 360 : deg < 0 ? (deg % 360) + 360 : deg;
			if (deg < 0 && deg > 360)
				System.out.println(deg);
		} else {
			deg = vn > 0 ? 0 : 180;
		}
		return deg;
	}

	protected static final double calculateMagnitude(float vn, float ve) {
		return Math.round(Math.sqrt((vn * vn) + (ve * ve)));
	}

	protected String generateKey(String date_time) throws ParseException {
		calendar.setTime(sdf.parse(date_time));
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		// int month=calendar.get(Calendar.MONTH);
		long dt = 0l;
		dt = day * 10000 + calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
		String key = null;
		key = year + "-" + dt;
		return key;
	}

	protected static int adjustMagnitude(double mag, double deg) {
		if (!(deg > 0 && deg < 180)) {
			mag = mag / -1;
		}
		return mag > 0 ? 1 : 0;
	}

}
