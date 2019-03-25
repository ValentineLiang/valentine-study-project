package com.valentine.regularexpressiondemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegExpTest {

    static Pattern p = Pattern.compile("^.*?(?=\\()");

    public static void main(String[] args) {
        String str = "北京市(朝阳区)(西城区)(海淀区)";
        Matcher m = p.matcher(str);
        if (m.find()) {
            System.out.println(m.group());
        }
    }
}