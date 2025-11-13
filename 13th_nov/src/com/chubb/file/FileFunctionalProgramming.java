package com.chubb.file;

import java.io.*;
import java.nio.file.*;

public class FileFunctionalProgramming {
	public static void main(String[] args) {
		String filePath = "file.txt";

		try {
			long cnt = Files.lines(Paths.get(filePath))
					.flatMap(line -> java.util.Arrays.stream(line.split("\\W+")))
					.filter(word -> word.equalsIgnoreCase("India")).count();
			System.out.println("Count of India:" + cnt);
		} catch (IOException exp) {
			System.out.println("Error in reading file: " + exp);
		}
	}
}