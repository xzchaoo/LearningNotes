# 描述 #
https://github.com/Unitech/pm2  
 
特性:
  
- 简单高效的进程管理
- 通过重启和初始化脚本让你的应用一直在线
- 无需改动代码, 集群化你的node程序, 提高性能和可靠性
- 无需额外配置, 热加载node程序

# 安装 #

	npm install pm2@latest -g


# 用法 #

启动

	启动4个实例
	pm2 start app.js -i 4
	这相当于
	{
	  "apps" : [{
	    "script"    : "api.js",
	    "instances" : "max",
	    "exec_mode" : "cluster" 
	  }]
	}


列表
	
	pm2 ls

停止

	pm2 stop <app-name>

删除

	pm2 delete <app-name>


restart 杀掉应用再启动

reload 和restart不同, 可以做到0 downtime



进入监控界面
	  
	pm2 monit

# 用yaml/json来描述你的应用群 #


	apps:
		- script: /root/node_workspace/myapp/bin/www
		  instances: 4
		  exec_mode: cluster
          watch: true





# 杂 #

覆盖掉默认的名字  
--name my-api

-i 指定实例个数, 0或max为 取CPU个数, -1表示CPU数量-1

运行在集群模式, 实例数=CPU个数  
pm2 start app.js -i 0



--only  
--watch  
--max-memory-restart 20M  
--node-args="--harmony"

instances 实例个数撒地方
  
exec_mode fork/cluster
  
watch boolean/[]
  
ignore_watch
  
max_memory_restart 超过多少内存就杀掉
  
env 环境变量
    
env_ ? 特殊环境下的环境变变量
  
source_map_support

instance_var

 "node_args" : "--harmony"