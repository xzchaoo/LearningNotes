#!/bin/bash

function start(){
	echo 启动mysql
	docker run -d --name mysql xzchaoo/bs2-mysql:1.1

	echo 为mysql填充数据
	while ! docker exec mysql sh -c 'exec mysql -uroot -p"70862045" --default-character-set=utf8 -e "source /bs2.sql;"'; do
		echo 尝试填充数据
		sleep 1
	done
	echo 填充数据成功

	echo 启动jboss...
	docker run -d --name jboss --link mysql:mysql xzchaoo/bs2-jboss:1.4

	echo 启动redis...
	docker run -d --name redis redis:3.0.7-alpine

	echo 启动tomcat...
	docker run -d -p 8080:8080 --link jboss:jboss --link redis:redis --name tomcat xzchaoo/bs2-tomcat:1.1

}
function stop(){
	echo 停止tomcat
	docker stop tomcat && docker rm tomcat
	
	echo 停止redis
	docker stop redis && docker rm redis

	echo 停止jboss
	docker stop jboss && docker rm jboss

	echo 停止mysql
	docker stop mysql && docker rm mysql
}
function usage(){
	echo 'usage: start 用于启动, stop用于停止'
}

if [ "$1" == "start" ];then
	start
elif [ "$1" == "stop" ];then
	stop
else
	usage
fi
