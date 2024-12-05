package com.studentmanagement.service.impl;

import com.studentmanagement.dao.FilterStudent;
import com.studentmanagement.dao.Student;
import com.studentmanagement.dao.UpdateStudent;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.exceptions.StudentNotFoundException;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.StudentManagementService;
import com.studentmanagement.util.StudentUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Transactional
@Service
public class StudentManagementServiceImpl implements StudentManagementService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentEntity addStudent(Student student) {
        StudentEntity entity = StudentUtil.toEntity(student);
        return studentRepository.save(entity);
    }
  @Cacheable(value = "students")
    public List<StudentEntity> getAllStudent(){

        return studentRepository.findAll();
    }

    @Cacheable(value="students",key = "#id")
    @Override
    public Optional<StudentEntity> getStudentById(int id) {
          return Optional.ofNullable(studentRepository.findById(id).orElseThrow(
                  () -> new StudentNotFoundException("Student  id " + id + " not found")
          ));
    }
    @CachePut(value = "students",key = "#id")
    @Override
    public StudentEntity updateStudent(int id, UpdateStudent studentUpdates) {
        Optional<StudentEntity> studentOpt = studentRepository.findById(id);
        if(studentOpt.isPresent()){
            StudentEntity s=studentOpt.get();
            if(Objects.nonNull(studentUpdates.getEmail())) {
                s.setEmail(studentUpdates.getEmail());
            }
            if(Objects.nonNull(studentUpdates.getPhone())) {
                s.setPhone(studentUpdates.getPhone());
            }
            return studentRepository.save(s);

        }
        else{
            return null;
        }
        }
        //@CacheEvict(key = "#id")
    @Override
    public boolean softDeleteStudent(int id) {
        Optional<StudentEntity> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            StudentEntity s=studentOpt.get();
//            studentRepository.delete(s);
            s.setDeleted(true);
            studentRepository.save(s);
            return true;
        }

        return false;
    }
    @Override
    public List<StudentEntity> filterStudents(FilterStudent filteredStudent) {
        List<StudentEntity> students = studentRepository.findAllBydeleted(false);
        if(filteredStudent.getGrade()!=null ||filteredStudent.getSubject()!=null||filteredStudent.getMarks()!=null) {
            students = students.stream()
                    .filter(student -> {
                        boolean matchGrade = filteredStudent.getGrade() == null || student.getGrade() != null && filteredStudent.getGrade().equals(student.getGrade());
                        boolean matchSubject = filteredStudent.getSubject() == null || student.getSubject() != null && filteredStudent.getSubject().equals(student.getSubject());
                        boolean matchMarks = filteredStudent.getMarks() == null || student.getMarks() != null && filteredStudent.getMarks().equals(student.getMarks());

                        return matchGrade && matchSubject && matchMarks;
                    })
                    .collect(Collectors.toList());
        }


        Double minMarks= filteredStudent.getMinMarks();
        Double maxMarks= filteredStudent.getMaxMarks();
        if(filteredStudent.getMinMarks()!=null || filteredStudent.getMaxMarks()!=null){
            students=students.stream()
                    .filter(student ->{
                        boolean withinMin=minMarks==null ||( student.getMarks()!=null && student.getMarks()>=minMarks);
                        boolean withinMax=maxMarks==null ||( student.getMarks()!=null && student.getMarks()<=maxMarks);
                        return withinMin && withinMax;

                    })
                    .collect(Collectors.toList());

        }

        return students;
    }



}









   
