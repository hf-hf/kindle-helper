# kindle-helper

<p align="left">
    <a>
    	<img src="https://img.shields.io/badge/JDK-1.8+-brightgreen.svg" >
    </a>
</p>

## Project Introduction
Kindle-helper is a Java dependency package that can help us quickly generate `mobi` e-books with cover and catalog.

## Software Environment
- JDK1.8+
- Maven3.0+

## Realization Principle
Kindle-helper builds `opf` and other files that are born into books through a series of template files, and then calls kindleGen to generate `mobi` files.

## Operation Instructions
Because of the size of the package, kindleGen is not included in the dependent package, so please put kindleGen under the /bin directory of the same level of the jar package before running. You can also customize the external kindleGen directory. You can build MobiWriter through kindlegenPath.

[kindleGen Official Download](https://www.amazon.com/gp/feature.html?docId=1000765211)

## Instructions
Build the Book and fill in the book chapters, then call the corresponding file generator.

## Generate `.mobi`
Create a MobiWriter incoming Book to generate `mobi`. To attach a cover image, call the MobiWriter constructor of the coverUrl parameter, which currently only supports remote images.

## Generate `.txt`
Create TxtWriter to pass in Book to generate `txt'.

## Run Screenshots
![demo](/images/demo.gif)

## Code Example
```
Book book = new Book();
ChapterInfo chapterInfo = new ChapterInfo();
//...Assign Book and chapterInfo
book.setChapters(new ChapterInfo[]{chapterInfo});
Writer writer = new MobiWriter();
writer.generate(book, "D:\\tmp3");
```
See you for details.[Main.java](/src/test/java/top/hunfan/kindle/Main.java)
 
# Maven Temporary Repository
Add the following configuration to the next layer of the project element of the POM file：

```
<repositories>
    <repository>
        <id>mvnrepo</id>
        <name>mvn repository</name>
        <url>https://gitee.com/hf-hf/mvnrepo/blob/master</url>
    </repository>
</repositories>
```

Then add kindle-helper dependencies：

```
<dependency>
    <groupId>top.hunfan</groupId>
    <artifactId>kindle-helper</artifactId>
    <version>0.0.5</version>
</dependency>
```