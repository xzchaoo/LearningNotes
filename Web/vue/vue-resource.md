https://github.com/pagekit/vue-resource

http://7xteex.com1.z0.glb.clouddn.com/json/daily/20170426/33_play_0.json


Vue.http.options.root = '/root';
Vue.http.headers.common['Authorization'] = 'Basic YXBpOnBhc3N3b3Jk';

可以设置某个组件级别的http默认配置
```
new Vue({
  http: {
    root: '/root',
    headers: {
      Authorization: 'Basic YXBpOnBhc3N3b3Jk'
    }
  }

})
```