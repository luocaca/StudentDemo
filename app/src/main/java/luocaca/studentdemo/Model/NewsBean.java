package luocaca.studentdemo.Model;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

/**
 * https://github.com/open-android/RetrofitUtils/blob/master/retrofitutils/src/main/java/com/itheima/retrofitutils/HttpHelper.java
 * https://github.com/open-android/BaseRecyclerAndAdapter
 * Created by Administrator on 2017/12/28 0028.
 */

public class NewsBean extends BasePageBean<NewsBean.Resultbean.Itemsbean> {

    /**
     * code : 1
     * message : success
     * result : {"items":[{"author":"淡漠悠然","body":"DBCP （Database Connection Pool）是一个依赖Jakarta commons-pool对象池机制的数据库连接池，Tomcat的数据源使用的就是DBCP。 更新内容： 将A...","commentCount":10,"href":"https://www.oschina.net/news/91938/apache-commons-dbcp-2-2-0","id":91938,"pubDate":"2017-12-28 14:17:37","recommend":true,"title":"Apache Commons DBCP  2.2.0 发布","type":6,"viewCount":726},{"author":"疯狂的二哈","body":"       RhaPHP微信免费开源公众号管理系统，支持多公众号管理，平台独立且快速简洁易用。灵活的扩展应用机制，具有容易上手，几乎融合微信接...","commentCount":4,"href":"https://www.oschina.net/news/91936/rhaphp-1-2-5-released","id":91936,"pubDate":"2017-12-28 12:21:49","recommend":true,"title":"微信公众号管理系统 RhaPHP1.2.5 新版本发布 ","type":6,"viewCount":1013},{"author":"青苗","body":"mybatis-plus 是一款 mybatis 动态 SQL 自动注入 mybatis 增删改查 CURD&nbsp;操作中间件。让 mybatis 拥有 hibernate 的单表高效，也保留 xml sq...","commentCount":10,"href":"https://www.oschina.net/news/91935/mybatis-plus-2-1-8","id":91935,"pubDate":"2017-12-28 11:37:13","recommend":true,"title":"mybatis-plus 2.1.8-SNAPSHOT 发布，代号：翻车鱼","type":6,"viewCount":1187},{"author":"88250","body":"这是 Pipe 博客平台的第一个正式版，欢迎大家使用和反馈建议！ 简介 Pipe 是一款小而美的开源博客平台，通过黑客派账号登录即可使用。 动机 产品...","commentCount":5,"href":"https://www.oschina.net/news/91934/pipe-1-0-0-released","id":91934,"pubDate":"2017-12-28 11:29:14","recommend":true,"title":"Go 开源博客平台 Pipe 1.0.0 发布！","type":6,"viewCount":233},{"author":"周其","body":"2017年编程语言排行榜出炉了。该排行榜由 EEE Spectrum 杂志发布。 在榜单上，我们可以发现，排在前三名的分别为：Python、C、Java 。 被程序员称为...","commentCount":54,"href":"https://www.oschina.net/news/91933/2017-programming-language-list-released","id":91933,"pubDate":"2017-12-28 10:57:35","recommend":true,"title":"2017 年编程语言排行榜：php 仅第 8 名，Java 第 3！","type":6,"viewCount":5625},{"author":"杰睿宁","body":"MicroDao同时支持mysql和oracle MicroDao相对mybatis的优点： sql脚本支持修改后热部署实时生效。 bean与数据库字段映射关系，通过注解设置到bean中...","commentCount":0,"href":"https://www.oschina.net/news/91932/micro-dao-v1-0-2-released","id":91932,"pubDate":"2017-12-28 10:50:01","recommend":false,"title":"MicroDao 中式道(dao) v1.0.2 发布","type":6,"viewCount":322},{"author":"淡漠悠然","body":"caffeine 2.6.1 已发布。Caffeine 是基于 Java 8 的高性能，接近最佳的缓存库。更新内容： 高速缓存 Fixed null value being propagated to callb...","commentCount":6,"href":"https://www.oschina.net/news/91931/caffeine-2-6-1-released","id":91931,"pubDate":"2017-12-28 10:40:33","recommend":false,"title":"caffeine 2.6.1 发布，Java 8 的高性能缓存库","type":6,"viewCount":834},{"author":"UPUPW","body":"柚皮Windows服务器集成环境智控平台（以下简称UPUPW ANK），主要用于一键部署全能网站运行环境，适用服务器生产环境和本地开发环境。 UPUPW ANK控制...","commentCount":13,"href":"https://www.oschina.net/news/91929/upupw-ank-1-1-4-released","id":91929,"pubDate":"2017-12-28 09:28:56","recommend":false,"title":"服务器全能环境 UPUPW ANK V1.1.4 发布","type":6,"viewCount":1383},{"author":"周其","body":"为何选择将 VoIP 技术开源？音视频技术还可以应用在哪里？还可以触及哪些未知的领域？图鸭科技创始人武俊敏为我们解答。","commentCount":9,"href":"https://www.oschina.net/question/3703517_2272127","id":2272127,"pubDate":"2017-12-28 08:43:29","recommend":false,"title":"图鸭科技武俊敏：技术无国界，开源让技术向前迈进一步","type":2,"viewCount":1444},{"author":"周其","body":"一个纯PHP实现的多进程，定时任务管理工具，支持守护进程","commentCount":0,"href":"https://gitee.com/jianglibin/cron-manager","id":91927,"pubDate":"2017-12-28 08:29:53","recommend":false,"title":"码云推荐 | PHP 实现的定时任务管理工具 cron-manager","type":0,"viewCount":5},{"author":"周其","body":"Haven 可将 Android 智能手机转变为个人监控系统，以监控任何试图窃取隐私和安全的意外入侵者，可保护自己的个人空间和财物而不会泄露自己的隐私。...","commentCount":7,"href":"https://www.oschina.net/p/haven","id":46538,"pubDate":"2017-12-28 08:29:20","recommend":false,"title":"Haven \u2014 将手机变成个人监控系统","type":1,"viewCount":3268},{"author":"周其","body":"要了解Mysql的通讯协议，首先需要知道是以哪种连接方式去连接Mysql服务器的。","commentCount":8,"href":"https://my.oschina.net/OutOfMemory/blog/1595684","id":1595684,"pubDate":"2017-12-28 08:29:02","recommend":false,"title":"每日一博 | Mysql 通讯协议分析","type":3,"viewCount":1811},{"author":"周其","body":"技术始终是不断发展着的。OpenStack，Progressive Web Apps，Rust，R，认知云，人工智能（AI），物联网等新的发展正在把我们传统的认知模式抛弃。以...","commentCount":4,"href":"https://www.oschina.net/news/91924/10-open-source-technology-trends-2018","id":91924,"pubDate":"2017-12-28 08:28:48","recommend":false,"title":"2018 年开源技术 10 大发展趋势","type":6,"viewCount":2996},{"author":"周其","body":"今年，微服务，容器和云非常普遍。6月份，Evans Data Corp 进行的一项调查显示，几乎一半的云开发人员正在使用不可变的架构和微服务。有几项新服务...","commentCount":4,"href":"https://www.oschina.net/news/91922/a-new-era-in-it-architectures","id":91922,"pubDate":"2017-12-28 08:28:18","recommend":false,"title":"2017 年，开启了 IT 架构的新纪元","type":6,"viewCount":2588},{"author":"周其","body":"过去一年，网络安全问题成为各大媒体争相报道的新闻。从全球70多个国家57,000多台计算机受到黑客攻击到美国国家安全局Windows操作系统的被勒索软件...","commentCount":11,"href":"https://www.oschina.net/news/91921/lack-of-cyber-security-professionals","id":91921,"pubDate":"2017-12-28 08:27:49","recommend":false,"title":"黑客攻击频发 : 原因是缺乏网络安全专员 ?","type":6,"viewCount":1320},{"author":"周其","body":"年初有预感，今年是我的大灾之年，一整年都小心翼翼，临近年底，各种烦心事翁涌而至，莫非是要翻船了。","commentCount":32,"href":"https://my.oschina.net/xxiaobian/blog/1595901","id":1595901,"pubDate":"2017-12-28 08:27:37","recommend":false,"title":"周四乱弹 \u2014 前两天BUG还没改完啊？老子不改了！","type":3,"viewCount":3913},{"author":"周其","body":"在这个阳历年即将过去的一周，不妨来看看，开源之道眼中的2017都发生了哪些可能改变历史的事情。 1.GitHub 发布开源指南 GitHub 在今年2月14日的发...","commentCount":8,"href":"https://www.oschina.net/news/91919/opensource-big-news-in-2017","id":91919,"pubDate":"2017-12-28 08:27:06","recommend":false,"title":"2017 年，开源界发生了哪些事？","type":6,"viewCount":2031},{"author":"达尔文","body":"Manjaro Linux 17.1-rc3 发布了。开发团队的工作重心主要放在 Calamares，Xorg-Server，QT 5.10 和内核上。此外，在新安装包中添加了 Thunderbird。...","commentCount":6,"href":"https://www.oschina.net/news/91918/manjaro-17-1-rc3-released","id":91918,"pubDate":"2017-12-28 08:23:42","recommend":false,"title":"Manjaro Linux 17.1-rc3，基于 Arch Linux 的操作系统","type":6,"viewCount":741},{"author":"达尔文","body":"Mozilla Firefox 58.0 Beta 13 发布了。新版引入了 WebVR 的支持，此版本在旧微码下的 Intel Broadwell-U 处理器平台上运行可能会有崩溃的 bug。 ...","commentCount":3,"href":"https://www.oschina.net/news/91917/firefox-58-beta13-released","id":91917,"pubDate":"2017-12-28 08:17:04","recommend":false,"title":"Mozilla Firefox 58.0 Beta 13 (Quantum) 发布","type":6,"viewCount":1111},{"author":"达尔文","body":"Ember.js v2.18.0-beta.5 发布了，Ember.js 是一个用于创建 web 应用的 JavaScript MVC 框架，采用基于字符串的 Handlebars 模板，支持双向绑定、观...","commentCount":0,"href":"https://www.oschina.net/news/91916/emberjs-2-18-0-beta-5","id":91916,"pubDate":"2017-12-28 08:13:41","recommend":false,"title":"Ember.js v2.18.0-beta.5 发布，JavaScript MVC 框架","type":6,"viewCount":475}],"nextPageToken":"DBA816934CD0AA59","prevPageToken":"0997C855C600E421","requestCount":20,"responseCount":20,"totalResults":84467}
     * time : 2017-12-28 20:59:54
     */

    public int code;
    public String message;
    public Resultbean result;
    public String time;

    @Override
    public List<NewsBean.Resultbean.Itemsbean> getItemDatas() {
//        return getItemDatas();
        return result.items;
    }

    public static class Resultbean {
        /**
         * items : [{"author":"淡漠悠然","body":"DBCP （Database Connection Pool）是一个依赖Jakarta commons-pool对象池机制的数据库连接池，Tomcat的数据源使用的就是DBCP。 更新内容： 将A...","commentCount":10,"href":"https://www.oschina.net/news/91938/apache-commons-dbcp-2-2-0","id":91938,"pubDate":"2017-12-28 14:17:37","recommend":true,"title":"Apache Commons DBCP  2.2.0 发布","type":6,"viewCount":726},{"author":"疯狂的二哈","body":"       RhaPHP微信免费开源公众号管理系统，支持多公众号管理，平台独立且快速简洁易用。灵活的扩展应用机制，具有容易上手，几乎融合微信接...","commentCount":4,"href":"https://www.oschina.net/news/91936/rhaphp-1-2-5-released","id":91936,"pubDate":"2017-12-28 12:21:49","recommend":true,"title":"微信公众号管理系统 RhaPHP1.2.5 新版本发布 ","type":6,"viewCount":1013},{"author":"青苗","body":"mybatis-plus 是一款 mybatis 动态 SQL 自动注入 mybatis 增删改查 CURD&nbsp;操作中间件。让 mybatis 拥有 hibernate 的单表高效，也保留 xml sq...","commentCount":10,"href":"https://www.oschina.net/news/91935/mybatis-plus-2-1-8","id":91935,"pubDate":"2017-12-28 11:37:13","recommend":true,"title":"mybatis-plus 2.1.8-SNAPSHOT 发布，代号：翻车鱼","type":6,"viewCount":1187},{"author":"88250","body":"这是 Pipe 博客平台的第一个正式版，欢迎大家使用和反馈建议！ 简介 Pipe 是一款小而美的开源博客平台，通过黑客派账号登录即可使用。 动机 产品...","commentCount":5,"href":"https://www.oschina.net/news/91934/pipe-1-0-0-released","id":91934,"pubDate":"2017-12-28 11:29:14","recommend":true,"title":"Go 开源博客平台 Pipe 1.0.0 发布！","type":6,"viewCount":233},{"author":"周其","body":"2017年编程语言排行榜出炉了。该排行榜由 EEE Spectrum 杂志发布。 在榜单上，我们可以发现，排在前三名的分别为：Python、C、Java 。 被程序员称为...","commentCount":54,"href":"https://www.oschina.net/news/91933/2017-programming-language-list-released","id":91933,"pubDate":"2017-12-28 10:57:35","recommend":true,"title":"2017 年编程语言排行榜：php 仅第 8 名，Java 第 3！","type":6,"viewCount":5625},{"author":"杰睿宁","body":"MicroDao同时支持mysql和oracle MicroDao相对mybatis的优点： sql脚本支持修改后热部署实时生效。 bean与数据库字段映射关系，通过注解设置到bean中...","commentCount":0,"href":"https://www.oschina.net/news/91932/micro-dao-v1-0-2-released","id":91932,"pubDate":"2017-12-28 10:50:01","recommend":false,"title":"MicroDao 中式道(dao) v1.0.2 发布","type":6,"viewCount":322},{"author":"淡漠悠然","body":"caffeine 2.6.1 已发布。Caffeine 是基于 Java 8 的高性能，接近最佳的缓存库。更新内容： 高速缓存 Fixed null value being propagated to callb...","commentCount":6,"href":"https://www.oschina.net/news/91931/caffeine-2-6-1-released","id":91931,"pubDate":"2017-12-28 10:40:33","recommend":false,"title":"caffeine 2.6.1 发布，Java 8 的高性能缓存库","type":6,"viewCount":834},{"author":"UPUPW","body":"柚皮Windows服务器集成环境智控平台（以下简称UPUPW ANK），主要用于一键部署全能网站运行环境，适用服务器生产环境和本地开发环境。 UPUPW ANK控制...","commentCount":13,"href":"https://www.oschina.net/news/91929/upupw-ank-1-1-4-released","id":91929,"pubDate":"2017-12-28 09:28:56","recommend":false,"title":"服务器全能环境 UPUPW ANK V1.1.4 发布","type":6,"viewCount":1383},{"author":"周其","body":"为何选择将 VoIP 技术开源？音视频技术还可以应用在哪里？还可以触及哪些未知的领域？图鸭科技创始人武俊敏为我们解答。","commentCount":9,"href":"https://www.oschina.net/question/3703517_2272127","id":2272127,"pubDate":"2017-12-28 08:43:29","recommend":false,"title":"图鸭科技武俊敏：技术无国界，开源让技术向前迈进一步","type":2,"viewCount":1444},{"author":"周其","body":"一个纯PHP实现的多进程，定时任务管理工具，支持守护进程","commentCount":0,"href":"https://gitee.com/jianglibin/cron-manager","id":91927,"pubDate":"2017-12-28 08:29:53","recommend":false,"title":"码云推荐 | PHP 实现的定时任务管理工具 cron-manager","type":0,"viewCount":5},{"author":"周其","body":"Haven 可将 Android 智能手机转变为个人监控系统，以监控任何试图窃取隐私和安全的意外入侵者，可保护自己的个人空间和财物而不会泄露自己的隐私。...","commentCount":7,"href":"https://www.oschina.net/p/haven","id":46538,"pubDate":"2017-12-28 08:29:20","recommend":false,"title":"Haven \u2014 将手机变成个人监控系统","type":1,"viewCount":3268},{"author":"周其","body":"要了解Mysql的通讯协议，首先需要知道是以哪种连接方式去连接Mysql服务器的。","commentCount":8,"href":"https://my.oschina.net/OutOfMemory/blog/1595684","id":1595684,"pubDate":"2017-12-28 08:29:02","recommend":false,"title":"每日一博 | Mysql 通讯协议分析","type":3,"viewCount":1811},{"author":"周其","body":"技术始终是不断发展着的。OpenStack，Progressive Web Apps，Rust，R，认知云，人工智能（AI），物联网等新的发展正在把我们传统的认知模式抛弃。以...","commentCount":4,"href":"https://www.oschina.net/news/91924/10-open-source-technology-trends-2018","id":91924,"pubDate":"2017-12-28 08:28:48","recommend":false,"title":"2018 年开源技术 10 大发展趋势","type":6,"viewCount":2996},{"author":"周其","body":"今年，微服务，容器和云非常普遍。6月份，Evans Data Corp 进行的一项调查显示，几乎一半的云开发人员正在使用不可变的架构和微服务。有几项新服务...","commentCount":4,"href":"https://www.oschina.net/news/91922/a-new-era-in-it-architectures","id":91922,"pubDate":"2017-12-28 08:28:18","recommend":false,"title":"2017 年，开启了 IT 架构的新纪元","type":6,"viewCount":2588},{"author":"周其","body":"过去一年，网络安全问题成为各大媒体争相报道的新闻。从全球70多个国家57,000多台计算机受到黑客攻击到美国国家安全局Windows操作系统的被勒索软件...","commentCount":11,"href":"https://www.oschina.net/news/91921/lack-of-cyber-security-professionals","id":91921,"pubDate":"2017-12-28 08:27:49","recommend":false,"title":"黑客攻击频发 : 原因是缺乏网络安全专员 ?","type":6,"viewCount":1320},{"author":"周其","body":"年初有预感，今年是我的大灾之年，一整年都小心翼翼，临近年底，各种烦心事翁涌而至，莫非是要翻船了。","commentCount":32,"href":"https://my.oschina.net/xxiaobian/blog/1595901","id":1595901,"pubDate":"2017-12-28 08:27:37","recommend":false,"title":"周四乱弹 \u2014 前两天BUG还没改完啊？老子不改了！","type":3,"viewCount":3913},{"author":"周其","body":"在这个阳历年即将过去的一周，不妨来看看，开源之道眼中的2017都发生了哪些可能改变历史的事情。 1.GitHub 发布开源指南 GitHub 在今年2月14日的发...","commentCount":8,"href":"https://www.oschina.net/news/91919/opensource-big-news-in-2017","id":91919,"pubDate":"2017-12-28 08:27:06","recommend":false,"title":"2017 年，开源界发生了哪些事？","type":6,"viewCount":2031},{"author":"达尔文","body":"Manjaro Linux 17.1-rc3 发布了。开发团队的工作重心主要放在 Calamares，Xorg-Server，QT 5.10 和内核上。此外，在新安装包中添加了 Thunderbird。...","commentCount":6,"href":"https://www.oschina.net/news/91918/manjaro-17-1-rc3-released","id":91918,"pubDate":"2017-12-28 08:23:42","recommend":false,"title":"Manjaro Linux 17.1-rc3，基于 Arch Linux 的操作系统","type":6,"viewCount":741},{"author":"达尔文","body":"Mozilla Firefox 58.0 Beta 13 发布了。新版引入了 WebVR 的支持，此版本在旧微码下的 Intel Broadwell-U 处理器平台上运行可能会有崩溃的 bug。 ...","commentCount":3,"href":"https://www.oschina.net/news/91917/firefox-58-beta13-released","id":91917,"pubDate":"2017-12-28 08:17:04","recommend":false,"title":"Mozilla Firefox 58.0 Beta 13 (Quantum) 发布","type":6,"viewCount":1111},{"author":"达尔文","body":"Ember.js v2.18.0-beta.5 发布了，Ember.js 是一个用于创建 web 应用的 JavaScript MVC 框架，采用基于字符串的 Handlebars 模板，支持双向绑定、观...","commentCount":0,"href":"https://www.oschina.net/news/91916/emberjs-2-18-0-beta-5","id":91916,"pubDate":"2017-12-28 08:13:41","recommend":false,"title":"Ember.js v2.18.0-beta.5 发布，JavaScript MVC 框架","type":6,"viewCount":475}]
         * nextPageToken : DBA816934CD0AA59
         * prevPageToken : 0997C855C600E421
         * requestCount : 20
         * responseCount : 20
         * totalResults : 84467
         */

        public String nextPageToken;
        public String prevPageToken;
        public int requestCount;
        public int responseCount;
        public int totalResults;
        public List<Itemsbean> items;

        public static class Itemsbean {
            /**
             * author : 淡漠悠然
             * body : DBCP （Database Connection Pool）是一个依赖Jakarta commons-pool对象池机制的数据库连接池，Tomcat的数据源使用的就是DBCP。 更新内容： 将A...
             * commentCount : 10
             * href : https://www.oschina.net/news/91938/apache-commons-dbcp-2-2-0
             * id : 91938
             * pubDate : 2017-12-28 14:17:37
             * recommend : true
             * title : Apache Commons DBCP  2.2.0 发布
             * type : 6
             * viewCount : 726
             */

            public String author;
            public String body;
            public int commentCount;
            public String href;
            public int id;
            public String pubDate;
            public boolean recommend;
            public String title;
            public int type;
            public int viewCount;

            @Override
            public String toString() {
                return "Itemsbean{" +
                        "author='" + author + '\'' +
                        ", body='" + body + '\'' +
                        ", commentCount=" + commentCount +
                        ", href='" + href + '\'' +
                        ", id=" + id +
                        ", pubDate='" + pubDate + '\'' +
                        ", recommend=" + recommend +
                        ", title='" + title + '\'' +
                        ", type=" + type +
                        ", viewCount=" + viewCount +
                        '}';
            }
        }
    }


    //http://www.oschina.net/action/apiv2/news?pageToken=
}
