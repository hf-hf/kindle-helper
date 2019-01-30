package top.hunfan.kindle.utils;

import java.util.Properties;

/**
 * 环境工具
 * @author hefan
 * @date 2019/1/29 16:19
 */
public class EnvironmentUtils {

    public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWindow() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("win")) {
            return true;
        } else {
            return false;
        }
    }

}
