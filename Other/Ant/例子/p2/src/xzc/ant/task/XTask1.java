package xzc.ant.task;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class XTask1 extends Task {
	private File file;

	public void execute() throws BuildException {
		System.out.println( file.getPath() );
	}

	public void setFile(File file) {
		this.file = file;
	}
}
