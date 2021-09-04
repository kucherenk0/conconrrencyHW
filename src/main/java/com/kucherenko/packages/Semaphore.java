package com.kucherenko.packages;

public class Semaphore {

    private Boolean isPing = true;
    private final int MAX_ITERATIONS = 10;
    java.util.concurrent.Semaphore semaphore;


    public static void main(String[] args) {
        new Semaphore().pingPong();
    }

    private void pingPong() {
        semaphore = new java.util.concurrent.Semaphore(1);

        Thread ping = new Thread(pingJob);
        Thread pong = new Thread(pongJob);

        ping.start();
        pong.start();

        try {
            pong.join();
            ping.join();
        } catch (InterruptedException e) {
            System.out.println("Error while interrupting thread");
            e.printStackTrace();
        }
    }

    Runnable pingJob = new Runnable() {
        private int counter = 0;

        public void run() {
            while (counter < MAX_ITERATIONS) {
                try {
                    semaphore.acquire();
                    if (isPing) {
                        counter++;
                        System.out.println("PING " + counter);
                        isPing = false;
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    Runnable pongJob = new Runnable() {
        private int counter = 0;

        public void run() {
            while (counter < MAX_ITERATIONS) {
                try {
                    semaphore.acquire();
                    if (!isPing) {
                        counter++;
                        System.out.println("PONG " + counter);
                        isPing = true;
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}

