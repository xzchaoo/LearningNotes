ѧϰ
Git������״̬
	committed:�����Ѿ���ȫ�浽�������ݿ�
	modified:���޸ĵ�û���ύ���������ݿ�
	staged: Staged means that you have marked a modified file in its current version to go into your next commit snapshot

The basic Git workflow goes something like this:
1. You modify files in your working directory.
2. You stage the files, adding snapshots of them to your staging area.
3. You do a commit, which takes the files as they are in the staging area and
stores that snapshot permanently to your Git directory.

git�������ļ�
~/.gitconfig
~/.config/git
����Ĳֿ���


��������ָ�Ϊxzchaoo
git config --global user.name xzchaoo
����������
git config --get user.name     ��   get config user.name
	������Щ��������~/.gitconfig����ļ�
	������� --global ��ôӦ���ǵ�ǰĿ¼�µ���˼
�鿴����
	git config --list
���ñ༭��
git config --global core.editor emacs             windows�¾�û��ô������
�鿴����
	git help config  ��  git config --help   �鿴����config�İ���


��һ���Ѵ��ڵ�Ŀ¼���ʼ���ֿ�
git init

echo "test" > 1.txt
git add 1.txt                  add�����Խ���һ��Ŀ¼ ��Ŀ¼�µ��ļ��������
git commit -m "my version 1"

��һ���ֿ����¡
git clone				�����Դ�����cloneһ������  git clone http:xx yy   clone��yy��
clone��checkout����
	clone������ȫ�ĸ���(�������е���ʷ����?)�ӷ�������


��git status�鿴��ǰ״̬
	-b ��ʾ��ǰ��֧����
	-s ��ʾ��̵�����  ����ܺ��� ��Ϊ����Լ�����
		��ɫ�ı�ʾstaged
		��ɫ��ʾ��ǰ�ļ���״̬
		M modify
		A add
		?? δ������ļ�
�ڸ�Ŀ¼�½���һ��.gitignore�ļ�
	�����������������Ը�Ϊ���
	Ҳ��������Ŀ¼�����
	ʹ�� git status --ignored ����ǿ�ƿ������Ե��ļ�
	����ֻ��Ϊ�ܹ�����ļ���Ч
	�Ѿ���������ļ�����Ӱ��

*.log
#����һ��Ŀ¼ Ҳ���Բ�д/ ֻ��������ʾһ��
dir1/
.gitignore
��ʾ������Щ�ļ�
	����ʹ��#����ͷ����ʾע��
*ƥ��0����
[abc]ƥ��abc�����ַ�
?��ʾһ�������ַ�
a/**/b ��ʾa������b  ��һ����ֱ�ӵĶ���  ,   ֱ�Ӷ�����a/b
[0-9] 0��9
��̾��ȡ��

!*.[ch]  ����*.c,*.h��������Ҫ
build/   ���Ŀ¼����Ҫ
src/**/readme.txt  ����Ҫ

cat > .gitignore << EOF
hello
*.o
*.h
EOF

���ض���ʽ����



git diff 2.txt����������ǰ��2.txt�����stated���2.txt��ʲô����
git diff --staged 2.txt��������staged���2.txt��HEAD���2.txt������
	--cached��--staged��ͬ��
git diff HEAD 2.txt �鿴�㵱ǰ��2.txt��HEAD���2.txt�Ĳ��
**�����ָ��2.txt�Ļ� ��ô����ʾ�����ļ��Ĳ���
**ע�ⲻͬλ�õıȽ�

**������һ������
diff --git a/a.cpp b/a.cpp
index 8f5fce0..091d372 100644
--- a/a.cpp							��������Ƚϵĵ�1���ļ�
+++ b/a.cpp							��������Ƚϵĵ�2���ļ�
@@ -2,13 +2,17 @@					����a�ļ���2-13�ж�Ӧ��b�ļ���2-17��
 using namespace std;				�ճ� �������û��ǰ��-��ǰ��+��ȫ�������ճ�
 
 void fun(){
-	cout<<"fun���÷�"<<endl;			��a�ļ�������ط�ɾ����һ��
-	for(int i=0;i<100;++i){			ͬ��
+	for(int i=0;i<101;++i){			��a�ļ�������ط������һ��
 		cout<<i<<endl;
 	}
+	cout<<"fun���÷�"<<endl;
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
ͨ����Щ�����Ϳ��Խ�a�ļ����b�ļ�










	
ʹ�� git commit -a -m "msg"
	�Զ����޸Ĺ��Ķ�������staged���ύ��ȥ
		ʡ������git add��
	���û��-a�Ļ�ֻ���ύstaged������
	�������a,��ô���޸Ĺ�������(��������staged)Ҳ�ᱻ�ύ

ʹ��rm(linux����,��windows��С����)���Ƴ�һ���ļ�,�� git status ��ͷ����ļ���״̬���not staged deleted��
�����ʹ�� git checkout -- 5.txt���ָ�����ļ�
���������ͨ��git rm 5.txt���Ƴ��ļ��Ļ�
	git��֪�������������,�����Զ����ļ�����staged
	������5.txt��״̬�� statged deleted

ʹ�� git rm --cached 5.txt ����git��Ҫ�Ƴ�5.txt
	��������ɾ����Ŀ¼��5.txt �����ύ�Ժ�
���5.txt�Ͳ���������� ���û��ʹ��--cached�Ļ��ǻ����ɾ����
����ʹ��ͨ���
git rm log/*.log  ��Ȼ������ ����֮ǰ��.gitignore���Ѿ���*.log������

�ƶ��ļ�
git rm 4.txt 44.txt
��ȼ���
mv 4.txt 44.txt
git mv 4.txt
git add 44.txt


git reset HEAD 1.txt		ȡ��1.txt��staged״̬(����master��֧�滻index(cached,staged))
git reset HEAD ��HEAD�滻����������

git branch b1 ����һ��b1��֧(�Ե�ǰΪ���)
git branch b1 b2 ����һ��b1��֧,��b2Ϊ���(b2��һ��<commit>)


���л���֧��ʱ��HEADҲ����Ӧ��ָ���Ӧ�ķ�֧���á�




git reset HEADʱ ��������Ŀ¼���ᱻmaster��֧��Ŀ¼�����滻 ������������


git log�鿴�ύ��ʷ��¼
	-2ֻ��ʾ���2��
	-p��ʾ��ÿ�εĲ���diff
	--stat��ӡ��ÿ���ļ��ı仯
	--pretty=oneline ʹ�����Ϊһ��(����,����,msg����)
	--pretty=format:"%h - %an, %ar : %s"
		�����һЩѡ��
	--graph ��ʾ��|+-*��ģ�µ�ͼ�λ�����
	�����һЩѡ��
	--since=16:10 �Դӽ���16:10�Ժ��ύ�ı仯
		�����Խ���һЩ�� 2015-01-15
		--since "2 days ago"
	--after ��--sinceһ��
	--before �� --untilһ��
	--until��since�෴
	--author "xzchaoo" ����������
		�������õ����� Ҫô��˫���� Ҫô����(��windwos��)
	--grep ���ݹؼ�����
		--grep "��һ��"
	-S4 ֻ��ʾ���ַ���"4" �йص�  [����] [ɾ��] ����

����㲻С��commit��,������ʵ��������ĳЩ�����
��Ϳ���������Щ����
Ȼ��git commit --amend -m 'һ�����������ĳЩ����'
��ʵ��˼�����滻�����һ���ύ�İ汾(Ӧ�ö԰�?)  ��ͨ�������ֶλ��ǿ����һر����ǵİ汾��!
You end up with a single commit �C the second commit replaces the results of the first.  

����㽫һЩ��Ҫ���ļ����Ϊstaged��
������Ŀ¼���кܶ��޹ص��ļ�
��Ȼ��С�� git add * �� . �� �Ǹ���ô��
һ����git reset HEAD xxx.txt һ��һ���ָ� ����ȡ��staged���
�� git reset HEAD . ��ֱ�ӷſ� ���Խ������в���ȡ��staged


diff�����÷�

windowsʹ�� �����п��Դ��� ����  .xxx�ļ�
	�������GUI��ʽ�����ʾ��Ҫ�����ļ���

git reset HEAD <XXX.txt>        ����HEAD��״̬ ���� xxx.txt


stated��һ��״̬  ��Щ�ļ�������commit��ʱ���ύ

ʹ��git add ���Խ�һ���ļ����Ϊstated  ���ʱ����ΪA
����ٴ�֮������ļ��ֱ��Ķ���  ���ʱ����ΪB
��ôҪ�ٴ�git add ���� commit��ʱ�� ���ύ��������A
	Ȼ��commit�� Ŀ¼����ά������ΪB,  ��ʱ������� �������Ŀ��clone ��ô������� ����A



git clean -fd �����ǰ��������û�м���汾����ļ���Ŀ¼
	f��ʾǿ��
	d��ʾ��ɾ��Ŀ¼
git clean -fdn �����ǰ��������û�м���汾����ļ���Ŀ¼
	n��ʾdry-run �൱��Ԥ�����



��/r/1��
git init ��һ���ط�����Ϊ�ֿ�
��/r/2��
git clone ../1 ��1�����ݿ�¡һ�ݵ�2
	git clone username@host:/path/to/repository
	Ҳ���Ե�/r��ִ��  git clone 1 2 ��˼�㶮��

windows���Դ�mkdir
	mkdir a\b\c ������ mkdir a/b/c
���һ���ļ���������

[git ls-files]
	�г��������ļ�

g write-tree



[git stash]
	����ǰ�Ĺ���������������(ֻ�� �ܹ��� ���ļ��ᱻ����)
	���ҵ�ǰ�Ĺ����������ݻ�ָ���HEAD

git stash save "��������"
	"��������"��һ��msg
	-k���滺���� ����������ΪHEAD��

��git stash list
	�鿴����Ĺ���
git stash pop
	�������һ��,�Ǹ����������ջ��ɾ��
	��ָ���������ջ

git stash apply [--index] [<stash>] ָ��index���������� ���Իָ� �������ջ��ɾ��
	��pop���� ֻ�ǲ����ջ���Ƴ����� �൱��peek
git stash drop
	ɾ��ջ��
git stach clear

git log --graph --pretty=raw stash@{0}
	��ӡ������stash@{0}����־
	���Է���stash@{0}����������֧���(merge)��
	��һ�������ǰ汾����
	�ڶ��������Ǻ����ǻ�������?!
$ g stash list
stash@{0}: WIP on master: 8c8f012 ʵ�ֵ�½����
stash@{1}: WIP on master: ed6e989 ��5��
����������stash
git diff stash@{0}^1 stash@{0}
	���ǿ���� ��ʱ�İ汾�����뱣��(stash@{0})������
git diff stash@{0}^1 stash@{0}^2
	���ǿ���� ��ʱ�İ汾�����뵱ʱ�Ļ�����������



[git clean]
	git clean -nd
	git clean -fd
	





git log -1 --pretty=raw		���ӡ��
	commit tree parent �����������sha1��ϣֵ
git cat-file -t sha1Value ���Բ鿴��Ӧ������
git cat-file -p sha1Value ���Բ鿴��Ӧ������
git cat-file -p HEAD^:xxx.txt
git cat-file blob HEAD:abc.txt
git add 1.txt
	gid add *

�ύ
git commit -m "��һ���޸�"
	--allow-empty������ύ
����������Ķ��Ѿ��ڱ��زֿ��HEAD����

�ύ��Զ�ֿ̲�
git push origin master


�鿴��ǰ��֧
git branch

ɾ��xx��֧
git branch -d xx

����֧���͵�Զ�ֿ̲�
git push origin <branch>

���±��زֿ⵽����
git pull <remote> <local>
	��Զ�̵�<remote>�� �������ص�<local>

��ǩ
git tag 1.0.0 1b2e1d63ff
	1b2e1d63ff ������Ҫ��ǵ��ύ ID ��ǰ 10 λ�ַ���ʹ�����������ȡ�ύ ID��
	git log


��������Ҫ���������еı��ظĶ����ύ�����Ե��������ϻ�ȡ���µİ汾�����㱾������ָ֧������
git fetch origin
git reset --hard origin/master

git reset e3a9686
	ǿ��masterָ��e3a9686
	�������޸Ĺ��������ļ�


git reset
	--hard
		�滻����������
	--soft
		�滻��master��ָ��
		��Ӱ�칤�����ͻ�����
	--mixed
		�滻��master�ͻ�����
		��Ӱ�칤����
	Ĭ����--mixed

��������
	git reset �൱�� git reset HEAD
		��master�ͻ������޸�ΪHEAD�汾
	git reset (HEAD)? -- 1.txt ��1.txt����������
	git reset --soft HEAD^
		�ص��ύ֮ǰ

HEAD������ָ�� refs/heads/master

git fetch origin master ��origin����master�����´���
git merge origin/master ��o/m�ϲ����ҵ�ǰ��Ŀ¼

git remote -v �鿴�ֿ�
���һ��Զ�ֿ̲�
git remote add pb http://dsfsdfjskdjfsdklf/fjdsfjkl
����git remote -v�ͷ��ֶ���һ��

git fetch pb��pb��Ŀ��������

git fetch origin ���������еķ�������˵��¹��� , �������������� ���ǻ�û�кϲ� ��Ҫ�ֶ��ϲ�һ��
git pull �������ݲ����Զ��ϲ�
git push origin master ���汾�ύ��origin��master��֧
git remote show origin ��ӡһЩ��Ϣ
git remote rename test1 test2 ������
git remote rm test2 �Ƴ�


git tag �г���ǰ���õ�tag



HEAD master����

master������һ��commit������  ��Ȼ��ָ���µ���һ��

g cat-file commit HEAD

������HEAD^��ʾ HEAD����һ���ύ�汾

�����������Ѿ����˺ü������汾��
��һ���汾��65bbcfff7
��ʹ�� git reset --hard 65bbcfff7
��ʹ����ص���һ���汾 ͬʱ �м���⼸���汾����ʷ��Ϣ��������
�������ܼǵ�ĳ���汾�ı�ű���41a594fbd,��ô����ܻ�ȥ

�����ͨ�� tail -5 .git/logs/ref/heads/master���鿴�����־��Ϣ,���ҵ����Ի�ȥ��id
git�ṩһ������ git reflog show master |head -5 ���鿴
��������
git reset --hard master@{2}
���һ�θı��� master@{0}
�л�Ϊǰ2�θı�֮ǰ��ֵ




�ڱ��زֿ��.git/config�����
[receive]
	denyCurrentBranch = ignore


ѧһ��powershell��ô�ð�

Windows�� ��linuxС����
cat
ls


����windows-cmd����


master
	��һ���α� ָ����һ���ύ�汾
	git reset�����޸�master

reset


HEAD�����ü����
HEAD�������Ϊͷָ�� �ǵ�ǰ�������Ļ����汾
	

[git checkout]
	�����޸�HEAD��ָ��

git checkout -- 2.txt �ӻ�����������������
git checkout -- . �ӻ������������� �൱��ȡ���Լ�����ڻ��������޸�
git checkout <branch> -- 2.txt ��branch�������������͹�����
git checkout <branch> �л���ĳ��֧
	�൱������branch���»������͹�����,����HEAD <- branch
	ע���ʱmaster�������
git checkout <new_branch> <start_branch>
git checkout �鿴������,������,HEAD�Ĳ���
git checkout HEAD �鿴������,������,HEAD�Ĳ���
git checkout . �û�����ֱ�Ӹ��Ǳ����ļ�
�������ظĶ�
git checkout -- <filename>  �ص����һ�ε��ύ״̬��
�������ʹ�� HEAD �е����������滻����Ĺ���Ŀ¼�е��ļ�������ӵ��������ĸĶ����Լ����ļ���������Ӱ�졣
�л�����֧
git checkout master
git checkout HEAD <xxx.txt>  
git checkout b1 -- 2.txt��b1��2.txt���ǵ�ǰ��������2.txt
���һ��������������2.txt
b1���Ըĳ�CommitId
git checkout b1 �л���b1��֧
git checkout -b b2����һ��b2��֧���һ����� �൱�����������ϲ�
git checkout -b b2 <start point> ��������
git checkout -- 1.txt		��cached��1.txt�滻������Ŀ¼��1.txt
git checkout HEAD 1.txt		��HEAD��1.txt�滻������Ŀ¼��1.txt
git checkout HEAD~1 -- xxx.txt
	HEAD~1 �� HEAD^ һ������ָHEAD����һ���ύ
Ҫ����checkout�����������Ѿ��ύ
����ʹ��-f�������ͷָ��״̬
�л���xx��֧
git checkout -b xx

[����ͷָ��״̬]
	ָ����HEADָ��һ�������commit-id������ĳ����֧

[git reset]
	�ҵ����:һ����˵master��ָ��Ľڵ��Ǹ�������ύ�ϲ���������,
	���޷�ǿ���޸���,����ʹ��reset
	���ڷ�֧�ڲ��İ汾�л�,�����л��ط���3��:������,������,�汾����(�㵱ǰ���ڵķ�֧�����һ��commit����)
	--mixed ֻ���޸Ļ�����,�汾���� �����Ĭ��ֵ
	--soft ֻ���޸İ汾����
	--hard 3��ȫ��
	������˵����:
	git reset HEAD
		��HEAD���� �汾�����ͻ�����,���ڴ�ʱ�汾������HEADָ��ͬһ�� �������������ʡ�� ʣ�µľ�����HEAD����������
		һ���㻺������ ����a.txt�ļ� �� HEAD��a.txt�ļ� ��ȫһ�� ��ô���൱����û�н��� git add a.txt , ����ȡ����git add a.txtָ��
	git reset --hard HEAD^
		����һ�ε�HEAD^����3����

[git reflog]
	�鿴����ĳ��ָ��ı�Ǩ��ʷ
	-n ���Բ鿴���n��

	git reflog -1 �鿴 HEAD ���һ�εı�Ǩ��ʷ

[git merge]
	HEAD <- ��ǰHEAD���ƶ��汾�ϲ�֮���������HEAD
	�ϲ�֮��ǰ��HEAD�ͻ���2������
	�ϲ�������֧����ǰ�ķ�֧
	git merge <branch>
		���ܻᷢ����ͻ,   ���ļ����Ϊ�ɹ� : git add <filename> 
	
	ʹ��git status��ʱ���ܻ��������Ѿ�����˰汾����,��ʱ�����Ѿ�����˵���Ҳû��ʾ
	������ͻ��ʱ�����������ݻ���<<<<===>>>���б�ʶ
	ͬʱ�����������3���ļ�
	:1:a.txt �˴γ�ͻ�������ļ��Ĺ�ͬ����
	:2:a.txt �˴γ�ͻ������ļ�
	:3:a.txt �˴γ�ͻ�кϲ����ļ�
	����ĳ�ͻֻ�����ļ��������ϵĳ�ͻ �󲿷ֶ��ǿ��Խ����
	����������һ�� : ����ͻ: ���� �����˽�1���ļ��ĳɲ�ͬ������ ����1��push��
	��ô2��Ҫ�������ͻ�� �����ͻ��ʱ��2������3���ļ� �ֱ���: ԭʼ�ļ� 1��������ļ� 2��������ļ�
	Ȼ��2Ҫ���вþ�: ʹ�� git add/rm ʹ�����ֻʣ��һ���ļ� Ȼ����� commit
	-sָ���ϲ�����


git diff <source_branch> <target_branch>

���ڰ汾�л�
�½���֧
git checkout -v <new_branch>
	�Ե�ǰ��֧Ϊ��㽨����֧

merge
	�л���master��֧ ��һЩ����



[git tag]
	git tag -m "this is a tag" tag1
	-dɾ��
	Ĭ��tagֻ�ڱ��ذ汾���пɼ�
	����������Դ���潨���İ汾��
	�����Ҫpush��Դ�Ļ� ��Ҫ����:
		git push origin mytag
	�� git push <remote_url>    :<tagname> ɾ��Զ�̵�tag



git add -u
	���Խ��ܹ�����ļ����µ�������
	����1.txt�ܵ����� �����޸�����֮����ʹ�� git add 1.txt �����ӵ�������
	����㻹�� 2.txt 3.txt�� �����Ǻ��鷳 ��ʼ�� git add -u �����������ӹ�ȥ
git add -A
	���Խ������ļ�(�Ķ������ļ����µ��ļ�)�ӽ�������

	
�ָ�ɾ���ļ�
git cat-file -p HEAD~1:xxx.txt > xxx.txt
git show HEAD~1:xxx.txt > xxx.txt
git checkout HEAD~1 -- xxx.txt
HEAD~1�൱��HEAD^
����1,2�� ����ʾ + �ض���
������3��ֱ�����һ�����õ�xxx.txt

��
git grep '����'

ֻ���ڸ�Ŀ¼��һ��.git
����Ŀ¼��Ĳ��������Ҫ�õ�.git�����ݵĻ�����ݹ�������

git rev-parse --git-dir
	�鿴.git����ڵ�ǰ·����λ��
git rev-parse --show-prefix
	��ʾ��ǰ·�������.git��λ��

[git descript]
	��ʾ�����ύ��һ���׼ǵ�����
	��ѡȡ����ð汾�������̱���Ϊ������汾��

[git clone]
	ͨ����ͨ��¡���������ĵİ汾���ǶԵȵ�
	A ��¡���� B
	Bͨ�� git pull ��A���������� ����ͬ��

	--bare
		������İ汾��
		initҲ����bare
		git init --bare

[git push]
	����push����İ汾��

[git pull]
	��Դ��������
	pull=fetch+merge
[git fetch]
	���±��زֿ� Ҳ��Ӱ�컺�����͹�����
	

[git remote]
	-v

��֧
Bugfix��֧ �ֽ� ������֧
���Է�֧

git rev-parse user1/getopt master

����

[git cherry-pick]
	��ѡ����,ʵ���ύ���µķ�֧���ط�

�������

g log --oneline --decorate
06269aa (HEAD, tag: t4, master) 4
9928f19 (tag: t3) 3
6867265 (tag: t2) 2
209074c (tag: t1) 1

e66dd4b

git rebase --onto t1 t2 t4����˼������
	��t1Ϊ��� ���ζ�t1ʵʩ(t2,t4]
	Ȼ�����״̬�ͻ���һ�� unbrand�� ��������¸���һ��tag
	���� git reset --hard ����id

	�����Ļ��Ϳ��Ժ��Ե�t2��Ч��

