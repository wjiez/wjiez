package com.wei;

import com.wei.pojo.Exercise;
import com.wei.utils.Utils;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    @Test
    public void testGeneration(){
        int amount= 1000,numLimit =10;
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
