# kindle-helper

[English](https://github.com/hf-hf/kindle-helper/tree/master/README_EN.md)

<p align="left">
    <a>
    	<img src="https://img.shields.io/badge/JDK-1.8+-brightgreen.svg" >
    </a>
</p>

## 项目介绍
Kindle-helper，支持使用kindlegen生成包含封面、目录的mobi格式文件。

## 生成mobi
kindlegen需放置在jar包同级目录的/bin下，系统会根据当前环境windows/linux使用指定的可执行文件(kindlegen/kindlegen.exe)，创建MobiWriter对象传入Book生成mobi。

[kindlegen官方下载](https://www.amazon.com/gp/feature.html?docId=1000765211)

## 生成epub
暂无

## 生成txt
TxtWriter

## Gitee临时maven仓库

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

添加kindle-helper依赖：

```
 <dependency>
	<groupId>top.hunfan</groupId>
	<artifactId>kindle-helper</artifactId>
	<version>1.0-SNAPSHOT</version>
 </dependency>
```

## 代码示例

详情见[Main.java](/src/test/java/top/hunfan/kindle/Main.java)
 
