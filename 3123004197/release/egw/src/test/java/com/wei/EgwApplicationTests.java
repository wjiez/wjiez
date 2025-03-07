package com.wei;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EgwApplicationTests {
    PaperDuplicationCheck sample = new PaperDuplicationCheck();
    @Test//测试找不到文件的异常提示
    void testException1(){

        Assertions.assertThrows(RuntimeException.class, ()->{
            sample.paperDuplicationCheck("123","src/test/resources/orig_0.8_add.txt","src/test/resources/result.txt");
        });

    }
    @Test//测试找不到文件的异常提示
    void testException2(){

        Assertions.assertThrows(RuntimeException.class, ()->{
            sample.paperDuplicationCheck("src/test/resources/orig.txt","123","src/test/resources/result.txt");
        });

    }
    @Test//测试找不到文件的异常提示
    void testException3(){

        Assertions.assertThrows(RuntimeException.class, ()->{
            sample.paperDuplicationCheck("","","");
        });

    }
    @Test//测试文件内容无实际内容的异常提示
    void testException4(){
        Assertions.assertThrows(ArithmeticException.class, ()->{
            sample.paperDuplicationCheck("src/test/resources/zero.txt","src/test/resources/zero1.txt","src/test/resources/result.txt");
        });
    }


    @Test//测试案例
    void testPaperDuplicationCheck1() {
        sample.paperDuplicationCheck("src/test/resources/orig.txt","src/test/resources/orig_0.8_add.txt","src/test/resources/result.txt");
    }

    @Test//测试案例
    void testPaperDuplicationCheck2() {
        sample.paperDuplicationCheck("src/test/resources/orig.txt","src/test/resources/orig_0.8_del.txt","src/test/resources/result.txt");
    }
    @Test//测试案例
    void testPaperDuplicationCheck3() {
        sample.paperDuplicationCheck("src/test/resources/orig.txt","src/test/resources/orig_0.8_dis_1.txt","src/test/resources/result.txt");
    }
    @Test//测试案例
    void testPaperDuplicationCheck4() {
        sample.paperDuplicationCheck("src/test/resources/orig.txt","src/test/resources/orig_0.8_dis_10.txt","src/test/resources/result.txt");
    }
    @Test//测试案例
    void testPaperDuplicationCheck5() {
        sample.paperDuplicationCheck("src/test/resources/orig.txt","src/test/resources/orig_0.8_dis_15.txt","src/test/resources/result.txt");
    }
    @Test//测试案例
    void testPaperDuplicationCheck6() {
        sample.paperDuplicationCheck("src/test/resources/orig.txt","src/test/resources/orig.txt","src/test/resources/result.txt");
    }

}
