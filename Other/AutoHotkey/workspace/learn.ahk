SysGet, currentMonitor, MonitorPrimary

#Persistent
;SetTimer, KeepMonitor, 100
return


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
global currentMonitor

SysGet, MonitorCount, MonitorCount
tempCurrentMonitor:=currentMonitor+1
if(tempCurrentMonitor > MonitorCount){
	tempCurrentMonitor:=1
}
currentMonitor:=tempCurrentMonitor

LockMonitor(currentMonitor)
MoveToMonitorCenter(currentMonitor)

return

;��Ҫ�ж���ķֺ�

;���ﶨ����һ������ ��Ĭ��ֵ
fun1(a, b,c:=3) {
	return a + b + c
}

!q::
	inputbox, name, ѯ��, ���������?
	if(name!=""){
		;����������������
		result:=fun1(1,2)
		msgbox %A_YYYY%
		msgbox, ���Ǳ���, ��������� %name% %result%
	}else{
		v1=1
		v2=%v1%
		v3=v2
		v4:=v3
		v5:="v4"
		v8:=1+2+3
		if(1=1){
			msgbox �㰴����alt+q %v1%+%v2%+%v3%+%v4%+%v5%
		}
	}
return