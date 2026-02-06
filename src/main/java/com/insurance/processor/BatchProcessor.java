package com.insurance.processor;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class BatchProcessor {

    private static final Semaphore semaphore = new Semaphore(1);

    public static void processAll(){

        try{

            File folder = new File(
                    BatchProcessor.class
                            .getClassLoader()
                            .getResource("claims")
                            .getFile()
            );

            ExecutorService executor =
                    Executors.newFixedThreadPool(3);
            // threads can be >1 now safely

            for(File file : folder.listFiles()){

                executor.submit(() -> {

                    try{
                        semaphore.acquire();

                        ClaimProcessor.process(file.getAbsolutePath());

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        semaphore.release();
                    }

                });
            }

            executor.shutdown();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
