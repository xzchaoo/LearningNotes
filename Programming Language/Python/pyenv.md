https://github.com/yyuu/pyenv
https://github.com/yyuu/pyenv-virtualenv
python的多版本管理工具

在多个python版本中切换
它可以:
1. 改变全局的py版本, 或某个用户
2. 每个项目都可以有自己的py版本
3. 可以通过环境变量控制使用的版本

它本身不依赖于python, 而是shell脚本, 所以windows用户???
它本身需要加入到$PATH后才能工作.

# 注意 #
pyenv通过在$PATH的最前面加入一个特殊的目录 来达到切换版本的效果
所以你要用 python pip 而不是 /usr/bin/python3

# 如何决定版本 #
如果指定了环境变量 PYENV_VERSION, 那么就用它
pyenv shell

当前目录下的 .python-version 文件
pyenv local

最近一个父目录的 .python-version 文件
pyenv global

全局文件 $(pyenv root)/version

如果上面4个都没找到, 那么最终的效果和pyenv不存在时是一样的

# 命令 #
pyenv which

安装位置
$(pyenv root)/versions

pyenv install 3.6.0 安装3.6.0版本 可以使用tag键 搜索当前可用的版本
每次安装新版本的时候会去python官网下载 所以有点慢
要求操作系统上装有一些其他的软件, 貌似失败率挺高的, 而且非常非常慢, 需要编译
先到这个网站去安装一些依赖的包 https://github.com/yyuu/pyenv/wiki/Common-build-problems


pyenv uninstall 3.6.0 卸载
pyenv versions 查看当前已经安装的版本, 这个命令无法识别你通过其他方式安装的版本, 比如 /usr/bin/python3

pyenv local 2.7.6 将该项目的python版本设置为2.7.6, 会在当前目录下产生一个配置文件
pyenv local --unset 移除配置文件
高级用法
pyenv local 2.7.6 3.3.3
这回导致 python2.7.6 和 python3.3.3 被加入到$PATH

pyenv rehash 更新本地数据

pyenv global 3.4.1 设置全局默认版本

pyenv shell 2.7.6 设置当前shell的默认python版本是2.7.6

python -V 查看python版本

pyenv shell 2.7.6 会用环境变量控制版本

# 安装/升级 #
https://github.com/yyuu/pyenv#installation
https://github.com/yyuu/pyenv-installer

# 卸载 #
移除 /etc/bash.bashrc 中关于 pyenv 的配置
这个配置是当初安装的时候 用户手动加入的

完全卸载
rm -rf $(pyenv root)

bz2
readline
ssl


apt-get install readline readline-devel readline-static openssl openssl-devel openssl-static bzip2-devel bzip2-libs

sqlite-devel

# 于 virtualenvwrapper整合 #
1. git clone https://github.com/yyuu/pyenv-virtualenvwrapper.git ~/.pyenv/plugins/pyenv-virtualenvwrapper
2. 切换到某个具体的python版本
3. 在你的shell里执行 pyenv virtualenvwrapper 启动 wrapper
4. 此后你就可以使用 workon 了

其实当你 workon xxx 切换到xxx环境之后, 它也会在$PATH的最前面添加一个路径
所以
如果你的pyenv 切换到 2.7.6, 但是你workon到一个环境, 这个环境用的是3.4.3, 那么最终你用的是3.4.3

# 实验 #
一台机器同时装有 2.7.6 3.4.3
有一个项目p1, 使用3.4.3, 在环境ep1里, 安装 requests
有一个项目p2, 使用2.7.6, 在环境ep2里, 安装 requests
