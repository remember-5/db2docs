# 描述
因为最近需要编写数据库表结构文档,于是基于`swing`和 [screw](https://github.com/pingfangushi/screw) 做了GUI版本的生成文档小工具

GUI方式的数据库生成文档，降低了门槛，运维人员和产品经理也可以轻松生成使用

![](./docs/1.jpg)
![](./docs/2.jpg)
![](./docs/3.jpg)

## 文档截图

+ **html**

<p align="center">
   <img alt="HTML" src="https://images.gitee.com/uploads/images/2020/0622/161414_74cd0b68_1407605.png">
</p>
<p align="center">
   <img alt="screw-logo" src="https://images.gitee.com/uploads/images/2020/0622/161723_6da58c41_1407605.png">
</p>

+ **word**

<p align="center">
   <img alt="word" src="https://images.gitee.com/uploads/images/2020/0625/200946_1dc0717f_1407605.png">
</p>

+ **markdwon**

<p align="center">
   <img alt="markdwon" src="https://images.gitee.com/uploads/images/2020/0625/214749_7b15d8bd_1407605.png">
</p>
<p align="center">
   <img alt="markdwon" src="https://images.gitee.com/uploads/images/2020/0625/215006_3601e135_1407605.png">
</p>

- 启动环境: 需要安装 jdk1.8+ 
- 支持数据库类型: Mysql,MariaDB,TIDB,Oracle,SqlServer,PostgreSQL
- 支持生成文档类型: Word、Html、Markdown
- 启动方式: 双击jar启动 

# 如何打包
`mvn package` 会生成`db2doc.jar` 和 `db2doc-jar-with-dependencies.jar`

后者可以双击运行

# 贡献
- [screw](https://github.com/pingfangushi/screw) 感谢大佬开发的生成工具
