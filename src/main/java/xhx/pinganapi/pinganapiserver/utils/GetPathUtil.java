package xhx.pinganapi.pinganapiserver.utils;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class GetPathUtil {
    /*public static void main(String[] args) {
        String path = Thread.currentThread().getContextClassLoader().getResource("url.properties").getPath();
        Properties properties = new Properties();
        try {
            FileInputStream fs = new FileInputStream(path);
            properties.load(fs);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        String str = properties.getProperty("url71");
        System.out.println(str);
    }*/
    public static String getUrl(String url){
        String path = Thread.currentThread().getContextClassLoader().getResource("url.properties").getPath();
        Properties properties = new Properties();
        try {
            FileInputStream fs = new FileInputStream(path);
            properties.load(fs);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        String str = properties.getProperty(url);
        return str;
    }
}
