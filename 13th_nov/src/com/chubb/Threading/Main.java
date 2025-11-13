package com.chubb.Threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		Worker worker= new Worker();
//		worker.start();
		int cores=Runtime.getRuntime().availableProcessors();
		ExecutorService service = Executors.newFixedThreadPool(cores);
		service.execute(worker);
		service.execute(worker);
//		service.shutdown();
		
	}

}
