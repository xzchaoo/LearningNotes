# 可以不带分号
-- 注释

--[[
多行注解
--]]

function f1(n)
	if n == 0 then
		return 1
	else
		return n*f1(n-1)
	end
end

function mysqrt(x)
	return math.sqrt(x)
end

x=mysqrt(16)

-- print('enter a number: ')
-- io.read('*number')

a=x

print (f1(a))
print (arg[0])

c=2
function f2()
	local c=c+2
	return 1,2,c
end
a,b,d=f2()
print(a,b,c,d)

a={x=1,y=2,z=3,4,5,6}
for i,v in pairs(a) do
	print(i,v)
end
function ceshi()
	local a=4;
	a=3
end
print(ceshi())
print(1,ceshi(),2)
print(1,0,2)