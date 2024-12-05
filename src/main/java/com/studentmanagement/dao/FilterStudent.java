package com.studentmanagement.dao;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterStudent {
    private Double marks;
    private    String subject;
    private String grade;
    private Double minMarks;
    private Double maxMarks;
}

