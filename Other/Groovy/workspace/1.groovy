/*public class HelloWorld{
	public static void main(def args){
	//ʹ��def�������
	//���ſ���ʡ��
	//Ĭ����public
	//����Ҫ�ֺ�
	//���Բ��÷���
	//֧����������
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
for(i in 0..10){//ע���������10
	print i
}
for(i in 0..<10){//ע���������10
	print i
}

def name='xzc'
def age=20
println "��ֵ$name,$age,$i"

//���巽��
f1(p1,p2=2){
	def p3=p1+p2
	println "$p1+$p2=$p3"
}
f1(1)