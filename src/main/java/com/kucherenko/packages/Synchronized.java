package com.kucherenko.packages;

public class Synchronized {

    private Boolean isPing = true;
    private final int MAX_ITERATIONS = 10;

    public static void main(String[] args) {
        new Synchronized().pingPong();
    }

    private void pingPong() {
        Thread ping = new Thread(pingJob);
        Thread pong = new Thread(pongJob);

        ping.start();
        pong.start();

        try {
            pong.join();
            ping.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Runnable pingJob = new Runnable() {
        private int counter = 0;

        public void run() {
            while (counter < MAX_ITERATIONS) {
                synchronized (isPing) {
                    if (isPing) {
                        counter++;
                        System.out.println("PING " + counter);
                        isPing = false;
                    }
                }
            }
        }
    };

    Runnable pongJob = new Runnable() {
        private int counter = 0;

        public void run() {
            while (counter < MAX_ITERATIONS) {

                synchronized (isPing) {
                    if (!isPing) {
                        counter++;
                        System.out.println("PONG " + counter);
                        isPing = true;
                    }
                }
            }
        }
    };

}
