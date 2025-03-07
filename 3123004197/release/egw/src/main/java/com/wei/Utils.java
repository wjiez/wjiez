package com.wei;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Utils {
    //将文件内容读取成字符串
    public static String FileToString(String FilePath){
        try {
            return FileUtils.readFileToString(new File(FilePath),"UTF-8").replaceAll("\\s|\\p{Punct}","");
        } catch (IOException e) {
            System.out.println("文件路径有误");
            throw new RuntimeException(e);
        }
    }
    //将两个字符传分别转换为集合,再计算jaccard(jaccard=交集元素个数/并集元素个数)相似系数
    public static double jaccardCal(String origin,String copy) {
        int count = 0;
        //创建两个集合容器
        HashSet<Character> originSet = new HashSet<Character>();
        HashSet<Character> copySet = new HashSet<Character>();
        for(int i = 0 ; i < origin.length(); i++){
            if (originSet.add(origin.charAt(i))){
                count++;
            }
        }
        for(int i = 0 ; i < copy.length(); i++){
            if (copySet.add(copy.charAt(i))) {
                count++;
            }
        }
        //将两个集合合并,合并集合的大小为并集元素个数
        originSet.addAll(copySet);
       // System.out.println(originSet.size());
        //处理当除数为0的情况
        if(originSet.size()!=0){
            return (count-originSet.size())*1.0/originSet.size();
        }else {
            System.out.println("两个文件没有实际内容");
            throw new ArithmeticException();
        }





    }
    //将数据结果以保留二位且带百分号的形式写入目标文件
    public static void writeToFile(String filePath,Double data){
        //将小数转化为有百分号的数字
        data = data*100;
        String result = String.format("%.2f%%", data);
        try {
            File file = new File(filePath);
            FileUtils.writeStringToFile(new File(filePath),result+"\n\r","UTF-8",false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
