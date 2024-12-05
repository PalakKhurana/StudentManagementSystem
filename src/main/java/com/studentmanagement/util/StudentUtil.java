package com.studentmanagement.util;

import com.studentmanagement.dao.Student;
import com.studentmanagement.entity.StudentEntity;

public class StudentUtil {
    // Convert Student to StudentEntity
    public static StudentEntity toEntity(Student student) {
        if (student == null) {
            return null;
        }
        StudentEntity entity = new StudentEntity();
        entity.setName(student.getName());
        entity.setEmail(student.getEmail());
        entity.setGrade(student.getGrade());
        entity.setMarks(student.getMarks());
        entity.setPhone(student.getPhone());
        entity.setSubject(student.getSubject());
        entity.setDeleted(false);
        return entity;
    }

    // Convert StudentEntity to Student
    public static Student toModel(StudentEntity entity) {
        if (entity == null) {
            return null;
        }

        Student student = new Student();
        student.setName(entity.getName());
        student.setEmail(entity.getEmail());
        student.setGrade(entity.getGrade());
        student.setPhone(entity.getPhone());
        student.setMarks(entity.getMarks());
        student.setSubject(entity.getSubject());
        return student;
    }
}



