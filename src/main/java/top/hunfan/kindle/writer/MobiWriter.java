package top.hunfan.kindle.writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.hunfan.kindle.config.TemplateConfig;
import top.hunfan.kindle.domain.Book;
import top.hunfan.kindle.domain.ChapterInfo;
import top.hunfan.kindle.utils.CacheUtils;
import top.hunfan.kindle.utils.EnvironmentUtils;
import top.hunfan.kindle.utils.IOUtils;
import top.hunfan.kindle.utils.SeparatorUtils;

/**
 * mobi文件生成
 * @author hefan
 * @date 2019/1/25 17:46
 */
public class MobiWriter implements Writer{

    private static final Logger log = LoggerFactory.getLogger(MobiWriter.class);

    private static final String PROCESS_CMD = "%s %scontent.opf -c1 -verbose -o %s";

    private static final String MOBI_PREFIX = ".mobi";

    private static final SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

    private String tempPath;

    private File tempDirectory;

    private URL coverUrl;

    private String kindlegenPath = "." + SeparatorUtils.getFileSeparator()
            + "bin" + SeparatorUtils.getFileSeparator();

    private TemplateConfig config = new TemplateConfig();

    public MobiWriter() {
    }

    public MobiWriter(URL coverUrl) {
        this.coverUrl = coverUrl;
    }

    public MobiWriter(String kindlegenPath) {
        this.kindlegenPath = kindlegenPath;
    }

    public MobiWriter(URL coverUrl, String kindlegenPath) {
        this.coverUrl = coverUrl;
        this.kindlegenPath = kindlegenPath;
    }

    private String completingPath(String path){
        if(path.endsWith(SeparatorUtils.getFileSeparator())){
            return path;
        }
        return path += SeparatorUtils.getFileSeparator();
    }

    private void createTempPath(String savePath){
        String tempPath = savePath + format.format(new Date()) + SeparatorUtils.getFileSeparator();
        File tempDir = new File(tempPath);
        tempDir.deleteOnExit();
        tempDir.mkdirs();
        this.tempPath = tempPath;
        this.tempDirectory = tempDir;
    }

    @Override
    public void generate(Book book, String savePath) {
        generateMobi(book, completingPath(savePath));
    }

    public void generateMobi(Book book, String savePath) {
        //加载模板
        loadTemplates();
        //创建临时目录
        createTempPath(savePath);
        //生成封面
        createCover(book);
        //生成章节文件
        createChapters(book);
        //生成样式文件
        createStyle(book);
        //生成目录文件
        createBookToc(book);
        //目录跳转文件
        createNaxToc(book);
        //创建内容文件
        createOpf(book);
        //生成
        exec(book, savePath);
        //清理临时文件夹
        delelteTempDir();
    }

    private void delelteTempDir(){
        IOUtils.delete(this.tempDirectory);
    }

    private String endWithMobi(String name){
        return name + MOBI_PREFIX;
    }

    private void createCover(Book book) {
        String path = tempPath + "cover.html";
        String content = CacheUtils.get("cover.html");
        content = content.replace("___BOOK_NAME___", book.name);
        content = content.replace("___BOOK_AUTHOR___", book.author);
        content = createCoverImage(content);
        IOUtils.write(content, path);
    }

    private String createCoverImage(String content){
        if(null == this.coverUrl){
            return content.replace("___BOOK_COVER___", "");
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = this.coverUrl.openStream();
            os = new FileOutputStream(new File(this.tempPath + "cover.jpg"));
            IOUtils.write(is, os);

            content = content.replace("___BOOK_COVER___",
                    "<img src=\"cover.jpg\" alt=\"cover\" style=\"height: 100%\"/>");
        } catch (Exception e){
            log.error("create cover.jpg error!", e);
        } finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
        return content;
    }


    private void createChapters(Book book) {
        for (int i = 0; i < book.getChapters().length; i++) {
            ChapterInfo chapter = book.chapters[i];
            String path = tempPath + "chapter" + i + ".html";
            String content = CacheUtils.get("chapter.html");
            content = content.replace("___CHAPTER_ID___", "Chapter " + i);
            content = content.replace("___CHAPTER_NAME___", chapter.title);
            String chapterContent = chapter.body;
            chapterContent = chapterContent.replace("\r", "");
            String[] ps = chapterContent.split("\n");
            chapterContent = "";
            for (String p : ps) {
                String pStr = "<p class=\"a\">";
                pStr += "　　" + p;
                pStr += "</p>";
                chapterContent += pStr;
            }
            content = content.replace("___CONTENT___", chapterContent);
            IOUtils.write(content, path);
        }

    }

    private void createStyle(Book book) {
        String content = CacheUtils.get("style.css");
        String path = tempPath + "style.css";
        IOUtils.write(content, path);
    }

    private void createBookToc(Book book) {
        String path = tempPath + "book_toc.html";
        String content = CacheUtils.get("book_toc.html");
        String tocContent = "";
        for (int i = 0; i < book.getChapters().length; i++) {
            ChapterInfo chapter = book.chapters[i];
            String tocLine = String.format("<dt class=\"toc-dt\"><a href=\"chapter%d.html\">%s</a></dt>\r\n",
                    i, chapter.title);
            tocContent += tocLine;
        }
        content = content.replace("___CONTENT___", tocContent);
        IOUtils.write(content, path);
    }

    private void createNaxToc(Book book) {
        String path = tempPath + "toc.ncx";
        String content = CacheUtils.get("toc.ncx");
        content = content.replace("___BOOK_NAME___", book.name);
        content = content.replace("___BOOK_AUTHOR___", book.author);
        String tocContent = "";
        for (int i = 0; i < book.getChapters().length; i++) {
            ChapterInfo chapter = book.chapters[i];
            String tocLine = String.format("<navPoint id=\"chapter%d\" playOrder=\"%d\">\r\n", i, i + 1);
            tocLine += String.format("<navLabel><text>%s</text></navLabel>\r\n", chapter.title);
            tocLine += String.format("<content src=\"chapter%d.html\"/>\r\n</navPoint>\r\n", i);
            tocContent += tocLine;
        }
        content = content.replace("___NAV___", tocContent);
        IOUtils.write(content, path);
    }

    private void createOpf(Book book) {
        String path = tempPath + "content.opf";
        String content = CacheUtils.get("content.opf");
        content = content.replace("___BOOK_ID___", book.id);
        content = content.replace("___BOOK_NAME___", book.name);
        String manifest = "";
        String spine = "";
        for (int i = 0; i < book.getChapters().length; i++) {
            String tocLine = "";
            tocLine = String.format("<item id=\"chapter%d\" href=\"chapter%d.html\" " +
                    "media-type=\"application/xhtml+xml\"/>\r\n", i, i);
            manifest += tocLine;
            // spine
            String spineLine = "";
            spineLine = String.format("<itemref idref=\"chapter%d\" linear=\"yes\"/>\r\n", i);
            spine += spineLine;
        }
        content = content.replace("___MANIFEST___", manifest);
        content = content.replace("___SPINE___", spine);
        IOUtils.write(content, path);
    }

    private void loadTemplates() {
        CacheUtils.put(config.getCover());
        CacheUtils.put(config.getBookToc());
        CacheUtils.put(config.getChapter());
        CacheUtils.put(config.getContent());
        CacheUtils.put(config.getStyle());
        CacheUtils.put(config.getToc());
    }

    public void exec(Book book, String savePath) {
        String cmdStr = String.format(PROCESS_CMD, kindlegenPath + getToolName(),
                tempPath, endWithMobi(book.getName()));
        InputStream is = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        String textLine = "";
        try {
            Process process = Runtime.getRuntime().exec(cmdStr);
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((textLine = br.readLine()) != null) {
                if(0 != textLine.length()){
                    log.debug(textLine);
                }
            }
            log.debug("exec finish!");
            //移动生成的mobi到savePath
            IOUtils.copy(tempPath + endWithMobi(book.getName()),
                    savePath + endWithMobi(book.getName()));
        } catch (IOException e) {
            log.error("exec error!", e);
        } finally {
            IOUtils.close(br);
            IOUtils.close(isr);
            IOUtils.close(is);
        }
    }

    private String getToolName(){
        if(EnvironmentUtils.isWindow()){
            return "kindlegen.exe";
        } else {
            return "kindlegen";
        }
    }

}
