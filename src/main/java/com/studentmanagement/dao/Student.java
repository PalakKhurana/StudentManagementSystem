package com.studentmanagement.dao;

import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private String name;
    private String email;
    private String grade;
    private Double marks;
    private String subject;
    private Boolean deleted=false;
    private Integer phone;

}
