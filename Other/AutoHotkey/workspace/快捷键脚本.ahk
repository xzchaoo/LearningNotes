#NoEnv
#Persistent

TIMER_LOCK_MOUSE:=0

;windows�������� http://blog.163.com/cayyenne@126/blog/static/121862614201019082750/

;����

;�û���Ŀ¼
USERHOME = %HOMEDRIVE%%HOMEPATH%
;�û�����
DESKTOP = %A_desktop%
;Ĭ�Ϲ���Ŀ¼
DEFAULT_WORKING_DIR = %USERHOME%

;�������ʾ����index, ;���������ó�ʼ��ʾ������Ϊ1, ��Ϊ����2����ʾ������Ϊ����ʾ��!
SysGet, currentMonitor, MonitorPrimary
;currentMonitor:=1


;ÿ10��������һ��������
;win10�µ���Ļò�ƿ������ó�ʹ�����粻�˱߽�
if (TIMER_LOCK_MOUSE=1){
	SetTimer, KeepMonitor, 10
}

;�鿴����ϵͳ����Ϣ
;MsgBox,, Operating system info,
;(
;OS Type: %A_OSType%
;OS Version: %A_OSVersion%
;)


;ͨ��


;��������
^!Numpad8::send {Volume_Up}
^!Numpad2::send {Volume_Down}
^!Numpad0::send {Volume_Mute}


;��һ��/��һ��/��ͣ
^!Numpad4::send {Media_Prev}
^!Numpad6::send {Media_Next}
^!Numpad5::send {Media_Play_Pause}


;�ڵ�ǰĿ¼�´�cmd, ����explorer.exe ����ʱ��Ч
#IfWinActive ahk_exe explorer.exe
^!n::
id := winActive()

;ͨ��spy���Բ鿴�����ڵ���Ϣ, ��win7 �� win10 ���Դ������������л�ȡ��ǰ·������Ϣ
;win7 ControlGetText,epath, ToolbarWindow322, ahk_id %id%
;win10 ControlGetText,epath, ToolbarWindow323, ahk_id %id%

;�ȳ���win10
ControlGetText,epath, ToolbarWindow323, ahk_id %id%
if(epath=""){
;�ٳ���win7
	ControlGetText,epath, ToolbarWindow322, ahk_id %id%
}
;TODO ����������滻�Ǹ����?
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
#IfWinNotActive, ahk_exe explorer.exe
^!n::
;msgbox %DEFAULT_WORKING_DIR%
SetWorkingDir %DEFAULT_WORKING_DIR%
run cmd
return

#IfWinNotActive



;��cmd.exe��powershell.exe�����ʱ�� ����Alt+F4 �� Shift+Insert
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



;�򿪼�Ŀ¼
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



;����

;��google
#g::run, http://www.google.com

;qq�����
#q::run "C:\Program Files\Tencent\QQBrowser\QQBrowser.exe" -sc=desktopshortcut -fixlaunch=0

;��markdownpad2
#Numpad2::run "C:\Program Files (x86)\MarkdownPad 2\MarkdownPad2.exe"

;��idea
#+i::run "D:\Program Files\JetBrains\IntelliJ IDEA 2018.1.3\bin\idea64.exe"

;��pycharm
;#+p::run D:\Program Files (x86)\JetBrains\PyCharm 2016.3.2\bin\pycharm64.exe

;��chrome
#Numpad3::run "C:\Users\xzchaoo\AppData\Local\Google\Chrome\Application\chrome.exe"

;����Ѷqq
#Numpad7::run "C:\Program Files\Tencent\QQ\Bin\QQScLauncher.exe"

;��xftp
#+f::run C:\Program Files (x86)\NetSarang\Xftp 5\Xftp.exe

;��xshll
#+x::run C:\Program Files (x86)\NetSarang\Xshell 5\Xshell.exe

;��΢�ſͻ���
#+c::run "C:\Program Files (x86)\Tencent\WeChat\WeChat.exe"

;��everything
#w::run C:\Program Files\Everything\Everything.exe

;������������
#NumpadMult::run "D:\Program Files (x86)\Netease\CloudMusic\cloudmusic.exe"

;��navicat
#+n::run C:\Program Files\PremiumSoft\Navicat Premium\navicat.exe

;��editplus
#Numpad9::run "C:\Program Files\Notepad++\notepad++.exe"

