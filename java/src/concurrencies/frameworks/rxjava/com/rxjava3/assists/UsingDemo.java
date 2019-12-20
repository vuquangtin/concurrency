package com.rxjava3.assists;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;

import com.rxjava3.change.entities.Student;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class UsingDemo {
    public static void main(String[] args) {
        Observable.using(new Supplier<Student>() {
            @Override
            public Student get() throws Throwable {
                Student student = new Student("aaa");
                return student;
            }
        }, new Function<Student, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Student student) throws Throwable {
                return Observable.just(student.getName());
            }
        }, new Consumer<Student>() {
            @Override
            public void accept(Student student) throws Throwable {
                System.out.println(student.getName()+"++");

            }
        }).subscribe(str -> System.out.println(str+"*"));
    }
}
