https://docs.djangoproject.com/en/1.10/ref/contrib/messages/

```
def m2(request):
    msg = request.GET.get('msg', '')
    if msg != '':
        messages.info(request, msg)
        messages.warning(request, msg)
    return JsonResponse({'msg': msg})


def m1(request):
    return render(request, 'a2/m1.html', {'messages': messages.get_messages(request)})

```

