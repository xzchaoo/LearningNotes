http://ui-grid.info/docs/#/tutorial

基础demo
```
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
	<meta charset="UTF-8">
	<script src="node_modules/angular/angular.min.js"></script>
	<script src="node_modules/angular-ui-grid/ui-grid.min.js"></script>
	<link rel="stylesheet" href="node_modules/angular-ui-grid/ui-grid.min.css">
	<script>
		angular.module('app', ['ui.grid']).run(function ($rootScope) {
			$rootScope.data = [
				{id: 1, username: 'aaa', age: 11},
				{id: 2, username: 'bbb', age: 22},
				{id: 3, username: 'ccc', age: 33},
			];
		});
	</script>
	<style>
		.myGrid {
			width: 500px;
			height: 250px;
		}
	</style>
</head>
<body>
<div ui-grid="{data:data}" class="myGrid"></div>
</body>
</html>
```
默认就已经支持字段的排序了

ui-grid="gridOptions"
gridOptions是一个对象:
1. columnDefs:[] 字段定义
2. enableFilter 启动过滤功能
3. enableSorting 启动默认排序功能, 每个字段可以自行禁用

```
enableSorting 控制是否可以排序
type 用于显示设置字段类型 string number numberStr date, 这会影响默认的排序行为
filter:{term:1} 设置该字段的默认过滤term是1
sort: {
  direction: uiGridConstants.DESC,
  priority: 1
}
suppressRemoveSort:true 该字段不可移除
sortingAlgorithm:function(a,b,rowA,rowB,direction){} 自定义排序函数
```
自定义过滤函数
function (term, cellValue, row, column)
filter 还支持 placeholder 属性

内置了一些常见过滤器
范围
select

可以定制footer
一般footer用于显示聚合值, 也内置了许多的聚合函数 sum count 之类

支持双向绑定, 你可以修改表格
enableCellEdit
