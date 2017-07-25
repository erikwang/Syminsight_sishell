package com.ibm.stg.pc.sym.si.widget;

import java.util.ArrayList;

import com.ibm.stg.pc.sym.si.util.CustomerPainTable;
import com.ibm.stg.pc.sym.si.util.PainItem;


public class CustomerPain {

	static ArrayList<float[]> customera = new ArrayList<>();
	
	static float[] PainMatrix = new float[]{
			-1,(float) -0.75,(float) -0.5,(float) -0.25,0,(float) 0.25,(float) 0.5,(float) 0.75,1
	};
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		customera.add(PainMatrix);
		updateMatrix(1,20);
		updateMatrix(2,40);
		updateMatrix(3,20);
		updateMatrix(4,20);
		updateMatrix(5,20);
		updateMatrix(6,40);
		updateMatrix(7,40);
		showMatrix();
		
		
		CustomerPainTable ptable = new CustomerPainTable();
		
		
		for(int i = 1; i< customera.size(); i++){
			PainItem pitem = new PainItem();
			pitem.setCurrent(0);
			pitem.setBaseline(0);
			pitem.getScore(customera,i);
			ptable.setPainItem(pitem, i);
		}
		//pitem.getScore(customera, 6);
		//ptable.setPainItem(pitem, 0);
		
		ptable.showPainTable();
		System.out.println("Total score = "+ptable.getTotalScore());

	}

		
	public static void setCustomerValue(float item[]){

	}
	
	public static void updateMatrix(int row, float base){
		float[] test = new float[PainMatrix.length];
		for(int t = 0; t < PainMatrix.length;t++){
			test[t] = (float)(base/8) * t; 	
		}
		customera.add(test);
	}
	
	public static void showMatrix(){
		System.out.println(customera.size());
		for(int t = 0 ; t < customera.size(); t++){
			if(t%9 == 0){
				System.out.println("-----------------");
			}
			for(int tt = 0; tt < customera.get(t).length;tt++){
				System.out.print(customera.get(t)[tt]+",");
			}
			System.out.println("\n----------------");
		}
	}

}
