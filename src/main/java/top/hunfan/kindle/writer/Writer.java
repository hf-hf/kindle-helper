package top.hunfan.kindle.writer;

import java.io.IOException;

import top.hunfan.kindle.domain.Book;

/**
 * 生成接口
 * @author hf-hf
 * @date 2019/1/25 17:46
 */
public interface Writer {

    /**
     * 生成
     * @author hf-hf
     * @date 2019/1/25 17:45
     * @param book          书籍
     * @param savePath      保存路径
     */
    void generate(Book book, String savePath) throws IOException;

}
