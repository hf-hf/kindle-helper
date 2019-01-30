package top.hunfan.kindle.writer;

import top.hunfan.kindle.domain.Book;

/**
 * 生成接口
 * @author hefan
 * @date 2019/1/25 17:46
 */
public interface Writer {

    /**
     * 生成
     * @author hefan
     * @date 2019/1/25 17:45
     */
    void generate(Book book, String savePath);

}
