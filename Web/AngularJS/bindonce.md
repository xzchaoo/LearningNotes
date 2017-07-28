https://github.com/Pasvaz/bindonce

一旦你的组件渲染, 那么值只会绑定一次, 大部分场景都是很有用的.

原来是这样
```
<ul>
    <li ng-repeat="person in Persons">
        <a ng-href="#/people/{{person.id}}"><img ng-src="{{person.imageUrl}}"></a>
        <a ng-href="#/people/{{person.id}}"><span ng-bind="person.name"></span></a>
        <p ng-class="{'cycled':person.generated}" ng-bind-html-unsafe="person.description"></p>
    </li>
</ul>
```

现在要这样
```
<ul>
    <li bindonce ng-repeat="person in Persons">
        <a bo-href="'#/people/' + person.id"><img bo-src="person.imageUrl"></a>
        <a bo-href="'#/people/' + person.id" bo-text="person.name"></a>
        <p bo-class="{'cycled':person.generated}" bo-html="person.description"></p>
    </li>
</ul>
```

bo提供了很多版本, 可以用于一次性绑定

需要注意的是, 因为bo只会绑定一次, 假设我们现在要对一个用户的信息进行呈现, user
而初始化的时候, 假设你还没加载user, 此时user是undefined 或 null的
会导致bo绑定的结果都是空
此时可以使用 bindonce="user" 来解决这个问题
他的意思是 直到 user 非空才进行第一次绑定
