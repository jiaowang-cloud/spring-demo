package jin.li.yun.com.common.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author WangJiao
 * @since 2021/07/26
 */
class Producer implements Runnable{
    private Queue<Integer> queue;
    private int maxSize;

    public Producer(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (queue.size() == maxSize){
                    try{
                        System.out.println("Queue is Full");
                        queue.wait();
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
                Random random = new Random();
                int i = random.nextInt();
                System.out.println("Produce " + i);
                queue.add(i);
                queue.notify();
            }
        }
    }
}

class Consumer implements Runnable{

    private Queue<Integer> queue;
    private int maxSize;

    public Consumer(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (queue.isEmpty()){
                    System.out.println("Queue is Empty");
                    try{
                        queue.wait();
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
                int v = queue.remove();
                System.out.println("Consume " + v);
                queue.notify();
            }
        }
    }
}

public class MainDemo {
    public static void main(String[] args){
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 2;
        Producer p = new Producer(queue, maxSize);
        Consumer c = new Consumer(queue, maxSize);
        Thread pT = new Thread(p);
        Thread pC = new Thread(c);
        pT.start();
        pC.start();
    }
}