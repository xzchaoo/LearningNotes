;windows环境变量 http://blog.163.com/cayyenne@126/blog/static/121862614201019082750/

;设置

;用户家目录
USERHOME = %HOMEDRIVE%%HOMEPATH%
;用户桌面
DESKTOP = %A_desktop% ;
;默认工作目录
DEFAULT_WORKING_DIR = %USERHOME%

;通用

;音量控制
^!Numpad8::send {Volume_Up}
^!Numpad2::send {Volume_Down}
^!Numpad0::send {Volume_Mute}

;上一首/下一首
^!Numpad4:: send {Media_Prev}
^!Numpad6:: send {Media_Next}
^!Numpad5:: send {Media_Next}


;在当前目录下打开cmd
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
if (epath="计算机") or (epath="此电脑") or (epath="")
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

;打开家目录
#Home::run %USERHOME%


;个人
;qq浏览器
#q::run C:\Program Files (x86)\Tencent\QQBrowser\QQBrowser.exe

;打开目录
#End::run D:\xzc

;打开outlook
#+o::run C:\Program Files\Microsoft Office\Office15\OUTLOOK.EXE

;拦截 windows + tab 为 其他快捷键, 用于我的虚拟桌面
#Tab::send #{F3}

;打开markdownpad2
#Numpad2::run D:\xzc\tools\markdownpad\MarkdownPad2.exe

;打开idea
#+i::run C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1.3\bin\idea64.exe

;打开chrome
#Numpad3::run C:\Program Files (x86)\Google\Chrome\Application\chrome.exe

;打开EditPlus
#Numpad9::run C:\Program Files (x86)\EditPlus\editplus.exe

;打开腾讯qq
#Numpad7::run C:\Program Files (x86)\Tencent\QQ\Bin\QQScLauncher.exe

;打开xftp
#+f::run C:\Program Files (x86)\NetSarang\Xftp 4\Xftp.exe

;打开xshll
#+x::run C:\Program Files (x86)\NetSarang\Xshell 5\Xshell.exe

;打开微信客户端
#+c::run C:\Program Files (x86)\Tencent\WeChat\WeChat.exe

;打开everything
#w::run C:\Program Files\Everything\Everything.exe

;打开金山词霸
#Numpad6::run C:\Users\xiangfeng.xzc\AppData\Local\Kingsoft\Power Word 2016\2016.2.3.0069\PowerWord.exe

;打开酷狗
#NumpadMult::run C:\Program Files (x86)\KuGou\KGMusic\KuGou.exe

;打开navicat
#+n::run C:\Program Files\PremiumSoft\Navicat Premium\navicat.exe

;打开virtual box
#+v::run C:\Program Files\Oracle\VirtualBox\VirtualBox.exe
