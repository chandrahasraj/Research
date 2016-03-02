package student.analysis;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Utils extends Constants{
	protected List<Integer> compareAndSaveBase3ValuesAsDecimals(List<String> temp, ArrayList<Double[]> v3){
		List<Integer> add=new ArrayList<>();
		for (Double[] dle : v3) {
			try {
				if (dle[0] != 0 && dle[1] != 0 && dle[2] != 0) {
					dle[0]=dle[0]==-1?new Double(0):dle[0];
					dle[1]=dle[1]==-1?new Double(0):dle[1];
					dle[2]=dle[2]==-1?new Double(0):dle[2];
					String s = Integer.parseInt(String.valueOf(dle[0]).substring(0, 1)) + ""
							+ Integer.parseInt(String.valueOf(dle[1]).substring(0, 1)) + ""
							+ Integer.parseInt(String.valueOf(dle[2]).substring(0, 1));
					int decimal = temp.indexOf(Integer.toString(Integer.parseInt(s, 3), 3));
					add.add(decimal);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return add;
	}

	protected List<String> generateBase3Values() {
		System.out.println("wasted:" + count + ",Not wasted:" + (notwasted+wastedMap.size())+", TotalCount:"+totalCount);
		List<String> temp = new ArrayList<>();
		for (int i = 0; i < 27; i++) {
			String value = Integer.toString(i, 3);
			temp.add(value);
		}
		return temp;
	}


	protected double calculateDegree(float vn, float ve) {
		double deg;
		if (ve != 0) {    
			deg = Math.toDegrees(Math.atan(vn / ve));
			deg = deg > 360 ? deg % 360
					: deg < 0 ?(deg%360)+360:deg;
		} else
			deg = 0;
		return deg;
	}

	protected static final double calculateMagnitude(float vn, float ve) {
		return Math.sqrt((vn * vn) + (ve * ve));
	}

	protected String generateKey(float vn, float ve, String date_time) throws ParseException {
		calendar.setTime(sdf.parse(date_time));
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		int month=calendar.get(Calendar.MONTH);
		long dt=0l;
		dt = day * 10000 + calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
		String key=null;
		if(year==2006&&month==11)
			key = year + "-" + dt;
		return key;
	}

	protected static double adjustMagnitude(double degll, double deghh, double mag, double deg) {
		if(degll>deghh){
			if(!(deg<degll&&deg>deghh)){
				mag=mag/-1;
			}
		}else{
			if(!(deg>degll&&deg<deghh)){
				mag=mag/-1;
			}
		}
		return mag;
	}
}


