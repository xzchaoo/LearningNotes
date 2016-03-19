# Paint/画笔 #
颜色/透明度
画笔转弯处的处理
      paint.setStrokeJoin(Paint.Join.ROUND);   
      paint.setStrokeCap(Paint.Cap.ROUND);  
线宽
	citePaint.setStrokeWidth(1); 
模式(填充,描边,填充+描边)
	paint.setStyle(Style.STROKE); 
抗锯齿
	paint.setAntiAlias(true);
文本大小
	citePaint.setTextSize(14);  
可以获得字体的各种高度指标
拆分字符串

ColorFilter

# Canvas #
arc
像素
图片
圆
线 多边形 椭圆
路径
图片
文字
矩形
圆角矩形

旋转 缩放 平移 扭曲
矩阵

# Path #
    Path path = new Path(); //定义一条路径   
    path.moveTo(10, 10); //移动到 坐标10,10   
    path.lineTo(50, 60);   
    path.lineTo(200,80);   
    path.lineTo(10, 10);   
                             



# Bitmap #
创建 复制 压缩 缩放
与Buffer的操作

## Bitmap.Config ##
密度

# BitmapFactory #
将byte[]/文件/流转成bitmap
## BitmapFactory.Options ##
	解码时候的选项
	设置密度 期望的大小