package top.hunfan.kindle.config;

/**
 * 模板配置
 * @author hefan
 * @date 2019/1/24 14:26
 */
public class TemplateConfig {

    private String bookToc = "tpl/book_toc.html";
    private String chapter = "tpl/chapter.html";
    private String content = "tpl/content.opf";
    private String cover = "tpl/cover.html";
    private String style = "tpl/style.css";
    private String toc = "tpl/toc.ncx";

    public TemplateConfig() {
    }

    public String getBookToc() {
        return this.bookToc;
    }

    public TemplateConfig setBookToc(String bookToc) {
        this.bookToc = bookToc;
        return this;
    }

    public String getChapter() {
        return this.chapter;
    }

    public TemplateConfig setChapter(String chapter) {
        this.chapter = chapter;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public TemplateConfig setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCover() {
        return this.cover;
    }

    public TemplateConfig setCover(String cover) {
        this.cover = cover;
        return this;
    }

    public String getStyle() {
        return this.style;
    }

    public TemplateConfig setStyle(String style) {
        this.style = style;
        return this;
    }

    public String getToc() {
        return this.toc;
    }

    public TemplateConfig setToc(String toc) {
        this.toc = toc;
        return this;
    }

}
