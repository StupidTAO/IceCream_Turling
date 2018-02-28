# IceCream_Turling
这是一个基于第三方与服务开发的Android APP，本项目与Siri功能类似可与用户对话，查询天气、检索信息、智能聊天等主要功能。为保护用户隐私并增强体验，增加了
手势解锁功能和侧滑界面等小功能。欢迎大家访问我的主页，了解更多有趣项目[http://caohaitao.club](http://caohaitao.club)
>这个项目只是为了满足个人兴趣、学习Android，如何你想制作专属的智能聊天机器人，你可以看看[图灵机器人](http://www.tuling123.com/)。

## 图灵机器人是什么？
看看[图灵机器人官方介绍](https://www.kancloud.cn/turing/web_api/522992)，可以看到如下定义：
图灵机器人API是在人工智能的核心能力（包括语义理解、智能问答、场景交互、知识管理等）的基础上，为广大开发者、合作伙伴和企业提供的一系列基于云计算和大数据
平台的在线服务和开发接口。
开发者可以利用图灵机器人的API创建各种在线服务，灵活定义机器人的属性、编辑机器人的智能问答内容，打造个人专属智能交互机器人，也支持多渠道（微信公众平台、
QQ聊天）的快速接入。

## 功能简介
智能聊天机器人功能模块共分为：引导页模块、手势解锁模块、主界面模块、侧滑菜单模块、附属功能模块
本设计主要分为两部分，app自身功能设计和第三方接口再封装设计。
### 引导页模块介绍
从欢迎界面总判断是否是首次登录，有无设置手势密码，首次登录转到引导界面，设置手势转到验证手势界面。
<img src="http://github.com/StupidTAO/Picture/blob/master/%E5%BC%95%E5%AF%BC%E9%A1%B5.png" width="150" height="200" alt="引导页"/>

### 手势解锁模块介绍
验证手势密码，从showWelcomm.xml文件提取密码，回调接口OnGestureLockViewListener中方法判断是否正确,正确就会进入主界面
![手势解锁](https://github.com/StupidTAO/Picture/blob/master/%E6%89%8B%E5%8A%BF%E8%A7%A3%E9%94%81.png)
### 主界面模块介绍
模仿QQ的聊天界面实现发送消息，同步刷新界面，实时显示消息。通过将消息包装为ChatMessage的实例对象添加至Listview中并刷新。
![主界面](https://github.com/StupidTAO/Picture/blob/master/%E4%B8%BB%E9%A1%B5%E9%9D%A2.png)
### 侧滑效果模块介绍
原理分析：在主屏幕下塞入2个布局，使用HorizontalScrollView实现水平滑动布局文件的更换和引入自定义SlidingMenu，重写继承自HorizontalScrollView 的
View工具类的使用，获得手机屏幕相关的数据
![侧滑效果](https://github.com/StupidTAO/Picture/blob/master/%E4%BE%A7%E6%BB%91%E6%95%88%E6%9E%9C.png)
### 附属功能模块介绍
实现相应响应功能需要用Intent来调用系统自带功能如相机、短信、打电话、连接网页、设置、以及选择SD卡中的照片内容来设置对话人物的头像。实现调用SD卡中符合要求
的内容，并返回值。
![附属功能1](https://github.com/StupidTAO/Picture/blob/master/%E9%99%84%E5%B1%9E%E5%8A%9F%E8%83%BD1.png)![附属功能2](https://github.com/StupidTAO/Picture/blob/master/%E9%99%84%E5%B1%9E%E5%8A%9F%E8%83%BD2.png)
## 第三方接口再封装设计
与图灵机器人智能助手SDK的交互Api 地址： http://www.tuling123.com/openapi/api
请求方式： http get
数据格式： json
请求示例http://www.tuling123.com/openapi/api?key=KEY&info=明天北京飞拉萨的飞机
返回结果（JSON格式）："code":306000 ,"text":"********",
JSON字段解析
## 贡献
欢迎任何人参与这个项目，大胆推送你的pull request（即使你是编程新手）


