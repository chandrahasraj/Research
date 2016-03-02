package student.analysis;

import java.util.Random;

public class RandomNumberGenerator {

	public static void main(String[] args) {
		Random r=new Random();
		int value=27;
		int ar[]=new int[value];
		int sum=0;
		for(int i=0;i<value;i++){
			ar[i]=(r.nextInt(value)+1);
			sum+=ar[i];
		}
		System.out.println(sum);
		float te=0;
		for(Integer i:ar){
			float f=(new  Float(i))/sum;
			System.out.print(f+",");
			te+=f;
		}
		System.out.println();
		System.out.println(te);
	}

}
