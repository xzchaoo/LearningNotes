http://react-s-alert.jsdemo.be/
https://github.com/juliancwirko/react-s-alert
alert的效果 很好

配置项
position: 'top-right' 表示右上角, bottom-left 表示左下
effect: 特效 'scale'一旦使用特效就要导入相应的css 还有其他的特效
onShow: function, 这是一个回调
beep: false
timeout: 'none' 超过这个时间自动关闭
offset: 100


msg可以是字符串, 或一个组件
info(msg,cfg)
error(msg,cfg)
closeAll(); 关闭所有

全局配置, 就是配置你的那个 Alert 元素
<Alert stack={{limit: 3, spacing:50}} html={true} />
stack用于控制当有多个 alert 的时候, 他们是否堆叠起来
用true表示要堆叠起来, 并且个数无限
如果是false 则表示不堆叠, 这样所有的alert会重叠在一起

如果stack是一个对象, 则 limit表示屏幕上最多显示3个 alert space 表示了他们的位置



effect 控制特效, 使用哪些就需要导入哪些的css
slide
scale
bouncyflip
flip
genie
jelly
stackslide

position 用于控制弹出的位置
top (full width)
bottom (full width)
top-right
top-left
bottom-right
bottom-left

timeout 超过时间自动关闭, 毫秒
'noen' 表示不自动关闭 等待手动关闭

html 控制内容是否要转义

offset 控制alert的偏移量, 如果你是放在 top 位置(只要是属于"上"的位置), 那么top就会+上这个值

beep 可以控制播放一个音乐...

onShow/onClose 回调

customFields 自定义字段

contentTemplate 可以自定义控制弹出的内容, 通常和 customFields 配合使用
官方又个例子可以去看看
