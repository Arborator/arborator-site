package cnrs.rhapsodie.treebankbrowser.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Corpus {
	/**
	 * From a conll or other, return each sentence in a String. Then gives you the list
	 * of these sentences.
	 * @param conllPath
	 * @return List<String> sentences
	 * @throws Exception
	 */
	public static List<String> getSentences(String conllPath) throws Exception{
		File fichier = new File(conllPath);
		BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
		String texte = lecteur.toString();
		StringBuilder phrase = new StringBuilder();
		List<String> phrases = new ArrayList<String>();

		while ((texte = lecteur.readLine()) != null) {
			if (texte.length() == 0) {
				if (phrase.length() > 0){
					phrases.add(phrase.toString());
					phrase = new StringBuilder();
				}
				continue;
			}
			phrase.append( texte + "\n" );
		}
		lecteur.close();
		if(phrase.length() != 0)
		phrases.add(phrase.toString());
		return phrases;
	}
}
