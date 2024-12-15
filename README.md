# 项目描述
Auto-SSRF是一款基于BurpSuite MontoyaApi的自动SSRF漏洞探测插件

## 运行原理
1. 捕获参数中存在URL链接特征的请求包
2. 参数值替换为BurpSuite Collaborator的payload(类似DNSLOG)
3. 重新发包, 并监听BurpSuite Collaborator
4. 如果BurpSuite Collaborator收到目标站点发出的请求(HTTP请求),则疑似存在SSRF漏洞
5. 手动确认

## 插件截图
![img.png](https://code-pictures.oss-cn-beijing.aliyuncs.com/auto_ssrf_img.png)
![img_1.png](https://code-pictures.oss-cn-beijing.aliyuncs.com/auto_ssrf_img_1.png)
![img.png](https://code-pictures.oss-cn-beijing.aliyuncs.com/auto_ssrf_img_2.png)

## Q&&A
Q: 插件配置中的被动扫描、扫描Proxy等等指什么?

A: 插件捕获BurpSuite流量的区域
- 被动扫描 指BurpSuite本身的被动扫描器
- Proxy 指所有通过BurpSuite的MITM的流量,在Tab栏 Proxy - Http history中可以看到这些流量
- Repeater 指所有在BurpSuite Repeater中发送的流量

---
Q: 插件配置中的缓存配置是干什么的?

A: 插件使用重复流量过滤器，来防止扫描重复的流量影响效率和结果展示。已被扫描过的请求会放到缓存中，第二次扫描时插件发现该请求在缓存中已存在， 则会放弃扫描。由于缓存是存储在内存中的, 重启BurpSuite时缓存会丢失, 所以插件提供了缓存持久化机制, 可以将缓存对象序列化后存储到指定的文件中, 下次启动时插件自动将缓存对象从文件加载到内存。

---
Q: 最下面的那个保存配置有什么用?

A: 插件的配置也是存储在内存中的, 重启BurpSuite时会丢失, 保存配置按钮用来将配置存储到文件中, 下次启动时自动读取文件来恢复配置。

# 项目状态
- [ ] 开发中
- [x] 维护中
- [ ] 停止维护
---

# Features
- [x] 插件的启动/关闭取决于BurpSuite的被动扫描状态
- [x] 支持扫描Proxy、Repeater
- [x] 可疑点使用dnslog探测
- [x] 线程池大小可配置
- [x] 支持JSON请求体的扫描
- [x] 支持XML请求体的扫描
- [x] 重复流量过滤
- [x] 重复流量过滤器的缓存持久化
- [x] 配置持久化
- [ ] 接入SRC厂商提供的SSRF靶子
- [ ] 探测127.0.0.1消除误报
- [ ] 自动 Bypass