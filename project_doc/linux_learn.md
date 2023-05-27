```
腾讯云服务器配置ssl 证书
https://cloud.tencent.com/document/product/400/35244
```

nginx ssl 配置证书
```


user root;
worker_processes auto;
error_log /var/log/nginx/error.log;
pid /run/nginx.pid;

include /usr/share/nginx/modules/*.conf;

events {
    worker_connections 1024;
}

http {
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile            on;
    tcp_nopush          on;
    tcp_nodelay         on;
    keepalive_timeout   65;
    types_hash_max_size 2048;

    include             /etc/nginx/mime.types;
    default_type        application/octet-stream;
    # 转发地址
    include /etc/nginx/conf.d/*.conf;

    server {
        listen       443 ssl http2 default_server;
        server_name  domain.top www.domain.top;
        root         /usr/share/nginx/html;

        ssl_certificate "/root/ssl_crt/domain.top_bundle.crt";
        ssl_certificate_key "/root/ssl_crt/domain.top.key";
        ssl_session_cache shared:SSL:1m;
        ssl_session_timeout  10m;
        ssl_ciphers HIGH:!aNULL:!MD5;
        ssl_prefer_server_ciphers on;

        include /etc/nginx/default.d/*.conf;

        location / {
            
        }

        error_page 404 /404.html;
        location = /404.html {
        }

        error_page 500 502 503 504 /50x.html;
        location = /50x.html {
        }
         
        location /pub/ {
           proxy_set_header   Host $http_host;
           proxy_set_header   X-Real-IP   $remote_addr;
           proxy_set_header   Remote_Addr   $remote_addr;
           proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
           proxy_pass http://localhost:8989;
        }

        location /statics/ {
           proxy_set_header   Host $http_host;
           proxy_set_header   X-Real-IP   $remote_addr;
           proxy_set_header   Remote_Addr   $remote_addr;
           proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
           proxy_pass http://localhost:8989;
        }
        


    }
    # 80端口转发到 443 地区
    server {
       listen 80;
       server_name domain.top www.domain.top;
       return 301 https://$server_name$request_uri;  

    }


}




```

##### 防火墙 epel

```

# centos7.9 防火墙操作
https://blog.csdn.net/jianxuan/article/details/121416840
# epel 操作
http://www.taodudu.cc/news/show-3524824.html?action=onClick
# 下载安装文件 epel 
wget -O /etc/yum.repos.d/epel.repo http://mirrors.cloud.tencent.com/repo/epel-7.repo

# 阿里的 repo 源即可

https://blog.csdn.net/weixin_45946254/article/details/129016056

wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum clean all 
yum makecache



service iptables status 

查看防火墙的状态
systemctl status firewalld.service
关闭防火墙
systemctl stop firewalld.service
启用防火墙
systemctl start firewalld.service
开机禁用防火墙
systemctl disable firewalld.service
开机启用防火墙
systemctl enable firewalld.service





```
