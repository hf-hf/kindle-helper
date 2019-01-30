package top.hunfan.kindle.domain;

/**
 * 书籍信息
 * @author hefan
 * @date 2019/1/25 14:14
 */
public class Book {

    public String id;

    public String name;

    public String author;

    public ChapterInfo[] chapters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ChapterInfo[] getChapters() {
        return chapters;
    }

    public void setChapters(ChapterInfo[] chapters) {
        this.chapters = chapters;
    }
}
