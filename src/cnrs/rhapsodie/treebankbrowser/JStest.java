package cnrs.rhapsodie.treebankbrowser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cnrs.rhapsodie.treebankbrowser.utils.Tools;

public class JStest {

	public static void main(String[] args) throws Exception{
//		ScriptEngineManager factory = new ScriptEngineManager();
//	    ScriptEngine engine = factory.getEngineByName("JavaScript");
//	    engine.eval("print('Hello, World'); "
//	    		+ "var mystr = '1	oui	oui	INT	INT	_	4	dm\n2	allez	aller	VRB	VRB	_	1	dm\n3	on	on	CLS	CLS	_	4	suj\n4	va	aller	VRB	VRB	_	0	root\n5	prendre	prendre	VNF	VNF	_	4	comp\n6	un peu	un peu	ADV	ADV	_	5	ad\n7	de	de	PRE	PRE	_	6	disflink	_	_';"
//	    		+ ""
//	    		+ "");
	    
		
		/*URL oracle = new URL("http://www.lemonde.fr");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
        */
        
	    ScriptEngineManager factory = new ScriptEngineManager();
	    ScriptEngine engine = factory.getEngineByName("JavaScript");
	    engine.eval("document.location.href='http://www.google.com';");
	    
//	    String script = new Tools().accessRessourceFile(
//	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
//				, "resources", File.separatorChar, "interface-statique",
//				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "raphael.js"));
//	    engine.eval(script);
	    
//	    String script = new Tools().accessRessourceFile(
//	    	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
//	    				, "resources", File.separatorChar, "interface-statique",
//	    				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "arborator.view.js"));
//	    engine.eval(script);
//	    String script = new Tools().accessRessourceFile(
//	    	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
//	    				, "resources", File.separatorChar, "interface-statique",
//	    				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "justDrawConll.js"));
//	    engine.eval(script);
	    
	    String script = new Tools().accessRessourceFile(
	    	    String.format("%s%s%s%s%s%s%s%s%s%s", File.separatorChar
	    				, "resources", File.separatorChar, "interface-statique",
	    				File.separatorChar, "samples" , File.separatorChar, "js", File.separatorChar, "conll2tree.js"));
	    engine.eval(script);
	    
//        try {
//			engine.eval("print('Hello, World');");
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
