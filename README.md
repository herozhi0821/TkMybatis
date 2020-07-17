# TkMybatis
# 一、前述

**TKMybatis**与**Hikari** 在以往项目使用并不多，前段时间看网络视频学习介绍了这两个东西也就亲手试了一下，感觉还可以，并没有很复杂，上手简单。
**Hikari** 连接池是springboot中自带的，因为字节码精简、自定义数组、集合等原因，他的处理效率很好。**Druid**用的比较多，阿里开源项目，国内实践很多，所以都很棒。
**TKMybatis**则是简化了一些执行SQL，对于简单的可以实现无SQL。当然很复杂的我也没有太好方法。**Mybatis-Plus**我还没有用过，之后会去看，到时发博。
**PageHelper**感觉并没有很好，与我之前自己写的一个分页工具类差不多，都是在查询完之后再处理集合，但是个性化的东西还是不少的，满足了大多数情况。但还是不太喜欢这个，仅为学习。
整体结构如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200717163632429.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQyOTU5MDM=,size_16,color_FFFFFF,t_70)
# 二、整合
springboot项目创建过程就不再阐述，我用的sts直接就是点点点。
## 1、pom所需jar
核心jar包，其余看个人所需。Hikari的就不需要再单独引入了
```java
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.1.5</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.13</version>
        </dependency>
```

## 2、配置文件
简化，剩余参数不展示
```java
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url= jdbc:mysql://localhost:3306/security_api?serverTimezone=Hongkong&characterEncoding=UTF-8&useSSL=false
spring.datasource.username= root
spring.datasource.password= root

mybatis.mapper-locations=classpath*:mapping/menu.xml
mybatis.type-aliases-package=net.cnki.tkmybatis.pojo

spring.devtools.restart.enabled=true
```
## 3、实体
并无太多注意项
```java
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "menu")
public class Menu {
    @Id
    private Integer id;
    private String url;
    private String menuName;
    private Integer parentId;
    private Date updateTime;
    private String remark;
    private String urlPre;
}

```
## 4、公共Mapper
1.这里需要说一下，这个mapper并非service实现类引入的，并且泛型类型必须带着。
2.我只用的mysql数据库，所以继承了**MySqlMapper<T>**，同时引入放入均为tk的jar。
3.如果采用springboot启动类上扫描mapper时，不可把这个加进去。且也是tk的jar
```java
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>  {
}
```
## 5、mapper接口层
对于mybatis内的基础接口不必再添加，接口中均已实现，只有自定义SQL的接口需要再此写入，同时，继承公共Mapper。
```java
import java.util.List;

import net.cnki.pojo.Menu;
import net.cnki.tk.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {
    public List<Menu> getMenuBysql(Integer id);
}
```
xml文件只需要将字段信息放进去就可以了，自定义接口可以写入，与原方式一样。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200717170913718.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQyOTU5MDM=,size_16,color_FFFFFF,t_70)
## 6、service层以及实现层
接口就不再展示，可以自己定义了。这里需要注意，Example 方法的使用，包含很多条件，可以满足很多情况下的条件。我还没有挨个的看就不一一阐述。
直接就房实现层的代码吧。对于分页的使用，我习惯于在service实现层处理分页，因此在此处集成。

```java
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public PageInfo<Menu> getMenuAll() {
        PageHelper.startPage(1, 10, "update_time desc");
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(menuMapper.selectAll());
        return pageInfo;
    }

    @Override
    public List<Menu> getMenuByContions() {
        Example example = new Example(Menu.class);
        example.createCriteria().andBetween("updateTime", "2020-06-18 17:06:40", "2020-06-18 17:10:40");
        example.setOrderByClause("id desc");
        
        return menuMapper.selectByExample(example);
    }

    @Override
    public Menu getMenuByContion() {
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("menuName","上传检测");
        return menuMapper.selectOneByExample(example);
    }

    @Override
    public List<Menu> getMenuBysql(Integer id) {
        // TODO Auto-generated method stub
        return menuMapper.getMenuBysql(id);
    }
}
```
其实，分页也简单，两行即可。

```java
PageHelper.startPage(1, 10, "update_time desc");//分页条件，有多个构造，自己选择
PageInfo<Menu> pageInfo = new PageInfo<Menu>(menuMapper.selectAll());//处理集合数据
```
# 三、总结
到此处已经结束的集成。实现相对简单，但是依然存在不灵活的地方。
