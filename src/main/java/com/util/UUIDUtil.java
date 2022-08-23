package com.util;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class UUIDUtil {

    /*生成UUID*/
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    /*
     * 生成指定数目的UUID
     * */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] retArray = new String[number];
        for (int i = 0; i < number; i++) {
            retArray[i] = getUUID();
        }
        return retArray;
    }

    public static void main(String[] args) throws ParseException {
        String uuid = getUUID();
        System.out.println(uuid);
    }
}
