package com.yswdqz.library.controller;

import com.yswdqz.library.mapper.BookCommmentMapper;
import com.yswdqz.library.mapper.HasaMapper;
import com.yswdqz.library.model.BookCommentVO;
import com.yswdqz.library.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/bookComment")
public class BookCommentController {
    @Autowired
    private BookCommmentMapper bookCommmentMapper;
    @Autowired
    private HasaMapper hasaMapper;
    @CrossOrigin
    @PostMapping("/insert")
    public Res insert(@RequestBody BookCommentVO bookcomment){
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date borrowtime1 = s.parse(bookcomment.getPosttime());
            Integer insert = bookCommmentMapper.insert(bookcomment.getCommentid()
                    , bookcomment.getRate()
                    , bookcomment.getContent(), borrowtime1);
            hasaMapper.insert(bookcomment.getCommentid(), bookcomment.getIsbn());
            return insert>0 ? Res.okRes("success",null):Res.noRes("false",null);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Res.noRes("false",null);
    }
}
