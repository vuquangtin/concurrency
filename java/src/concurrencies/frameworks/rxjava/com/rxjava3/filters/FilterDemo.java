package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;

import com.rxjava3.change.entities.Person;
import com.rxjava3.change.entities.Student;
import com.rxjava3.change.entities.Teacher;

/**
 * @author ousiyuan
 * @date 2019/10/9
 */
public class FilterDemo {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        return integer <= 4;
                    }
                })
                .subscribe(integer -> System.out.println(integer+"*"));

        Person[] peoples = {new Student("aaa"), new Teacher("bbb"), new Student("ccc")};
        Observable.fromArray(peoples)
                .ofType(Student.class)
                .subscribe(people -> System.out.println(((Student)people).getName()));
    }
}
