package demo.jdk7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class NetworkDemo {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		// URLClassLoader
		//URLClassLoader.newInstance(new URL[] {}).close();

		//
		// create a class loader loading from "foo.jar"
		//
		URL url = new URL("file:foo.jar");
		URLClassLoader loader = new URLClassLoader(new URL[] { url });
		Class cl = Class.forName("Foo", true, loader);
		Runnable foo = (Runnable) cl.newInstance();
		foo.run();
		loader.close();

		// foo.jar gets updated somehow

		loader = new URLClassLoader(new URL[] { url });
		cl = Class.forName("Foo", true, loader);
		foo = (Runnable) cl.newInstance();
		// run the new implementation of Foo
		foo.run();
	}

}
