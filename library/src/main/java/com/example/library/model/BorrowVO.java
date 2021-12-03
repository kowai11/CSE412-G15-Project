package com.example.library.model;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowVO {
    private Book book;
    private Date borrowtime;
    private Date returntime;
}
