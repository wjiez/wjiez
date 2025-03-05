package com.wei;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EgwApplicationTests {

    @Test
    void testPaperDuplicationCheck() throws Exception {

        new PaperDuplicationCheck().paperDuplicationCheck("E:\\test\\o.txt","E:\\test\\c.txt","E:\\test");
    }

}
