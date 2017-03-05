https://github.com/angular-ui/ui-validate
https://htmlpreview.github.io/?https://github.com/angular-ui/ui-validate/master/demo/index.html

配合原生的验证器, 感觉已经非常够用了!
可以简化原本的自定义校验器的编写!
缺点就是你需要将验证函数引入到你的$scope里 会导致耦合

假设你的项目会在4个地方使用到检查用户名是否唯一这个功能, 那么需要在4处的$scope都引入检查函数(异步的, 返回一个promise)
如果有必要的话还是建议使用directive吧
不过使用这个项目确实不错, 可以马上实现一些比较好的校验, 比如 password1==password2

```
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
	<meta charset="UTF-8">
	<script src="node_modules/angular/angular.min.js"></script>
	<script src="node_modules/angular-ui-validate/dist/validate.min.js"></script>
	<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<script>
		angular.module('app', ['ui.validate']).run(function ($timeout, $q, $rootScope) {
			var vm = $rootScope;
			vm.user = {
				username: 'xzc',
				password: 'xzc',
			};
			vm.submit = function (form) {
				alert('submit');
			};
			vm.validateUsername = function (username) {
				return username == 'xzcxzc';
			}
			vm.isUsernameUnique = function (username) {
				console.log('isUsernameUnique');
				var d = $q.defer();
				$timeout(function () {
					if (username == 'xzcxzc') {
						d.resolve();
					} else {
						d.reject();
					}
				}, 1000);
				return d.promise;
			};
		});
	</script>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-xs-4">
			<form name="form" class="submit(form)">
				{{form.username.$valid}}
				<div class="form-group has-{{form.username.$valid?'success':'error'}}">
					<label>用户名</label>
					<input type="text" name="username" ng-model="user.username" class="form-control"
					       required
					       minlength="6"
					       maxlength="10"
					       ui-validate-async="'isUsernameUnique($value)'"
					>
					       <!--ui-validate="'validateUsername($value)'"-->
				</div>
				<div class="form-group has-{{form.password.$valid?'success':'error'}}">
					<label>密码1</label>
					<input type="password" name="password" ng-model="user.password" class="form-control" required
					       minlength="6" maxlength="16">
				</div>
				<div class="form-group has-{{form.password2.$valid?'success':'error'}}">
					<label>密码2</label>
					<input type="password" name="password2" ng-model="user.password2" class="form-control"
					       ui-validate="'form.password.$valid && $value==user.password'">
				</div>
				<button type="button" class="btn btn-primary"
				        ng-disabled="form.$invalid || form.$pending || form.$submitted">提交
				</button>
				<button type="reset" class="btn btn-warning">Reset</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>
```
