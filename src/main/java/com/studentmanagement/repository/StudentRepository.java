package com.studentmanagement.repository;

import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Integer> {
    List<StudentEntity> findAllBydeleted(Boolean deleted);


}
