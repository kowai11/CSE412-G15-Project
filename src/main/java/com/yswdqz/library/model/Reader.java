package com.yswdqz.library.model;

import lombok.Data;

@Data
public class Reader {
    private Integer id;
    private String name;
    private Character gender;
    private String accountnumber;
    private String contactinfo;
    private Integer numofbookcanborrow;
}
