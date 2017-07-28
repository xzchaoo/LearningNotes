https://github.com/fronteed/icheck
http://icheck.fronteed.com/

icheck.js
导入皮肤css
```
$('input').iCheck({
	checkboxClass:'icheckbox_square-blue',
})
因为你使用了 icheckbox_square-blue 所以要导入下面的css
<link href="//cdn.bootcss.com/iCheck/1.0.2/skins/square/blue.css" rel="stylesheet">
```

有几种皮肤和颜色可以选

你的checkbox要这样放:
```
<label>
  <input type="checkbox" name="quux[1]" disabled>
  Foo
</label>

<label for="baz[1]">Bar</label>
<input type="radio" name="quux[2]" id="baz[1]" checked>

<label for="baz[2]">Bar</label>
<input type="radio" name="quux[2]" id="baz[2]">
```

会生成这样的结果
```
<label>
  <div class="icheckbox disabled">
    <input type="checkbox" name="quux[1]" disabled>
  </div>
  Foo
</label>

<label for="baz[1]">Bar</label>
<div class="iradio checked">
  <input type="radio" name="quux[2]" id="baz[1]" checked>
</div>

<label for="baz[2]">Bar</label>
<div class="iradio">
  <input type="radio" name="quux[2]" id="baz[2]">
</div>
```

