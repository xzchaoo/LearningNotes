;windows�������� http://blog.163.com/cayyenne@126/blog/static/121862614201019082750/

;����

;�û���Ŀ¼
USERHOME = %HOMEDRIVE%%HOMEPATH%
;�û�����
DESKTOP = %A_desktop% ;
;Ĭ�Ϲ���Ŀ¼
DEFAULT_WORKING_DIR = %USERHOME%

;ͨ��

;��������
^!Numpad8::send {Volume_Up}
^!Numpad2::send {Volume_Down}
^!Numpad0::send {Volume_Mute}

;��һ��/��һ��
^!Numpad4:: send {Media_Prev}
^!Numpad6:: send {Media_Next}
^!Numpad5:: send {Media_Next}


;�ڵ�ǰĿ¼�´�cmd
#IfWinActive ahk_exe explorer.exe
{
^!n::
id := winActive()
;DetectHiddenWindows on
ControlGetText,epath, ToolbarWindow322, ahk_id %id%
;MsgBox %epath%
epath := RegExReplace(epath, "^.+? ","")
;MsgBox %epath%
;epath := SubStr(epath, 5)
if (epath="�����") or (epath="�˵���") or (epath="")
{
	epath=%DESKTOP%
}
SetWorkingDir %epath%
run cmd
return
}
#IfWinNotActive ahk_exe explorer.exe
{
^!n::
SetWorkingDir %DEFAULT_WORKING_DIR%
run cmd
return
}

;�򿪼�Ŀ¼
#Home::run %USERHOME%


;����
;qq�����
#q::run C:\Program Files (x86)\Tencent\QQBrowser\QQBrowser.exe

;��Ŀ¼
#End::run D:\xzc

;��outlook
#+o::run C:\Program Files\Microsoft Office\Office15\OUTLOOK.EXE

;���� windows + tab Ϊ ������ݼ�, �����ҵ���������
#Tab::send #{F3}

;��markdownpad2
#Numpad2::run D:\xzc\tools\markdownpad\MarkdownPad2.exe

;��idea
#+i::run C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1.3\bin\idea64.exe

;��chrome
#Numpad3::run C:\Program Files (x86)\Google\Chrome\Application\chrome.exe

;��EditPlus
#Numpad9::run C:\Program Files (x86)\EditPlus\editplus.exe

;����Ѷqq
#Numpad7::run C:\Program Files (x86)\Tencent\QQ\Bin\QQScLauncher.exe

;��xftp
#+f::run C:\Program Files (x86)\NetSarang\Xftp 4\Xftp.exe

;��xshll
#+x::run C:\Program Files (x86)\NetSarang\Xshell 5\Xshell.exe

;��΢�ſͻ���
#+c::run C:\Program Files (x86)\Tencent\WeChat\WeChat.exe

;��everything
#w::run C:\Program Files\Everything\Everything.exe

;�򿪽�ɽ�ʰ�
#Numpad6::run C:\Users\xiangfeng.xzc\AppData\Local\Kingsoft\Power Word 2016\2016.2.3.0069\PowerWord.exe

;�򿪿ṷ
#NumpadMult::run C:\Program Files (x86)\KuGou\KGMusic\KuGou.exe

;��navicat
#+n::run C:\Program Files\PremiumSoft\Navicat Premium\navicat.exe

;��virtual box
#+v::run C:\Program Files\Oracle\VirtualBox\VirtualBox.exe
