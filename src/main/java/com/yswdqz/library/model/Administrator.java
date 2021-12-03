package com.yswdqz.library.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Administrator implements Serializable {
    private Integer id;
    private String name;
    private Character gender;
    private String password;
    private String contactInfo;
    private String accountNumber;
}
