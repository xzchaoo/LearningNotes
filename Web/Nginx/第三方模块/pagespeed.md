# 简介 #

# 安装 #

# 配置 #
默认情况下只会对Vary: Accept-Encoding(无法关闭)做检测
可以启动
pagespeed RespectVary on;
对所有Vary做检测

默认情况下不会对带有Cache-Control: no-transform的响应做处理
pagespeed DisableRewriteOnNoTransform off;

默认情况下会对text/html 的标签进行小写
pagespeed LowercaseHtmlNames on;


pagespeed ModifyCachingHeaders off;
建议是打开?

pagespeed PreserveUrlRelativity on;


# 管理员配置 #
1. 在server里放一个: ``pagespeed AdminPath /admin`, 此后放问该server的/admin的时候就会出现admin的控制页面
2. 由于是admin的控制页面, 所以你可能需要 allow ip;或 deny all;一下来保证安全
3. admin页面里集合了所有的面板
4. 你也可以单独把一个面板分配到某个url
pagespeed AdminDomains Disallow *; 不允许所有人访问
pagespeed AdminDomains Allow admin.example.com; 只允许以这个域名访问
5. 默认情况下, 数据统计是针对所有的虚拟主机的, 可以通过``pagespeed UsePerVhostStatistics on;(放在http里)``使得统计只针对当前你访问的域名对应的主机
6. pagespeed MessageBufferSize 100000; 用于配置消息buffer大大小

# 配置过滤器 #
## rewrite ##
过滤器有3个等级: PassThrough CoreFilters OptimizeForBandwidth
1. CoreFilters 包含了一些对于大部分站点来说都是安全的过滤器, 这是默认等级.
2. OptimizeForBandwidth 提供了更强的安全保证

切换等级
pagespeed RewriteLevel PassThrough;

CoreFilters 包含如下的过滤器
```
add_head
combine_css
combine_javascript
convert_meta_tags
extend_cache
fallback_rewrite_css_urls
flatten_css_imports
inline_css
inline_import_to_link
inline_javascript
rewrite_css
rewrite_images
rewrite_javascript
rewrite_style_attributes_with_url
```

pagespeed DisableFilters rewrite_images,combine_css;

To turn off specific filters and forbid them from being turned on by query parameters, request headers, or in a location-specific configuration section, specify (for example):
pagespeed ForbidFilters rewrite_css,rewrite_javascript;

pagespeed EnableFilters combine_css,extend_cache,rewrite_images;
pagespeed EnableFilters rewrite_css,rewrite_javascript;


pagespeed ForbidAllDisabledFilters true;


pagespeed EnableFilters add_head;
如果页面不存在<head>就自动加上



pagespeed on;
启动ps功能

pagespeed FileCachePath /var/ngx_pagespeed_cache;
指定ps的缓存路径

pagespeed RespectVary on;
是否针对不同的Vary进行优化, 如果是这样的话, 可能会有多个缓存的版本.
Vary: Accept-Encoding是肯定会做优化的

pagespeed DisableRewriteOnNoTransform off;
当有 Cache-Control: no-transform 的时候, 是否要禁用优化?

pagespeed LowercaseHtmlNames on;
是否要小写html的标签名?

pagespeed ModifyCachingHeaders on;
默认情况下, ps会修改返回的CacheControl使得它为 Cache-Control: no-cache, max-age=0, 是否要允许这个行为?


默认情况下, ps会将它自己安装在所有的server块上,
可以在http上写一些配置, 然后就会被继承下来

默认情况下, 当你使用了一些关于图片的优化, ps会在html页面里嵌入一些代码, 这些代码会通过post方法, 发送回一些回馈信息, 你可以禁用这一行为
pagespeed CriticalImagesBeaconEnabled false;

默认情况下, 当你使用了一些关于图片/js/css的优化时, 相应的元素(比如img元素)的src会发生变化
Enabling ImagePreserveURLs will forbid the use of the following filters: inline_preview_images, lazyload_images, extend_cache_images, inline_images, and sprite_images.
Enabling CssPreserveURLs will forbid the use of the following filters: combine_css, extend_cache_css, inline_css, inline_import_to_link, and outline_css.
Enabling JsPreserveURLs will forbid the use of the following filters: canonicalize_javascript_libraries, combine_javascript, defer_javascript, extend_cache_javascript, inline_javascript, and outline_javascript.

但这并不意味着对于图片的优化就无效了, 部分对于图片的优化可以与原图片的地址相同: 比如我请求了一张大小为500的图片 /1.jpg, 第一次访问, ps会根据情况在后台开启一个进程对该图片进行压缩, 如果这个操作能在10ms内完成, 那么就使用压缩后的图片, 如果无法在10ms完成, 那么就直接返回原始的图片, 然后过了一会儿, 这个图片被压缩完毕了, 下一次我再请求 /1.jpg, ps就会发现图片已经被压缩完毕了, 因此返回的是压缩后的版本.

因为对资源进行处理是需要耗费比较多的CPU的, 因为ps有一个策略是 处理最常备访问的元素, 而可能忽略比较少访问的元素: 通过随机放弃比较昂贵的处理操作(比如css和image的操作). 总之经常被访问的元素的处理优先级比较
高. 优先级比较低的元素可能只被优化了一部分或完全没有优化过.

pagespeed RewriteRandomDropPercentage Percent;
目前只有css和image支持.
当你访问一个没有处理过的元素的时候, 它有多少的概率会被放弃优化处理.
比如设置成90, 那么如果你访问同一个元素50次, 那么它被处理的概率就是1-0.9^50
需要手动找到一个平衡点
在css里引用的图片不会被随机丢弃

OptimizeForBandwidth 等级不会对html代码进行修改, 它只会对css和js文件进行压缩, 对图片进行原地压缩转码.

pagespeed Allow type/html;



pagespeed AvoidRenamingIntrospectiveJavascript off;

pagespeed MaxCacheableContentLength -1;
限制要优化的最大的文件大小



collapse_whitespace
折叠空白

combine_css
合并css文件

pagespeed MaxCombinedCssBytes MaxBytes;
限制合并后的大小

combine_javascript
折叠js

pagespeed MaxCombinedJsBytes MaxBytes;

combine_heads
将多个header合成一个
一般来说不应该有多个头的

convert_meta_tags

dedup_inlined_images
如果有两个img, 他们的图片是一样的话, 那么就会进行优化
但要求每张相同的图片都要带id

defer_javascript
将js文件推迟到页面加载完毕再加载
不建议用

elide_attributes
移除某些标签的某些属性

extend_cache

https://developers.google.com/speed/pagespeed/module/
有好几个章节没有理解...

