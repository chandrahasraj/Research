package student.analysis;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class SequenceFileGenerator extends Utils{
	public static void main(String[] args) throws IOException, ParseException {
		SequenceFileGenerator obj=new SequenceFileGenerator();
		MapInserter inserter=new MapInserter();

		for (File f : directory.listFiles()) {
			if (f.getName().endsWith(".csv")
					&& (f.getName().startsWith("AD") || f.getName().startsWith("AK") || f.getName().startsWith("D"))) {
				List<String> lines = FileUtils.readLines(f);
				boolean firstline = true;
				for (String line : lines) {
					if (!firstline) {
						if (!line.split(",")[22].trim().equalsIgnoreCase("NA")
								&& !line.split(",")[23].trim().equalsIgnoreCase("NA")
								&& (line.split(",")[8] != null && !line.split(",")[8].isEmpty())) {
							totalCount++;
							float vn = Float.parseFloat(line.split(",")[22]);
							float ve = Float.parseFloat(line.split(",")[23]);
							String date_time=line.split(",")[8];
							
							String key=obj.generateKey(vn,ve,date_time);
							if(key!=null){
								double degrees=obj.calculateDegree(vn,ve);
								double mag=calculateMagnitude(vn,ve);
								
								if(!(degrees<90&&degrees>270))
									mag=mag/-1;
								inserter.insertIntoMap(mag,key,degrees,f.getName());
							}
						}
					}
					firstline = false;
				}
			}
		}
		
		inserter.modifyEqualKeyInsertions();
		inserter.modifyUnequalKeyInsertions();
		List<String> temp=obj.generateBase3Values();
		List<Integer> add = new ArrayList<>();
		add.addAll(obj.compareAndSaveBase3ValuesAsDecimals(temp,v3));
		add.addAll(obj.compareAndSaveBase3ValuesAsDecimals(temp,v2));
		FileUtils.writeStringToFile(newFile, StringUtils.join(add, ","), true);
	}
	
	/*private Collection<? extends Integer> compareAndSaveRemainingValuesAsDecimals(List<String> temp) {
		List<Integer> add=new ArrayList<>();
		for (Map.Entry<String, Double[]> pp : v2.entrySet()) {
			Double[] temp1 = pp.getValue();
			String s = Integer.parseInt(String.valueOf(temp1[0]).substring(0, 1)) + ""
					+ Integer.parseInt(String.valueOf(temp1[1]).substring(0, 1)) + ""
					+ Integer.parseInt(String.valueOf(temp1[2]).substring(0, 1));
			int decimal = temp.indexOf(Integer.toString(Integer.parseInt(s, 3), 3));
			add.add(decimal);
		}
		return add;
	}*/

}