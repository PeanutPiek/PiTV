/**
 * 
 */
package de.gg.pi.tv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.io.IOUtils;

/**
 * @author PeanutPiek
 *
 */
public class Utils {
	
	/**
	 * 
	 */
	private static Utils _instance = null;
	
	/**
	 * 
	 */
	private Utils() { }
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String readFile(String file) throws IOException, FileNotFoundException {
		FileNotFoundException fileNotFound = new FileNotFoundException("File " + file + " does not exists!");
		ClassLoader classLoader = getClass().getClassLoader();
		URL fUrl = classLoader.getResource(file);
		if(fUrl==null) throw fileNotFound;
		File f = new File(fUrl.getFile());
		if(!f.exists()) throw fileNotFound;
		BufferedReader reader = new BufferedReader( new FileReader (f) );
		String line = null;
		StringBuilder sb = new StringBuilder();
		// Read File Content.
		while( ( line = reader.readLine() ) != null ) sb.append( line );
		// Return File Content.
		return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Utils getToolkit() {
		if(_instance==null) _instance = new Utils();
		return _instance; 
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String loadExternalSource(String source) {
		InputStream in = null;
		try {
			URL url = new URL(source);
			in = url.openStream();
			String ext = IOUtils.toString(in);
			return ext;			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in!=null)
				IOUtils.closeQuietly(in);
		}
		return null;
	}
	
}
