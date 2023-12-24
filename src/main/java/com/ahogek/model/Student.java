package com.ahogek.model;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * 关于 sealed permits
 */
sealed interface Shape permits Circle, Rectangle {
    double area();
}


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@interface MyAnnotation {
}

record Circle(double radius) implements Shape {

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

record Rectangle(double width, double height) implements Shape {

    @Override
    public double area() {
        return width * height;
    }
}

/**
 * @author AhogeK ahogek@gmail.com
 * @since 2023-12-10 15:57:03
 */
public record Student(int id, String firstName, String lastName) {

    static Logger log = Logger.getLogger(Student.class.getName());

    /**
     * record 中可以有静态的属性和静态代码块
     */
    static String school = "MIT";

    static {
        log.log(Level.INFO, () -> "This student is from" + school);
    }

    /**
     * record 中可以有构造方法
     * 可以在构造方法中对属性进行校验
     */
    public Student {
        if (id < 0) throw new IllegalArgumentException("id must be positive");
    }
}

/**
 * 关于java中的抽象类
 */
@SuppressWarnings("java:S106")
abstract class Animal {

    Logger animalLog = Logger.getLogger(Animal.class.getName());

    abstract void makeSound();

    /**
     * 抽象类中可以有非抽象方法
     */
    public void eat() {
        animalLog.info("I can eat.");
    }
}

class Dog extends Animal {

    Logger dogLog = Logger.getLogger(Dog.class.getName());

    @Override
    void makeSound() {
        dogLog.info("Bark");
    }
}

/**
 * record 可以实现接口, 但是不能继承类
 * record 可以嵌套，嵌套的 record 是隐式静态
 * record 可以加注解，其注解具有传播性
 * record 可以序列化与反序列化，但存在限制，我们可以将其实例序列化到文件中，
 * 然后再从文件中反序列化回来，但是无法通过常规的序列化钩子方法来定制这个过程。
 */
record Person(@MyAnnotation String name, int age, Address address) implements Comparable<Person>, Serializable {

    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }

    /**
     * 隐式静态
     */
    record Address(String street, String city) implements Serializable {
    }
}

/**
 * record 可以有泛型
 */
record Pair<K, V>(K key, V value) {

    public String keyValueType() {
        return key.getClass().getName() + ":" + value.getClass().getName();
    }
}

@SuppressWarnings("java:S106")
class Test {

    static Logger log = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student s1 = new Student(1, "Ahoge", "K");
        Student s2 = new Student(s1.id(), s1.firstName(), s1.lastName());
        log.log(Level.INFO, () -> "s1.equals(s2): " + s1.equals(s2));

        Animal dog = new Dog();
        dog.makeSound();
        dog.eat();

        Person p1 = new Person("Jonah", 20, new Person.Address("123", "Beijing"));
        Person p2 = new Person("AhogeK", 18, new Person.Address("456", "Shanghai"));
        Stream.of(p1, p2).sorted().forEach(p -> log.log(Level.INFO, p::toString));

        Constructor<?>[] constructors = Person.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            for (Parameter parameter : constructor.getParameters()) {
                Annotation[] annotations = parameter.getAnnotations();
                for (Annotation annotation : annotations) {
                    log.log(Level.INFO, () -> "Parameter: " + parameter.getName() + " Annotation: " + annotation);
                }
            }
        }

        for (Annotation annotation : Person.class.getDeclaredFields()[0].getAnnotations()) {
            log.log(Level.INFO, () -> "Field annotation: " + annotation);
        }

        for (Method declaredMethod : Person.class.getDeclaredMethods()) {
            for (Annotation annotation : declaredMethod.getAnnotations()) {
                log.log(Level.INFO, () -> "Method annotation: " + annotation);
            }
        }

        // 序列化
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
            oos.writeObject(p1);
        }

        // 反序列化
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"))) {
            Person p3 = (Person) ois.readObject();
            log.log(Level.INFO, p3::toString);
        }

        Pair<String, Integer> pair = new Pair<>("AhogeK", 18);
        log.log(Level.INFO, pair::toString);
        log.log(Level.INFO, pair::keyValueType);

        Shape circle = new Circle(2.0);
        Shape rectangle = new Rectangle(2.0, 3.0);
        log.log(Level.INFO, () -> "circle.area(): " + circle.area());
        log.log(Level.INFO, () -> "rectangle.area(): " + rectangle.area());
    }
}