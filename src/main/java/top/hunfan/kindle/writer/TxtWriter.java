package top.hunfan.kindle.writer;

import java.io.File;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.hunfan.kindle.domain.Book;
import top.hunfan.kindle.utils.IOUtils;

/**
 * txt文件生成
 * @author hefan
 * @date 2019/1/25 17:46
 */
public class TxtWriter implements Writer{

    private static final Logger log = LoggerFactory.getLogger(TxtWriter.class);

    private static final String TXT_PREFIX = ".txt";

    private static final String NEW_LINE = "\r\n";

    @Override
    public void generate(Book book, String savePath) {
        if(book.getChapters() == null || book.getChapters().length == 0){
            log.info("generate book chapter is empty!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Arrays.stream(book.getChapters()).forEach(info ->
                sb.append(info.title)
                        .append(NEW_LINE)
                        .append(info.body)
                        .append(NEW_LINE)
        );
        String filePath = savePath + File.separator + book.getName() + TXT_PREFIX;
        IOUtils.write(sb.toString(), new File(filePath));
    }

}
