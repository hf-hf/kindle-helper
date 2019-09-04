package top.hunfan.kindle.domain;

/**
 * img标签
 * @author hefan
 * @date 2019/9/4 13:52
 */
public class ImgTag {

    private String html;

    private String src;

    public ImgTag() {
    }

    public ImgTag(String html, String src) {
        this.html = html;
        this.src = src;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
