package com.example.library.model;

import lombok.Data;

@Data
public class BookCommentVO {
    private Integer commentid;
    private Integer rate;
    private String content;
    private String posttime;
    private String isbn;
}
