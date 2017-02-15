语义和所有支持yield的语言一样

```
public static IEnumerable<int> f1()
{
    for (int i = 0; i < 10; ++i)
    {
        yield return i;
    }
}

static void Main(string[] args)
{
    foreach (var i in f1())
    {
        Console.Out.WriteLine(i);
    }
}
```
