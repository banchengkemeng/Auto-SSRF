# 项目描述
Auto-SSRF是一款基于BurpSuite MontoyaApi的自动SSRF漏洞探测插件

# 项目状态
- [x] 开发中
- [ ] 维护中
- [ ] 停止维护

# Features
- [x] 插件的启动/关闭取决于BurpSuite的被动扫描状态
- [x] 可疑点使用dnslog探测
- [x] 线程池大小可配置
- [ ] 接入SRC厂商提供的SSRF靶子
- [ ] 支持主动扫描
- [ ] 探测127.0.0.1消除误报
- [ ] 自动 Bypass