# nvm #
nodejs版本管理器
https://github.com/creationix/nvm

https://github.com/coreybutler/nvm-windows
windows版本


# 安装nvm #
本身不需要安装node
最新的额命令可以到官网去找
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.6/install.sh | bash
注销重启, 就有nvm命令了

# 命令 #

nvm install node 安装最新的node
nvm install 8.8.1 安装8.8.1版本
nvm use node 切换到node, 其实这里node是一个别名
bvn install 6.11.5 切换到6.11.5
nvm run node --version 用最新版本的node执行
nvm exec 4.2 node --version 用4.2的版本执行 "node --verion命令"
nvm which 5.0 查看这个版本的node的安装位置

nvm ls 列出已经安装了哪些node, 这里也可以看出别名
nvm ls-remote 列出总的有哪些node是支持的
nvm use 8.0 切换版本
npm run 6.10.3 app.js 用6.10.3跑app.js
nvm alias default node 设置当前PATH里的node为默认版本
nvm alias default 8.1.0 用8.1.0作为默认版本

deactivate

