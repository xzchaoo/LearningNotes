md5
sha1
sha128
sha256

```
import hashlib

md5 = hashlib.md5()
md5.update('admin'.encode('utf-8'))
print(md5.hexdigest())

```

