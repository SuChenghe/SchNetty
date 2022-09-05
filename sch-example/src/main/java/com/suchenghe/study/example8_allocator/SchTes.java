package com.suchenghe.study.example8_allocator;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SchTes {

    static class MergeView{
        List<String> str;

        public List<String> getStr() {
            return str;
        }

        public void setStr(List<String> str) {
            this.str = str;
        }
    }

    public static void main(String[] args) {
        MergeView mergeView = new MergeView();
        mergeView.str = new ArrayList<>();
        mergeView.str.add("1");
        mergeView.str.add("2");
        mergeView.str.add("3");
        mergeView.str.add("4");
        mergeView.str.add("5");

        List newList = new ArrayList();


        mergeView.getStr().forEach(subgroup -> {
            if (subgroup.equals("3") || subgroup.equals("4")) {
                System.out.println("Primary Group: {}.3");
                return;
            }
            newList.add(subgroup);
        });

        System.out.println(newList);

        System.out.println("result:"+"11".compareTo(null));
    }

}
