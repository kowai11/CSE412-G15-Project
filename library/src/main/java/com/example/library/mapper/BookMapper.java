package com.example.library.mapper;

import com.example.library.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("insert into book (isbn,author,title,publisher,booktype,numinstock)" +
            " values(#{book.isbn},#{book.author},#{book.title}" +
            ",#{book.publisher},#{book.booktype},#{book.numinstock})")
    Integer insertBook(@Param("book") Book book);

    @Delete("delete from book where isbn = #{isbn}")
    Integer deleteBook(String isbn);

    @Update("update book set author = #{book.author}" +
            ",title = #{book.title},publisher = #{book.publisher}, " +
            "booktype = #{book.booktype},numinstock = #{book.numinstock} where isbn = #{book.isbn}")
    Integer updateBook(@Param("book") Book book);

    @Select("select * from book where isbn = #{isbn}")
    Book getBookById(String isbn);

    @Select("select * from book where title like #{title}")
    List<Book> searchBook(String title);

}
