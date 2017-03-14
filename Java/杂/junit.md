# 断言 #
org.junit.Assert的静态方法
org.hamcrest.CoreMatchers的静态方法

# 假设 #
只有在前提满足的情况下才会进行测试
```
import static org.junit.Assume.*
@Test public void filenameIncludesUsername() {
    assumeThat(File.separatorChar, is('/'));
    assertThat(new User("optimus").configFileName(), is("configfiles/optimus.cfg"));
}
```


# 聚合测试 #
@RunWith(Suite.class)
@SuiteClasses(TestClass1.class, ...)

# 测试 #
@Test(expected=期待的异常, timeout=毫秒)

# Rules #
需要用 @Rule 或 @ClassRule 注解
两者表示作用范围的不同 @Rule 对于每个测试, @ClassRule 对于该类里的所有测试
和@Before 喝 @BeforeClass 可以类比

https://github.com/junit-team/junit4/wiki/Rules

@Rule
public final TemporaryFolder tempFolder = new TemporaryFolder();

@Rule
public final ExpectedException exception = ExpectedException.none();

TemporaryFolder
用于创建在测试结束之后自动删除的目录

ExternalResource  用于在测试开始之前启动一个外部资源, 测试结束之后关闭, 比如socket

ErrorCollector 用于记录测试过程中的错误, 而不是遇到第一个错误(未捕获的异常)就直接结束了

Verifier
TestWatcher 推荐, 用于监听测试的结果
TestWatchman 废弃
TestName 其 getMethodName返回值是当前方法的名字
Timeout 设置超时时间
ExpectedException 抛出异常的情况

RuleChain

自定义Rule, 实现 TestRule 接口


# 参数 #
https://github.com/junit-team/junit4/wiki/Parameterized-tests

# 忽略 #
@Ignore

# Runner #
@RunWith

@Suite
@Parameterized
@Categories
也是特殊的Runner

# Category #
```
public interface FastTests { /* category marker */ }
public interface SlowTests { /* category marker */ }

public class A {
  @Test
  public void a() {
    fail();
  }

  @Category(SlowTests.class)
  @Test
  public void b() {
  }
}

@Category({SlowTests.class, FastTests.class})
public class B {
  @Test
  public void c() {

  }
}

```

```
@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
  // Will run A.b and B.c, but not A.a
}
```

```
@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
  // Will run A.b, but not A.a or B.c
}
```

于 maven-surefire-plugin 插件配合使用, 可以导出报告

# Test fixtures #
用于在每次测试的时候重置一些东西 以保证测试的环境是正确的
其实就是
Before After BeforeClass AfterClass 的使用

# 执行顺序 #
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

多线程时用的辅助库 https://github.com/jhalterman/concurrentunit
