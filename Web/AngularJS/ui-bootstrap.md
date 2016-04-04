tpls版本 将 一些模板直接内嵌在js脚本中

angular.module('myModule', ['ui.bootstrap']);

如果你使用了 CSP mode, 那么需要把csp的css导入

# Buttons  #
作为属性加在按钮上
uib-btn-checkbox 以按钮的形式显示一个checkbox
uib-btn-radio 可以设置一个表达式, 其结果决定了该radio是否可选

不过用于checkbox和radio的话, 它的颜色变化似乎不是太明显



# Modal #
$uibModal

主要是使用open(options)方法, 它会返回一个对象, 通过这个对象可以让 对话框 关闭, 还可以通过promise获取对话框的返回值
options主要是用于控制 templateUrl, controller, size等


# Dropdown #
一个dropdown由 dropdown-toggle 和 dropdown-menu组成

```
<li uib-dropdown ng-if="AuthService.isLogin()">
	<a href="#" uib-dropdown-toggle>{{AuthService.getCurrentUser().username}}
		<span class="caret"></span>
	</a>
	<ul uib-dropdown-menu>
		<li ui-sref-active="active"><a ui-sref="user.me">个人信息</a></li>
		<li><a ng-click="onLogout()">注销</a></li>
		<li><a href="#">Another action</a></li>
		<li><a href="#">Something else here</a></li>
		<li role="separator" class="divider"></li>
		<li><a href="#">Separated link</a></li>
		<li role="separator" class="divider"></li>
		<li><a href="#">One more separated link</a></li>
	</ul>
</li>
```

uib-dropdown 支持的一些属性, 直接写在对应的元素的属性上
auto-close 点击了某项后是否自动关闭
keyboard-nav 是否允许键盘上下来选择项
on-toggle 一个表达式, 当开关状态改变时执行
is-open 一个表达式, 用于控制开关

uib-dropdown-menu 可以带一个 template-url 属性


