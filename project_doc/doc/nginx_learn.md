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
