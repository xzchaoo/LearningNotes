# gulpfile.js #
不再是一个 js文件 而是一个目录, 应该是利用node.js对包的搜索机制
实际上会加在的是 gulpfile.js/index.js 这个文件

gulpfile.js/index.js 利用 require-dir 将 gulpfile.js/tasks/ 下的所有js文件都requrie一下, 每个js文件定义了一个任务
**扩展点**:想要添加gulp任务, 只需要在 gulpfile.js/tasks/ 新增文件文件即可

## 常见任务 ##
1. clean: 删除 public 目录
2. css: 处理 src/stylesheets 下的 sass scss css
	1. 如果不是生产环境, 则:
		1. 生成 sourcemap
	2. 处理sass
	3. autoprefixer
	4. 如果是生产环境
		1. 最小化
3. default
	1. 依赖 clean, html/css/js/字体/图片的处理 static 然后再watch
4. deploy
	1. TODO
5. fonts
	1. src/fonts -> public/fonts
6. html
	1. 将 src/html 下的html文件复制到public/html下
	2. 如果是生产环境
		1. 最小化
	3. 在这里处理模板问题 
7. images
	1. 将 src/images/ 下的图片文件 复制到 public/images
	2. 压缩
8. production
	1. clean 各种任务 rev size-report static
9. server
	1. 启动nodejs服务器 使用 express 框架
10. sizereport
11. static
	1. 将 src/static 下的所有文件复制到 public/static 下
	2. watch时仅会处理改变过的文件
12. svgSpirite
13. watch
	1. 这些任务是可以监听的 ['fonts', 'iconFont', 'images', 'svgSprite','html', 'css'] 的变化
14. webpackProduction
	1. 导入 webpack 的配置 并且执行webpack
