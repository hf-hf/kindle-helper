package top.hunfan.kindle.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存工具类
 * @author hefan
 * @date 2019/1/25 16:29
 */
public class CacheUtils {

    public static Map<String, String> cache = new ConcurrentHashMap<>();

    public static void put(String path){
        String fileName = getFileName(path);
        cache.putIfAbsent(fileName, IOUtils.readResource(path));
    }

    public static void put(String key, String value){
        cache.putIfAbsent(key, value);
    }

    public static String get(String key){
        return cache.get(key);
    }

    public static String getFileName(String filePath){
        int index = filePath.lastIndexOf("/");
        if(-1 == index){
            return filePath;
        }
        return filePath.substring(filePath.lastIndexOf("/") + 1,
                filePath.length());
    }

}
