很好看的弹窗

导入js和css之后就可以开始使用了
sweetAlert("Oops...", "Something went wrong!", "error");

swal 是 sweetAlert 的简称

```
$('#delete').click(function () {
	swal({
		title: "询问",
		text: "你确定要删除该账号吗?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: "确认删除",
		cancelButtonText: "手滑了",
		closeOnConfirm: false,
		showLoaderOnConfirm: true
	}, function () {
		$.ajax({
			url: '/baccounts/${baccount.mid?c}',
			type: 'DELETE',
			dataType: 'json',
			success: function (resp) {
				if (resp.code == 0) {
					location.pathname = '/baccounts';
				} else {
					swal('错误', resp.msg, 'error');
				}
			},
			error: function (resp) {
				swal('错误', '错误了', 'error');
			}
		});
	});
});
```

```
title 标题
text 文本
type warning error success info 如果设置成input 那么会是一个输入框
allowEscapeKey 是否允许 esc 键取消该对话框
allowOutsideClick 点在外面就关闭
showCancelButton/showConfirmButton 是否显示按钮
customClass 额外的css
confirmButtonText/confirmButtonColor/cancelButtonText 定制按钮
showLoaderOnConfirm
closeOnConfirm 当点击确认的时候关闭
closeOnCancel 点击取消的时候关闭

定制图标
imageUrl/imageSize

timer 毫秒 定时关闭

html 默认是false 如果是true则text不会被转义

animation 默认是true(使用的是 pop 效果) 可以的取值 true, pop slide-from-top slide-from-bottom

定制input
inputType inputPlaceholder inputValue
```