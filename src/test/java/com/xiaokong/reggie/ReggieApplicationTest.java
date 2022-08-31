package com.xiaokong.reggie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
class ReggieApplicationTest {
    @Test
    public void tesss() {
        Student student = new Student(new Date());
        String s = JSON.toJSONString(student);
        System.out.println(s);
    }
}

class Student {
    @JSONField(format = "yyyy-MM-dd")
    private Date date;

    public Student(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student() {
    }
}