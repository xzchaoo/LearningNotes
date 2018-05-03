# 注 #
多和开源框架学学, 慢慢积累就行了! 最重要的就是积累!

# Javadoc 常用标记 #

	/**
	* @author xzchaoo
	* @date 2018/01/01
	*/
	public class Foo {
		...
	}


# 生成javadoc文档 #
IDEA -> Tools -> Generate JavaDoc

# SuppressWarnings #
可以让一些静态分析工具忽略某些警告  
比如有一个方法的参数没用到, 编译器或者sonar会提示你该变量没用到, 建议移除, 这时你可以对这个参数使用 @SuppressWarnings("unused") 这样它们就不会再提示了

# 参考 #
- https://blog.csdn.net/gxf212/article/details/1399568  
- https://blog.csdn.net/GarfieldEr007/article/details/54959597

