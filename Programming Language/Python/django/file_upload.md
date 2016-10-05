https://docs.djangoproject.com/en/1.10/topics/http/file-uploads/

FileField

request.FILES['file']

```
def handle_uploaded_file(f):
    with open('some/file/name.txt', 'wb+') as destination:
        for chunk in f.chunks():
            destination.write(chunk)
```