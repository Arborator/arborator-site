package cnrs.rhapsodie.treebankbrowser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FilenameUtils;

import cnrs.rhapsodie.treebankbrowser.util.OsValidator;
import cnrs.rhapsodie.treebankbrowser.utils.Corpus;
import cnrs.rhapsodie.treebankbrowser.utils.ResourcesFromJar;
import cnrs.rhapsodie.treebankbrowser.utils.Tools;

/**
 * Class which generate the static web interface and launch it on the default browser
 * (Chrome is recommanded for easy animations)
 * @author Gaël Guibon
 *
 */
public class StaticGenerator {
	static String emptySample = "";
	static String title = "Super corpus";
	static String subtitle = "ceci est un sous-titre";
	static String baseDirCrea = "generatedUI";
	static String currentDir = System.getProperty("user.dir");
	static String rawDir = "";
	static String licenceTitle = "A very confined licence !";
	static String licenceHtml = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
			+ "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>";
	static String projectTitle = "My superb project !";
	static String projectHtml = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
			+ "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>";
	
	final static Pattern pattern = Pattern.compile("<h2 id=\"samplename\">(.+?)</h2>");
	private static HashMap<String, List<String>> mapDirsContents = new HashMap<String, List<String>>();
	private static HashMap<String, List<String[]>> mapDirsContentsLocation = new HashMap<String, List<String[]>>();

	public void generateStaticInterface(String title, String subtitle, 
			String projectTitle, String projectHtml, String licenceTitle, String licenceHtml
			, String rawDir) throws Exception{
		this.title = title;
		this.subtitle = subtitle;
		this.projectTitle = projectTitle;
		this.projectHtml = projectHtml;
		this.licenceTitle = licenceTitle;
		this.licenceHtml = licenceHtml;
		
		this.rawDir = rawDir;
		
		mapDirsContents.clear();
		mapDirsContentsLocation.clear();
		
		insertMetaInfo();
//		creaHtmlTreesRhapsodie(this.rawDir);
		creaHtmlTreesRhapsodieDirs(this.rawDir);
//		adaptSampleView();
		adaptSampleViewWithDirs();
		File index = new File(baseDirCrea+ File.separatorChar +"index.html");
		openIt(index.getAbsolutePath());
//		((JavascriptExecutor)this.webDriver).executeScript("alert('Test')");
//		this.webDriver.switchTo().alert().accept();
	}

	
	public void dir2svg(String title, String subtitle, 
			String projectTitle, String projectHtml, String licenceTitle, String licenceHtml
			, String rawDir) throws Exception{
		this.title = title;
		this.subtitle = subtitle;
		this.projectTitle = projectTitle;
		this.projectHtml = projectHtml;
		this.licenceTitle = licenceTitle;
		this.licenceHtml = licenceHtml;
		
		this.rawDir = rawDir;
		
		mapDirsContents.clear();
		mapDirsContentsLocation.clear();
		
		insertMetaInfo();
		creaHtmlGlobalTreeWithAllConlls(this.rawDir);
//		adaptSampleViewWithDirs();
//		File index = new File(baseDirCrea+ File.separatorChar +"index.html");
//		openIt(index.getAbsolutePath());
	}
	
//	private  void creaHtmlTrees(String dirPath) throws Exception {
//		List<String> listDirs = Tools.listDirs(dirPath);
//		int sampleIndex = 1;
//		for(String dir : listDirs){
//			//initiate each raw dir
//			File dirOld = new File(String.format("%s%s%s%s%s", dirPath, File.separatorChar, dir, File.separatorChar, "old" ));
//			File dirNew = new File(String.format("%s%s%s%s%s", dirPath, File.separatorChar, dir, File.separatorChar, "new" ));
//			// get sample informations sample.info
//			HashMap<String, String> sampleInfos = getSampleInfos(String.format("%s%s%s%s%s", dirPath, File.separatorChar, dir
//					, File.separatorChar, "sample.info"));
//			// get content of files in each dir
//			List<String> oldPaths = Tools.dir2listepaths(dirOld.getAbsolutePath());
//			List<String> newPaths = Tools.dir2listepaths(dirNew.getAbsolutePath());
//			StringBuilder sbOld = new StringBuilder();
//			StringBuilder sbNew = new StringBuilder();
//			for(String path : oldPaths) sbOld.append( Tools.readFile(path) + "\n" );
//			for(String path : newPaths) sbNew.append( Tools.readFile(path) + "\n");
//			// create the paths dirs if none
//			File dirFile = new File ( String.format("%s%s%s%s%s", baseDirCrea, File.separatorChar
//					, "samples", File.separatorChar, "sample"+sampleIndex ) );
//			dirFile.mkdirs();
//			
//			// merge the html contents for each tree html
////			String treesModel = Tools.readFile( String.format("%s%s%s%s%s", 
////					StaticGenerator.class.getResource("/resources/interface-statique").toExternalForm()
//////					baseDir
////					, File.separatorChar, "samples", File.separatorChar , "treesModel.html" ) );
////			String treesModel = StaticGenerator.class.getResourceAsStream("/resources/interface-statique/samples/treesModel.html");
//			Tools tool = new Tools();
////			String treesModel = tool.accessRessourceFile("/resources/interface-statique/samples/treesModel.html");
//			String treesModel = tool.accessRessourceFile(String.format("%s%s%s%s%s%s%s%s", File.separatorChar
//					, "resources", File.separatorChar, "interface-statique",
//					File.separatorChar, "samples" , File.separatorChar, "treesModel.html"));
//			List<String> sentOld = Corpus.getSentences(Tools.tempFile("old", ".txt", sbOld.toString()));
//			StringBuilder sbOldMerged = new StringBuilder();
//			for (String sent : sentOld){
//				sbOldMerged.append(String.format("%s%s%s\n", "<conll>", 
//					Tools.replaceLastOccurrence(sent, "\n", "") , "</conll>") );
//			}
//			List<String> sentNew = Corpus.getSentences(Tools.tempFile("new", ".txt", sbNew.toString()));
//			StringBuilder sbNewMerged = new StringBuilder();
//			for (String sent : sentNew){
//				sbNewMerged.append(String.format("%s%s%s\n", "<conll>",
//						Tools.replaceLastOccurrence(sent, "\n", "") , "</conll>") );
//			}
//			// write the conlls inside the treesModel and create the files
//			Tools.ecrire( String.format("%s%s%s", dirFile.getAbsolutePath(), File.separatorChar
//					, "oldTrees.html") , treesModel.replace("{conlls}", sbOldMerged)
//								.replace("{samplename}", sampleInfos.get("sample name")));
//			Tools.ecrire( String.format("%s%s%s", dirFile.getAbsolutePath(), File.separatorChar
//					, "trees.html") , treesModel.replace("{conlls}", sbNewMerged)
//								.replace("{samplename}", sampleInfos.get("sample name")));
//			
//			sampleIndex++;
//		}
//	}
//	
//	
//	private  void creaHtmlTreesRhapsodie(String dirPath) throws Exception {
//		List<String> listFiles = new ArrayList<String>();
//		Tools.listFilesAndSubfiles(dirPath, listFiles);
//		int sampleIndex = 1;
//		for (String filePath : listFiles){
//			
//			String extension = FilenameUtils.getExtension(filePath);
//			String filename = FilenameUtils.getName(filePath);
//			String filebasename = FilenameUtils.getBaseName(filePath);
//			
//			// get the sample name from the file name
//			HashMap<String, String> sampleInfos = new HashMap<String, String>();
//			
//			System.out.println("PATH " + File.separator + dirPath + File.separator  + filename);
//			
//			if(filePath.equals(dirPath + File.separator  + filename)){
//				System.out.println("equal");
//				sampleInfos.put("sample name", filename);
//			}else{
//				System.out.println("not equal");
//				String tree = filePath.replace(dirPath+File.separator, "|");
//				tree = tree.replaceAll("/", "<br/>|-----");
//				sampleInfos.put("sample name", tree);
//			}
//			
//			StringBuilder sbOld = new StringBuilder();
//			StringBuilder sbNew = new StringBuilder();
//			
//			
//			String oldVersion = filePath.replace(filename, filebasename)+"_old."+extension;
//			
//			if(Tools.dirFileExists(oldVersion)) sbOld.append( Tools.readFile(oldVersion) + "\n" );
//			sbNew.append( Tools.readFile(filePath) + "\n");
//			
//			// create the paths dirs if none
//			File dirFile = new File(String.format("%s%s%s%s%s", baseDirCrea, File.separatorChar, "samples",
//					File.separatorChar, "sample" + sampleIndex));
//			dirFile.mkdirs();
//			
//			Tools tool = new Tools();
//			String treesModel = tool.accessRessourceFile(String.format("%s%s%s%s%s%s%s%s", File.separatorChar
//					, "resources", File.separatorChar, "interface-statique",
//					File.separatorChar, "samples" , File.separatorChar, "treesModel.html"));
//			
//			if(sbOld.toString().length() !=0){
//				List<String> sentOld = Corpus.getSentences(Tools.tempFile("old", ".txt", sbOld.toString()));
//				StringBuilder sbOldMerged = new StringBuilder();
//				for (String sent : sentOld){
//					sbOldMerged.append(String.format("%s%s%s\n", "<conll>", 
//						Tools.replaceLastOccurrence(sent, "\n", "") , "</conll>") );
//				}
//				Tools.ecrire(String.format("%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, "oldTrees.html"),
//						treesModel.replace("{conlls}", sbOldMerged).replace("{samplename}",
//								sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name")) );
//
//			}
//			
//			List<String> sentNew = Corpus.getSentences(Tools.tempFile("new", ".txt", sbNew.toString()));
//			StringBuilder sbNewMerged = new StringBuilder();
//			for (String sent : sentNew){
//				sbNewMerged.append(String.format("%s%s%s\n", "<conll>",
//						Tools.replaceLastOccurrence(sent, "\n", "") , "</conll>") );
//			}
//			
//			
//			// write the conlls inside the treesModel and create the files
//			
//			Tools.ecrire(String.format("%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, "trees.html"),
//					treesModel.replace("{conlls}", sbNewMerged).replace("{samplename}",
//							sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name")) );
//			
//			sampleIndex++;
//		}
//		
//		
//		
//	}
	
	private  void creaHtmlTreesRhapsodieDirs(String dirPath) throws Exception {
		
		mapDirsContents = Tools.dir2map(dirPath);
		
		int sampleIndex = 1;
		Iterator it = mapDirsContents.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
//	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	        
	        
	        List<String> listFiles = (List<String>) pair.getValue();
	        List<String[]> listFilesLocation = new ArrayList<String[]>();
	        int i = 0;
	        for (String filePath : listFiles){
	        	String extension = FilenameUtils.getExtension(filePath);
				String filename = FilenameUtils.getName(filePath);
				String filebasename = FilenameUtils.getBaseName(filePath);
				String filePathNoName = FilenameUtils.getPath(filePath);
				
				// get the sample name from the file name
				HashMap<String, String> sampleInfos = new HashMap<String, String>();
				
				boolean isFile ;
				if(new File(filePath).isDirectory()){
					sampleInfos.put("sample name", filename);
					sampleInfos.put("type", "directory");
					isFile = false;
				}else{
					sampleInfos.put("sample name", filename);
					sampleInfos.put("type", "file");
					isFile = true;
				}
				
				
				// create the paths dirs if none
				String p = String.format("%s%s%s%s%s", baseDirCrea, File.separatorChar, "samples",
						File.separatorChar, "sample" + sampleIndex);
//						File.separatorChar, "files");
				File dirFile = new File(p);
				dirFile.mkdirs();
				
				if(isFile){
					// get conll content into a sb
					StringBuilder sbNew = new StringBuilder();
					sbNew.append( Tools.readFile(filePath));
					
					// get the treesModel content
					Tools tool = new Tools();
					String treesModel = tool.accessRessourceFile(String.format("%s%s%s%s%s%s%s%s", File.separatorChar
							, "resources", File.separatorChar, "interface-statique",
							File.separatorChar, "samples" , File.separatorChar, "treesModel.html"));
					
					
					// convert conll content to list of conll sentences
					List<String> sentNew = Corpus.getSentences(Tools.tempFile("new", ".txt", sbNew.toString()));
					// merge the tree model with the conll sentences into <conll> tag
					StringBuilder sbNewMerged = new StringBuilder();
					for (String sent : sentNew){
						sbNewMerged.append(String.format("%s%s%s\n", "<conll>",
								Tools.replaceLastOccurrence(sent, "\n", "") , "</conll>") );
					}
					
					
					// write the conlls inside the treesModel and create the files
					
					if(i-1 < 0){
						String nextname = "";
						// check if there actually is a next name, or if this is a file alone
						if(i+1 == listFiles.size()){
							nextname = "#";
							Tools.ecrire(String.format("%s%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, filename, ".html"),
									treesModel.replace("{conlls}", sbNewMerged).replace("{samplename}",
											sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name"))
											.replace("{previous}", "../../index.html").replace("{next}", "../../index.html") );
						}else{
						nextname = ".."+File.separator+"sample"+(sampleIndex+1) + File.separator +FilenameUtils.getName(listFiles.get(i+1));
						Tools.ecrire(String.format("%s%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, filename, ".html"),
								treesModel.replace("{conlls}", sbNewMerged).replace("{samplename}",
										sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name")+" - Treebank Browser")
										.replace("{previous}", "../../index.html").replace("{next}", nextname+".html") );
						}
						
					}else if(i+1 == listFiles.size()){
						String previousname = ".."+File.separator+"sample"+(sampleIndex-1) + File.separator + FilenameUtils.getName(listFiles.get(i-1));
						Tools.ecrire(String.format("%s%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, filename, ".html"),
								treesModel.replace("{conlls}", sbNewMerged).replace("{samplename}",
										sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name")+" - Treebank Browser")
										.replace("{previous}", previousname+".html").replace("{next}", "../../index.html") );
					}else{
						String nextname = ".."+File.separator+"sample"+(sampleIndex+1) + File.separator +FilenameUtils.getName(listFiles.get(i+1));
						String previousname = ".."+File.separator+"sample"+(sampleIndex-1) + File.separator +FilenameUtils.getName(listFiles.get(i-1));
						Tools.ecrire(String.format("%s%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, filename, ".html"),
								treesModel.replace("{conlls}", sbNewMerged).replace("{samplename}",
										sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name")+" - Treebank Browser")
										.replace("{previous}", previousname+".html").replace("{next}", nextname+".html") );
					}
				}
				// add additionnal infos to a new list string[]
				listFilesLocation.add(new String[]{filePath+".html", p, sampleInfos.get("type")});
//				listFilesLocation.add(new String[]{filePath, String.valueOf(sampleIndex), sampleInfos.get("type")});
				
				// increment sampleIndex
				if(isFile)sampleIndex++;
				
				// for the list index
				i++;
	        }
	        mapDirsContentsLocation.put((String)pair.getKey(), listFilesLocation);
	        
	        
	        // TODO : continuer à recréer l'arborescence en map (en filesysteme ca doit rester plat)
	        //, chaque fichier .html a une info sur son lieu dans l'arbo virtuelle, + son chemin réel + son nom
	        // + ajouter visu arborescence 
	        // ne pas recréer les dirs (donc modifier code ci-dessus)
	        
	    }
	    System.out.println("locations :");
	    Iterator itLocation = mapDirsContentsLocation.entrySet().iterator();
	    while (itLocation.hasNext()) {
	        Map.Entry pair = (Map.Entry)itLocation.next();
	        List<String[]> list = (List<String[]>)pair.getValue();
//	        for (String[] val : list)
//	        	System.out.println(pair.getKey() + " = " + val[0]  + " " + val[1]+ " "+ val[2]);
	    }
//		System.out.println("locations : \n" + mapDirsContentsLocation.toString());
	}
	
	
	
	
	private  void creaHtmlGlobalTreeWithAllConlls(String dirPath) throws Exception {
		
		mapDirsContents = Tools.dir2map(dirPath);
		
		int sampleIndex = 1;
		Iterator it = mapDirsContents.entrySet().iterator();
		
		// get the treesModel content
		Tools tool = new Tools();
		String treesModel = tool.accessRessourceFile(String.format("%s%s%s%s%s%s%s%s", File.separatorChar
				, "resources", File.separatorChar, "interface-statique",
				File.separatorChar, "samples" , File.separatorChar, "treesModel.html"));
		StringBuilder sbNewMerged = new StringBuilder();
		
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
//	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	        
	        
	        List<String> listFiles = (List<String>) pair.getValue();
	        List<String[]> listFilesLocation = new ArrayList<String[]>();
	        int i = 0;
	        
			
			HashMap<String, String> sampleInfos = new HashMap<String, String>();
			File dirFile = new File("filetemp");
			String filename = "";
	        
//	        for (String filePath : listFiles){
			String filePath = "/home/gael/Documents/SRCMF/Rhapsodie/Orfeo/echantillon/27_JD_CP_100226_parse";
	        	String extension = FilenameUtils.getExtension(filePath);
				filename = FilenameUtils.getName(filePath);
				String filebasename = FilenameUtils.getBaseName(filePath);
				String filePathNoName = FilenameUtils.getPath(filePath);
				
				// get the sample name from the file name
				sampleInfos = new HashMap<String, String>();
				
				boolean isFile ;
				if(new File(filePath).isDirectory()){
					sampleInfos.put("sample name", filename);
					sampleInfos.put("type", "directory");
					isFile = false;
				}else{
					sampleInfos.put("sample name", filename);
					sampleInfos.put("type", "file");
					isFile = true;
				}
				
				
				// create the paths dirs if none
				String p = String.format("%s%s%s%s%s", baseDirCrea, File.separatorChar, "samples",
						File.separatorChar, "sample" + sampleIndex);
//						File.separatorChar, "files");
				dirFile = new File(p);
				dirFile.mkdirs();
				
				if(isFile){
					// get conll content into a sb
					StringBuilder sbNew = new StringBuilder();
					sbNew.append( Tools.readFile(filePath));
					
					// convert conll content to list of conll sentences
					List<String> sentNew = Corpus.getSentences(Tools.tempFile("new", ".txt", sbNew.toString()));
					// merge the tree model with the conll sentences into <conll> tag
					
					for (String sent : sentNew){
						sbNewMerged.append(String.format("%s%s%s\n", "<conll>",
								Tools.replaceLastOccurrence(sent, "\n", "") , "</conll>") );
					}
					
				}
				
				
				
				
				// add additionnal infos to a new list string[]
				listFilesLocation.add(new String[]{filePath+".html", p, sampleInfos.get("type")});
//				listFilesLocation.add(new String[]{filePath, String.valueOf(sampleIndex), sampleInfos.get("type")});
				
				// increment sampleIndex
				if(isFile)sampleIndex++;
				
				// for the list index
				i++;
//	        }
	        
	        
//	        Tools.ecrire(String.format("%s%s%s%s", dirFile.getAbsolutePath(), File.separatorChar, filename, ".html"),
//					treesModel.replace("{conlls}", sbNewMerged).replace("{samplename}",
//							sampleInfos.get("sample name")).replace("{title}", sampleInfos.get("sample name")+" - Treebank Browser")
//							.replace("{previous}", "../../index.html").replace("{next}", "../../index.html") );
	        mapDirsContentsLocation.put((String)pair.getKey(), listFilesLocation);
	        
	        // TODO : continuer à recréer l'arborescence en map (en filesysteme ca doit rester plat)
	        //, chaque fichier .html a une info sur son lieu dans l'arbo virtuelle, + son chemin réel + son nom
	        // + ajouter visu arborescence 
	        // ne pas recréer les dirs (donc modifier code ci-dessus)
	        
	    }
	    Tools.ecrire("conlls.html", treesModel.replace("{conlls}", sbNewMerged));
	    System.out.println("locations :");
	    Iterator itLocation = mapDirsContentsLocation.entrySet().iterator();
	    while (itLocation.hasNext()) {
	        Map.Entry pair = (Map.Entry)itLocation.next();
	        List<String[]> list = (List<String[]>)pair.getValue();
//	        for (String[] val : list)
//	        	System.out.println(pair.getKey() + " = " + val[0]  + " " + val[1]+ " "+ val[2]);
	    }
//		System.out.println("locations : \n" + mapDirsContentsLocation.toString());
	    
	    
	}
	
	
	/**
	 * parse the sample.info and returns the values in a hashmap
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private  HashMap<String, String> getSampleInfos(String path) throws Exception{
		List<String> lines = Tools.path2liste(path);
		HashMap<String, String> values = new HashMap<String, String>();
		for(String line : lines){
			String[] cols = line.split("\t");
			if(cols[0].equals("sample name")) values.put("sample name", cols[1]);
		}
		return values;
	}
	
	/**
	 * adapt the sample view with the reality of the trees.html 
	 * @throws IOException
	 */
//	private  void adaptSampleView() throws IOException {
//		List<String> listDir = Tools.listDirs(baseDirCrea+File.separatorChar +"samples");
//		StringBuilder samplesView = new StringBuilder();
//		for(String dir : listDir){
//			String dirPath = String.format("%s%s%s%s%s", baseDirCrea , File.separatorChar, "samples" 
//				, File.separatorChar, dir );
//			if(Tools.dirFileExists(String.format("%s%s%s", dirPath, File.separatorChar, "trees.html" ) ) 
//					|| Tools.dirFileExists(String.format("%s%s%s", dirPath, File.separatorChar, "oldTrees.html")) ){
//				// retrieve the numbers of trees for both old and new
//				int n = Tools.countOccurrences( Tools.readFile(dirPath+ File.separatorChar +"trees.html"),"<conll>");
//				int o = 0;
//				if(Tools.dirFileExists(dirPath+ File.separatorChar +"oldTrees.html")) o = Tools.countOccurrences( Tools.readFile(dirPath+ File.separatorChar +"oldTrees.html"),"<conll>");
//				// get the href content for both old and new
//				String relPath = String.format("%s%s"
//						, Tools.relativisePath(dirPath, currentDir).replace(baseDirCrea+File.separatorChar, "")
//						, "trees.html");
//				String relOldPath = String.format("%s%s"
//						, Tools.relativisePath(dirPath, currentDir).replace(baseDirCrea+File.separatorChar, "")
//						, "oldTrees.html");
//				// get the sample name
//				
//				final Matcher matcherOld = pattern.matcher(Tools.readFile(
//						String.format("%s%s"
//								, dirPath + File.separatorChar
//								, "trees.html")));
//				final Matcher matcherNew = pattern.matcher(Tools.readFile(
//						String.format("%s%s"
//								, dirPath + File.separatorChar
//								, "trees.html")));
//				if(matcherOld.find()){
//					//generate the sample row
//					samplesView.append(	sampleRow(o, n, relOldPath, relPath, matcherOld.group(1)) );
//				}else if(matcherNew.find()){
//					samplesView.append(	sampleRow(o, n, relOldPath, relPath, matcherNew.group(1)) );
//				}else{
//					samplesView.append( sampleRow(o, n, relOldPath, relPath, "Nonamed Sample") );
//				}
//			}
//		}
//		
//		replaceInFile(baseDirCrea+File.separatorChar +"samples.html", "{sampleslist}", samplesView.toString() );
//	}
	
	/**
	 * adapt the sample view with the reality of the trees.html and the directory structure
	 * @throws IOException
	 */
	private  void adaptSampleViewWithDirs() throws IOException {
		int i = 0;
		StringBuilder samplesView = new StringBuilder();
		
		// first process the "root" key directory (in order to have it show first
		List<String[]> listRoot = mapDirsContentsLocation.get("root");
		// get the number of files (and not dirs) in this directory (entry key)
        List<String[]> listFilesRoot = new ArrayList<String[]>();
        for(String[] val : listRoot){if(val[2].equals("file"))listFilesRoot.add(val);}
        if(listFilesRoot.size() != 0){
        	samplesView.append("<div class=\"panel-group\"><div class=\"panel panel-default\">"
    			+ "<div class=\"panel-heading\"> "
//    			+ "<button class=\"btn btn-success opacity-hover\" style=\"width:100%\" "
//    			+ "data-toggle=\"collapse\" data-target=\"#collapse"+i+"\">"
//    			+ "<h4 class=\"panel-title\">"+ "root"  +"</h4><span class=\"badge\">"+ listFilesRoot.size() +"</span></button></div>"
    			+ "</div>"
				+ "<div id=\"collapse"+i+"\" class=\"panel-collapse collapse in\"> <div class=\"panel-body\">");
        }else{
        	samplesView.append("<div class=\"panel-group\"><div class=\"panel panel-default\">"
        			+ "<div class=\"panel-heading\"> "
        			+ "<button class=\"btn btn-default btn-empty opacity-hover\" style=\"width:100%\" "
        			+ "data-toggle=\"collapse\" data-target=\"#collapse"+i+"\">"
        			+ "<h4 class=\"panel-title\">"+ "root"  +"</h4><span class=\"badge\">"+ listFilesRoot.size() +"</span></button></div>"
        			+ "<div id=\"collapse"+i+"\" class=\"panel-collapse collapse\"> <div class=\"panel-body\">");
        }
        for (String[] val : listRoot){
        	if(val[2].equals("file")){
        		String dirPath = val[1];
        		String sampleName = FilenameUtils.getName(val[0]);
    			if(Tools.dirFileExists(String.format("%s%s%s", dirPath, File.separatorChar, sampleName ) ) ){
    				
    				// retrieve the numbers of trees for both old and new
    				int n = Tools.countOccurrences( Tools.readFile(dirPath+ File.separatorChar +sampleName),"<conll>");
    				int o = 0;
    				
    				// get the href content for both old and new
    				String relPath = String.format("%s%s"
    						, Tools.relativisePath(dirPath, currentDir).replace(baseDirCrea+File.separatorChar, "")
    						, sampleName);
    				
    				// get the sample name
    				final Matcher matcherNew = pattern.matcher(Tools.readFile(
    						String.format("%s%s"
    								, dirPath + File.separatorChar
    								, sampleName)));
    				
    				if(matcherNew.find()){
    					samplesView.append(	sampleRow(o, n, "", relPath, matcherNew.group(1)) );
    				}else{
    					samplesView.append( sampleRow(o, n, "", relPath, "Nonamed Sample") );
    				}
    			}
        	}else if(val[2].equals("directory")){
        		
        	}
        }
        samplesView.append("</div> </div> </div> </div>");
        i++;
		
		// iterate through the map
		Iterator itLocation = mapDirsContentsLocation.entrySet().iterator();
	    while (itLocation.hasNext()) {
	        Map.Entry pair = (Map.Entry)itLocation.next();
	        // avoid the "root" entry and the absolute dir
	    	if((pair.getKey().equals("root"))||(pair.getKey().equals(rawDir))){continue;}
	        List<String[]> list = (List<String[]>)pair.getValue();
	        // get the number of files (and not dirs) in this directory (entry key)
	        List<String[]> listFiles = new ArrayList<String[]>();
	        for(String[] val : list){
	        	if(val[2].equals("file"))listFiles.add(val);
	        }
	        
	        if(i>0){
	        	samplesView.append("<div class=\"panel-group\"><div class=\"panel panel-default\">"
	        			+ "<div class=\"panel-heading\"> "
	        			+ "<button class=\"btn btn-success opacity-hover\" style=\"width:100%\" "
	        			+ "data-toggle=\"collapse\" data-target=\"#collapse"+i+"\">"
	        			+ "<h4 class=\"panel-title\">"+ (String)pair.getKey()  +"</h4><span class=\"badge\">"+ listFiles.size() +"</span></button></div>"
	        			+ "<div id=\"collapse"+i+"\" class=\"panel-collapse collapse\"> <div class=\"panel-body\">");
	        }
	        
	        for (String[] val : list){
	        	if(val[2].equals("file")){
	        		String dirPath = val[1];
	        		String sampleName = FilenameUtils.getName(val[0]);
        			if(Tools.dirFileExists(String.format("%s%s%s", dirPath, File.separatorChar, sampleName ) ) ){
        				
        				// retrieve the numbers of trees for both old and new
        				int n = Tools.countOccurrences( Tools.readFile(dirPath+ File.separatorChar +sampleName),"<conll>");
        				int o = 0;
        				
        				// get the href content for both old and new
        				String relPath = String.format("%s%s"
        						, Tools.relativisePath(dirPath, currentDir).replace(baseDirCrea+File.separatorChar, "")
        						, sampleName);
        				
        				// get the sample name
        				final Matcher matcherNew = pattern.matcher(Tools.readFile(
        						String.format("%s%s"
        								, dirPath + File.separatorChar
        								, sampleName)));
        				
        				if(matcherNew.find()){
        					samplesView.append(	sampleRow(o, n, "", relPath, matcherNew.group(1)) );
        				}else{
        					samplesView.append( sampleRow(o, n, "", relPath, "Nonamed Sample") );
        				}
        			}
	        	}else if(val[2].equals("directory")){
	        		
	        	}
	        }
	        if(i>0)samplesView.append("</div> </div> </div> </div>");

	        i++;
	    }
	    
	    replaceInFile(baseDirCrea+File.separatorChar +"index.html", "{sampleslist}", samplesView.toString() );
		}
	
	
	/**
	 * generate the html for one row given the informations.
	 * @param o - oldSample number of trees
	 * @param n - newSample number of trees
	 * @param oUrl - href url for the sample
	 * @param nUrl - href url for the sample (usually trees.html)
	 * @param sampleName
	 * @return the html
	 */
	private  String sampleRow(int o, int n, String oUrl, String nUrl, String sampleName ){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"row no-margin\">");
		if(n == 0){
			sb.append("<li class=\"list-group-item col-xs-12 opacity-hover\"><a class=\"btn-list btn btn-default btn-empty\" style=\"text-align: left; width:50%; margin-left:25%; font-weight: bold;\"><span class=\"badge\">0</span>"+sampleName+"</a></li>");
		}else{
			sb.append("<li class=\"list-group-item col-xs-12 opacity-hover\"><a  href=\""+nUrl+"\" class=\"btn-list animsition-link btn btn-primary\" style=\"text-align: left; width:50%; margin-left:25%; font-weight: bold;\"><span class=\"badge\">"+n+"</span>"+sampleName+"</a></li>");
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * generate the html for one row given the informations. This is for a directory name separator.
	 * @param o - oldSample number of trees
	 * @param n - newSample number of trees
	 * @param oUrl - href url for the sample
	 * @param nUrl - href url for the sample (usually trees.html)
	 * @param sampleName
	 * @return the html
	 */
	private  String sampleRowDir(String dirName ){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"row no-margin\">");
		if(dirName.length()!=0){
			sb.append("<div class=\"alert alert-success\"><strong>"+ dirName +"</strong> </div>");
//			sb.append("<li class=\"list-group-item col-xs-12 opacity-hover\"><a class=\"btn-list btn btn-default btn-empty\" style=\"text-align: left;\"><span class=\"badge\">0</span>"+sampleName+"</a></li>");
		}else{
			sb.append("<div class=\"alert alert-success\"><strong>directory</strong> </div>");
//			sb.append("<li class=\"list-group-item col-xs-12 opacity-hover\"><a  href=\""+nUrl+"\" class=\"btn-list animsition-link btn btn-primary\" style=\"text-align: left;\"><span class=\"badge\">"+n+"</span>"+sampleName+"</a></li>");
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * replace a string content by another in a file (overwrite the current file)
	 * @param filePath
	 * @param toReplace
	 * @param toInsert
	 */
	private  void replaceInFile(String filePath, String toReplace, String toInsert){
		String content = "";
		try {
			content = Tools.readFile(filePath);
			if((toInsert!=null)&&(toInsert.length()!=0)){
				content = content.replace(toReplace, toInsert);
			}else{
				content = content.replace(toReplace, "");
			}
			Tools.ecrire(filePath, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private  void insertMetaInfo() throws Exception{
//		Tools tool = new Tools();
//		String html = tool.accessRessourceFile("/home/gael/workspacefx/RHAPSODIE/resources/interface-statique/index.html");
//		Tools.copyDirToAnotherDir(baseDir, baseDirCrea);
//		
		
		// version needed outside a jar
//		Tools.copyDirToAnotherDir(StaticGenerator.class.getResource("/resources/interface-statique").toExternalForm()
//				.replace("file:", "").replace("jar:","")
//				, baseDirCrea);
		
		
		
		// erase the samples directory first to avoid files leak from project to another
		Tools.deleteFolder(new File(String.format("%s%s%s", baseDirCrea, File.separatorChar, "samples")));

		ResourcesFromJar rfj = new ResourcesFromJar();
		rfj.get();

		
		
		List<String> listAllFilePaths = new ArrayList<String>();
		Tools.listFilesAndSubfiles(baseDirCrea, listAllFilePaths);
		for(String path : listAllFilePaths){
			if(FilenameUtils.getExtension(path).equals("html")){
				replaceInFile(path, "{title}", title);
				replaceInFile(path, "{subtitle}", subtitle);
				replaceInFile(path, "{projecttitle}", projectTitle);
				replaceInFile(path, "{projecthtml}", projectHtml);
				replaceInFile(path, "{licencetitle}", licenceTitle);
				replaceInFile(path, "{licencehtml}", licenceHtml);
			}
		}
	}
	
	
	private  void openIt(String path) throws URISyntaxException {
//		DesktopApi.browse(new URI("file://" + path));
		
		if (OsValidator.isWindows()) {
			System.out.println("This is Windows");

			try {
				Runtime rt = Runtime.getRuntime();
				String url = "file:///" + path.replaceAll("/", "\\");
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OsValidator.isMac()) {
			System.out.println("This is Mac");
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				Thread t = new Thread("New Thread") {
					public void run() {
						try {
							desktop.browse(new URI("file://" + path));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				t.start();
			}
		} else if (OsValidator.isUnix()) {
			System.out.println("This is Unix or Linux");
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				Thread t = new Thread("New Thread") {
					public void run() {
						try {
							desktop.browse(new URI("file://" + path));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				t.start();
			}
		} else {
			System.out.println("Your OS is not supported!!");
		}
	}
	
	
	/**
	 * returns a list of file paths of every files under the root
	 * @param path
	 * @param listToFill
	 */
	private static void listFilesAndSubfiles( String path, List<String> listToFill, String pattern ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                listFilesAndSubfiles( f.getAbsolutePath(), listToFill, f.getPath() );
            }
            else {
                listToFill.add(f.getAbsolutePath().replace(pattern,"") + File.separator + f.getAbsolutePath());
            }
        }
    }
	
	
	private static void runJS(String tree) throws Exception{
		ScriptEngineManager factory = new ScriptEngineManager();
	    ScriptEngine engine = factory.getEngineByName("JavaScript");
	    String script = new Tools().accessRessourceFile(
	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
				, "resources", File.separatorChar, "interface-statique",
				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "raphael.js"));
	    script = new Tools().accessRessourceFile(
	    	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
	    				, "resources", File.separatorChar, "interface-statique",
	    				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "arborator.view.js"));
	    engine.eval(script);
	    script = new Tools().accessRessourceFile(
	    	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
	    				, "resources", File.separatorChar, "interface-statique",
	    				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "justDrawConll.js"));
	    engine.eval(script);
	    script = new Tools().accessRessourceFile(
	    	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
	    				, "resources", File.separatorChar, "interface-statique",
	    				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "conll2tree.js"));
	    engine.eval(script);
	    try {
			engine.eval("print('Hello, World');");
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
