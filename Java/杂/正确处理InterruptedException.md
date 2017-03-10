http://www.ibm.com/developerworks/cn/java/j-jtp05236.html
```
while(!Thread.interruptted()){
	try{
		...	
	}catch(IE e){
		Thread.currentThread().interrupt();
		break;	
	}
}
```