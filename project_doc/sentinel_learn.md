https://mp.weixin.qq.com/s/wVYEPqt98VYzIhOeNIdktg

关于限流的算法大体上可以分为四类：固定窗口计数器、滑动窗口计数器、

漏桶（也有称漏斗 Leaky bucket）、
流量流入的速度是不定的，但是流出的速度是恒定的,也就是说请求的流量超过漏桶大小，那么超出的流量将会被丢弃，接收的请求以固定的速率进行处理。

令牌桶（ Token bucket）
以一定地速度往令牌桶里丢令牌，当一个请求过来的时候，会去令牌桶里申请一个令牌，如果能够获取到令牌，那么请求就可以正常进行，反之被丢弃。
像 Guava 和 Sentinel 的实现都有冷启动 / 预热的方式。为了避免在流量激增的同时把系统打挂，令牌桶算法会在最开始一段时间内冷启动，随着流量的增加，系统会根据流量大小动态地调整生成令牌的速度，直到最终请求达到系统阈值。

Sentinel 中统计用到了滑动窗口算法，然后也有用到漏桶、令牌桶算法


