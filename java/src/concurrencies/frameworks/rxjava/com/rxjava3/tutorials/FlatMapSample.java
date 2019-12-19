package com.rxjava3.tutorials;

import java.util.ArrayList;
import java.util.List;

import com.rxjava3.change.entities.Course;
import com.rxjava3.change.entities.Student;
import com.rxjava3.utils.Utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FlatMapSample {
	static List<Student> sStudents = new ArrayList<Student>();
	static int i;

	static String getCourse() {
		return "course" + i;
	}

	static String getId() {
		return "id:" + i++;
	}

	static {
		Utils.println("init");
		Student student0 = new Student("leo");
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(getCourse(), getId()));
		courses.add(new Course(getCourse(), getId()));
		courses.add(new Course(getCourse(), getId()));
		student0.setCoursesList(courses);

		Student student1 = new Student("lel");
		List<Course> courses1 = new ArrayList<>();
		courses1.add(new Course(getCourse(), getId()));
		courses1.add(new Course(getCourse(), getId()));
		courses1.add(new Course(getCourse(), getId()));
		student1.setCoursesList(courses1);

		Student student2 = new Student("nen");
		List<Course> courses2 = new ArrayList<>();
		courses2.add(new Course(getCourse(), getId()));
		courses2.add(new Course(getCourse(), getId()));
		courses2.add(new Course(getCourse(), getId()));
		student2.setCoursesList(courses2);

		sStudents.add(student0);
		sStudents.add(student1);
		sStudents.add(student2);
	}

	public static final void main(String[] args) {
		useMap();
		Utils.println("============= divide ===============");
		useFlatMap();
	}

	private static void useMap() {
		Consumer<Course> consumer = new Consumer<Course>() {

			@Override
			public void accept(Course course) throws Throwable {
				Utils.println("action1 call");

				Utils.println(course.getName());

			}
		};
		Observable.fromIterable(sStudents)
				.concatMapIterable(new Function<Student, List<Course>>() {
					@Override
					public List<Course> apply(Student student) throws Throwable {
						Utils.println("map apply");
						return student.getCoursesList();
					}
				}).subscribe(consumer);
	}

	private static void useFlatMap() {
		Observable.fromIterable(sStudents)
				.flatMap(new Function<Student, Observable<Course>>() {
					@Override
					public Observable<Course> apply(Student student) {
						return Observable.fromIterable(student.getCoursesList());
					}
				}).subscribe(new Consumer<Course>() {
					@Override
					public void accept(Course course) {
						Utils.println(course.getName());
					}
				});
	}
}