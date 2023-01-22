
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

```