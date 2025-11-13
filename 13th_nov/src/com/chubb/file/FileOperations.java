	package com.chubb.file;
	
	import java.io.*;
	
	public class FileOperations{
		public static void main(String[] args) {
			String filePath="file.txt";
			int cnt=0;
			
			try(BufferedReader reader=new BufferedReader(new FileReader(filePath))){
				String line;
				while((line=reader.readLine())!=null) {
					String[] words=line.split(" ");
					for(String word: words) {
						if(word.equalsIgnoreCase("India")) {
							cnt++;
						}
					}
				}
				System.out.println("Count of India:"+cnt);
				
			}catch(IOException exp) {
				System.out.println("Error in reading file: "+exp);
			}
		}
	}