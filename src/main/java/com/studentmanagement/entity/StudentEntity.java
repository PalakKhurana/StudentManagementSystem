package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="student_database")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String grade;
    private Double marks;
    private String subject;
    private Boolean deleted=false;
    private Integer phone;
    }


