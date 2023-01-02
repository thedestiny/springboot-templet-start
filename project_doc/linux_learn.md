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
