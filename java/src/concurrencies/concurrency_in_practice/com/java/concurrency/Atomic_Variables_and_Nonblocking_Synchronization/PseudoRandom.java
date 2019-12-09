package com.java.concurrency.Atomic_Variables_and_Nonblocking_Synchronization;

import java.util.Random;

/**
 * 伪随机数生成器
 * @author buildhappy
 *
 */
public class PseudoRandom{
	Random rand = new Random();
	//int seed;
	public int calculateNext(int seed){
		return rand.nextInt(seed);
	}
}