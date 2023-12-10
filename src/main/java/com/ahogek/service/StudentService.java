package com.ahogek.service;

import com.ahogek.model.Student;

import java.util.Optional;

/**
 * @author AhogeK ahogek@gmail.com
 * @since 2023-12-10 15:48:39
 */
public class StudentService {

    public Optional<Student> getStudent(int id) {
        return switch (id) {
            case 1 -> Optional.of(new Student(1, "John", "Doe"));
            case 2 -> Optional.of(new Student(2, "Jane", "Goodall"));
            case 3 -> Optional.of(new Student(3, "Max", "Born"));
            default -> Optional.empty();
        };
    }
}
