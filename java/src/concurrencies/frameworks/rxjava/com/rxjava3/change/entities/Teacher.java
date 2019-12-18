package com.rxjava3.change.entities;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Teacher extends Person{
    public Teacher(String s){
        name = s;
    }

    public String getName(){
        return name;
    }
}
