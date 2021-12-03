package com.yswdqz.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String author;
    private String isbn;
    private String title;
    private String publisher;
    private String booktype;
    private Integer numinstock;
}
