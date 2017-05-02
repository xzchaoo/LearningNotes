https://lodash.com/

keys(不支持原型继承) keysIn(支持原型继承)获得一个对象的key

each/forEach (集合,迭代器)


# template #
https://lodash.com/docs/4.17.4#template


var compiled = _.template('hello <%= user %>!');
compiled({ 'user': 'fred' });

```
<% 使用原生js语句
<%= user %> 插值 不转义, 也可以使用 ${user}语法
<%- user %> 会转义
用\转义


var text = '<% jq.each(users, function(user) { %><li><%- user %></li><% }); %>';
var compiled = _.template(text, { 'imports': { 'jq': jQuery } });
compiled({ 'users': ['fred', 'barney'] });

```

# includes #
_.includes(names,name,0)
0表示下标
