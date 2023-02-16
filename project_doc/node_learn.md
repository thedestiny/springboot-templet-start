
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


# 升级到稳定版
sudo n stable
# 升级到最新版
sudo n lastest
# 切换使用node版本
sudo n 12.13.0
# 删除某个node版本
sudo n rm 12.13.0
# 用指定版本执行脚本
sudo n use 12.13.0  some.js


  nvm install 8.0.0                     Install a specific version number
  nvm use 8.0                           Use the latest available 8.0.x release
  nvm run 6.10.3 app.js                 Run app.js using node 6.10.3
  nvm exec 4.8.3 node app.js            Run `node app.js` with the PATH pointing to node 4.8.3
  nvm alias default 8.1.0               Set default node version on a shell
  nvm alias default node                Always default to the latest available node version on a shell

  nvm install node                      Install the latest available version
  nvm use node                          Use the latest version
  nvm install --lts                     Install the latest LTS version
  nvm use --lts                         Use the latest LTS version


```