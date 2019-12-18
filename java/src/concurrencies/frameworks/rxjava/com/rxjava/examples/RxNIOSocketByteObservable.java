package com.rxjava.examples;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;

/**
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RxNIOSocketByteObservable {

	private int portNum = 8080;
	private int bufferSize = 1024;
	private byte[] messageDelimiter = "\n".getBytes();

	public RxNIOSocketByteObservable() {
	}

	public RxNIOSocketByteObservable(int port, int bufferSize) {
		this.portNum = port;
		this.bufferSize = bufferSize;
	}

	public int getPortNumber() {
		return this.portNum;
	}

	public int getBufferSize() {
		return this.bufferSize;
	}

	public Observable<byte[]> toObservable() throws Exception {
		return Observable.create(new RxNIOOnSubscribe());
	}

	private class RxNIOOnSubscribe implements Observable.OnSubscribe<byte[]> {

		private Selector selector;
		private ServerSocketChannel serverSocket;
		private ByteArrayOutputStream bufferedOutput;

		RxNIOOnSubscribe() throws Exception {
			selector = Selector.open();
			serverSocket = ServerSocketChannel.open();
			InetSocketAddress hostAddress = new InetSocketAddress("localhost",
					portNum);
			serverSocket.bind(hostAddress);
			serverSocket.configureBlocking(false);
			serverSocket.register(selector, serverSocket.validOps(), null);

			bufferedOutput = new ByteArrayOutputStream();
		}

		public void call(Subscriber<? super byte[]> subscriber) {

			while (!subscriber.isUnsubscribed()) {

				try {
					for (;;) {
						int numReadyKeys = selector.select();

						if (numReadyKeys <= 0)
							continue;

						Set selectedKeys = selector.selectedKeys();
						Iterator iter = selectedKeys.iterator();

						while (iter.hasNext()) {
							SelectionKey ky = (SelectionKey) iter.next();

							if (ky.isAcceptable()) {
								SocketChannel client = serverSocket.accept();
								client.configureBlocking(false);
								client.register(selector, SelectionKey.OP_READ);
							} else if (ky.isReadable()) {

								SocketChannel client = (SocketChannel) ky
										.channel();
								ByteBuffer buffer = ByteBuffer.allocate(64);

								int numBytes = client.read(buffer);

								if (numBytes < 0) {
									client.close();
								} else if (numBytes > 0) {

									for (int i = 0; i < numBytes; i++) {

										if (buffer.get(i) == messageDelimiter[0]) {
											subscriber.onNext(bufferedOutput
													.toByteArray());
											bufferedOutput.reset();
										} else {
											bufferedOutput.write(buffer.get(i));
										}
									}
								}
							}
							iter.remove();
						}
					}
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		}
	}
}