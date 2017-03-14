git tag <tagname> 以当前提交为基础建立一个tag
git tag <tagname> <commit> 以指定提交为基础建立一个tag

git checkout <tagname> 切换到这个tag 进入分离头模式

因为 <tagname> 可以用于 <branchname> 所以不建议两者命名相同

将tag推到远程
git push origin v1.1.3

常用选项
-a 添加注解: 创建人 时间等信息
-m 可以携带一个信息

查看所有标签
git tag
