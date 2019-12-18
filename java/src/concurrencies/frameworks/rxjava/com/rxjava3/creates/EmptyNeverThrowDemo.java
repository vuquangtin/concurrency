package com.rxjava3.creates;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class EmptyNeverThrowDemo {
    public static void main(String[] args) {
        new EmptyDemo().create();
        new NeverDemo().create();
        new ThrowDemo().create();
    }
}
