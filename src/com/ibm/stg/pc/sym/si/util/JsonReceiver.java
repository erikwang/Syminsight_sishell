package com.ibm.stg.pc.sym.si.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.stream.*;
import javax.json.stream.JsonParser.Event;
import javax.json.*;

public class JsonReceiver{
	
	public static void getJosn1(){

			String string = "https://cognitivesupportserver.w3ibm.mybluemix.net/token";
			
			try {
				URL url = new URL ("https://cognitivesupportserver.w3ibm.mybluemix.net/token");
				String encoding = Base64.getEncoder().encodeToString(("Admin:Admin").getBytes("UTF-8"));

				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);
				connection.setRequestProperty ("Authorization", "Basic " + encoding);

				//use json retrieve code to replace it.
				InputStream content = (InputStream)connection.getInputStream();
				//BufferedReader in = new BufferedReader (new InputStreamReader (content));
				
				 JsonParser parser = Json.createParser(content);
					 while (parser.hasNext()) {
						 Event e = parser.next();
						 if (e == Event.KEY_NAME) {
					              switch (parser.getString()) {
					                  case "name":
					                      parser.next();
					                    System.out.print(parser.getString());
					                    System.out.print(": ");
					                    break;
					                case "message":
					                    parser.next();
					                    System.out.println(parser.getString());
					                    System.out.println("---------");
					                    break;
					             }
					         }
					 }
				
				
				String line;
				
				JsonObject jsonObject = null;
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
	 
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	 
					while (in.readLine() != null) {
					}
					System.out.println("REST Service Invoked Successfully..");
					in.close();
				
				
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
			}catch(Exception e) {
					e.printStackTrace();
			}

			
				// Step1: Let's 1st read file from fileSystem
				// Change CrunchifyJSON.txt path here
				//InputStream crunchifyInputStream = new FileInputStream("/Users/<username>/Documents/CrunchifyJSON.txt");
				//InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
				//BufferedReader br = new BufferedReader(crunchifyReader);
				String line = "Admim:Admin";
					 
				JsonObject jsonObject = null;
				try {
					//jsonObject = new JsonObject(line);
				
				System.out.println(jsonObject);
				URL url = new URL("https://cognitivesupportserver.w3ibm.mybluemix.net/product/SpectrumConductorwithSpark");
				String encoding = Base64.getEncoder().encodeToString(("Admin:Admin").getBytes("UTF-8"));

				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
	 
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	 
					while (in.readLine() != null) {
					}
					System.out.println("REST Service Invoked Successfully..");
					in.close();
				} catch (Exception e) {
					System.out.println("Error while calling  REST Service");
					System.out.println(e);
				}
	 
				//br.close();
	}
}