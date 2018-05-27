package com.ibm.stg.pc.sym.si.util;

import java.util.ArrayList;

public class PainItem{
	 String itemname;
	 int current;
	 int baseline;
	 float changepercentage;
	 float score;
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getBaseline() {
		return baseline;
	}
	public void setBaseline(int baseline) {
		this.baseline = baseline;
	}
	public float getChangepercentage() {
		return changepercentage;
	}
	public void setChangepercentage(float changepercentage) {
		this.changepercentage = changepercentage;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
	public float getScore(ArrayList<float[]> pmatrix, int index){
		for (int t=0; t< pmatrix.get(index).length - 1;t++){
			this.changepercentage  = (float)(this.current - this.baseline)/this.baseline;
			//System.out.println("pmatrix LHS = "+pmatrix.get(0)[t] + " RHS = "+pmatrix.get(0)[t+1]);
			if (changepercentage <= pmatrix.get(0)[0] ){
				this.score = pmatrix.get(index)[0];
				return score;
			}
			
			if (changepercentage > pmatrix.get(0)[pmatrix.get(index).length -1] ){
				this.score = pmatrix.get(index)[pmatrix.get(index).length -1];
				return score;
			}
			
			if( (pmatrix.get(0)[t] <= changepercentage) && (changepercentage <= pmatrix.get(0)[t+1])){
				//System.out.println("hit t = "+t+" index = "+index);
				this.score = pmatrix.get(index)[t+1];
				return score;
			}
		}
		return 0;
	}
	
	public void showPainItem(){
		System.out.println("Item [current] = "+current+" [baseline] = "+baseline+" [ChangePercentage] = "+changepercentage+ " [Score] = "+score);
	}
	
}
