package com.valentine.stream;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {

    @Data
    private static class User {
       private Integer age;
       private String name;
    }

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i ++) {
            User user =  new User();
            user.setAge(i);
            user.setName("波多野结衣" + i);
            list.add(user);
        }
        System.out.println(beforeJava7(list));
        System.out.println(java8(list));
    }

    public static List<String> beforeJava7(List<User> users){
        // 取年龄 <= 20的用户
        List<User> tmpList = new ArrayList<>();
        for (User user : users) {
            if (user.getAge() <= 20){
                tmpList.add(user);
            }
        }
        // 排序
        Collections.sort(tmpList, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });
        // 取名字
        List<String> userNames = new ArrayList<>();
        for(User user : tmpList){
            userNames.add(user.getName());
        }
        return userNames;
    }

    public static List<String> java8(List<User> users){
        //为了利用多核架构并行执行这段代码，只需要把stream()换成parallelStream()：
        List<String> userNames = users.stream()
                .filter(user -> user.getAge() <= 20)
                .sorted(Comparator.comparing(User::getName))
                .map(User::getName)
                .collect(Collectors.toList());
        return userNames;
    }
}
