;拦截#q 打开QQ浏览器
#q::Run, D:\Program Files\Tencent\QQBrowser\QQBrowser.exe

;微信
#+c::run, D:\Program Files (x86)\Tencent\WeChat\WeChat.exe


;
;#w::msgbox 干嘛
;#r::msgbox 干嘛


;特殊处理!1
!1::Return

;禁用下面的快捷键
#u::return
#h::return
#k::return



;在当前目录下打开cmd
#IfWinActive ahk_exe explorer.exe
^!n::
id := winActive()
DetectHiddenWindows on
ControlGetText,epath, ToolbarWindow323, ahk_id %id%
;MsgBox %epath%
epath := RegExReplace(epath, "^.+? ","")
;MsgBox %epath%
;epath := SubStr(epath, 5)
if (epath="此电脑") or (epath="")
{
	epath=%A_desktop%
}
SetWorkingDir %epath%
run cmd
return
#IfWinActive

;!2::
;send, #+{right}
;return


;打开Google
;!g::run, http://www.google.com


;下面是一些例子

;::ftw::Free the whales
;^!f::send "Free the whales"

;::ftw::
;send, Free the whales
;return

;:*:btw::By the way; 不需要结束字符就可以生效

/*
^k::
send,
(
我
是
很
多
行
)
return

;%A_ProgramFiles%
;Run, %A_MyDocuments%
;Run, http://www.google.com
*/



