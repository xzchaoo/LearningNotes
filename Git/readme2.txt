��������﷨

--system --global �� Ĭ��
git config --global user.name "w3c"
git config --global user.email w3c@w3cschool.cc

git init --bare . ��.�����Ϊһ����ֿ�(��ֿ���Ա�push,�����й�����,�Ǵ��ֿ�)
git init . ��.�����Ϊһ���ֿ�(������ɹ�����)

git clone http://... . ����ַ��Ӧ�Ĳֿ� ��¡�� .  ��ַ��������������ʽ  Ҳ֧���ļ�ϵͳ

git add
git rm
git mv

git commit -m "��������" �ύ

git checkout <branch> �л���<branch>
git checkout -b <branch> <start> ��<start>Ϊ��㴴����֧<branch>��checkout����
git branch <branch> �Ե�ǰ��HEAD����һ����֧<branch>
git branch <branch> <start> ��<start>Ϊ��㴴��һ����֧
git branch -d <branch> ɾ��
git push ���͵�Դ�ֿ�
git pull ��Դ�ֿ�����������
git merge <branch> ��<branch> �ϲ�����ǰ

git log --oneline --decorate --graph

git status -s

git diff

git reset HEAD

git help reset �鿴reset�İ���

git tag -a v0.9 85fc7e7
git tag -a <tagname> -m "w3cschool.cc��ǩ"

git fetch

git remote
