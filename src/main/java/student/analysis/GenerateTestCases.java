package student.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class GenerateTestCases extends Constants{

static List<Integer> all_data=new LinkedList<>();
	
	public static void main(String as[]){
		sequenceCalculator(new File(Map_directory,"final_with_interpolation.properties"));
	}
	
	static void sequenceCalculator(File file){
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(file));
			for(String key:prop.stringPropertyNames()){
				String temp[]=key.split("-");
				String year=temp[0];
				long dt=Long.valueOf(temp[1]);
				int day=(int)dt/10000;
				Calendar c=Calendar.getInstance();
				c.set(Calendar.DAY_OF_YEAR, day);
//				System.out.println("day-->"+day+",month-->"+c.get(Calendar.MONTH)+",year-->"+year);
				if(year.equals("2011")&&(c.get(Calendar.MONTH)==9)){
					String value=prop.get(key).toString();
					List<String> values=Arrays.asList(value.split(";"));
					if(values.size()==5){
					int[] base2=new int[5];
					base2[0]=-1;base2[1]=-1;base2[2]=-1;base2[3]=-1;base2[4]=-1;
					for(String loc:values){
						String ar[]=loc.split(",");
						float deg=Float.valueOf(ar[1]);
	//					int mag=(deg>0&&deg<180)?1:0;
						String sensor=ar[2];
						setArrayBasedOnSensorName(sensor, base2, deg);
					}
					String s_temp="";
					for(int in_temp:base2){
						s_temp+=in_temp;
					}
					all_data.add(Integer.parseInt(s_temp,2));
					}
				}
			}
			FileUtils.writeStringToFile(seq_file, StringUtils.join(all_data,","));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void setArrayBasedOnSensorName(String sensor,int[] array,float deg){
		switch(sensor){
			case "AK_Dat":{
				array[1]=deg>0&&deg<180?1:0;
				break;
			}
			case "D_Data":{
				array[4]=deg>0&&deg<180?0:1;
				break;
			}
			case "B_Data":{
				array[2]=deg>90&&deg<270?1:0;
				break;
			}
			case "C_Data":{
				array[3]=deg>0&&deg<180?1:0;
				break;
			}
			case "AD_Dat":{
				array[0]=deg>0&&deg<180?1:0;
				break;
			}
		}
	}

}
