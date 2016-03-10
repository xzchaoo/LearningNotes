package gson;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Test;

import bean.Person;
import bean.Shabi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

//GSON常见简单操作
public class TestGSON {
	// Shabi是一个普通javabean
	// Person是一个普通javabean再加上一个shabi
	@Test
	public void test1() {
		// 根据对象构造json表达式
		//Gson g = new GsonBuilder().setVersion( 1.0 ).create();
		
		Gson g=new Gson();
		Person p = new Person();
		p.setId222( 1 );
		p.setName( "xzc" );
		p.setAge( 20 );
		p.setItems( Arrays.asList( "1", "22", "333", "4444" ) );
		Shabi sb = new Shabi();
		sb.setId( 2 );
		sb.setName( "sb2" );
		sb.setCeshi( "ceshi```123" );
		p.setShabi( sb );
		String js = g.toJson( p );
		System.out.println( js );// 构造后的表达式
	}

	// 从字符串转回对象
	@Test
	public void test2() throws Exception {
		Gson g = new Gson();
		Person p = new Person();
		p.setId222( 1 );
		p.setName( "xzc" );
		p.setAge( 20 );
		p.setItems( Arrays.asList( "1", "22", "333", "4444" ) );
		Shabi sb = new Shabi();
		sb.setId( 2 );
		sb.setName( "sb2" );
		sb.setCeshi( "ceshi```123" );
		p.setShabi( sb );
		String js = g.toJson( p );
		p = g.fromJson( js, Person.class );
		System.out.println( p.toString() );
		System.out.println( p.getShabi().getName() );
	}

	// 数组
	@Test
	public void test3() throws Exception {
		// 根据json构造对象 假设没有json对应的类
		Gson g = new Gson();
		JsonArray a = new JsonArray();
		a.add( new JsonPrimitive( "2273" ) );
		System.out.println( a.toString() );
		System.out.println( g.toJson( a ) );
	}

	@Test
	public void testJSONObject() {
		JsonObject jo = new JsonObject();
		jo.addProperty( "name", "xzc" );
		System.out.println( jo.get( "name" ).getAsString() );
		jo.addProperty( "name", "xzc2" );
		System.out.println( jo.get( "name" ).getAsString() );
		jo.add( "name", JsonNull.INSTANCE );
		//System.out.println( jo.get( "name" ).getAsString() );
	}
	
	@Test
	public void testArray(){
		JsonArray ja=new JsonArray();
		ja.add( new JsonPrimitive( "123" ) );
		ja.add( new JsonPrimitive( 7758 ) );
		System.out.println(ja.toString());
	}
	
	@Test
	public void testWriter() throws IOException{
		StringWriter sw=new StringWriter();
		JsonWriter jw=new JsonWriter( sw );
		jw.setHtmlSafe( true );
		jw.beginArray();
		
		jw.beginObject();
		jw.name("name").value( "<script>ceshio</script>" );
		jw.name( "age" ).value( 20 );
		jw.endObject();
		
		jw.endArray();
		
		jw.close();
		sw.close();
		System.out.println(sw.toString());
	}
}
