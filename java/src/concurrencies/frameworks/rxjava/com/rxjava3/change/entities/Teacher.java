package com.rxjava3.change.entities;

/**
 * @author ousiyuan
 * @date 2019/10/9
 */
public class Teacher extends Person{
    public Teacher(String s){
        name = s;
    }

    public String getName(){
        return name;
    }
}
