package com.studentmanagement.controller;

import com.studentmanagement.dao.FilterStudent;
import com.studentmanagement.dao.ResponseCode;
import com.studentmanagement.dao.Student;
import com.studentmanagement.dao.UpdateStudent;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.exceptions.StudentNotFoundException;
import com.studentmanagement.service.StudentManagementService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.studentmanagement.util.Constants;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentManagementService student;

    private  static final Logger logger= LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/enroll")
    public ResponseEntity<ResponseCode<StudentEntity>> addStudent(@RequestBody Student studentEntity) {
        logger.info("Adding a new student");
          StudentEntity addedStudent= student.addStudent(studentEntity);
          logger.debug("Student added successfully!",addedStudent);
          return ResponseEntity.status(Constants.CREATED)
                  .body(ResponseCode.success("Student added successfully",addedStudent));
    }
    @PostMapping("/filter")
    public ResponseEntity<ResponseCode<List<StudentEntity>>> filterStudents(@RequestBody FilterStudent filterStudent) {
        logger.info("Filtering student with filters {}:",filterStudent);
        List<StudentEntity>filteredStudents=student.filterStudents(filterStudent);
        if(filteredStudents.isEmpty()){
            logger.warn("No Student records match for the given criteria:{}",filterStudent);
              return ResponseEntity.status(Constants.NOT_FOUND)
                  .body(ResponseCode.error(Constants.NO_CONTENT,"No Student records match for the given data.")) ;

        }
        logger.debug("Found {} students matching criteria",filteredStudents.size());
        return ResponseEntity.status(Constants.SUCCESSFUL)
                .body(ResponseCode.success("Following students match the criteria:",filteredStudents));
    }


    @GetMapping("/all")
    public List<StudentEntity> getAllStudent(){
        logger.info("Fetching all student data");
        return this.student.getAllStudent();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseCode<Optional<StudentEntity>>> getStudentById(@PathVariable("id")int id) {
        logger.info("Fetching student data with ID:{}",id);
        Optional<StudentEntity> studentEntity=student.getStudentById(id);
        return ResponseEntity.status(Constants.SUCCESSFUL)
        .body(ResponseCode.success("Student found: ", studentEntity));
//        if (studentEntity.isEmpty() || studentEntity.get().getDeleted() ) {
//            logger.warn("Student not found");
//            return ResponseEntity.status(Constants.NOT_FOUND).body(ResponseCode.notFound(404,"Student not found"));
//        }
//        else
//         {
//            logger.debug("student found");
//            return ResponseEntity.status(Constants.SUCCESSFUL)
//                    .body(ResponseCode.success("Student found: ", studentEntity.get()));
//        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseCode<StudentEntity>> updateStudent(@PathVariable("id") int id, @RequestBody UpdateStudent studentUpdates) {
        StudentEntity updatedStudent = student.updateStudent(id, studentUpdates);
        logger.info("Updating student with id:{}",id);
        if (updatedStudent != null) {
            logger.info("Student with ID:{} updated",id);
            return ResponseEntity.status(Constants.SUCCESSFUL)
                    .body(ResponseCode.success("Details updated successfully!",updatedStudent));
        } else {
            logger.warn("Failed to update ID:{}",id);
            return ResponseEntity.status(Constants.NOT_FOUND).body(ResponseCode.notFound(404,"Student not found in database"));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseCode<String>> softDeleteStudent(@PathVariable("id")int id) {
        boolean isDeleted=student.softDeleteStudent(id);
        logger.info("Deleting student with ID:{} ",id);
        if(isDeleted){
            logger.info("Student with ID{} soft deleted successfully",id);
            return ResponseEntity.status(Constants.SUCCESSFUL)
                    .body(ResponseCode.success("Student marked as deleted","Deleted id:"+id));
        }
        else{
            logger.warn("Failed to soft delete Student of ID: {} ",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseCode.error(HttpStatus.NOT_FOUND.value(), "Student not found"));
        }

    }

}






