单个 store
单个 reducer

当 state==undefined 的时候 需要返回出初始化状态
reducer不可以修改state!
一定要保证不可修改 如果 state 是一个对象, 那么你不可以修改 state 的key的指向, 但是你可以修改key对应的value的属性
如果state是数组, 那么你不可以修改它的元素, 但是你可以修改它的元素的属性

const store = createStore(your_reducer);//这会导致 以 state==undefined 调用 reducer 函数 进行初始化

console.log(store.getState());

订阅

const unSubscribe = store.subscribe( () => {
//store.getState(); 拿到状态 重新渲染
});
返回值是注销函数



store.dispatch(action)

action应该是一个具有如下格式的对象:
```
{
	type:'一个字符串'
	其他字段你自己定义
}
```

# ES6语法 #
lambda表达式
()=>{}

{a,b,c} 是 {a:a,b:b,c:c} 的简写

const 定义的时候必须赋值 并且不可修改

let 与 var 的区别?

# 常用技巧 #
Object.assign({},x,y,z);
```
{
	...obj,
	key1:value1
}
```
[array1,array2] 相当于 array1.concat(array2)


函数式无状态组件
```
import React, { PropTypes } from 'react'

const Todo = ({ onClick, completed, text }) => (
  <li
    onClick={onClick}
    style={{
      textDecoration: completed ? 'line-through' : 'none'
    }}
  >
    {text}
  </li>
)

Todo.propTypes = {
  onClick: PropTypes.func.isRequired,
  completed: PropTypes.bool.isRequired,
  text: PropTypes.string.isRequired
}

export default Todo
```


```
import React, { PropTypes } from 'react'
import Todo from './Todo'

const TodoList = ({ todos, onTodoClick }) => (
  <ul>
    {todos.map(todo =>
      <Todo
        key={todo.id}
        {...todo}
        onClick={() => onTodoClick(todo.id)}
      />
    )}
  </ul>
)

TodoList.propTypes = {
  todos: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number.isRequired,
    completed: PropTypes.bool.isRequired,
    text: PropTypes.string.isRequired
  }).isRequired).isRequired,
  onTodoClick: PropTypes.func.isRequired
}

export default TodoList
```


无状态的第一个参数是...TODO
第二个参数是...TODO

子组件 需要声明才会收到
contextTypes={
}

父组件
Provider.childContextTypes={
store:React.ProtTYpes.object
}
https://egghead.io/lessons/javascript-redux-extracting-action-creators
https://sentry.io/welcome/
