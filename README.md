# 目录

- [教程视频](#教程视频)
- [使用的技术](#使用的技术)
- [环境配置](#环境配置)
- [项目启动](#项目启动)
- [已实现功能](#已实现功能)
- [待完善功能](#待完善功能)
- [更新日志](#更新日志)

<br>

## 教程视频

- [HTML教程](https://www.bilibili.com/video/BV11t411K74Q?spm_id_from=333.337.search-card.all.click)
- [CSS教程](https://www.bilibili.com/video/BV1tt411M7Vj?spm_id_from=333.337.search-card.all.click)
- [JavaScript教程](https://www.bilibili.com/video/BV1Ft411N7R3?spm_id_from=333.337.search-card.all.click)

以上三个视频教的内容稍显古老，实践时用处不大，但能够弥补很多前端基础知识，有助于开发时的理解。

- **极力推荐**：[Vue+Springboot前后端分离开发教程](https://www.bilibili.com/video/BV14y4y1M7Nc?spm_id_from=333.337.search-card.all.click)

上面的视频均学习完毕，此外也零零散散参考了其它教程。

<br>

## 使用的技术

- **前端：Vue**

  [Vue官方文档](https://v3.cn.vuejs.org/)，没屁用。

- **后端：Springboot**

  [Springboot项目自动搭建](https://start.spring.io/)，可以使用该网站完成`Springboot`脚手架搭建。

- **UI组件库：[Element Plus](https://element-plus.gitee.io/zh-CN/)**

  该官方文档比较有用。

重点关注以上三项，剩余的了解作用，直接调用即可，具体细则日后再学习。

- axios：帮助前端向后端发送各种`request`请求，配置文件路径如下。

  `/frontend-platform/utils/request.js`

- MyBatis和MyBatis-Plus：对数据库进行操作的接口。其中后者相对前者能够减少代码量，但不是很会用，目前看来只在对单表进行增删改查及分页操作时比较好使，其余情况直接使用`Mybatis`，书写SQL语句，更易理解。使用时直接导入依赖。[`mybatis-plus`官方文档（没屁用）](https://baomidou.com/)

- lombok：在后端实体类中有效，`lombok`会帮我们写好`get`和`set`方法，直接调用即可。减少了代码量。使用时直接导入依赖。
- hutool：第三方工具类，整个项目只在判字符串是否为空时用过一次，我寻思不用也可以。没有存在感。使用时直接导入依赖。[`hutool`官方文档](https://www.hutool.cn/)

更多详情见[教程视频](#教程视频)中极力推荐一项。

- （4月20日新增）echarts：用于做数据可视化。[`echarts`官方文档](https://echarts.apache.org/zh/index.html)

<br>

## 环境配置

- **数据库**：目前沿用MySQL。老师要求使用的Gauss数据库可能具有一定学习成本，且最终不一定能跑出来。是否使用Gauss数据库再议。
  - 数据库可视化操作：Navicat（破解版）
  - 建表语句见项目根目录下`course_choose.sql`文件（含结构和数据），直接将其导入MySQL即可

- **开发软件**：IDEA（破解版）
- Java版本：我用的是JDK11，用8应该也可以。

<br>

## 项目启动

- 项目结构：根目录下，`frontend-platform` 文件夹为前端项目相关代码，`backend-platform`文件夹为后端项目相关代码。

- 启动：

  - 安装`nodejs`，直接官网下载就好。安装完成后打开命令行输入命令`node -v`和`npm -v`检查版本，能够正常显示则安装成功。

  - 导入建表语句。
  
  - 直接使用IDEA打开项目最外层文件夹。
  - 初次打开需要等待maven安装依赖，该过程可能会相当漫长，可以通过配置maven镜像源来解决，具体上网搜。（maven：能够帮助我们整理大量依赖，我暂且将其理解为外部库函数）
  - 此外应该不会有其它问题，若有则上网搜。
  - 前端：在`frontend-platform`目录下打开终端（可使用命令行，也可以直接使用IDEA内置终端），输入`npm run serve`运行前端程序，终端会返回一个URL，将其复制到浏览器地址栏访问即可。
  - 后端：直接运行`DemoApplication`文件。
  - 登录账号：
    - 系统管理员：100
    - 学生：19122169~19122200
    - 教师：101~160
  
  - 登录密码：默认与账号一致，内置修改密码功能。
  
  - 若输出账号密码后点击登录没反应，可以打开`Navicat`后再尝试。

<br>

## 已实现功能

- **登录**
  + 不同的角色（系统管理员，学生，教师）登录时会跳转至对应路由。
  + 提供表单验证判空。
  + 账号密码错误提示。
- **登录后**
  - 页面右上角会显示欢迎词和当前用户姓名。
  - 点击右上角下拉框可以修改密码或退出登录。
- **学生**
  - 选课，退课功能。均提供针对课名的模糊查询。选课时提供课程时间冲突提示功能。
  - 针对所选学期显示课程表，成绩，绩点，总学分。
- **教师**
  - 可查看当前教师目前所教授的所有班级，并提供针对课名的模糊查询。
  - 能够针对任何班级进行成绩录入，各个学生的平时成绩，考试成绩由教师手动录入。
  - 录入完成后会提示要求更新综合成绩，点击对应按钮系统根据输入的平时分考试分占比，自动计算各个学生的综合成绩。若变更平时成绩或考试成绩后没有更新就返回，会提示用户且无法返回。
- **系统管理员**
  - 显示当前数据库中所有学生，教师，课程，班级信息。
  - 提供学生，教师，课程，班级信息的新增，删除，更新功能。其中四者的ID（数据库表中均为主键）需手动输入，且暂无判误（主键重复）功能。新增班级时提供教师时间冲突提示功能。
  - 学生信息管理中提供更新绩点功能，点击按钮会更新所有学生总绩点。

<br>

## 待完善功能

- 系统管理员录入信息，或是教师录入学生成绩时只能逐字段键入，效率很低。可以做一个通过导入`excel`一键录入的功能。此外也没有将表内信息导出成`excel`的功能。

- 系统管理员新增各类信息时需要输入作为主键的ID，若录入了主键重复的字段会直接报错。还需要在新增信息时加入一个检查主键是否重复的功能，出现错误时提示用户输入有误。（已实现）

下面是一些老师有要求，但目前还没有实现的功能：

- 没有设置各班级**选课人数上限**。（已实现）
- 没有为不同的角色提供各类**统计**分析，或许还需要一些图表之类的玩意儿。（教师部分已新增饼图）
- 数据库中至少包含一个**触发器**和一个**存储过程**在系统中使用和调用，没有做。（已实现）
  - 存储过程构思：或许某个查询使用可以通过调用一个存储过程实现。
  - 触发器构思：删除某学生信息的同时，删除成绩表中其相关信息。删除某教师信息，某课程信息同理。

此外，若页面上出现一些信息显示不完整（如学分，欢迎词等）的情况，请再刷新一次界面。调试程序时需善用开发者工具和Debug功能。

<br>

> 特别鸣谢：我室友
>
> *2022/4/14*

<br>

## 更新日志

- 4月16日更新
  - 修复了无法更新学生信息的bug。原因是更新所有学生绩点的方法的方法名与`MyBatis-Plus`内置方法冲突。
  - 新增院系管理页面。页面中展示了目前数据库中所有院系信息，并提供了相应增删改操作。其部分数据库相关操作的SQL写在了mapper的映射xml文件中。
  - 实现了**存储过程**的调用。系统中设计了一个存储过程，通过对它的调用，可以在院系管理页面展示各院学生数，教师数，课程数。
  - 实现了**触发器**的调用。系统中设计了四个触发器，通过对它们的调用，可以在删除某表数据的同时，删除在其余表中与该数据相关的数据。
  - 提交项目时删除了后端工程中的`target`文件夹。
  - 更新了SQL文件，新增创建存储过程和触发器的语句。
  - 提供了针对各类信息新增操作时的ID判误功能，主键重复时会提示用户。
  - 优化了UI界面。
- 4月20日更新
  - 可查看各班级学生成绩分布饼图。
  - 设置了各班级选课人数上限，并提供相关反馈。
  - 更新了SQL文件，classes表新增选课人数相关字段。
