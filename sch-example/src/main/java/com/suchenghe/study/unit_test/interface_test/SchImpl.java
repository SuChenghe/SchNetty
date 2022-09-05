package com.suchenghe.study.unit_test.interface_test;

/**
 * @author SuChenghe
 * @date 2020/10/29 15:46
 * Description: No Description
 */
public class SchImpl implements InterfaceParent2{

    @Override
    public String getStr1() {
        return "str1";
    }

    @Override
    public String getStr2() {
        return "str2";
    }

    @Override
    public String getStr3() {
        return "str3";
    }

    public static void main(String[] args) {
        InterfaceParent2 interfaceParent2 = new SchImpl();
        System.out.println(interfaceParent2.getStr1());
        System.out.println(interfaceParent2.getStr2());
    }
}
