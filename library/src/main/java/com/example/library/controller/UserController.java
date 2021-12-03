package com.example.library.controller;

import com.example.library.data.AdminUser;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.UserMapper;
import com.example.library.model.*;
import com.example.library.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;
    @CrossOrigin
    @PostMapping("/adminstrator")
    public Res loginByAdmin(@RequestBody Map<String,String> map){
        String username = map.get("accountNumber");
        String password = map.get("password");
        Administrator administrator = userMapper.loginByAdmin(username, password);

        if(administrator != null){
            AdminUser.administrator = administrator;
            return Res.okRes("success",null);
        }
        return Res.noRes("false",null);
    }
    @CrossOrigin
    @PostMapping("/reader")
    public Res loginByReader(@RequestBody Map<String,String> map){
        String username = map.get("accountNumber");
        String password = map.get("password");
        Reader reader = userMapper.loginByReader(username, password);

        if(reader != null){
            AdminUser.reader = reader;
            return Res.okRes("success",null);
        }
        return Res.noRes("false",null);
    }
    @CrossOrigin
    @GetMapping("/readerDetail")
    public Res userDetail(String accountnumber){
        Integer idByAccount = userMapper.getIdByAccount(accountnumber);
        Reader readerById = userMapper.getReaderById(idByAccount);
        return Res.okRes("success",readerById);
    }
    @CrossOrigin
    @GetMapping("/adminDetail")
    public Res adminDetail(String accountnumber){
        Integer idByAccountAdmin = userMapper.getIdByAccountAdmin(accountnumber);

        Administrator adminById = userMapper.getAdminById(idByAccountAdmin);
        return Res.okRes("success",adminById);
    }
    @CrossOrigin
    @GetMapping("/getBorrow")
    public Res getBorrow(String accountnumber){
        Integer idByAccount = userMapper.getIdByAccount(accountnumber);
        List<Borrow> borrowBooks = userMapper.getBorrowBooks(idByAccount);
        List<Book> collect1 = borrowBooks.stream().map((a) -> {
            return bookMapper.getBookById(a.getIsbn());
        }).collect(Collectors.toList());

//        List<BorrowVO> collect = borrowBooks.stream().map((a) -> {
//            BorrowVO borrowVO = new BorrowVO();
//            borrowVO.setBorrowtime(a.getBorrowtime());
//            borrowVO.setReturntime(a.getReturntime());
//            borrowVO.setBook(bookMapper.getBookById(a.getIsbn()));
//            return borrowVO;
//        }).collect(Collectors.toList());
        return Res.okRes("success",collect1);
    }
}
