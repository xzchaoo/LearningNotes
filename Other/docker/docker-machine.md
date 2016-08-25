ls

create
start
stop

active 打印出当前活动的主机

create --driver virtualbox d2

create-machine create --driver name


for /f "tokens=*" %i in ('docker-machine env d2') do %i

docker-machine create
