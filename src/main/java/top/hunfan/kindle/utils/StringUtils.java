package top.hunfan.kindle.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.hunfan.kindle.domain.ImgTag;

/**
 * Provide some String operations.
 * @author hefan
 * @date 2019/3/12 11:06
 */
public class StringUtils {

    private static final Pattern P_IMG_SRC =
            Pattern.compile("<img\\b[^<>]*?\\bsrc[\\s\\t\\r\\n]*=[\\s\\t\\r\\n]*[\"\"']?[\\s\\t\\r\\n]*(?<imgUrl>[^\\s\\t\\r\\n\"\"'<>]*)[^<>]*?/?[\\s\\t\\r\\n]*>",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern P_IMG_SUFFIX = Pattern.compile(".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG)");

    private static final Pattern P_P = Pattern.compile("<(P|p)+>");

    public static boolean isBlank(CharSequence cs) {
        int strLen;

        if ((cs == null) || ((strLen = cs.length()) == 0)){
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getImgSrc(String content) {
        List<String> result = new ArrayList<>();
        Matcher mImgSrc = P_IMG_SRC.matcher(content);
        String imgSrc;
        while (mImgSrc.find()) {
            imgSrc = mImgSrc.group(1);
            result.add(imgSrc);
        }
        return result;
    }

    public static List<ImgTag> getImgTag(String content) {
        List<ImgTag> result = new ArrayList<>();
        Matcher mImgSrc = P_IMG_SRC.matcher(content);
        String imgHtml;
        String imgSrc;
        while (mImgSrc.find()) {
            imgHtml = mImgSrc.group(0);
            imgSrc = mImgSrc.group(1);
            result.add(new ImgTag(imgHtml, imgSrc));
        }
        return result;
    }

    public static String getFileName(String url) {
        String src = filterImageUrl(url);
        return src.substring(src.lastIndexOf("/") + 1, src.length());
    }

    public static String filterImageUrl(String url) {
        Matcher mImg = P_IMG_SUFFIX.matcher(url);
        while (mImg.find()) {
            return mImg.group();
        }
        return url;
    }

    public static boolean isImage(String url){
        return P_IMG_SUFFIX.matcher(url).matches();
    }

    public static String filterContent(String content){
        if(paragraphed(content)){
            return content;
        }
        return paragraph(content);
    }

    public static boolean paragraphed(String content){
        Matcher mP = P_P.matcher(content);
        return mP.find();
    }

    public static String paragraph(String content){
        String chapterContent = content.replace("\r", "");
        String[] ps = chapterContent.split("\n");
        chapterContent = "";
        for (String p : ps) {
            String pStr = "<p class=\"a\">";
            pStr += "　　" + p;
            pStr += "</p>";
            chapterContent += pStr;
        }
        return chapterContent;
    }

}
