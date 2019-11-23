package com.gate.juc;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Phaser:按照不同的阶段对线程进行执行。
 */

public class T07_Phaser {

    static Random r = new Random();
    static MarriagePhser phaser = new MarriagePhser();

    static class MarriagePhser extends Phaser {

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("宾客离开" + registeredParties);
                    System.out.println();
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person extends Thread {

        String name;

        public Person(String name) {this.name = name; }

        public void arrive() {
            milliSleep(1);
            System.out.println(name + "到达现场");
            phaser.arriveAndAwaitAdvance();
        }
        public void eat() {
            milliSleep(1);
            System.out.println(name + "吃完");
            phaser.arriveAndAwaitAdvance();
        }
        public void leave() {
            if(name != "新娘" && name != "新郎") {
                milliSleep(1);
                System.out.println(name + "离开");
                phaser.arriveAndAwaitAdvance();
            }else {
                phaser.arriveAndDeregister();
            }

        }
        @Override
        public void run() {
            arrive();
            eat();
            leave();
        }
    }

    private static void milliSleep(int sec){
        try {
            TimeUnit.SECONDS.sleep(r.nextInt(sec));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        phaser.bulkRegister(7);

        for (int i = 0; i < 5; i++) {

            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新娘")).start();
        new Thread(new Person("新郎")).start();
    }
}
