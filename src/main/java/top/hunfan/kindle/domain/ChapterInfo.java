package top.hunfan.kindle.domain;

/**
 * 章节信息
 * @author hefan
 * @date 2019/1/25 14:14
 */
public class ChapterInfo {

    public String title;

    public String body;

    public String localImagesPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocalImagesPath() {
        return localImagesPath;
    }

    public void setLocalImagesPath(String localImagesPath) {
        this.localImagesPath = localImagesPath;
    }
}
