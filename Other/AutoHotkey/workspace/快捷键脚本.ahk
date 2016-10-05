#NoEnv
#Persistent

;windows环境变量 http://blog.163.com/cayyenne@126/blog/static/121862614201019082750/

;设置

;用户家目录
USERHOME = %HOMEDRIVE%%HOMEPATH%
;用户桌面
DESKTOP = %A_desktop% ;
;默认工作目录
DEFAULT_WORKING_DIR = %USERHOME%


currentMonitor:=1

;鼠标
SetTimer, KeepMonitor, 10
return


;通用


;音量控制
^!Numpad8::send {Volume_Up}
^!Numpad2::send {Volume_Down}
^!Numpad0::send {Volume_Mute}

;上一首/下一首
^!Numpad4:: send {Media_Prev}
^!Numpad6:: send {Media_Next}
^!Numpad5:: send {Media_Play_Pause}


;在当前目录下打开cmd
#IfWinActive ahk_exe explorer.exe
^!n::
id := winActive()

;win7 ControlGetText,epath, ToolbarWindow322, ahk_id %id%
;win10 ControlGetText,epath, ToolbarWindow323, ahk_id %id%

ControlGetText,epath, ToolbarWindow323, ahk_id %id%
if(epath=""){
	ControlGetText,epath, ToolbarWindow322, ahk_id %id%
}
epath := RegExReplace(epath, "^.+? ","")
if (epath="计算机") or (epath="此电脑") or (epath="")
{
	epath=%DESKTOP%
}
SetWorkingDir %epath%
run cmd
return
#IfWinActive


;特殊处理 直接打开cmd
#IfWinNotActive ahk_exe explorer.exe
^!n::
;msgbox %DEFAULT_WORKING_DIR%
SetWorkingDir %DEFAULT_WORKING_DIR%
run cmd
return

#IfWinNotActive


#IfWinActive ahk_exe cmd.exe

!F4::
WinClose
return

+Insert::
sendinput %Clipboard%
return

#IfWinActive


;打开家目录
#Home::run %USERHOME%


KeepMonitor:
LockMonitor(currentMonitor)
return

LockMonitor(num){
	SysGet Monitor, Monitor, %num%
	VarSetCapacity(Rect, 16)
	NumPut(MonitorLeft,Rect,0,"int")
	NumPut(MonitorTop,Rect,4,"int")
	NumPut(MonitorRight,Rect,8,"int")
	NumPut(MonitorBottom,Rect,12,"int")
	DllCall("ClipCursor",Ptr, &Rect)
	VarSetCapacity(Rect, 0)
	;msgbox (%MonitorLeft%,%MonitorTop%,%MonitorRight%,%MonitorBottom%)
	return
}


MoveToMonitorCenter(num){
	SysGet Monitor, Monitor, %num%
	x:= (MonitorLeft+MonitorRight) // 2
	y:= (MonitorTop+MonitorBottom) // 2
	DllCall("SetCursorPos", int, x, int, y)
	;msgbox (%num%,%x%,%y%)
	return
}

!1::
{
global currentMonitor

SysGet, MonitorCount, MonitorCount
tempCurrentMonitor:=currentMonitor + 1
if(tempCurrentMonitor > MonitorCount){
	tempCurrentMonitor:=1
}
currentMonitor:=tempCurrentMonitor

LockMonitor(currentMonitor)
MoveToMonitorCenter(currentMonitor)
return
}



;个人

;打开google
#g::run, http://www.google.com

;qq浏览器
#q::run C:\Program Files\Tencent\QQBrowser\QQBrowser.exe

;打开markdownpad2
#Numpad2::run D:\Program Files\markdownpad2\MarkdownPad2.exe

;打开idea
#+i::run D:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.2.4\bin\idea64.exe

;打开pycharm
#+p::run D:\Program Files (x86)\JetBrains\PyCharm 2016.2.3\bin\pycharm64.exe

;打开chrome
#Numpad3::run C:\Program Files (x86)\Google\Chrome\Application\chrome.exe

;打开腾讯qq
#Numpad7::run C:\Program Files\Tencent\QQ\Bin\QQScLauncher.exe

#Numpad0::run F:\SmallTool\窗口置顶工具.exe

;打开xftp
#+f::run C:\Program Files (x86)\NetSarang\Xftp 5\Xftp.exe

;打开xshll
#+x::run C:\Program Files (x86)\NetSarang\Xshell 5\Xshell.exe

;打开微信客户端
#+c::run C:\Program Files (x86)\Tencent\WeChat\WeChat.exe

;打开everything
#w::run C:\Program Files\Everything\Everything.exe

;打开酷狗
#NumpadMult::run D:\Program Files\KuGou\KuGou.exe

;打开navicat
#+n::run C:\Program Files\PremiumSoft\Navicat Premium\navicat.exe

;打开virtual box
#+v::run C:\Program Files\Oracle\VirtualBox\VirtualBox.exe

;打开Sublime
#Numpad8::run C:\Program Files\Sublime Text 3\sublime_text.exe

;打开editplus
#Numpad9::run C:\Program Files\EditPlus\EditPlus.exe


