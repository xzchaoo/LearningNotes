在21.16 Configuring Spring MVC里可以找到由<mvc:annotation-driven/>所添加的默认的转换器

FormattingConversionServiceFactoryBean

```xml
<mvc:annotation-driven>
<mvc:message-converters>
<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
<property name="objectMapper" ref="objectMapper" />
</bean>
<bean class="org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter">
<property name="objectMapper" ref="xmlMapper" />
</bean>
</mvc:message-converters>
</mvc:annotation-driven>
<bean id="objectMapper" class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean"
p:indentOutput="true"
p:simpleDateFormat="yyyy-MM-dd"
p:modulesToInstall="com.fasterxml.jackson.module.paramnames.ParameterNamesModule" />
<bean id="xmlMapper" parent="objectMapper" p:createXmlMapper="true" />
```