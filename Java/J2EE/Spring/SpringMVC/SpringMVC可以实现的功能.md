1. 路径映射
2. 获取header, cookie, query, pathVariable
3. 原始请求
4. 拦截器, 拦截所有 or 根据路径匹配拦截
5. 本地化/国际化
	1. 国际化一般支持 Locale 就行了, TimeZone 可以不用
6. 直接回显字符串
7. 返回json, jsonp
	1. @RestController( 自动包含了 @ResponseBody )
	2. @JsonView(User.WithoutPasswordView.class)
	3. 支持jsonp
	```
	@ControllerAdvice public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {public JsonpAdvice() {super("callback");}}
	```
8. 异常处理




数据格式化
1. converter : 用于不同类型之间的转换, 可以不用考虑Locale
2. formatter : 用于类型与字符串的相互转换, 需要考虑Locale
@DateFormat
@NumberFormat
以Date为例, 假设现在要匹配 yyyy--MM--dd 这个样式, 那么可以在参数上指定 @DateTimeFormat(pattern = "yyyy--MM--dd")  这样就会对这个参数适用这个格式
也可以使用@InitBinder 在 WebDataBinder.addCustomFormatter 添加一个DateFormatter 指定样式
不过这似乎会替换掉原有的类型


支持其他http方法
restful
