# kindle-helper

[English](https://github.com/hf-hf/kindle-helper/tree/master/README_EN.md)

<p align="left">
    <a>
    	<img src="https://img.shields.io/badge/JDK-1.8+-brightgreen.svg" >
    </a>
</p>

## 项目介绍
kindle-helper是一个Java依赖包，它可以帮助我们快捷的生成包含封面、目录的 `.mobi` 电子书。

## 软件环境
- JDK1.8+
- Maven3.0+

## 实现原理
kindle-helper通过一系列模板文件构建出生成书籍的 `.opf` 等文件，之后调用kindleGen生成 `.mobi` 文件。

## 运行须知
因打包大小问题，依赖包中未包含kindleGen，因此运行前请将kindleGen放置在jar包同级目录的/bin下，也可自定义外部kindleGen目录，请通过kindlegenPath构建MobiWriter。

[kindleGen官方下载](https://www.amazon.com/gp/feature.html?docId=1000765211)

## 使用说明
构建Book，并填充书籍章节chapters，之后调用对应的文件生成器即可。

### 生成`.mobi`
创建MobiWriter传入Book生成 `.mobi`，若要附带封面图片，请调用coverUrl参数的MobiWriter构造器，目前仅支持远程图片。

### 生成`.txt`
创建TxtWriter传入Book生成 `.txt`。

## 运行截图
![demo](/images/demo.gif)

## 代码示例
```
Book book = new Book();
ChapterInfo chapterInfo = new ChapterInfo();
//...赋值Book和chapterInfo
book.setChapters(new ChapterInfo[]{chapterInfo});
Writer writer = new MobiWriter();
writer.generate(book, "D:\\tmp3");
```
详情见[Main.java](/src/test/java/top/hunfan/kindle/Main.java)

## Maven临时仓库
在pom文件project元素的下一层增加如下配置：

```
<repositories>
    <repository>
        <id>mvnrepo</id>
        <name>mvn repository</name>
        <url>https://gitee.com/hf-hf/mvnrepo/blob/master</url>
    </repository>
</repositories>
```

之后添加kindle-helper依赖：

```
<dependency>
    <groupId>top.hunfan</groupId>
    <artifactId>kindle-helper</artifactId>
    <version>0.0.5</version>
</dependency>
```
