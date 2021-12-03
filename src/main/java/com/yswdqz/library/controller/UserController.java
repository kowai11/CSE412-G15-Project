package com.yswdqz.library.controller;

import com.yswdqz.library.data.AdminUser;
import com.yswdqz.library.mapper.BookMapper;
import com.yswdqz.library.mapper.UserMapper;
import com.yswdqz.library.model.*;
import com.yswdqz.library.res.Res;
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
    public Res userDetail(Integer id){
        Reader readerById = userMapper.getReaderById(id);
        return Res.okRes("success",readerById);
    }
    @CrossOrigin
    @GetMapping("/adminDetail")
    public Res adminDetail(Integer id){
        Administrator adminById = userMapper.getAdminById(id);
        return Res.okRes("success",adminById);
    }
    @CrossOrigin
    @GetMapping("/getBorrow")
    public Res getBorrow(Integer id){
        List<Borrow> borrowBooks = userMapper.getBorrowBooks(id);
        List<BorrowVO> collect = borrowBooks.stream().map((a) -> {
            BorrowVO borrowVO = new BorrowVO();
            borrowVO.setBorrowtime(a.getBorrowtime());
            borrowVO.setReturntime(a.getReturntime());
            borrowVO.setBook(bookMapper.getBookById(a.getIsbn()));
            return borrowVO;
        }).collect(Collectors.toList());
        return Res.okRes("success",collect);
    }
}
