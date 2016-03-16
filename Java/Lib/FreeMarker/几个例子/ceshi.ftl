<!doctype html>
<html>
<head>
	<title>我是FTL1</title>
	<meta charset="utf-8">
</head>
<body>
<pre>
	current_template_name=${.current_template_name!''}
	lang=${.lang!''}
	locale=${.locale!''}
	locale_object=${.locale_object!''}
	locals=${.locals!''}
	main_template_name=${.main_template_name!''}
	node=${.node!''}
	now=${.now!''}
	output_encoding=${.output_encoding!''}
	template_name=${.template_name!''}
	url_escaping_charset=${.url_escaping_charset!''}
	version=${.version!''}
</pre>
<pre><#t>
                                       b                                                  <#t>
	for(int i=0;i<10;++i){
		...
		break;
	}
<#t>
<#t>
<#t>
</pre>

<#function ceshi a b c>
	<#local sum=a+b+c />
	<#if a gte b && a gte c>
		<#return a+sum/>
	<#elseif b gte a && b gte c>
		<#return b+sum/>
	<#else>
		<#return c+sum/>
	</#if>
</#function>
${ceshi(1,2,3)}

<#function avg nums...>
	<#local sum = 0>
	<#list nums as num>
		<#local sum = sum + num>
	</#list>
	<#if nums?size != 0>
		<#return sum / nums?size>
	</#if>
</#function>
${avg(1,2,3,4,5)}

<#import '/spring.ftl' as s />


<div>${.now}</div>
<div>
${.locale}
</div>
123


<#import '/lib/mylib.ftl' as my />
<div>
${my.email}
	<@my.echo text="测试"/>
	<@my.echo text="ces" />
</div>
<#macro greet text text2 text3="我是默认值">
<font color="red">${text}, ${text2}, ${text3}</font>
</#macro>

<#macro wrap1 count>
<div>下面是你的原始内容</div>
	<#list 1..count as x>
	<div><#nested x 'ceshi'/></div>
	</#list>
</#macro>

<@wrap1 count=3;x, y>
我是原始内容-我是第${x}次迭代 - ${y}
</@wrap1>


<div>
<@greet text="你妹夫" text2="哈哈"/>
</div>

<div>
<#escape x as x?upper_case>
	    ${'ceshi'}
	</#escape>
</div>
<div>
${(name!'empty')?upper_case}
	<#assign name = 'empty2'?upper_case >
	${(name!'empty')?upper_case}

</div>
<div>
${user['name']}
	${simpleMap['key1']}
	${5130379097?c}
	${7086204570862045?c}
</div>
<div>
${'a
	sjdfjsdlkfslkd
	b'}
</div>
<div>
	<ul>
	<#list userList2 as user>
		<li>${user.name}</li>
		<#sep>
		<li>,</li>
	<#else>空空如也
	</#list>
	</ul>
</div>
<hr/>
<div>
	<ul>
	<#list userList as user>
		<li>${user.name}</li>
	</#list>
	</ul>
</div>

<div>s_k1=${s_k1}, s_k2=${s_k2}</div>
<div>s_k1=${simpleMap.key1},s_k2=${simpleMap.key2}</div>
<div>${userList[0].name}</div>
<div>

<#if user.id lt 100 >
	id<100!
<#elseif user.id gt 10>
	id>10!
<#else>
	不知道
</#if>


</div>

<#include "/footer.ftl"/>

</body>
</html>