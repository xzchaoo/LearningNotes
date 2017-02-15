http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html?utm_source=tuicool
http://www.ruanyifeng.com/blog/2015/07/flex-examples.html

指定父容器的 display:flex;
也有 inline-flex;

> Webkit内核的浏览器，必须加上-webkit前缀。


> 设为Flex布局以后，子元素的float、clear和vertical-align属性将失效。

display:flex;的元素称为 flex 容器, 它的所有子元素称为 flex项

flex-direction 决定项目排列方向, 水平/垂直 x 反向
flex-wrap 如果一条轴线放不下 那么如何换行
justify-content 定义了主轴上元素的对齐方式
align-items 定义了元素在交叉轴上的对齐方式
align-content 定义了多根轴线的对其方式, 应该只有出现 "换行" 的时候才会有多根轴线吧

flex 左右的是同高的
