package com.ibm.stg.pc.sym.si.util;

import java.util.ArrayList;

public class CustomerPainTable {
	ArrayList<PainItem> PainTable;
	String index;
	
	public CustomerPainTable() {
		PainTable = new ArrayList<PainItem>();
		this.index = index;
	}

	public void setPainItem(PainItem pitem, int index){
		PainTable.add(pitem);
		//PainTable.set(index, pitem);
	}
	
	public void showPainTable(){
		for (int t = 0; t < PainTable.size();t++){
			PainTable.get(t).showPainItem();
		}
	}
	
	public float getTotalScore(){
		float total = 0;
		for (int t = 0; t < PainTable.size();t++){
			total = total + PainTable.get(t).getScore();
		}
		return total;
	}
}
