package top.hunfan.kindle.utils;

import java.util.Properties;

/**
 * 环境工具
 * @author hefan
 * @date 2019/1/29 16:19
 */
public class EnvironmentUtils {

    private static final String ENV_LINUX = "linux";

    private static final String ENV_WIN = "win";

    public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf(ENV_LINUX) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWindow() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith(ENV_WIN)) {
            return true;
        } else {
            return false;
        }
    }

}
