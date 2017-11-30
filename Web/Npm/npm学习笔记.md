# npm的安装与更新 #
## 安装 ##

## 更新 ##
npm install npm@latest -g


# 配置 #
## 修改全局依赖的位置 ##
mkdir ~/.npm-global
npm config set prefix '~/.npm-global'
export PATH=~/.npm-global/bin:$PATH
 source ~/.profile

# npm #
https://docs.npmjs.com/

获得安装路径 当全局安装时
npm config get prefix

设置安装路径
npm config set prefix '新的路径'
但是你要记得将该路径到处到path中, 否则无法直接在命令行执行指令
export PATH=新的路径/bin:$PATH

全局安装
npm install -g <NAME>

本地安装/安装在当前目录
npm install lodash --save
--save 将会在package.json写入依赖项

npm init -y 快速初始化


下载安装lodash到当前目录 并且在 package.json 里添加依赖信息
npm install lodash --save

https://docs.npmjs.com/getting-started/updating-local-packages

设置proxy
npm config set proxy http://cache.sjtu.edu.cn:8080
npm set proxy ...
npm config get proxy
npm get proxy
npm delete proxy
npm config edit

# package.json #
package.json用于描述:
- 描述
- 依赖


更新
npm update
npm update -g

npm uninstall lodash

npm uninstall --save lodash

npm install -g lodash

To find out which packages need to be updated, you can use npm outdated -g --depth=0.

npm uninstall -g jshint

使用npm publish 就可以把你当前的东西发布出去
然后到
https://www.npmjs.com/package/demo-test
去查看 其中demo-test是我们的项目名

参数
--A B	设置A为B
--A		设置A为true
--		参数结束
环境变量
npm_config_A=B 设置环境变量A=B
npm_config_A=B 设置环境变量A=true


npmrc Files

The four relevant files are:

per-project config file (/path/to/my/project/.npmrc)
per-user config file (~/.npmrc)
global config file ($PREFIX/npmrc)
npm builtin config file (/path/to/npm/npmrc)
See npmrc(5) for more details.

-v: --version
-h, -?, --help, -H: --usage
-s, --silent: --loglevel silent
-q, --quiet: --loglevel warn
-d: --loglevel info
-dd, --verbose: --loglevel verbose
-ddd: --loglevel silly
-g: --global
-C: --prefix
-l: --long
-m: --message
-p, --porcelain: --parseable
-reg: --registry
-f: --force
-desc: --description
-S: --save
-D: --save-dev
-O: --save-optional
-B: --save-bundle
-E: --save-exact
-y: --yes
-n: --yes false
ll and la commands: ls --long

.npmignore 跟.gitignore类似
Additionally, everything in node_modules is ignored

更新npm
npm install npm -g



# 05 - Using a 'package.json' #
最好的管理本地npm包的方式是建立一个package.json文件.
一个package.json可以让你:
	1. 可以作为一个文档描述你项目的依赖
	2. 可以让你指定依赖的项目的版本
	3. Makes your build reproducable which means that its way easier to share with other developers.
## 要求 ##
一个简单的package.json需要有: name, version
可以使用npm init来创建一个初始化的package.json
	-y, --yes 全部使用默认值
```
> npm init --yes
Wrote to /home/ag_dubs/my_package/package.json:
 
{
  "name": "my_package",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "ag_dubs",
  "license": "ISC",
  "repository": {
    "type": "git",
    "url": "https://github.com/ashleygwilliams/my_package.git"
  },
  "bugs": {
    "url": "https://github.com/ashleygwilliams/my_package/issues"
  },
  "homepage": "https://github.com/ashleygwilliams/my_package"
}
```
name: defaults to author name unless in a git directory, in which case it will be the name of the repository
version: always 1.0.0
main: always index.js
scripts: by default creates a empty test script
keywords: empty
author: whatever you provided the CLI
license: ISC
repository: will pull in info from the current directory, if present
bugs: will pull in info from the current directory, if present
homepage: will pull in info from the current directory, if present

设置
```
> npm set init.author.email "wombat@npmjs.com"
> npm set init.author.name "ag_dubs"
> npm set init.license "MIT"
```
## Specifying Packages ##
使用dependencies来指定依赖
devDenpencies用来指定开发和测试时的依赖

## Manaually editing your package.json ##
TODO
a semver expression 
npm install <package_name> --save 将依赖保存到dependencies里
npm install <package_name> --save-dev 将依赖保存到devDependencies里

## Managing dependency versions ##
 TODO
 semver rules

 # 06 - Upadating local packages #
 在package.json所在的目录下执行npm update
 npm outdated 这是啥?

 # 07 - Uninstalling local packages 卸载#
npm uninstall --save lodash

# 08 - Installing npm packages globally 全局安装npm包 #
npm install -g jshint
# 09 - Updating global packages #
更新全局的npm包
npm install -g <package>
想要找出哪些全局包需要更新的话,可以使用
npm outdated -g --depth=0.
想要更新所有全局的npm包
npm update -g. However
> for npm versions less than 2.6.1, this script is recommended to update all outdated global packages.

# 10 - Uninstalling global packages 卸载全局的包 #
npm uninstall -g jshint

# 11 - Creating Node.js modules #
TODO

# 12 - Publishing npm packages #
TODO

# 13 - Semantic versioning and npm 带有语意的版本 #

## Semver for publishers ##
如果一个项目要和别人共享, 那么它的版本应该要从1.0.0开始, 尽管有些项目并没有遵守.
修复bug或小的改动: Patch release, 最后一位+1, 成为1.0.1
添加不会破坏已有的特性的新特性, Minor release, 中间+1, 最后归0, 1.1.0
会改变向后兼容性的大的改动, Major release, 2.0.0

## Semver for consumers ##
Patch releases: 1.0 or 1.0.x or ~1.0.4
Minor releases: 1 or 1.x or ^1.0.4
Major releases: * or x