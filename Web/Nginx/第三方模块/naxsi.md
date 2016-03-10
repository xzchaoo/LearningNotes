https://github.com/nbs-system/naxsi/tree/master/nxapi
https://www.elastic.co/guide/index.html
http://bluereader.org/article/32032789

白名单
BasicRule wl:ID [negative] [mz:[$URL:target_url]|[match_zone]|[$ARGS_VAR:varname]|[$BODY_VAR:varname]|[$HEADERS_VAR:varname]|[NAME]]
格式
wl:ID 指的是你要对哪一条MainRule进行白名单操作, 可以单个数字比如 107, 可以是0, 表示对所有规则, 可以是41,42,43表示这3条规则, 可以是负数, -42表示所有>=1000的规则扣掉规则42(当然规则42本身不在这个范围里面).

mz: ...表示该请求的哪些属性不参与wl指定的规则的检测
比如规则123说, 用户的所有QueryString的参数的值不可以包含 '|'
如果我请求 /1.html?a=| 那么肯定就违反这个规则了
这时候可以: mz:$ARGS_VAR:a 意思就是说对于我的参数a, 这个规则不适用
因此我上面那个请求就可以通过这条规则了
其他选项奶奶个比如 $HEADERS_VAR:name 也是同理的

对于BasicRule的negative选项, 官方并没有多说

规则的语法
以下面一条为例子:
MainRule "str:=" "msg:equal in var, probable sql/xss" "mz:ARGS|BODY" "s:$SQL:2" id:1009;
id用于标识这一条规则
str: 表示字符串的对比, 因此这条的意思就是 对比字符串是否为 =(等于号)
msg: 表示这个规则的一个描述吧
mz: 表示要在哪里进行字符串的对比, 在这一条就是指:查询的参数, BODY(如果是POST)
s: 表示进行得分的统计 $SQL:2 表示这个请求为$SQL变量 加2分
这个分数用于: 当某个变量累计的分数超过你定义的分数之后, 你就可以认为这个请求是恶意的

 MainRule negative "rx:multipart/form-data|application/x-www-form-urlencoded" "msg:Content is neither mulipart/x-www-form.." "mz:$HEADERS_VAR:Content-type" "s:$EVADE:4" id:1402;
 这里的negative表示如果这个规则不匹配的话, 反而给$EVADE加4分
 
 
