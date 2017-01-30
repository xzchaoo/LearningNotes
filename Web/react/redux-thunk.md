npm install --save redux-thunk

import ReduxThunk from 'redux-thunk' // no changes here 😀

让你可以返回一个函数作为action, 这个函数会被调用, 它会接受一个 dispatch 参数
然后你就可以自己来定义什么时候调用dispatch 函数

# trunk #
一个trunk是一个包装了表达式的函数, 这样的话我们就可以延迟这个表达式的计算了
let x = 1 + 2;
let x = () => 1+2 ;

```
const store = createStore(
  reducer,
  applyMiddleware(thunk.withExtraArgument({ api, whatever }))
)

// later
function fetchUser(id) {
  return (dispatch, getState, { api, whatever }) => {
    // you can use api and something else here here
  }
}
```