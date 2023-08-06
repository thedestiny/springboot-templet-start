https://mp.weixin.qq.com/s/aTJkcrvyylZ_ixMPM7-B9Q

查看nginx 版本
./nginx  -v 
启动 nginx 
./nginx 
关闭 重启
./nginx -s stop quit reload


proxy_pass 

index alias

若按照上述配置的话，则访问/img/目录里面的文件时，ningx会自动去/var/www/image/目录找文件
location /img/ {
    alias /var/www/image/;
}

若按照这种配置的话，则访问/img/目录下的文件时，nginx会去/var/www/image/img/目录下找文件
location /img/ {
    root /var/www/image;
}

alias后面必须要用 "/" 结束，否则找不到文件,而root则可有可无。

location / {
    root /var/www/;
    index index.htm index.html;
}

当用户请求 / 地址时, Nginx 就会自动在 root 配置指令指定的文件系统目录下依次寻找 index.htm 和index.html 这两个文件。
先去找 index.htm 然后再去找 index.html


location /proxy/ {
    proxy_pass http://127.0.0.1/aaa/;
}
代理到URL：http://127.0.0.1/aaa/test.html
```
# 情形A
# 访问 http://www.test.com/testa/aaaa
# 后端的request_uri为: /testa/aaaa
location ^~ /testa/ {
    proxy_pass http://127.0.0.1:8801;
}

# 情形B
# 访问 http://www.test.com/testb/bbbb
# 后端的request_uri为: /bbbb
location ^~ /testb/ {
    proxy_pass http://127.0.0.1:8801/;
}

root 不会修改 alias 起别名会修改路径
proxy_pass / 会修改路径 无 / 则不会修改请求路径

# 情形E
# 访问 http://www.test.com/ccc/bbbb
# 后端的request_uri为: /aaa/ccc/bbbb
location /ccc/ {
    proxy_pass http://127.0.0.1:8801/aaa$request_uri;
}

# 情形F
# 访问 http://www.test.com/namea/ddd
# 后端的request_uri为: /yongfu?namea=ddd
location /namea/ {
    rewrite    /namea/([^/]+) /yongfu?namea=$1 break;
    proxy_pass http://127.0.0.1:8801;
}

# 情形G
# 访问 http://www.test.com/nameb/eee
# 后端的request_uri为: /yongfu?nameb=eee
location /nameb/ {
    rewrite    /nameb/([^/]+) /yongfu?nameb=$1 break;
    proxy_pass http://127.0.0.1:8801/;
}

```

匹配优先级
``` 
location  = / {
  # 精确匹配 / ，主机名后面不能带任何字符串
  [ configuration A ]
}

location  / {
  # 因为所有的地址都以 / 开头，所以这条规则将匹配到所有请求
  # 但是正则和最长字符串会优先匹配
  [ configuration B ]
}

location /documents/ {
  # 匹配任何以 /documents/ 开头的地址，匹配符合以后，还要继续往下搜索
  # 只有后面的正则表达式没有匹配到时，这一条才会采用这一条
  [ configuration C ]
}

location ~ /documents/Abc {
  # 匹配任何以 /documents/ 开头的地址，匹配符合以后，还要继续往下搜索
  # 只有后面的正则表达式没有匹配到时，这一条才会采用这一条
  [ configuration CC ]
}

location ^~ /images/ {
  # 匹配任何以 /images/ 开头的地址，匹配符合以后，停止往下搜索正则，采用这一条。
  [ configuration D ]
}

location ~* \.(gif|jpg|jpeg)$ {
  # 匹配所有以 gif,jpg或jpeg 结尾的请求
  # 然而，所有请求 /images/ 下的图片会被 config D 处理，因为 ^~ 到达不了这一条正则
  [ configuration E ]
}

location /images/ {
  # 字符匹配到 /images/，继续往下，会发现 ^~ 存在
  [ configuration F ]
}

location /images/abc {
  # 最长字符匹配到 /images/abc，继续往下，会发现 ^~ 存在
  # F与G的放置顺序是没有关系的
  [ configuration G ]
}

location ~ /images/abc/ {
  # 只有去掉 config D 才有效：先最长匹配 config G 开头的地址，继续往下搜索，匹配到这一条正则，采用
    [ configuration H ]
}

location ~* /js/.*/\.js

```


```

# 端口转发
server{
  listen 88;
  server_name  112.225.37.221 112.225.37.197;
  location / {
    proxy_pass  http://127.0.0.1:9274;
    proxy_set_header Host $proxy_host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
  }
}


```


underscores_in_headers 默认 off 即默认忽略带下划线的 header 所以自定义 header 时最好使用带-的header
这样做是为了避免把 headers 映射为 CGI 变量时出现歧义，因为破折号和下划线都会被映射为下划线，所以两者不好区分
