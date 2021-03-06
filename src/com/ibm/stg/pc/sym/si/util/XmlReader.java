package com.ibm.stg.pc.sym.si.util;

import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;


public class XmlReader{

		public XmlReader(){}
		public String getFromConf(String para,String fname){
								
								String returnval;
								returnval = "";
								try{
												
												DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
												DocumentBuilder builder = dbfactory.newDocumentBuilder();
												Document doc = builder.parse(new File(fname));
												Element root = doc.getDocumentElement();
												NodeList nl = root.getElementsByTagName(para);
												//NodeList dtable = ((Element)modules.item(0)).getElementsByTagName("module");
												Element paraElement = (Element)nl.item(0);
												returnval = paraElement.getFirstChild().getNodeValue();
								}catch(Exception e){
										System.out.println(e);
								}
								return returnval;
		}
	
		public Vector<String> getListFromConf(String para,String fname){
			Vector<String> returnList;
			returnList = new Vector<String>();
			try{
								DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
								DocumentBuilder builder = dbfactory.newDocumentBuilder();
								Document doc = builder.parse(new File(fname));
								Element root = doc.getDocumentElement();
								NodeList nl = root.getElementsByTagName(para);
								//NodeList dtable = ((Element)modules.item(0)).getElementsByTagName("module");
								//Element paraElement = (Element)nl.item(0);
								int t = nl.getLength();
								String paras;
								for (int i = 0;i<t;i++){
										Element pElement = (Element)nl.item(i);
										paras = pElement.getFirstChild().getNodeValue();
										returnList.add(paras);
								}
                                System.out.println("[DEBUG][XMLREADER] Parameter is "+para);
                                showList(returnList);
			}catch(Exception e){
						System.out.println(e);
			}
			return returnList;
		}
                
        public void showList(Vector<String> _in){
        	if(_in != null){
        		for(int t=0; t<_in.size();t++){
                          System.out.println("[DEBUG][XMLREADER] "+_in.get(t));
                }
            }else{
                    System.out.println("[DEBUG][XMLREADER] empty xml list");
            }     
        }
	
}   