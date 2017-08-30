package cnrs.rhapsodie.treebankbrowser.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import cnrs.rhapsodie.treebankbrowser.MainApp;

public class ResourcesFromJar {
	
	final String path = "resources/interface-statique";
	final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

	
	public ResourcesFromJar(){
		System.out.println("The jar file is : "+jarFile);
		try {
			System.out.println( URLDecoder.decode(jarFile.getAbsolutePath(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	public void get() throws Exception{
//		if(jarFile.isFile()) {  // Run with JAR file
		    final JarFile jar = new JarFile(URLDecoder.decode(jarFile.getAbsolutePath(), "UTF-8"));
		    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
		    List<JarEntry> entriesList = Collections.list(entries);
//		    while(entries.hasMoreElements()) {
//		    System.out.println("entries : " + entriesList.size());
//		    System.exit(0);
		    for(JarEntry entry : entriesList){
		        final String name = entry.getName();
		        if (name.startsWith(path + "/")) { //filter according to the path
		            String nameRelative = name.replace("resources"+File.separator+"interface-statique", "");
		            if(name.endsWith("/")){
		            	createTree("generatedUI//"+nameRelative);
		            }else if(FilenameUtils.getExtension(name).equals("jpg") || FilenameUtils.getExtension(name).equals("png")
		            		|| FilenameUtils.getExtension(name).equals("gif") || FilenameUtils.getExtension(name).equals("otf")
		            		|| FilenameUtils.getExtension(name).equals("ttf")){
		            	ExportResource("/"+name);
		            }
		            else{
		            	Tools tool = new Tools();
		            	String str = tool.accessRessourceFile("/"+name);
		            	Tools.ecrire("generatedUI"+nameRelative, str);
		            	
		            }
		        }
		    }
		    jar.close();
		    
//		} else { // Run with IDE
//		    final URL url = MainApp.class.getResource("/" + path);
//		    if (url != null) {
//		        try {
//		            final File apps = new File(url.toURI());
//		            for (File app : apps.listFiles()) {
//		                System.out.println(app);
//		            }
//		        } catch (URISyntaxException ex) {
//		            // never happens
//		        }
//		    }
//		    System.out.println("should close");
//		    
//		}
	}
	
	/**
	 * Create all the directories in the path if they do not exist, but delete the dir
	 * @param absolutePath
	 * @throws IOException
	 */
	public static void createTree(String absolutePath) throws IOException{
		if((FilenameUtils.getExtension(absolutePath).length()) != 0){
			System.out.println("you specified a file path, not a path of directories !");
			System.exit(0);
		}

		absolutePath = FilenameUtils.separatorsToUnix(absolutePath);
		absolutePath = FilenameUtils.normalizeNoEndSeparator(absolutePath);
		String[] directories = absolutePath.split("/");
		String path = "";
		//ArrayUtils.reverse(directories);
		for(int i = 0; i < directories.length; i++){
			path = "";
			int k = 0;
			while(!(k > i)){
				path += "/"+directories[k];
				k++;
			}
			System.out.println(path);
			Tools.createDir(path.replaceFirst("/", ""));
			
		}
		
		
	}
	
	public BufferedImage loadImage(String fileName){

	    BufferedImage buff = null;
	    try {
	        buff = ImageIO.read(MainApp.class.getResourceAsStream(fileName));
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return null;
	    }
	    return buff;

	}
	
	/**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource
     * @throws Exception
     */
    public String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = MainApp.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
//            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            System.out.println("jarFolder : " + jarFolder + " resourceName : " + resourceName);
            resStreamOut = new FileOutputStream( resourceName.replaceFirst("/resources/interface-statique", "generatedUI"));
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }
}
