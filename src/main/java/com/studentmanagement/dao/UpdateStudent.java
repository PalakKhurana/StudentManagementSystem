package com.studentmanagement.dao;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateStudent {
    private int id;
    private String email;
    private Integer phone;
}
