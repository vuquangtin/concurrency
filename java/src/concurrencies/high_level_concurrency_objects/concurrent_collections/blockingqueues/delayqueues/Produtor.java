package blockingqueues.delayqueues;

import java.util.concurrent.BlockingQueue;

public class Produtor extends Thread {
	private static final int intervaloProducao = 1000;
	private BlockingQueue<Produto> estoque;
	private int id;

	public Produtor(BlockingQueue<Produto> c, int id) {
		estoque = c;
		this.id = id;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Produto p = new Produto(i, i * 2);
				estoque.put(p); // InterruptedException
				System.out.println("Produtor " + id + " produziu o " + i + " com o tempo de " + i * 2
						+ "s, delay termina " + (i * 2 + i * intervaloProducao / 1000) + "s após início da produção!");
				sleep(intervaloProducao); // InterruptedException
			} catch (InterruptedException e) {
				System.exit(1);
			}
		}
	}
}