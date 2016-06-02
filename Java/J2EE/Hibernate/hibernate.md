basic
hibernate默认情况下回忽略basic的fetch选项， 即都是EAGER
除非你启动了字节码增强
因此对于 byte[] 的话， 你是无法实现懒加载的， 即使你使用了Lob
你需要再来一个对象 然后使用 OneToOne
Hibernate ignores this setting for basic types unless you are using bytecode enhancement. See the <chapters/pc/BytecodeEnhancement.adoc#BytecodeEnhancement,BytecodeEnhancement>> for additional information on fetching and on bytecode enhancement.


column

枚举类型
按照 int 或 字符串保存
Enumerated(EnumType)


时间类型
Sql 包的 Date Time Timestamp
不需要额外的元数据支持

util包的 Date Calendar
需要额外的元数据支持 @Temporal




@Transient



主键生成策略
identity int递增
	Identity
table 用一个表来保存主键， 有点缺陷：
	1. 在每次 Context 中（相当于是一个Session中）， 他会先预加50， 然后再本次session中就可以分配主键了， 但是如果你每次session只分配了一个id、
	那么你最后的id是这样的 ： 1 , 51 ,101 有的人就不太能接受了
	2. 需要陪着TableGenerator使用 name table pkColumnName valueColumnName
	3. 1提到的 Context 或 Session 也可能是指一次事务， 有点忘记了

auto 自动选择
sequence 需要数据库支持
	SequenceGenerator






OneToOne
OneToMany
	如果一对多是单向的， 那么需要第三个表的支持
ManyToOne
	
ManyToMany
	需要一个连接表 JoinTable
	name = 表名
	joinColumns = 你自己的主键
	inverseJoinColumns = 对象的主键

OrderBy 用于List，Set 让元素具有某种顺序
Map 支持各种 MapKey
List 还支持 index 具体怎么做忘了



持有端
反响端
mappedBy 表示这个关系由对方来维护

一对一主键映射
就是两个实体的主键是完全一致的


class Student{
	@OneToOne(mapedBy="student")
	private Card card;
}


学生卡
class Card{
	@OneToOne
	@PrimaryKeyJoinColumn
	private Student student;
}

JoinColumn
用于 两个对象有关系 的时候， 指定哪个column用于外键(这个字段在jpa中北叫做join column)


标量集合关联
ElementCollection








启动 字节码增强
@org.hibernate.annotations.LazyGroup
当同一个组的任何一个属性被加载之后， 整个组的属性都被被加载。
<build>
    <plugins>
        [...]
        <plugin>
            <groupId>org.hibernate.orm.tooling</groupId>
            <artifactId>hibernate-enhance-maven-plugin</artifactId>
            <version>$currentHibernateVersion</version>
            <executions>
                <execution>
                    <configuration>
                        <failOnError>true</failOnError>
                        <enableLazyInitialization>true</enableLazyInitialization>
                        <enableDirtyTracking>true</enableDirtyTracking>
                        <enableAssociationManagement>true</enableAssociationManagement>
                    </configuration>
                    <goals>
                        <goal>enhance</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        [...]
    </plugins>
</build>


In-line dirty tracking
意识是 你的类会被增强， 是的它自己知道他的哪些属性发生了变化， 可能是通过拦截set方法做到的吧？ 但是集合呢？

懒加载
这里指的是 em.getReference() 或 Session.load
这常用于创建关联关系




PersistenceContext 容器会帮我们管理
默认的行为好像是如果当前有事务的话就返回他 如果没有的话就创建一个新 并且返回

扩展的实体管理器 与 有状态的会话bean一起使用
在他被remove掉之后 这个事务管理器才结束
P96


PersistenceUnit 可以注入emf


如果你确信你的id是存在的那么很多情况下可以使用getReference

DELETE语句不会级联操作！


# 10. Fetching #
静态
select
join
batch
subselect

动态
fetch profiles
HQL/JQPL
entity graphs

Collections.singletonMap()

如果涉及到枚举类型, 只需要使用 @MapKeyEnumerated 或 @Enumerated 即可
Key,Value是简单值
```
@ElementCollection()
@CollectionTable(name = "exam_ceshi_map", joinColumns = @JoinColumn(name = "exam_id"))
@Column(name = "ceshi_value")
@MapKeyColumn(name = "ceshi_key")
private Map<String, Integer> ceshi;
```

Key是实体, Value是简单值
```
@ElementCollection
@CollectionTable(name = "exam_results_exam_student_score", joinColumns = @JoinColumn(name = "exam_id"))
@Column(name = "score")
@MapKeyJoinColumn(name = "student_id") 与上面相比只有这里改变了
private Map<Student, Integer> scores;
```

Key是简单值, Value是实体
```
@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
@JoinTable(
        name = "phone_register",
        joinColumns = @JoinColumn(name = "phone_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id"))
@MapKey(name = "since") 这里意味着这个key是Phone的一部分
@MapKeyTemporal(TemporalType.TIMESTAMP)
private Map<Date, Phone> phoneRegister = new HashMap<>();

@JoinTable(name = "ceshi_map_m4", joinColumns = @JoinColumn(name = "ceshi_id"), inverseJoinColumns = @JoinColumn(name = "m4_value_teacher_id"))
@ManyToMany
@MapKeyColumn(name = "m4_key_rank")
private Map<Integer, Teacher> m4;

```

Key,Value是实体
```
@JoinTable(name = "ceshi_map_m5", joinColumns = @JoinColumn(name = "ceshi_id"), inverseJoinColumns = @JoinColumn(name = "m5_value_teacher_id"))
@ManyToMany
@MapKeyJoinColumn(name = "m5_key_student_id")
private Map<Student, Teacher> m5;
```


