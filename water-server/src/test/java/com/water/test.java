package com.water;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class test {
    public static void main(String[] args) {
        //new Connect();
        String a = "asd%s";
        String format = String.format(a, "D");
        System.out.println(format);
    }
}
