package com.rxjava3.changes;

import com.rxjava3.change.entities.Person;
import com.rxjava3.change.entities.Student;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MapDemo {
    public static void main(String[] args) {
        Observable.just(1,2,3).map(num -> num+"+").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                System.out.println(s+"*");
            }
        });

        Person[] people = {new Student("aaa"), new Student("bbb"), new Student("ccc")};
        Observable.fromArray(people).cast(Student.class).subscribe(new Consumer<Student>() {
            @Override
            public void accept(Student student) throws Throwable {
                System.out.println(student.getName()+"+");
            }
        });
    }
}
