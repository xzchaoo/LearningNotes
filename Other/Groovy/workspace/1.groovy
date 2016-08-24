/*public class HelloWorld{
	public static void main(def args){
	//使用def定义变量
	//括号可以省略
	//默认是public
	//不需要分号
	//可以不用返回
	//支持三个引号
		println 123
	}
}
*/
for(i=0;i<10;++i){
	print i
}
println ''
for(def i=0;i<10;++i){
	print i
}
println ''
for(int i=0;i<10;++i){
	print i
}
for(i in 0..10){//注意这里包含10
	print i
}
for(i in 0..<10){//注意这里包含10
	print i
}

def name='xzc'
def age=20
println "插值$name,$age,$i"

//定义方法
f1(p1,p2=2){
	def p3=p1+p2
	println "$p1+$p2=$p3"
}
f1(1)