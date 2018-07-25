package com.dly.routeDemo;


import android.net.Uri;

import org.junit.Test;

import java.net.URLDecoder;

import static android.provider.Telephony.Mms.Part.CHARSET;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String realUrl = "route://test:8080?name=555";
        try {
            realUrl = URLDecoder.decode(realUrl, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri uri=Uri.parse(realUrl);
        System.out.println("uri = "+uri +"\n");
        System.out.println("scheme:" + uri.getScheme() + " host:" + uri.getHost() + " Authority:"+uri.getAuthority());

    }
}