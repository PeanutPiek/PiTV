/**
 * 
 */
package de.gg.pi.tv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
	

	public static String prettyFormat(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e); // simple exception handling, please review it
	    }
	}
	
	
	public static void archiveFile(File f) {
		
		String p = f.getAbsolutePath();
		String path = p.substring(0, p.lastIndexOf("\\"));
		String filename = f.getName();
		String ext = filename.substring(filename.lastIndexOf('.'));
		String fwoe = filename.replace(ext, "");
		
		File flast = null;
		
		int i = 0;
		while(f.exists()) {
			flast = f;
			f = new File(path + "\\" + fwoe + "_" + i++ + ext);
		}
		
		try {
			
			if(flast.exists()) {
				String fstr = flast.getAbsolutePath();
				String content = Utils.loadExternalSource("File:\\" + fstr);
				
				if(f.createNewFile()) {
				
					FileWriter fw = new FileWriter(f);
					fw.write(content);
					fw.flush();
					fw.close();
				
				} else System.err.println("Can not create archived File.");
			}
			
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		
		
	}
	

	public static PrintStream createPrintStream(String logPath) throws IOException {
		File f = new File(logPath);
		if(!f.exists()) { 
			f.createNewFile();
		} else {
			archiveFile(f);
			if(f.delete()) {
				f.createNewFile();
			} else System.err.println("Unable to delete old Log File.");
			
		}
		PrintStream ps = new PrintStream(f);
		return ps;
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
