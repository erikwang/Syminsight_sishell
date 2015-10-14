package com.ibm.stg.pc.sym.si.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SiUtil {

	public HashMap<String, String> parseParaFile(String fileurl){
		HashMap mypara = new HashMap();
		File filename = new File(fileurl);
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";  
	        String keyvalue[];	
			line = br.readLine();
			while (line != null) {  
				keyvalue = line.split("=",2);
				mypara.put(keyvalue[0],keyvalue[1]);
				line = br.readLine();
	        }
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return mypara;
	}
	
	public void showMap(HashMap map){
		System.out.println(map.keySet());
		System.out.println(map.values());
		
	}
	
	public void getFileList(){
		long a = System.currentTimeMillis();
        
        LinkedList list = new LinkedList();
        File dir = new File("./reports/");
        File[] file = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.addLast(file[i]);
            else
                System.out.println(file[i].getAbsolutePath());
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File) list.removeFirst();
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i]);
					else
						try {
							System.out.println(file[i].getCanonicalPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                }
            } else {
            	try {
					System.out.println(tmp.getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        //System.out.println(System.currentTimeMillis() - a);
	}
}
