package com.wei;



public class PaperDuplicationCheck {


    public void paperDuplicationCheck(String originFilePath,String copyFilePath,String resultFilePath)  {
        //读取原文和抄袭文本的内容
        String origin = Utils.FileToString(originFilePath);
        String copy = Utils.FileToString(copyFilePath);


        //将文章文字拆分成集合,并计算Jaccard相似系数
        Double similarity = Utils.jaccardCal(origin,copy);

        //相似率输出到答案文件中
        Utils.writeToFile(resultFilePath,similarity);

    }
}
