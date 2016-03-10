最基本的语法

--system --global 和 默认
git config --global user.name "w3c"
git config --global user.email w3c@w3cschool.cc

git init --bare . 将.建设成为一个裸仓库(裸仓库可以被push,不能有工作区,是纯仓库)
git init . 将.建设成为一个仓库(你可以由工作区)

git clone http://... . 将地址对应的仓库 克隆到 .  地址还可以由其他格式  也支持文件系统

git add
git rm
git mv

git commit -m "我做完了" 提交

git checkout <branch> 切换到<branch>
git checkout -b <branch> <start> 以<start>为起点创建分支<branch>并checkout到它
git branch <branch> 以当前的HEAD创建一个分支<branch>
git branch <branch> <start> 以<start>为起点创建一个分支
git branch -d <branch> 删除
git push 推送到源仓库
git pull 从源仓库拉东西回来
git merge <branch> 将<branch> 合并到当前

git log --oneline --decorate --graph

git status -s

git diff

git reset HEAD

git help reset 查看reset的帮助

git tag -a v0.9 85fc7e7
git tag -a <tagname> -m "w3cschool.cc标签"

git fetch

git remote
