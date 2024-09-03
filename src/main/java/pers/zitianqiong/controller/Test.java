package pers.zitianqiong.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static pers.zitianqiong.utils.Threads.sleep;

public class Test {

    ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    List<Integer> list;
    int capacity;

    public Test(int capacity) {
        list = new LinkedList<>();
        this.capacity = capacity;
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            while (size() >= capacity) {
                System.out.println("已满，值：" + element);
                notFull.await();
            }
            System.out.println("放入：" + element);
            list.add(element);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        int value;
        try {
            while (size() == 0) {
                System.out.println("空队列，等待。。。");
                notEmpty.await();
            }
            value = list.remove(0);
            System.out.println("获得值：" + value);
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
        return value;
    }

    public int size() {
        lock.lock();
        try {
            return list.size();
        } finally {
            lock.unlock();
        }
    }

    public void test(Test queue) throws InterruptedException {

        class Customer implements Runnable {
            @Override
            public void run() {
                try {
                    queue.enqueue(1); // 生产者线程P1将1插入队列。
                    queue.enqueue(0); // 生产者线程P2将0插入队列。
                    queue.enqueue(2); // 生产者线程P3将2插入队列。
                    sleep(10);
                    queue.enqueue(3); // 其中一个生产者线程将3插入队列。
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        class producter implements Runnable {
            @Override
            public void run() {
                try {
                    sleep(5);
                    System.out.println(queue.dequeue());// 消费者线程C1调用dequeue。
                    System.out.println(queue.dequeue()); // 消费者线程C2调用dequeue。
                    System.out.println(queue.dequeue()); // 消费者线程C3调用dequeue。
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Thread customer = new Thread(new Customer());
        Thread producer = new Thread(new producter());
        producer.start();
        customer.start();
        producer.join();
        customer.join();
        // 队列中还有1个元素。
        System.out.println(size());
    }

    public static void main(String[] args) throws InterruptedException {
        int[] nums = new int[]{12, 23, 34, 15, 27, 1, 77, 12, 13, 978};
        for (int num : nums) {
            System.out.print(num + " ");
        }
        int gap = nums.length;
        System.out.println();
        System.out.println("===========");
        do {
            gap = gap / 2;
            for (int i = 0; i < gap; i++) {
                for (int j = i + gap; j < nums.length; j += gap) {
                    int current = nums[j];
                    int k;
                    for (k = j - gap; k >= 0 && nums[k] > current; k -= gap) {
                        nums[j] = nums[i];
                    }
                    nums[k + gap] = current;
                }
            }
            System.out.println(gap + "组");
            for (int num : nums) {
                System.out.print(num + " ");
            }
            System.out.println();
        } while (gap != 1);
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
