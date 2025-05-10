package com.wei.main;

import com.wei.pojo.Exercise;
import com.wei.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;





public class ExerciseGeneration {

    public static void main(String[] args) {
        //输入数据缺少报错
        if(args.length!=2){
            System.out.println("输入格式错误!");
            return;
        }
        int amount=Integer.parseInt(args[0]),numLimit =Integer.parseInt(args[1]);
        //数值限制不宜太小
        if(numLimit<=3){
            System.out.println("数值限制请大于或等于4");
            return;
        }
        ArrayList<Exercise> exercises = new ArrayList<>();
        int start=0;//用于传递每次生成算式后查重的起始位置
        //一个运算符的算式生成
        Utils.exerciseGeneration(exercises,numLimit,2,amount/6,0);
        //两个运算符的算式生成
        Utils.exerciseGeneration(exercises,numLimit,3,amount/2,exercises.size());
        //三个运算符的算式生成
        Utils.exerciseGeneration(exercises,numLimit,4,amount,exercises.size());
        //将生成的所有算式进行打乱
        Collections.shuffle(exercises);
        //将题目和答案分别写入题目文件和答案文件
        Utils.writeToFile(exercises);
    }

}
