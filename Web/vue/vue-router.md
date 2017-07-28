# vue-router #

## router-link ##
to可以是字符串
还可以是对象 {name:'', path:'', params:{userId:1}, query:{a:1,b:2}}
name path 提供一个就行

replace 默认是false, 使用 router.replace() 而不是 push() 不可后退

append 默认是false

tag表示渲染成哪种类型的元素 默认是a
active-class 表示当前连接激活的时候的css类名 默认是 router-link-active
默认值可以通过路由的构造器的 linkActiveClass 来配置

exact 表示active路由匹配的时候是用精确匹配
因为/a是/的子路由 所有/会被添加激活的active类

这样激活的类就会被加在li上
```
<router-link tag="li" to="/foo" active-class="active">
  <a>/foo</a>
</router-link>
```

## router-view ##
类似 ui-view

name 默认是 defualt


# 路由信息对象 #
通过 this.$route 访问
router.beforeEach((to,from,next)=>{}) 里的 to,from 都是路由信息对象
```
const router = new VueRouter({
  scrollBehavior (to, from, savedPosition) {
    // to 和 from 都是 路由信息对象
  }
})
```


$route.path 对应当前的路径
$route.params
$route.query
$route.hash
$route.fullPath
$route.name
$route.matched 假设你在 /a/b/c, 它是/a/b 子组件, /a/b是/a的子组件, 那么此时matched=[/a的路由信息,/a/b的路由信息,/a/b/c的路由信息]


# Router构造函数 #
routes
mode: hash abstract history
base 默认是 /
linkActiveClass 默认是 router-link-active
scrollBehavior:(to,from,savePosition?)=>{}

## Router方法 ##
push replace go back forward
beforeEach afterEach

# RouterConfig #
path:string
component?:component
name?string
components? {name1:component1}
redirect:string|Location|Function
alias
children
beforeEnter
meta?any

# 注意 #
$router 和 $route 会被注入到每个组件里
每个组件允许额外的配置: beforeRouteEnter beforeRouteUpdate beforeRouteLeave
beforeRouteEnter 钩子 不能 访问 this，因为钩子在导航确认前被调用,因此即将登场的新组件还没被创建。

# 异步加载 #
这里假设所有的IE浏览器都不支持promise 因次用下面的语法
<!--[if IE]>
<script src="https://cdn.bootcss.com/es6-promise/4.1.0/es6-promise.auto.min.js"></script>
<![endif]-->
