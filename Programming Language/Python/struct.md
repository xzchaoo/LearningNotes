# 功能 #
1. 用于处理基本数据类型与字节数组的转换
2. 转换的同时可以形成一定的格式, 这在根据某些用户自定义的协议进行网络传输的时候很有用


```
import socket, time, schedule, struct, json

s = socket.socket()
s.connect(('livecmt-1.bilibili.com', 788))

data1 = json.dumps({'uid': 26017449, 'roomid': 27086}).encode('utf-8')

s.sendall(struct.pack('!ihhii', 16 + len(data1), 16, 1, 7, 1))
s.sendall(data1)

while True:
    headData = s.recv(16)
    totalLength, headLength, version, type, other = struct.unpack('!ihhii', headData)
    print(totalLength, headLength, version, type, other)
s.close()

while True:
    schedule.run_pending()
    time.sleep(1)
```