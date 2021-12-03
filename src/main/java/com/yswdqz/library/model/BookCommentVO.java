package com.yswdqz.library.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookCommentVO {
    private Integer commentid;
    private Integer rate;
    private String content;
    private String posttime;
    private String isbn;
}
