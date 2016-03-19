

核心是RestTemplate

支持gzip压缩
支持json,xml

ClientHttpRequestFactory

XxxForObject
XxxForEntity

基本上的格式是 接受一个 url,responseType,urlVariables

HttpMessageConverter用户转换跟MVC的时候一样

默认的转换器
http://docs.spring.io/spring-android/docs/1.0.1.RELEASE/reference/html/rest-template.html

Spring会根据contentType自动选择合适的转换器

# 各种验证方式 #

HTTP Basic Authentication

```java
// Set the username and password for creating a Basic Auth request
HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
HttpHeaders requestHeaders = new HttpHeaders();
requestHeaders.setAuthorization(authHeader);
HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

// Create a new RestTemplate instance
RestTemplate restTemplate = new RestTemplate();

// Add the String message converter
restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

try {
    // Make the HTTP GET request to the Basic Auth protected URL
    ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    return response.getBody();
} catch (HttpClientErrorException e) {
    Log.e(TAG, e.getLocalizedMessage(), e);
    // Handle 401 Unauthorized response
}
```