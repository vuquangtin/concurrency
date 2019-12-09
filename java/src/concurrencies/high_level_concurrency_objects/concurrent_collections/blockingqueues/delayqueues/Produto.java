package blockingqueues.delayqueues;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Produto implements Delayed {
	private int valor;
	private long inicio;
	private static long tempoProducao = System.currentTimeMillis();

	public Produto(int valor, long tempo) {
		inicio = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(tempo, TimeUnit.SECONDS);
		this.valor = valor;
	}

	@Override
	public int compareTo(Delayed o) {
		if (this.inicio < ((Produto) o).inicio) {
			return -1;
		} else if (this.inicio > ((Produto) o).inicio) {
			return 1;
		}
		return 0;

	}

	@Override
	public long getDelay(TimeUnit unit) {
		long decorrido = inicio - System.currentTimeMillis();
		return unit.convert(decorrido, TimeUnit.MILLISECONDS);
	}

	public int getValor() {
		return valor;
	}

	public long getTempoDecorido() {
		return TimeUnit.SECONDS.convert(System.currentTimeMillis() - tempoProducao, TimeUnit.MILLISECONDS);
	}
}