package student.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeSet;

public class MapInserter extends Utils {

	 void modifyUnequalKeyInsertions() {
		int savepointAK, savepointD, savepointAD = 0;
		for (Map.Entry<String, TreeSet<String>> ee : wastedMap.entrySet()) {
			TreeSet<String> arr = ee.getValue();
			savepointAK = savepointAD;
			savepointD = savepointAD;
			ArrayList<Double> degreeSet = new ArrayList<>();
			for (String values : arr) {
				String all[] = values.split(",");
				double mag = Double.parseDouble(all[1]), deg = Double.parseDouble(all[2]);
				double degll=deg + 90 > 360 ? deg % 360 : deg + 90,
						deghh=deg - 90 < 0 ? (deg % 360) + 360 : deg - 90;
				
				String type = all[0];
				try {
					if (type.startsWith("AD")) {
						Double[] dd = new Double[3];
						dd[0] = new Double(0);
						dd[1] = new Double(0);
						dd[2] = new Double(0);
						dd[0] = (double) (mag > rangeADH? 2 : mag < rangeADL ? -1 : 1);
						v3.add(dd);
						degreeSet.add(deg);
						savepointAD++;
					} else if (type.startsWith("AK")) {
						Double[] dd = new ArrayList<>(v3).get(savepointAK);
						double akdeg = new ArrayList<>(degreeSet).get(savepointAK);
						mag=adjustMagnitude(degll,deghh,mag,akdeg);
						dd[1] = (double) (mag > rangeAKH ? 2 : mag < rangeAKL ? -1 : 1);
						v3.add(savepointAK, dd);
						savepointAK++;
						if (v3.size() != 1)
							v3.remove(savepointAK);
					} else {
						Double[] dd = new ArrayList<>(v3).get(savepointD);
						double ddeg = new ArrayList<>(degreeSet).get(savepointD);
						mag=adjustMagnitude(degll,deghh,mag,ddeg);
						
						dd[2] = (double) (mag > rangeDH ? 2 : mag < rangeDL ? -1 : 1);
						v3.add(savepointD, dd);
						savepointD++;
						if (v3.size() != 1)
							v3.remove(savepointD);
					}
				} catch (IndexOutOfBoundsException e) {
					count--;
				}

			}
		}
	}

	 void modifyEqualKeyInsertions() {
		System.out.println("Size stored in map:"+intialMap.size());
		for (Map.Entry<String, ArrayList<String>> ee : intialMap.entrySet()) {
			ArrayList<String> arr = ee.getValue();
			arr.removeAll(Collections.singleton(null));
			if (arr.size() == 3) {
//				System.out.println("found");
				String ad = arr.get(0), ak = arr.get(1), d = arr.get(2);
				String adarr[] = ad.split(","), akarr[] = ak.split(","), darr[] = d.split(",");
				double addeg = Double.parseDouble(adarr[2]), akdeg = Double.parseDouble(akarr[2]),
						ddeg = Double.parseDouble(darr[2]);
				double admag = Double.parseDouble(adarr[0]), akmag = Double.parseDouble(akarr[0]),
						dmag = Double.parseDouble(darr[0]);
				double degll=addeg + 90 > 360 ? addeg % 360 : addeg + 90,
						deghh=addeg - 90 < 0 ? (addeg % 360) + 360 : addeg - 90;
				akmag=adjustMagnitude(degll,deghh,akmag,akdeg);
				akmag=adjustMagnitude(degll,deghh,dmag,ddeg);
				
				Double[] tt = new Double[3];
				tt[0] = admag < rangeADL ? new Double(-1) : admag > rangeADH ? 2 : 1;
				tt[1] = akmag < rangeAKL ? new Double(-1) : akmag > rangeAKH ? 2 : 1;
				tt[2] = dmag < rangeDL ? new Double(-1) : dmag > rangeDH ? 2 : 1;
				v2.add(tt);
				notwasted++;
			} else {
				String keys[] = ee.getKey().split("-");
				long dt = Long.valueOf(keys[1]) / 10000;
				String newKey = keys[0] + "-" + dt;
				if (wastedMap.containsKey(newKey)) {
					TreeSet<String> newValue = wastedMap.get(newKey);
					for (String s : arr) {
						String parr[] = s.split(",");
						String newValues = parr[1] + "," + parr[0] + "," + parr[2];
						newValue.add(newValues);
					}
				} else {
					TreeSet<String> newValue = new TreeSet<>();
					for (String s : arr) {
						String parr[] = s.split(",");
						String newValues = parr[1] + "," + parr[0] + "," + parr[2];
						newValue.add(newValues);
					}
					wastedMap.put(newKey, newValue);
				}
				count++;
			}
		}
	}

	void insertIntoMap(double mag, String key, double degrees,String fileName) {
		if (!intialMap.containsKey(key)) {
			ArrayList<String> ll = new ArrayList<>();
			if (fileName.startsWith("AD")){
				ll.add(0, mag + ",AD#" + key + "," + degrees);
				}
			else if (fileName.startsWith("AK")) {
				ll.add(0, null);
				ll.add(1, mag + ",AK#" + key + "," + degrees);
			} else {
				ll.add(0, null);
				ll.add(1, null);
				ll.add(2, mag + ",D#" + key + "," + degrees);
			}
			intialMap.put(key, ll);
		} else {
			ArrayList<String> ll = intialMap.get(key);
			if (fileName.startsWith("AD")){
				ll.add(0, mag + ",AD#" + key + "," + degrees);
			}
			else if (fileName.startsWith("AK")) {
				if (ll.get(0) == null)
					ll.add(0, null);
				ll.add(1, mag + ",AK#" + key + "," + degrees);
			} else {
				if (ll.get(0) == null)
					ll.add(0, null);
				if (ll.size() == 1)
					ll.add(1, null);
				ll.add(2, mag + ",D#" + key + "," + degrees);
			}
			intialMap.put(key, ll);
		}
	}
}
