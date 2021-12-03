package com.example.library.controller;

import com.example.library.mapper.BookCommmentMapper;
import com.example.library.mapper.HasaMapper;
import com.example.library.model.BookCommentVO;
import com.example.library.model.Bookcomment;
import com.example.library.model.Hasa;
import com.example.library.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @CrossOrigin
    @GetMapping("/get")
    public Res get(String isbn){
        List<Hasa> hasaByIsbn = hasaMapper.getHasaByIsbn(isbn);
        List<Bookcomment> collect = hasaByIsbn.stream().map((a) -> {
            return bookCommmentMapper.getById(a.getCommentId());

        }).collect(Collectors.toList());
        return Res.okRes("success",collect);
    }
}
