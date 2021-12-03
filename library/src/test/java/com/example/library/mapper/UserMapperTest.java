package com.example.library.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BorrowMapper borrowMapper;
    @Test
    void read() {
//        Book book = new Book();
//        book.setBooktype("2");
//        book.setAuthor("1");
//        book.setIsbn("1");
//        book.setPublisher("1");
//        book.setTitle("1");
//
//        bookMapper.updateBook(book);
        System.out.println("userMapper.searchBooksNumCanBorrow(11111) = " + userMapper.searchBooksNumCanBorrow(11111));

        userMapper.decreaseNum(11111);
        System.out.println("userMapper.searchBooksNumCanBorrow(11111) = " + userMapper.searchBooksNumCanBorrow(11111));


    }
}
