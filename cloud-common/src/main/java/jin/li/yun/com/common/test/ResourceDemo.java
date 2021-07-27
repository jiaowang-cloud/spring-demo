package jin.li.yun.com.common.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// 资源
class Resource {
  private String name;
  private String sex;

  public synchronized void set(String name, String sex) {
    this.name = name;
    this.sex = sex;
  }

  public void print() {
    System.out.println(this.name + "." + this.sex);
  }
}
// 赋值
class Input implements Runnable {
  private final Resource r;
  private Queue<Resource> queue;
  private boolean flag = false;

  Input(Resource r, Queue<Resource> queue) {
    this.r = r;
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (queue) {
        while (queue.size() > 0) {
          try {
            //  System.out.println("Queue is Full");
            queue.wait();
          } catch (InterruptedException ie) {
            ie.printStackTrace();
          }
        }
        if (flag) {
          r.set("mike", "man");
        } else {
          r.set("丽丽", "女女");
        }
        this.flag = !flag;
        queue.add(r);
        queue.notify();
      }
    }
  }
}
// 取值
class Output implements Runnable {
  private final Resource r;
  private Queue<Resource> queue;

  Output(Resource r, Queue<Resource> queue) {
    this.r = r;
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (queue) {
        while (queue.isEmpty()) {
          // System.out.println("Queue is Empty");
          try {
            queue.wait();
          } catch (InterruptedException ie) {
            ie.printStackTrace();
          }
        }
        queue.remove();
        r.print();
        queue.notify();
      }
    }
  }
}

public class ResourceDemo {
  public static void main(String[] args) {
    Queue<Resource> queue = new LinkedList<>();
    Resource r = new Resource();
    Input in = new Input(r, queue);
    Output out = new Output(r, queue);
    Thread t1 = new Thread(in);
    Thread t2 = new Thread(out);
    t1.start();
    t2.start();
  }
}
