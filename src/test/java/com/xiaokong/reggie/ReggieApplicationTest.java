package com.xiaokong.reggie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


@SpringBootTest
class ReggieApplicationTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void tesss() {
        ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 45));
        String s = JSON.toJSONString(nums);
        System.out.println(redisTemplate.opsForValue().get("xiaokong111")); // null
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