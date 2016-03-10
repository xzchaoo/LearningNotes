学习
Git有三种状态
	committed:数据已经安全存到本地数据库
	modified:已修改但没有提交到本地数据库
	staged: Staged means that you have marked a modified file in its current version to go into your next commit snapshot

The basic Git workflow goes something like this:
1. You modify files in your working directory.
2. You stage the files, adding snapshots of them to your staging area.
3. You do a commit, which takes the files as they are in the staging area and
stores that snapshot permanently to your Git directory.

git的配置文件
~/.gitconfig
~/.config/git
在你的仓库里


将你的名字改为xzchaoo
git config --global user.name xzchaoo
获得你的名字
git config --get user.name     或   get config user.name
	上面这些操作的是~/.gitconfig这个文件
	如果不带 --global 那么应该是当前目录下的意思
查看配置
	git config --list
设置编辑器
git config --global core.editor emacs             windows下就没这么幸运了
查看帮助
	git help config  或  git config --help   查看关于config的帮助


在一个已存在的目录里初始化仓库
git init

echo "test" > 1.txt
git add 1.txt                  add还可以接受一个目录 则目录下的文件都被添加
git commit -m "my version 1"

从一个仓库里克隆
git clone				还可以从网上clone一个下来  git clone http:xx yy   clone到yy里
clone与checkout区别
	clone接受完全的副本(包含所有的历史数据?)从服务器上


用git status查看当前状态
	-b 显示当前分支名称
	-s 显示简短的描述  这个很好用 因为结果言简意赅
		绿色的表示staged
		红色表示当前文件的状态
		M modify
		A add
		?? 未管理的文件
在根目录下建立一个.gitignore文件
	这样它的作用域是以根为起点
	也可以在子目录里放置
	使用 git status --ignored 可以强制看到忽略的文件
	忽略只对为受管理的文件有效
	已经被管理的文件不受影响

*.log
#忽略一个目录 也可以不写/ 只是明显提示一下
dir1/
.gitignore
表示忽略这些文件
	可以使用#在行头来表示注释
*匹配0或多个
[abc]匹配abc单个字符
?表示一个任意字符
a/**/b 表示a的子孙b  不一定是直接的儿子  ,   直接儿子是a/b
[0-9] 0到9
感叹号取反

!*.[ch]  除了*.c,*.h其他都不要
build/   这个目录都不要
src/**/readme.txt  都不要

cat > .gitignore << EOF
hello
*.o
*.h
EOF

本地独享式忽略



git diff 2.txt用于描述当前的2.txt与你的stated里的2.txt有什么区别
git diff --staged 2.txt用于描述staged里的2.txt与HEAD里的2.txt的区别
	--cached和--staged是同义
git diff HEAD 2.txt 查看你当前的2.txt与HEAD里的2.txt的差别
**如果不指定2.txt的话 那么会显示所有文件的差异
**注意不同位置的比较

**下面是一个例子
diff --git a/a.cpp b/a.cpp
index 8f5fce0..091d372 100644
--- a/a.cpp							表明参与比较的第1个文件
+++ b/a.cpp							表明参与比较的第2个文件
@@ -2,13 +2,17 @@					表明a文件的2-13行对应了b文件的2-17行
 using namespace std;				照抄 以下如果没有前导-或前导+的全部都是照抄
 
 void fun(){
-	cout<<"fun你妹夫"<<endl;			在a文件的这个地方删除这一行
-	for(int i=0;i<100;++i){			同上
+	for(int i=0;i<101;++i){			在a文件的这个地方添加这一样
 		cout<<i<<endl;
 	}
+	cout<<"fun你妹夫"<<endl;
 }
 int main(){
-	cout<<"start"<<endl;
+	cout<<"start2"<<endl;
 	fun();
+	cout<<"end2"<<endl;
 	return 0;
+}
+void fun2(){
+	cout<<"fun2"<<endl;
 }
\ No newline at end of file
通过这些操作就可以将a文件变成b文件










	
使用 git commit -a -m "msg"
	自动将修改过的东西当做staged的提交上去
		省得你再git add了
	如果没有-a的话只会提交staged的内容
	如果有了a,那么你修改过的内容(但都不是staged)也会被提交

使用rm(linux命令,或windows类小工具)来移除一个文件,再 git status 你就发现文件的状态变成not staged deleted了
你可以使用 git checkout -- 5.txt来恢复这个文件
但如果你是通过git rm 5.txt来移除文件的话
	git就知道了你这个动作,它会自动把文件加入staged
	所以你5.txt的状态是 statged deleted

使用 git rm --cached 5.txt 告诉git你要移除5.txt
	但并不会删除你目录的5.txt 当你提交以后
你的5.txt就不再纳入管理 如果没有使用--cached的话是会真的删除的
可以使用通配符
git rm log/*.log  当然别忘了 我们之前在.gitignore里已经将*.log忽略了

移动文件
git rm 4.txt 44.txt
这等价于
mv 4.txt 44.txt
git mv 4.txt
git add 44.txt


git reset HEAD 1.txt		取消1.txt的staged状态(即用master分支替换index(cached,staged))
git reset HEAD 用HEAD替换掉整个缓冲

git branch b1 创建一个b1分支(以当前为起点)
git branch b1 b2 创建一个b1分支,以b2为起点(b2是一个<commit>)


在切换分支的时候，HEAD也会相应的指向对应的分支引用。




git reset HEAD时 缓存区的目录树会被master分支的目录树所替换 但工作区不变


git log查看提交历史记录
	-2只显示最近2次
	-p显示出每次的差异diff
	--stat打印出每次文件的变化
	--pretty=oneline 使得输出为一行(名字,邮箱,msg部分)
	--pretty=format:"%h - %an, %ar : %s"
		查表还有一些选项
	--graph 显示用|+-*等模仿的图形化界面
	查表还有一些选项
	--since=16:10 自从今天16:10以后提交的变化
		还可以接受一些如 2015-01-15
		--since "2 days ago"
	--after 与--since一样
	--before 跟 --until一样
	--until跟since相反
	--author "xzchaoo" 根据作者找
		不可以用单引号 要么用双引号 要么不用(在windwos下)
	--grep 根据关键字找
		--grep "第一次"
	-S4 只显示跟字符串"4" 有关的  [增加] [删除] 操作

如果你不小心commit了,但是其实是忘了做某些事情的
你就可以做完这些事情
然后git commit --amend -m '一本道你完成了某些事情'
其实意思就是替换掉最后一次提交的版本(应该对吧?)  但通过技术手段还是可以找回被覆盖的版本的!
You end up with a single commit – the second commit replaces the results of the first.  

如果你将一些必要的文件标记为staged后
如果你的目录下有很多无关的文件
且然后不小心 git add * 或 . 了 那该怎么办
一般用git reset HEAD xxx.txt 一个一个恢复 可以取消staged标记
用 git reset HEAD . 或直接放空 可以将这所有操作取消staged


diff工具用法

windows使用 命令行可以创建 类似  .xxx文件
	如果你用GUI方式则会提示你要输入文件名

git reset HEAD <XXX.txt>        根据HEAD的状态 重置 xxx.txt


stated是一个状态  这些文件将会在commit的时候被提交

使用git add 可以将一个文件标记为stated  设此时内容为A
如果再次之后这个文件又被改动了  设此时内容为B
那么要再次git add 否则 commit的时候 被提交的内容是A
	然是commit后 目录还是维持内容为B,  此时如果别人 从你的项目里clone 那么而获得是 内容A



git clean -fd 清除当前工作区中没有加入版本库的文件和目录
	f表示强制
	d表示会删除目录
git clean -fdn 清除当前工作区中没有加入版本库的文件和目录
	n表示dry-run 相当于预览结果



在/r/1里
git init 将一个地方设置为仓库
在/r/2里
git clone ../1 将1的内容克隆一份到2
	git clone username@host:/path/to/repository
	也可以到/r里执行  git clone 1 2 意思你懂的

windows下自带mkdir
	mkdir a\b\c 而不是 mkdir a/b/c
添加一个文件到缓冲区

[git ls-files]
	列出缓冲区文件

g write-tree



[git stash]
	将当前的工作环境保存起来(只有 受管理 的文件会被保存)
	并且当前的工作区的内容会恢复成HEAD

git stash save "等下再做"
	"等下再做"是一个msg
	-k保存缓冲区 而不是重置为HEAD的

用git stash list
	查看保存的工作
git stash pop
	弹出最近一个,那个工作将会从栈里删除
	会恢复工作区和栈

git stash apply [--index] [<stash>] 指定index或工作的名字 可以恢复 但不会从栈里删除
	跟pop类似 只是不会从栈里移除而已 相当于peek
git stash drop
	删除栈顶
git stach clear

git log --graph --pretty=raw stash@{0}
	打印出关于stash@{0}的日志
	可以发现stash@{0}是由两个分支组成(merge)的
	第一个父亲是版本基带
	第二个父亲是好像是缓冲区吧?!
$ g stash list
stash@{0}: WIP on master: 8c8f012 实现登陆功能
stash@{1}: WIP on master: ed6e989 第5次
表明有两个stash
git diff stash@{0}^1 stash@{0}
	就是看这个 当时的版本基带与保存(stash@{0})的区别
git diff stash@{0}^1 stash@{0}^2
	就是看这个 当时的版本基带与当时的缓冲区的区别



[git clean]
	git clean -nd
	git clean -fd
	





git log -1 --pretty=raw		会打印出
	commit tree parent 这三个对象的sha1哈希值
git cat-file -t sha1Value 可以查看对应的类型
git cat-file -p sha1Value 可以查看对应的内容
git cat-file -p HEAD^:xxx.txt
git cat-file blob HEAD:abc.txt
git add 1.txt
	gid add *

提交
git commit -m "第一次修改"
	--allow-empty允许空提交
现在你的西改动已经在本地仓库的HEAD中了

提交到远程仓库
git push origin master


查看当前分支
git branch

删除xx分支
git branch -d xx

将分支推送到远程仓库
git push origin <branch>

更新本地仓库到最新
git pull <remote> <local>
	将远程的<remote>版 拉到本地的<local>

标签
git tag 1.0.0 1b2e1d63ff
	1b2e1d63ff 是你想要标记的提交 ID 的前 10 位字符。使用如下命令获取提交 ID：
	git log


假如你想要丢弃你所有的本地改动与提交，可以到服务器上获取最新的版本并将你本地主分支指向到它：
git fetch origin
git reset --hard origin/master

git reset e3a9686
	强制master指向e3a9686
	但并不修改工作区的文件


git reset
	--hard
		替换掉所有内容
	--soft
		替换掉master的指向
		不影响工作区和缓存区
	--mixed
		替换掉master和缓存区
		不影响工作区
	默认是--mixed

几个例子
	git reset 相当于 git reset HEAD
		将master和缓存区修改为HEAD版本
	git reset (HEAD)? -- 1.txt 将1.txt撤出缓存区
	git reset --soft HEAD^
		回到提交之前

HEAD本身是指向 refs/heads/master

git fetch origin master 从origin下载master的最新代码
git merge origin/master 将o/m合并到我当前的目录

git remote -v 查看仓库
添加一个远程仓库
git remote add pb http://dsfsdfjskdjfsdklf/fjdsfjkl
再用git remote -v就发现多了一个

git fetch pb将pb项目下载下来

git fetch origin 会下载所有的发到服务端的新工作 , 下是下载下来了 但是还没有合并 你要手动合并一下
git pull 拉下数据并且自动合并
git push origin master 将版本提交到origin的master分支
git remote show origin 打印一些信息
git remote rename test1 test2 重命名
git remote rm test2 移除


git tag 列出当前可用的tag



HEAD master区别

master本质是一次commit的引用  当然是指最新的那一次

g cat-file commit HEAD

可以用HEAD^表示 HEAD的上一次提交版本

假设你现在已经过了好几个而版本了
第一个版本是65bbcfff7
你使用 git reset --hard 65bbcfff7
将使得你回到第一个版本 同时 中间的这几个版本的历史信息被丢弃了
除非你能记得某个版本的编号比如41a594fbd,那么你就能回去

你可以通过 tail -5 .git/logs/ref/heads/master来查看你的日志信息,以找到可以回去的id
git提供一个命令 git reflog show master |head -5 来查看
可以这样
git reset --hard master@{2}
最近一次改变是 master@{0}
切换为前2次改变之前的值




在本地仓库的.git/config里添加
[receive]
	denyCurrentBranch = ignore


学一下powershell怎么用吧

Windows下 类linux小工具
cat
ls


常见windows-cmd命令


master
	像一个游标 指向了一个提交版本
	git reset用来修改master

reset


HEAD的重置即检出
HEAD可以理解为头指针 是当前工作区的基本版本
	

[git checkout]
	用于修改HEAD的指向

git checkout -- 2.txt 从缓冲区里检出到工作区
git checkout -- . 从缓冲区里检出所有 相当于取消自己相对于缓冲区的修改
git checkout <branch> -- 2.txt 从branch里检出到缓冲区和工作区
git checkout <branch> 切换到某分支
	相当于是用branch更新缓冲区和工作区,并且HEAD <- branch
	注意此时master并不会变
git checkout <new_branch> <start_branch>
git checkout 查看工作区,缓存区,HEAD的差异
git checkout HEAD 查看工作区,缓存区,HEAD的差异
git checkout . 用缓冲区直接覆盖本地文件
撤销本地改动
git checkout -- <filename>  回到最近一次的提交状态吧
此命令会使用 HEAD 中的最新内容替换掉你的工作目录中的文件。已添加到缓存区的改动，以及新文件，都不受影响。
切换主分支
git checkout master
git checkout HEAD <xxx.txt>  
git checkout b1 -- 2.txt用b1的2.txt覆盖当前工作区的2.txt
并且还会清除缓冲区的2.txt
b1可以改成CommitId
git checkout b1 切换到b1分支
git checkout -b b2创建一个b2分支并且换到它 相当于上面两条合并
git checkout -b b2 <start point> 类似上面
git checkout -- 1.txt		用cached的1.txt替换掉工作目录的1.txt
git checkout HEAD 1.txt		用HEAD的1.txt替换掉工作目录的1.txt
git checkout HEAD~1 -- xxx.txt
	HEAD~1 跟 HEAD^ 一样都是指HEAD的上一次提交
要进行checkout工作区必须已经提交
可以使用-f进入分离头指针状态
切换到xx分支
git checkout -b xx

[分离头指针状态]
	指的是HEAD指向一个具体的commit-id而不是某个分支

[git reset]
	我的理解:一般来说master所指向的节点是根据你的提交合并而决定的,
	你无法强制修改它,除非使用reset
	用于分支内部的版本切换,可以切换地方有3个:工作区,缓冲区,版本基带(你当前所在的分支的最后一次commit对象)
	--mixed 只会修改缓冲区,版本基带 这个是默认值
	--soft 只会修改版本基带
	--hard 3个全改
	具体来说就是:
	git reset HEAD
		用HEAD顶掉 版本基带和缓冲区,由于此时版本基带和HEAD指向同一个 所以这个操作就省了 剩下的就是用HEAD顶掉缓冲区
		一旦你缓冲区的 比如a.txt文件 和 HEAD的a.txt文件 完全一致 那么就相当于你没有进行 git add a.txt , 即你取消了git add a.txt指令
	git reset --hard HEAD^
		用上一次的HEAD^顶掉3个区

[git reflog]
	查看关于某个指针的变迁历史
	-n 可以查看最近n条

	git reflog -1 查看 HEAD 最近一次的变迁历史

[git merge]
	HEAD <- 当前HEAD与制定版本合并之后产生的新HEAD
	合并之后当前的HEAD就会有2个父亲
	合并其他分支到当前的分支
	git merge <branch>
		可能会发生冲突,   将文件标记为成功 : git add <filename> 
	
	使用git status有时可能会提醒你已经落后了版本库了,有时明明已经落后了但是也没提示
	发生冲突的时候工作区的内容会用<<<<===>>>进行标识
	同时缓存区里会有3个文件
	:1:a.txt 此次冲突的两个文件的共同祖先
	:2:a.txt 此次冲突中你的文件
	:3:a.txt 此次冲突中合并的文件
	上面的冲突只是在文件的内容上的冲突 大部分都是可以解决的
	但是有另外一种 : 树冲突: 比如 两个人将1个文件改成不同的名字 假设1先push了
	那么2就要来解决冲突了 解决冲突的时候2处会有3个文件 分别是: 原始文件 1改名后的文件 2改名后的文件
	然后2要进行裁决: 使用 git add/rm 使得最后只剩下一个文件 然后进行 commit
	-s指定合并策略


git diff <source_branch> <target_branch>

用于版本切换
新建分支
git checkout -v <new_branch>
	以当前分支为起点建立分支

merge
	切换到master分支 做一些事情



[git tag]
	git tag -m "this is a tag" tag1
	-d删除
	默认tag只在本地版本库中可见
	除非你是在源里面建立的版本库
	如果需要push到源的话 需要这样:
		git push origin mytag
	用 git push <remote_url>    :<tagname> 删除远程的tag



git add -u
	可以将受管理的文件更新到缓冲区
	比如1.txt受到管理 在你修改完它之后你使用 git add 1.txt 将它扔到缓冲区
	如果你还有 2.txt 3.txt呢 那岂不是很麻烦 开始用 git add -u 将他们批量扔过去
git add -A
	可以将所有文件(改动过的文件或新的文件)扔进缓冲区

	
恢复删除文件
git cat-file -p HEAD~1:xxx.txt > xxx.txt
git show HEAD~1:xxx.txt > xxx.txt
git checkout HEAD~1 -- xxx.txt
HEAD~1相当于HEAD^
方法1,2是 用显示 + 重定向
而方法3是直接添加一个引用到xxx.txt

杂
git grep '内容'

只有在根目录有一个.git
在子目录里的操作如果需要用到.git的内容的话将会递归向上找

git rev-parse --git-dir
	查看.git相对于当前路径的位置
git rev-parse --show-prefix
	显示当前路径相对于.git的位置

[git descript]
	显示最新提交的一个易记的名称
	会选取距离该版本最近的里程碑作为基础版版本号

[git clone]
	通过普通克隆产生出来的的版本库是对等的
	A 克隆产生 B
	B通过 git pull 从A那里拿数据 进行同步

	--bare
		生成裸的版本库
		init也可以bare
		git init --bare

[git push]
	可以push到裸的版本库

[git pull]
	从源更新下来
	pull=fetch+merge
[git fetch]
	更新本地仓库 也不影响缓存区和工作区
	

[git remote]
	-v

分支
Bugfix分支 又叫 发布分支
特性分支

git rev-parse user1/getopt master

补丁

[git cherry-pick]
	拣选操作,实现提交在新的分支上重放

变基操作

g log --oneline --decorate
06269aa (HEAD, tag: t4, master) 4
9928f19 (tag: t3) 3
6867265 (tag: t2) 2
209074c (tag: t1) 1

e66dd4b

git rebase --onto t1 t2 t4的意思好像是
	以t1为起点 依次对t1实施(t2,t4]
	然后你的状态就会变成一个 unbrand的 你可以重新给它一个tag
	或者 git reset --hard 它的id

	这样的话就可以忽略掉t2的效果

