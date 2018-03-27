# 第1章 - 入门#
分成接口和实现
开发人员对接口编程, 使用工厂方法创建对象, 不会直接跟是闲着打交道

安装加密扩展包
Policy Files

## 安装 Bouncy Castle Provider ##
### 方法1 ###
将它的jar包放到 jre/lib/ext
编辑 java.security 
加入一行
security.provider.N=org.bouncycastle.jce.provider.BouncyCastleProvider

### 方法2 ###
将它的jar包导入到你的项目
然后在程序的入口
Security.addProvider(new BouncyCastleProvider());

## Provider如何工作 ##
Cipher.getInstance("Blowfish/ECB/NoPadding","BC");
getInstance的时候可以显式提供Provider的名字, 比如上面的BC
如果不提供, 那么java会按照java.security里的顺序遍历, 直到找到第一个能提供该算法的provider

# 第2章 - 对称加密 #
了解各种对称加密算法
cipher模式和填充

AlgorithmName/Mode/TypeOfPadding 
后面2个是可选的, 如果不写那么会用provider提供的默认值

Mode常见的有

ECB:Electronic Code Book
块加密算法, 有一个问题是, 如果你的原始数据具有重复, 那么加密后的结果也很有可能会有重复, 增加了被攻击的可能性

CBC:Cipher Block Chaining
将需要加密的块与上一个块的加密结果进行XOR, 然后再去加密
可以通过 IvParameterSpec 设置与第一个块XOR的数据
根据这个特性, 还可以提供一个全为0的 IvParameterSpec, 然后对Cipher update 2次, 第1次用你的Iv, 第2次用药加密的数据
因为0 xor any = any
IV也可以是随机的, 你可以用SecureRandom进行填充, 但也要负责传输IV

也可以让Cipher自己生成IV
```
cipher.init(Cipher.ENCRYPT_MODE, key);
IvParameterSpec ivSpec = new IvParameterSpec(cipher.getIV());
```

CTS:Cipher Text Stealing
不知道干嘛的

CTR:可以生成和原文一样长的结果
实现方式
将 原文 和 临时生成的加密结果 进行XOR作为 最终的加密结果

OFB
CFB

ARC4


## 第3章 ##