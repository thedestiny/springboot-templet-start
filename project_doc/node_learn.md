
```
查看已经安装的 node 版本
nvm list
安装指定版本的 node
nvm install 12.3.0 
使用指定的版本
nvm use + 版本号 
卸载指定的版本号
nvm uninstall + 版本号 
 
 nvm v 查看nvm版本号
nvm off 关闭管理node版本
nvm on 开启管理node版本
nvm arch 显示node运行在32位还是64位
nvm node_mirror [url] 设置node镜像。默认是https://nodejs.org/dist/。如果不写url，则使用默认url。设置后可至安装目录settings.txt文件查看，也可直接在该文件操作。
nvm npm_mirror [url] 设置npm镜像。https://github.com/npm/cli/archive/。如果不写url，则使用默认url。设置后可至安装目录settings.txt文件查看，也可直接在该文件操作。
nvm install <version> [arch] 安装node， version是特定版本也可以是最新稳定版本latest。可选参数arch指定安装32位还是64位版本，默认是系统位数。可以添加–insecure绕过远程服务器的SSL。


# node 历史版本
https://nodejs.org/en/download/releases/



https://blog.csdn.net/qq_48892022/article/details/125904623

指定安装 npm 
npm install npm@3.8.6 -g

删除已经安装的版本
sudo n rm 14.19.0

安装切换版本
n 14.19.0 

检查所有的版本
n
安装版本
sudo n v14.19.0 // 版本v自定义

安装 n 模块
npm install -g n


```