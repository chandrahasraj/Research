package student.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class Constants {
	final static Calendar calendar = Calendar.getInstance();
	final static File directory = new File("/home/chandra/Documents/Data/Sub-Data");
	final static File newFile = new File("/home/chandra/Documents/Data/changedSeq.seq");
	final static File seq_file = new File("/home/chandra/Documents/Data/mapObjects/test_five.seq");
	final static File Map_directory=new File("/home/chandra/Documents/Data/mapObjects");
	final static File complete_map=new File("/home/chandra/Documents/Data/mapObjects/Complete_Set.properties");
	final static File single_sets=new File("/home/chandra/Documents/Data/mapObjects/sets/single_set.properties");
	final static File two_sets=new File("/home/chandra/Documents/Data/mapObjects/sets/two_sets.properties");
	final static File three_sets=new File("/home/chandra/Documents/Data/mapObjects/sets/three_sets.properties");
	final static File four_sets=new File("/home/chandra/Documents/Data/mapObjects/sets/four_sets.properties");
	final static File five_sets=new File("/home/chandra/Documents/Data/mapObjects/sets/five_sets.properties");
	final static File six_sets=new File("/home/chandra/Documents/Data/mapObjects/sets/six_sets.properties");
	final static File sets_folder=new File("/home/chandra/Documents/Data/mapObjects/sets");
	
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	TreeMap<String, ArrayList<String>> intialMap = new TreeMap<>();
	TreeMap<String, TreeSet<String>> wastedMap = new TreeMap<>();
	TreeMap<String,List<Double>> individualStack=new TreeMap<>();
	static ArrayList<Double[]> v2 = new ArrayList<>();
	static ArrayList<Double[]> v3 = new ArrayList<>();
	int count = 0, notwasted = 0;
	static int totalCount=0;
	final static int rangeADL=-9,rangeADH=-1,rangeAKL=-5,rangeAKH=-1,rangeDL=-2,rangeDH=-1;
	final static int range_FLOW_UP=1,range_FLOW_DOWN=-1,range_No_FLOW=0;
	
	void saveMapToFile(Map<String,Set<String>> total_store,File file) {
		Properties properties = new Properties();

		for (Map.Entry<String,Set<String>> entry : total_store.entrySet()) {
		    properties.put(entry.getKey(), StringUtils.join(entry.getValue(),";"));
		}

		try {
			properties.store(new FileOutputStream(file), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void saveMap(Map<String,String> total_store,File file){
		Properties properties = new Properties();

		for (Map.Entry<String,String> entry : total_store.entrySet()) {
		    properties.put(entry.getKey(), entry.getValue());
		}

		try {
			properties.store(new FileOutputStream(file), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
