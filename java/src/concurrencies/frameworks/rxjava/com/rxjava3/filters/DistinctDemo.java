package com.rxjava3.filters;

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
public class DistinctDemo {
    public static void main(String[] args) {
        // 过滤全列表重复
        // 基本类型会自动比较
        Observable.just(1, 2, 1, 1, 2, 3)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println(s + "*");
                    }
                });

        Student[] students = {new Student("1"), new Student("2"), new Student("1"),new Student("1"), new Student("2"),new Student("3")};
        Observable.fromArray(students)
                .distinct(student -> student.getName())
                .subscribe(student -> System.out.println(student.getName() + "*"));

//        Observable.fromArray(students)
//                .distinct(new Function<Student, String>() {
//                    @Override
//                    public String apply(Student student) throws Throwable {
//                        return student.getName();
//                    }
//                })
//                .subscribe(new Consumer<Student>() {
//                    @Override
//                    public void accept(Student s) throws Exception {
//                        System.out.println(s.getName() + "*");
//                    }
//                });

        // 过滤连续重复
        Observable.just(1, 2, 1, 1, 2, 3)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println(s + "*");
                    }
                });
    }
}
