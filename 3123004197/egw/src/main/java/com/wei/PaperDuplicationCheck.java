package com.wei;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class PaperDuplicationCheck {
    public void paperDuplicationCheck(String originFilePath,String copyFilePath,String resultFilePath) throws Exception {
        //首先创建三个文件对象
        File ofile = new File(originFilePath);
        File cfile = new File(copyFilePath);
        File rfile = new File(resultFilePath);
        //读取原文和抄袭文本的内容
        StringBuffer origin = new StringBuffer(FileUtils.readFileToString(ofile,"UTF-8").replaceAll("\\s|\\p{Punct}",""));
        StringBuffer copy = new StringBuffer(FileUtils.readFileToString(cfile,"UTF-8").replaceAll("\\s|\\p{Punct}",""));
        //将原文文字拆分成个体,计算Jaccard相似系数

        System.out.println(copy.length());
        System.out.println(copy);

    }
}
