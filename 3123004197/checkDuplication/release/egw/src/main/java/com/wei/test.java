package com.wei;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class test {
    //main函数用于命令行输入使用此程序
    public static void main(String[] args) throws IOException {

        new PaperDuplicationCheck().paperDuplicationCheck(args[0],args[1],args[2] );
    }

}
