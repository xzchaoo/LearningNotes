# 验证对象 #
当一个 ModelForm 的 is_valid 被调用的时候 就会调用其对应的model的 full_clean 方法, 这个方法负责下面的3个验证
1. clean_fields() clean每个字段
2. clean() 用于一些定制的clean逻辑
3. validate_unique() 处理数据库的唯一约束

ModelForm.save() 不会触发 full_clean 被调用 你需要自己保证

# 保存对象 #
保存之后id就会被自动填充

当你调用 save 的时候, 会:
1. pre-save 信号
2. pre-process the data 目前会在这里做 auto_now auto_now_add 的逻辑
3. prepare the data for the database 要求每个字段提供与数据库对应的类型
4. insert the data into the database 执行语句插入数据库
5. post-save 信号

## django是如何知道 insert 还是 update 的? ##
1. 如果你的模型有自增的主键, 那么
	1. 如果你的模型提供了id 那么就是update
	2. 如果没id 就是insert
2. 没有自增的主键, 那么必须提供id, 不过此时就不能根据id的有无来判断要 insert 还是 update 了, 这里应该只能先查询再决定了

可以通过save的参数强制执行要插入还是更新
