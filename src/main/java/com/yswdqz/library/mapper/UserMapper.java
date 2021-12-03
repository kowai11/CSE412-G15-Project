package com.yswdqz.library.mapper;

import com.yswdqz.library.model.Administrator;
import com.yswdqz.library.model.Borrow;
import com.yswdqz.library.model.Reader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from Administrator" +
            " where accountNumber =#{accountNumber}" +
            " and password = #{password}")
    Administrator loginByAdmin(String accountNumber, String password);

    @Select("select * from Reader" +
            " where accountnumber =#{accountNumber}" +
            " and password = #{password}")
    Reader loginByReader(String accountNumber, String password);

    @Select("select numofbookcanborrow from reader where id = #{id}")
    Integer searchBooksNumCanBorrow(Integer id);

    @Update("update reader set numofbookcanborrow = numofbookcanborrow-1 where id = #{id}")
    Integer decreaseNum(Integer id);

    @Update("update reader set numofbookcanborrow = numofbookcanborrow+1 where id = #{id}")
    Integer increaseNum(Integer id);

    @Select("select * from reader where id =#{id}")
    Reader getReaderById(Integer id);

    @Select("select * from administrator where id = #{id}")
    Administrator getAdminById(Integer id);

    @Select("select * from borrow where id = #{id}")
    List<Borrow> getBorrowBooks(Integer id);
}
