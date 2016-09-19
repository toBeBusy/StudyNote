package threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {
	// 等待队列最大长度
	private final int MAX_SIZE;

	// 处理线程数
	private final int WORKER_NUM;

	// 等待队列
	private List<Runnable> jobs = new ArrayList<Runnable>();

	// 任务处理线程队列
	private List<Worker> workers = new ArrayList<Worker>();

	//锁
	private ReentrantLock reentrantLock = new ReentrantLock();

	/**
	 * 构造方法
	 * 
	 * @param workNum
	 *            线程池中线程数
	 * @param maxSize
	 *            等待队列的最大等待线程数
	 */
	public MyThreadPool(int workNum, int maxSize) {
		this.MAX_SIZE = maxSize;
		this.WORKER_NUM = workNum;
		this.init();
	}

	/**
	 * 初始方法
	 */
	private void init() {
		// 循环创建工作线程
		for (int i = 0; i < WORKER_NUM; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			worker.start();
		}
	}
	
	/**
	 * 线程池销毁方法
	 */
	public void shutDown(){
		for (Worker worker : workers) {
			worker.shutDown();
		}
	}

	/**
	 * 接受任务方法
	 * @param task
	 */
	public void execute(Runnable task) {
		reentrantLock.lock();
		try {
			if (jobs.size() <= MAX_SIZE) {
				jobs.add(task);
			} else {
				System.out.println("任务已满！！！");
			}
		} finally {
			reentrantLock.unlock();
		}

	}

	private class Worker extends Thread {
		private boolean running = true;

		@Override
		public void run() {
			// 判断jobs里面有没有数据。
			while (running) {
				Runnable job = null;
				reentrantLock.lock();
				try {
					if (jobs.size() > 0) {
						job = jobs.remove(0);
					}
				} finally {
					reentrantLock.unlock();
				}

				if (null != job) {
					job.run();
				}
			}
		}
		
		public void shutDown(){
			running = false;
		}

	}

	class TestThread extends Thread {

		public TestThread(String name) {
			this.setName(name);
		}

		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("我是线程" + this.getName());
		}

	}

	public static void main(String[] args) {
		MyThreadPool pool = new MyThreadPool(1, 2);
		TestThread thread_1 = pool.new TestThread("thread_1");
		TestThread thread_2 = pool.new TestThread("thread_2");
		TestThread thread_3 = pool.new TestThread("thread_3");
		TestThread thread_4 = pool.new TestThread("thread_4");
		TestThread thread_5 = pool.new TestThread("thread_5");
		pool.execute(thread_1);
		pool.execute(thread_2);
		pool.execute(thread_3);
		pool.execute(thread_4);
		pool.execute(thread_5);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutDown();
	}

}
