package com.studentmanagement.service;

import com.studentmanagement.dao.FilterStudent;
import com.studentmanagement.dao.Student;
import com.studentmanagement.dao.UpdateStudent;
import com.studentmanagement.entity.StudentEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentManagementService {
    StudentEntity addStudent(Student student);
  Optional<StudentEntity>getStudentById(int id);
    List<StudentEntity> filterStudents(FilterStudent filteredStudent);
    StudentEntity updateStudent(int id, UpdateStudent studentUpdates);
    boolean softDeleteStudent(int id);
   // Map<Integer,StudentEntity> filterStudent(int id,FilterStudent studentFilters);

    public List<StudentEntity> getAllStudent();
    // void addOverallMarks(int id, double marks);
}


