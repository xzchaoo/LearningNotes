#NoEnv
#Persistent

TIMER_LOCK_MOUSE:=0

;windows环境变量 http://blog.163.com/cayyenne@126/blog/static/121862614201019082750/

;设置

;用户家目录
USERHOME = %HOMEDRIVE%%HOMEPATH%
;用户桌面
DESKTOP = %A_desktop%
;默认工作目录
DEFAULT_WORKING_DIR = %USERHOME%

;获得主显示器的index, ;而不是设置初始显示器索引为1, 因为可能2号显示器是作为主显示器!
SysGet, currentMonitor, MonitorPrimary
;currentMonitor:=1


;每10毫秒锁定一次鼠标鼠标
;win10下的屏幕貌似可以设置成使得鼠标跨不了边界
if (TIMER_LOCK_MOUSE=1){
	SetTimer, KeepMonitor, 10
}

;查看操作系统的信息
;MsgBox,, Operating system info,
;(
;OS Type: %A_OSType%
;OS Version: %A_OSVersion%
;)


;通用


;音量控制
^!Numpad8::send {Volume_Up}
^!Numpad2::send {Volume_Down}
^!Numpad0::send {Volume_Mute}


;上一首/下一首/暂停
^!Numpad4::send {Media_Prev}
^!Numpad6::send {Media_Next}
^!Numpad5::send {Media_Play_Pause}


;在当前目录下打开cmd, 仅在explorer.exe 激活时有效
#IfWinActive ahk_exe explorer.exe
^!n::
id := winActive()

;通过spy可以查看到窗口的信息, 在win7 和 win10 可以从以下两个类中获取当前路径的信息
;win7 ControlGetText,epath, ToolbarWindow322, ahk_id %id%
;win10 ControlGetText,epath, ToolbarWindow323, ahk_id %id%

;先尝试win10
ControlGetText,epath, ToolbarWindow323, ahk_id %id%
if(epath=""){
;再尝试win7
	ControlGetText,epath, ToolbarWindow322, ahk_id %id%
}
;TODO 这里的正则替换是干嘛的?
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
#IfWinNotActive, ahk_exe explorer.exe
^!n::
;msgbox %DEFAULT_WORKING_DIR%
SetWorkingDir %DEFAULT_WORKING_DIR%
run cmd
return

#IfWinNotActive



;当cmd.exe和powershell.exe激活的时候 处理Alt+F4 和 Shift+Insert
#IfWinActive, ahk_exe cmd.exe

!F4::
WinClose
return

+Insert::
sendraw %Clipboard%
return

#IfWinActive

#IfWinActive, ahk_exe powershell.exe

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
	NumPut(MonitorLeft,Rect,0,"Int")
	NumPut(MonitorTop,Rect,4,"Int")
	NumPut(MonitorRight,Rect,8,"Int")
	NumPut(MonitorBottom,Rect,12,"Int")
	DllCall("ClipCursor","Ptr", &Rect)
	VarSetCapacity(Rect, 0)
	;msgbox (%MonitorLeft%,%MonitorTop%,%MonitorRight%,%MonitorBottom%)
	return
}


MoveToMonitorCenter(num){
	SysGet Monitor, Monitor, %num%
	x:= (MonitorLeft+MonitorRight) // 2
	y:= (MonitorTop+MonitorBottom) // 2
	DllCall("SetCursorPos", "int", x, "int", y)
	;msgbox (%num%,%x%,%y%)
	return
}

!1::
{
global currentMonitor

SysGet, MonitorCount, MonitorCount
currentMonitor := currentMonitor = MonitorCount ? 1 : (currentMonitor+1)
if(TIMER_LOCK_MOUSE=1){
	LockMonitor(currentMonitor)
}
MoveToMonitorCenter(currentMonitor)
return
}

;!3::
;sendinput #+{Right}
;return


#IfWinActive A
!2::
ok:=1
if ( winActive("ahk_exe explorer.exe") )
{
	WinGetTitle t
	if(t=""){
		ok:=0
	}
}
if ( ok=1 )
{
	WinGetPos, X, Y
	X:=X+16
	Y:=Y+16
	CurrentMonitorIndex := 0

	SysGet, MonitorCount, MonitorCount
	Loop, %MonitorCount%
	{
		SysGet Monitor, Monitor, %A_Index%
		;msgbox, %A_Index%, %X%, %Y%, %MonitorLeft%, %MonitorRight%, %MonitorTop%, %MonitorBottom%
		if (X>=MonitorLeft&&X<=MonitorRight&&Y>=MonitorTop&&Y<=MonitorBottom)
		{
			CurrentMonitorIndex:=A_Index
			break
		}
	}
	if (CurrentMonitorIndex!=0)
	{
		CurrentMonitorIndex:= CurrentMonitorIndex=MonitorCount ? 1 : (CurrentMonitorIndex+1)
		X:=X-MonitorLeft
		Y:=Y-MonitorTop
		SysGet Monitor, Monitor, %CurrentMonitorIndex%
		X:=MonitorLeft+X-16
		Y:=MonitorTop+Y-16
		WinMove, %X%, %Y%
	}
}
Return
#IfWinActive



;个人

;打开google
#g::run, http://www.google.com

;qq浏览器
#q::run "C:\Program Files\Tencent\QQBrowser\QQBrowser.exe" -sc=desktopshortcut -fixlaunch=0

;打开markdownpad2
#Numpad2::run "C:\Program Files (x86)\MarkdownPad 2\MarkdownPad2.exe"

;打开idea
#+i::run "D:\Program Files\JetBrains\IntelliJ IDEA 2018.1.3\bin\idea64.exe"

;打开pycharm
;#+p::run D:\Program Files (x86)\JetBrains\PyCharm 2016.3.2\bin\pycharm64.exe

;打开chrome
#Numpad3::run "C:\Users\xzchaoo\AppData\Local\Google\Chrome\Application\chrome.exe"

;打开腾讯qq
#Numpad7::run "C:\Program Files\Tencent\QQ\Bin\QQScLauncher.exe"

;打开xftp
#+f::run C:\Program Files (x86)\NetSarang\Xftp 5\Xftp.exe

;打开xshll
#+x::run C:\Program Files (x86)\NetSarang\Xshell 5\Xshell.exe

;打开微信客户端
#+c::run "C:\Program Files (x86)\Tencent\WeChat\WeChat.exe"

;打开everything
#w::run C:\Program Files\Everything\Everything.exe

;打开网易云音乐
#NumpadMult::run "D:\Program Files (x86)\Netease\CloudMusic\cloudmusic.exe"

;打开navicat
#+n::run C:\Program Files\PremiumSoft\Navicat Premium\navicat.exe

;打开editplus
#Numpad9::run "C:\Program Files\Notepad++\notepad++.exe"

