package student.analysis;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.TreeSet;

public class Constants {
	final static Calendar calendar = Calendar.getInstance();
	final static File directory = new File("/home/chandra/Documents/Data/Sub-Data");
	final static File newFile = new File("/home/chandra/Documents/200611d.seq");
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	TreeMap<String, ArrayList<String>> intialMap = new TreeMap<>();
	TreeMap<String, TreeSet<String>> wastedMap = new TreeMap<>();
	static ArrayList<Double[]> v2 = new ArrayList<>();
	static ArrayList<Double[]> v3 = new ArrayList<>();
	int count = 0, notwasted = 0;
	static int totalCount=0;
	final static int rangeADL=-9,rangeADH=-1,rangeAKL=-5,rangeAKH=-1,rangeDL=-2,rangeDH=-1;
}
