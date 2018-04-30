# 描述 #
https://github.com/remy/nodemon

开发环境, 检测到文件变化, 自动重启机器

# 使用方法 #

nodemon [your node app]

--watch 目录, 可以用于控制监控的目录
--ignore 用于忽略一些目录或文件, 比如 "lib/*.js"
--delay 2 延迟2秒

> Note that by default, nodemon will ignore the .git, node_modules, bower_components, .nyc_output, coverage and .sass-cache directories and add your ignored patterns to the list. If you want to indeed watch a directory like node_modules, you need to override the underlying default ignore rules.

如果当前目录下有 package.json 那么可以不指定node app路径, 会自动用package.json里的main属性

nodemon可以有一个配置文件叫做 nodemon.json 或通过 --file 指定

	{
	  "verbose": true,
	  "ignore": ["*.test.js", "fixtures/*"],
	  "execMap": {
	    "rb": "ruby",
	    "pde": "processing --sketch={{pwd}} --run"
	  }
	}

也可以将配置放到 package.json里

	在里面新增一个属性
	"nodemonConfig": {
	    "ignore": ["test/*", "docs/*"],
	    "delay": "2500"
	  }


# 优雅shutdown #

	process.once('SIGUSR2', function () {
	  gracefulShutdown(function () {
	    process.kill(process.pid, 'SIGUSR2');
	  });
	});

