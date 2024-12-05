package com.studentmanagement.service.impl;

import com.studentmanagement.controller.StudentController;
import com.studentmanagement.dao.FilterStudent;
import com.studentmanagement.dao.Student;
import com.studentmanagement.dao.UpdateStudent;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.util.StudentUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentManagementServiceImplTest {
    private  static final Logger logger= LoggerFactory.getLogger(StudentManagementServiceImplTest.class);

    @Mock
    private   StudentRepository studentRepository;

    @InjectMocks
    private  StudentManagementServiceImpl studentManagementService;

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Add student Test- add new student")
    public void addStudentTest() {
       Student student= Student.builder().name("palak").email("yahoo.com").phone(2342).grade("B").subject("Physics").marks(76.0)
               .build();
       StudentEntity s= StudentUtil.toEntity(student);

     when(studentRepository.save(any(StudentEntity.class))).thenReturn(s);
       StudentEntity savedStudent=studentManagementService.addStudent(student);
       assertNotNull(savedStudent,"Failed:Added student cannot be null.");
        assertEquals("palak",savedStudent.getName(),"Failed:Name Mismatch");
        assertEquals("yahoo.com",savedStudent.getEmail(),"Failed:Name Mismatch");

        logger.info("addStudentTest passed!");
    }

    @Test
    @DisplayName("Get all student test")
    void getAllStudentTest() {
        List<StudentEntity>students=List.of(
                StudentEntity.builder().name("reena").email("google").build(),
                StudentEntity.builder().name("uma").email("coforge.com").build()
        );
        when(studentRepository.findAll()).thenReturn(students);
        List<StudentEntity>studentList=studentManagementService.getAllStudent();

        assertNotNull(studentList,"Failed:List should not be null.");
        assertEquals(2,studentList.size());
       verify(studentRepository,times(1)).findAll();

       logger.info("getAllStudentTest passed.");
        System.out.println("Successfully retrieved student list.");
//        List<StudentEntity>students=studentManagementService.getAllStudent();
//        assertNotNull(students);
//        assertFalse(students.isEmpty());
    }

    @Test
    @DisplayName("Display By ID Test- Should retrieve student data using ID")
    void getStudentByIdTest() {
        StudentEntity student=StudentEntity.builder()
                        .id(1)
                        .name("reena")
                        .build();
        when(studentRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(student));
        Optional<StudentEntity> result=studentManagementService.getStudentById(1);
        assertTrue(result.isPresent(),"Failed. Student not present.");
        assertEquals("reena",result.get().getName());
       verify(studentRepository,times(1)).findById(1);

       logger.info("Test passed successfully!");
    }

    @Test
    void updateStudentTest() {

 UpdateStudent updatedData=new UpdateStudent(1,"google.com",101);
 StudentEntity existingStudent=StudentEntity.builder().id(1).name("reena").email("old").phone(111).build();
 when(studentRepository.findById(1)).thenReturn(Optional.of(existingStudent));
 when(studentRepository.save(any(StudentEntity.class))).thenReturn(existingStudent);

 StudentEntity updatedStudent=studentManagementService.updateStudent(1,updatedData);
       assertEquals("google.com",updatedStudent.getEmail(),"Updated student email successfully!");
       assertEquals(101,updatedStudent.getPhone(),"Updated student phone number successfully!");

//        StudentEntity updatedStudent=studentManagementService.updateStudent(1,updatedData);
//        assertNotNull(updatedStudent);
//        assertEquals("google.com",updatedStudent.getEmail(),"Updated student email successfully!");
//        assertEquals(101,updatedStudent.getPhone(),"Updated student phone number successfully!");
    }

    @Test
    void softDeleteStudentTest() {
        StudentEntity student= StudentEntity.builder().id(1).build();
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        boolean deleted=studentManagementService.softDeleteStudent(1);
        assertTrue(deleted);
    }
   @ParameterizedTest
   @CsvSource({
           "B,Math,70.0",
           "A,Chemistry,60.0",
           "C,English,50.0"

   })
    void filterStudentsTest(String grade,String subject,double marks) {
        FilterStudent filter=new FilterStudent();
        filter.setGrade(grade);
        filter.setSubject(subject);
        filter.setMarks(marks);
       List<StudentEntity>mockStudents=List.of(
                StudentEntity.builder().name("lila").grade("B").subject("Math").marks(70.0).build(),
                StudentEntity.builder().name("usha").grade("A").subject("Chemistry").marks(60.0).build(),
                StudentEntity.builder().name("ria").grade("C").subject("English").marks(50.0).build()

       );
       when(studentRepository.findAllBydeleted(false)).thenReturn(mockStudents);
       List<StudentEntity>filteredStudents=studentManagementService.filterStudents(filter);
       assertNotNull(filteredStudents);
        assertEquals(1,filteredStudents.size());
       assertTrue(filteredStudents.size()>0);

    }
}