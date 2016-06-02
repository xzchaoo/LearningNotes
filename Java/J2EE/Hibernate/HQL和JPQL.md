http://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/Hibernate_User_Guide.html#hql

命名查询
基于 Criteria 的查询

```
@ElementCollection
@MapKeyEnumerated(EnumType.STRING)
private Map<AddressType, String> addresses = new HashMap<>();

@OneToMany(mappedBy = "phone")
@MapKey(name = "timestamp")
@MapKeyTemporal(TemporalType.TIMESTAMP )
private Map<Date, Call> callHistory = new HashMap<>();

@ElementCollection
private List<Date> repairTimestamps = new ArrayList<>(  );

@Enumerated(EnumType.STRING)
private PhoneType type;

```

