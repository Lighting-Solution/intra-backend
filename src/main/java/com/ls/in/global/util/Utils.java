package com.ls.in.global.util;

public class Utils {
    public static Integer stringToInteger(String str) {
        if(str == null || str.isEmpty() || str.equals("")) return null;
        return Integer.valueOf(str);
    }

    public static boolean checkIntegerNull(Integer integer) {
        if(integer == null) return true;
        return false;
    }

    public static boolean checkStringNull(String str) {
        if(str == null || str.isEmpty() || str.equals("")) return true;
        return false;
    }

    public static Integer converterDepartment(Integer departmentId) {
        return Integer.valueOf((String.valueOf(departmentId).charAt(0) +"00"));
    }
}
