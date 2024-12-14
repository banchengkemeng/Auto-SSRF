# 项目描述
Auto-SSRF是一款基于BurpSuite MontoyaApi的自动SSRF漏洞探测插件

# 项目状态
- [x] 开发中
- [ ] 维护中
- [ ] 停止维护

# Features
- [x] 插件的启动/关闭取决于BurpSuite的被动扫描状态
- [x] 支持扫描Proxy、Repeater
- [x] 可疑点使用dnslog探测
- [x] 线程池大小可配置
- [x] 支持JSON请求体的扫描
- [x] 支持XML请求体的扫描
- [x] 重复流量过滤
- [x] 重复流量过滤器的缓存持久化
- [ ] 接入SRC厂商提供的SSRF靶子
- [ ] 探测127.0.0.1消除误报
- [ ] 自动 Bypass