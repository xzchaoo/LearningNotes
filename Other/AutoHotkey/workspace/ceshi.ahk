;����#q ��QQ�����
#q::Run, D:\Program Files\Tencent\QQBrowser\QQBrowser.exe

;΢��
#+c::run, D:\Program Files (x86)\Tencent\WeChat\WeChat.exe


;
;#w::msgbox ����
;#r::msgbox ����


;���⴦��!1
!1::Return

;��������Ŀ�ݼ�
#u::return
#h::return
#k::return



;�ڵ�ǰĿ¼�´�cmd
#IfWinActive ahk_exe explorer.exe
^!n::
id := winActive()
DetectHiddenWindows on
ControlGetText,epath, ToolbarWindow323, ahk_id %id%
;MsgBox %epath%
epath := RegExReplace(epath, "^.+? ","")
;MsgBox %epath%
;epath := SubStr(epath, 5)
if (epath="�˵���") or (epath="")
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


;��Google
;!g::run, http://www.google.com


;������һЩ����

;::ftw::Free the whales
;^!f::send "Free the whales"

;::ftw::
;send, Free the whales
;return

;:*:btw::By the way; ����Ҫ�����ַ��Ϳ�����Ч

/*
^k::
send,
(
��
��
��
��
��
)
return

;%A_ProgramFiles%
;Run, %A_MyDocuments%
;Run, http://www.google.com
*/



