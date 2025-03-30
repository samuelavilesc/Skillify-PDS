package com.pds.skillify.ui;

import org.netbeans.jemmy.operators.JFrameOperator;

public class TestUtils {
    public static void waitAndClose(JFrameOperator window, long millis) throws InterruptedException {
        Thread.sleep(millis);
        window.close();
    }
}
