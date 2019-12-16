package com.rxjava3.changes;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import com.rxjava3.change.entities.Person;
import com.rxjava3.change.entities.Student;

/**
 * @author ousiyuan
 * @date 2019/9/30
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
