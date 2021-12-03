package com.yswdqz.library.controller;

import com.yswdqz.library.data.AdminUser;
import com.yswdqz.library.mapper.BookMapper;
import com.yswdqz.library.mapper.BorrowMapper;
import com.yswdqz.library.mapper.UpdateBookMapper;
import com.yswdqz.library.mapper.UserMapper;
import com.yswdqz.library.model.Book;
import com.yswdqz.library.model.Borrow;
import com.yswdqz.library.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UpdateBookMapper updateBookMapper;
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private UserMapper userMapper;
    @CrossOrigin
    @PostMapping("/insert")
    public Res insert(@RequestBody Book book){
        if(AdminUser.administrator == null){
            return Res.noRes("false",null);
        }
        Integer integer = bookMapper.insertBook(book);

        updateBookMapper.insert(AdminUser.administrator.getId(),book.getIsbn());
        return integer>0?Res.okRes("success",null):Res.noRes("false",null);
    }
    @CrossOrigin
    @GetMapping("/delete")
    public Res delete(String isbn){
        if(AdminUser.administrator == null){
            return Res.noRes("false",null);
        }
        Integer integer = bookMapper.deleteBook(isbn);

        updateBookMapper.insert(AdminUser.administrator.getId(),isbn);
        return integer>0?Res.okRes("success",null):Res.noRes("false",null);
    }
    @CrossOrigin
    @PostMapping("/update")
    public Res update(@RequestBody Book book){
        if(AdminUser.administrator == null){
            return Res.noRes("false",null);
        }
        Integer integer = bookMapper.updateBook(book);

        updateBookMapper.insert(AdminUser.administrator.getId(),book.getIsbn());
        return integer>0?Res.okRes("success",null):Res.noRes("false",null);
    }
    @CrossOrigin
    @GetMapping("/borrow")
    public Res borrow(String borrowtime,String returntime,String isbn){
        if(AdminUser.reader == null){
            return Res.noRes("false",null);
        }
        Book bookById = bookMapper.getBookById(isbn);
        if(bookById.getNuminstock()<=0){
            return Res.noRes("false",null);
        }
        Integer integer = userMapper.searchBooksNumCanBorrow(AdminUser.reader.getId());
        if(integer<=0){
            return Res.noRes("false",null);
        }
        else{
            userMapper.decreaseNum(AdminUser.reader.getId());
        }
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date borrowtime1 = s.parse(borrowtime);
            Date returntime1 = s.parse(returntime);
            Integer insert = borrowMapper.insert(AdminUser.reader.getId(), borrowtime1, returntime1, isbn);

            bookById.setNuminstock(bookById.getNuminstock()-1);
            return insert>0? Res.okRes("success",null):Res.noRes("false",null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Res.noRes("false",null);
    }
    @CrossOrigin
    @GetMapping("/return")
    public Res returnBook(String isbn){
        if(AdminUser.reader == null){
            return Res.noRes("false",null);
        }
        Borrow borrow = borrowMapper.searchByIdAndIsbn(AdminUser.reader.getId(),isbn);
        if(new Date().compareTo(borrow.getReturntime())>0){
            return Res.noRes("late",null);
        }
        userMapper.increaseNum(AdminUser.reader.getId());
        borrowMapper.deleteByIdAndIsbn(AdminUser.reader.getId(), isbn);
        Book bookById = bookMapper.getBookById(isbn);
        bookById.setNuminstock(bookById.getNuminstock()+1);
        bookMapper.updateBook(bookById);
        return Res.okRes("success",null);

    }
    @CrossOrigin
    @GetMapping("/detail")
    public Res getBookById(String isbn){
        Book bookById = bookMapper.getBookById(isbn);
        return Res.okRes("success",bookById);
    }
    @CrossOrigin
    @GetMapping("/search")
    public Res searchBook(String title){
        String realTitle = "%"+title+"%";
        List<Book> books = bookMapper.searchBook(realTitle);
        return Res.okRes("success",books);
    }
}
