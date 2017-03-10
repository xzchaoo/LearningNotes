obj = mock(clazz) mock一个对象

验证调用过某方法
verify(obj).方法

插桩
when(obj.fun1()).thenXxx

参数匹配
any(clazz) anyXxx()系列 anyInt()
