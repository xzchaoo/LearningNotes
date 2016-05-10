function add(a, b)
	return a+b;
end

local a = 1
local b = 2

a, b = string.find('xzchaoo','aoo')
print (a, b)

function p2(...)
	for i,v in ipairs{...} do --注意这里并没有写错 是用花括号!
		print (i .. v)
	end
end

p2(1,2,3)

print (unpack({1,2,3}))

print (string.format('%d+%d=%d',1,2,3))

function printTable(a)
	for i,v in ipairs(a) do
		print(i .. '=' .. v.username)
	end
end

a={{username='b'},{username='a'}}
printTable(a)
table.sort(a,function(a,b) return a.username<b.username end)
printTable(a)

local a = loadstring('print("asdf")')
--error('a')

