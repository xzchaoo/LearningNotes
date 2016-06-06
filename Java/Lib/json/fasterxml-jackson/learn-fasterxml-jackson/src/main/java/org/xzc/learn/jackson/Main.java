package org.xzc.learn.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.xzc.learn.jackson.entity.A1;
import org.xzc.learn.jackson.entity.Child1;
import org.xzc.learn.jackson.entity.Desc;
import org.xzc.learn.jackson.entity.Parent;
import org.xzc.learn.jackson.entity.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by xzchaoo on 2016/6/4 0004.
 */
public class Main {
	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		//构建json字符串
		ObjectNode o = om.createObjectNode();
		o.put("username", "xzchaoo");
		o.put("password", "70862045");
		o.putObject("status").put("code", 0).put("msg", "正常");
		System.out.println(o);

		//字符串to ObjectNode
		o = (ObjectNode) om.readTree("{\"username\":\"xzchaoo\",\"password\":\"70862045\",\"status\":{\"code\":0,\"msg\":\"正常\"}}\n");

		System.out.println("username" + o.get("username").asText());
		System.out.println("password" + o.get("password").asText());
		System.out.println("status.code" + o.findPath("status.msg").asText());
		System.out.println(o.path("status").path("msg").asText());

	}

	public static void main3(String[] args) throws IOException {
		ObjectMapper om = new ObjectMapper();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		JsonNode jn = om.readTree("{\"username\":\"xzc\",\"age\":202,\"a\":null}");
		System.out.println(jn.get("username").textValue());//必须严格是字符串
		System.out.println(jn.get("age").asText()); //可以转成字符串
		System.out.println(jn.get("a").getClass().getName());
		ObjectNode o = om.createObjectNode();
		o.put("name", "xzchaoo").put("age", 20);
		ObjectNode status = o.putObject("status");
		status.put("a", "a").put("b", "b");
		System.out.println(o);

		ObjectNode on = (ObjectNode) om.readTree("{\"name\":\"xzchaoo\",\"age\":20,\"status\":{\"a\":\"a\",\"b\":\"b\"}}");

	}

	public static void main1(String[] args) throws IOException {
		ObjectMapper om = new ObjectMapper();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Child1 c1 = new Child1();
		c1.setId(1);
		c1.setName("c1");
		c1.setP1("p1");
		String json = om.writeValueAsString(c1);
		System.out.println(json);
		Parent p = om.readValue(json, Parent.class);
		System.out.println(p.getClass().getName());
	}

	public static void main2(String[] args) throws IOException {
		User user = new User();
		user.setUsername("xzchaoo");
		user.setPassword("70862045");
		user.setBirthday(new Date());
		user.setCreateAt(new Date());
		user.setMoney(77);

		Desc desc = new Desc();
		desc.setContent("asdf");
		user.setDesc(desc);

		A1 a1 = new A1();
		a1.setA11("a11");
		a1.setA12("a12");
		a1.setA13("a13");
		user.setA1(a1);

		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.WRAP_ROOT_VALUE);
		ObjectWriter ow = om.writerWithView(User.Basic.class);

		String json = ow.writeValueAsString(user);
		System.out.println(json);
		user = om.readValue(json, User.class);
		System.out.println(user);

		json = "{\"username\":\"xzchaoo\",\"pa\":\"ceshi\",\"birthday\":70862045}";
		user = om.readValue(json, User.class);
		System.out.println(user);
		System.out.println(user.getOthers());
	}
}
