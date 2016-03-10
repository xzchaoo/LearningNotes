https://github.com/openresty/array-var-nginx-module

1. array_split
	1. array_split ',' $arg_names to=$names
	2. 将参数的names以,为分隔符拆成数组放到$names里
2. array_join
	1. array_join ',' $names
	2. 将$names自己join起来
3. array_map
	1. 对数组里每个元素都进行map
	2. array_map "[$array_it]" $names; 这个是原地的
	3. array_map "[$array_it]" $names to=$new_names; 保存到新变量中
4. array_map_op OP $array [to=$new_array]
	1. 对数组$array进行某种操作OP, 原地操作 或 将结果保存到新数组
	```
	set_quote_sql_str
	set_quote_pgsql_str
	set_quote_json_str
	set_unescape_uri
	set_escape_uri
	set_encode_base32
	set_decode_base32
	set_encode_base64
	set_decode_base64
	set_encode_hex
	set_decode_hex
	set_sha1
	set_md5
	```