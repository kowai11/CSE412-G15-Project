package com.example.library.model;

import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    private Integer id;
    private String isbn;
    private Date borrowtime;
    private Date returntime;
}
