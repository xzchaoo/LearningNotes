package xzc.ant.task;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import com.google.gson.JsonObject;

public class XTask1 extends Task {
	private File file;

	public void execute() throws BuildException {
		JsonObject jo = new JsonObject();
		jo.addProperty( "name", "xzc" );
		jo.addProperty( "age", 20 );
		System.out.println( jo.toString() );
		System.out.println( file.getPath() );
	}

	public void setFile(File file) {
		this.file = file;
	}
}
