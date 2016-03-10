function myIter()
	local n=10
	return function()
		if n > 0 then
			local nn=n;
			n=n-1;
			return nn;
		else
			return nil;
		end
	end
end

for i in myIter() do
	print(i)
end

io.write('ces','fdf','eee','\r\n')