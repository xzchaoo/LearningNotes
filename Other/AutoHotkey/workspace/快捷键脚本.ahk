#NoEnv
#Persistent

;windows�������� http://blog.163.com/cayyenne@126/blog/static/121862614201019082750/

;����

;�û���Ŀ¼
USERHOME = %HOMEDRIVE%%HOMEPATH%
;�û�����
DESKTOP = %A_desktop% ;
;Ĭ�Ϲ���Ŀ¼
DEFAULT_WORKING_DIR = %USERHOME%


currentMonitor:=1

;���
SetTimer, KeepMonitor, 10
return


;ͨ��


;��������
^!Numpad8::send {Volume_Up}
^!Numpad2::send {Volume_Down}
^!Numpad0::send {Volume_Mute}

;��һ��/��һ��
^!Numpad4::send {Media_Prev}
^!Numpad6::send {Media_Next}
^!Numpad5::send {Media_Play_Pause}


;�ڵ�ǰĿ¼�´�cmd
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
if (epath="�����") or (epath="�˵���") or (epath="")
{
	epath=%DESKTOP%
}
SetWorkingDir %epath%
run cmd
return
#IfWinActive


;���⴦�� ֱ�Ӵ�cmd
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


;�򿪼�Ŀ¼
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



;����

;��google
#g::run, http://www.google.com

;qq�����
#q::run C:\Program Files\Tencent\QQBrowser\QQBrowser.exe


;��Typora
#Numpad1::run C:\Program Files\Typora\Typora.exe

;��markdownpad2
#Numpad2::run D:\Program Files\markdownpad2\MarkdownPad2.exe

;��idea
#+i::run D:\Program Files (x86)\JetBrains\IntelliJ IDEA 2017.1\bin\idea64.exe

;��pycharm
#+p::run D:\Program Files (x86)\JetBrains\PyCharm 2016.3.2\bin\pycharm64.exe

;��chrome
#Numpad3::run C:\Program Files (x86)\Google\Chrome\Application\chrome.exe

;����Ѷqq
#Numpad7::run C:\Program Files\Tencent\QQ\Bin\QQScLauncher.exe

#Numpad0::run F:\SmallTool\�����ö�����.exe

;��xftp
#+f::run C:\Program Files (x86)\NetSarang\Xftp 5\Xftp.exe

;��xshll
#+x::run C:\Program Files (x86)\NetSarang\Xshell 5\Xshell.exe

;��΢�ſͻ���
#+c::run C:\Program Files (x86)\Tencent\WeChat\WeChat.exe

;��everything
#w::run C:\Program Files\Everything\Everything.exe

;�򿪿ṷ
#NumpadMult::run D:\Program Files\KuGou\KuGou.exe

;��navicat
#+n::run C:\Program Files\PremiumSoft\Navicat Premium\navicat.exe

;��virtual box
#+v::run C:\Program Files\Oracle\VirtualBox\VirtualBox.exe

;��Sublime
#Numpad8::run C:\Program Files\Sublime Text 3\sublime_text.exe

;��editplus
#Numpad9::run C:\Program Files\EditPlus\EditPlus.exe


