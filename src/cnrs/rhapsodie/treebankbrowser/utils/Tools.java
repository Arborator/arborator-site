package cnrs.rhapsodie.treebankbrowser.utils;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.developpez.adiguba.shell.Shell;
import com.google.common.base.Joiner;

/**
 * Classe possedant plusieurs methodes generiques, utiles dans de tres nombreux
 * cas. Cette classe est le couteau suisse permettant de gagner du temps.
 * 
 * @author Gael
 * 
 */
public class Tools {

	static Pattern SPECIAL_REGEX_CHARS = Pattern
			.compile("[{}()\\[\\].+*?^$\\\\|]");
	
	private static String dirName ;

	/**
	 * Ecrire dans un fichier
	 * 
	 * Créer le fichier et entre le String
	 * 
	 * @param filename
	 * @param text
	 */
	public static void ecrire(String fichier, String text) {

		FileWriter fw;
		try {
			fw = new FileWriter(fichier);

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter fichierSortie = new PrintWriter(bw);
			fichierSortie.println(text);
			fichierSortie.close();
			// ////////////////////////////////////////
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ajouter une ligne dans un fichier texte
	 * 
	 * Ouvrir le fichier et ajouter une ligne de texte
	 * 
	 * @param filename
	 * @param text
	 */
	public static void ajouter(String filename, String text) {
		BufferedWriter bufWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filename, true);
			bufWriter = new BufferedWriter(fileWriter);
			// Insérer un saut de ligne
			bufWriter.newLine();
			bufWriter.write(text);
			bufWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(Tools.class.getName())
					.log(Level.SEVERE, null, ex);
		} finally {
			try {
				bufWriter.close();
				fileWriter.close();
			} catch (IOException ex) {
				Logger.getLogger(Tools.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	/**
	 * Ecrire dans un fichier texte avec nom extrait du chemin.
	 * 
	 * @param chemin
	 *            Chemin du fichier.
	 * @param suffixe
	 *            Si on veut ajouter un suffixe au nom du fichier (ex: ".txt" |
	 *            ".new.txt")
	 * @param text
	 *            La variable du contenu.
	 * @throws IOException
	 */
	public static void ecrireSelonNomFichier(String chemin, String suffixe,
			String text) throws IOException {
		File file = new File(chemin);
		String filename = file.getName() + suffixe;
		ecrire(filename, text);
	}

	/**
	 * Prend un string et retourne son flux.
	 * 
	 * @param string
	 * @return le flux du string
	 */
	public static BufferedReader StringToBufferedReader(String string) {
		// convert String into InputStream
		InputStream Fichier = new ByteArrayInputStream(string.getBytes());
		// read it with BufferedReader
		BufferedReader lecteur = new BufferedReader(new InputStreamReader(
				Fichier));
		return lecteur;
	}

	/**
	 * Ouvre une fenetre de selection et retourne la valeur en jfilechooser.
	 * Exemple : JFileChooser jfc = Fichier.ChoixFichier();
	 * 
	 * @return Retourne le Jfilechooser du fichier, soit le resultat de la
	 *         fenêtre de choix.
	 * @throws IOException
	 */
	public static JFileChooser ChoixFichier() throws IOException {
		JFileChooser conll = new JFileChooser(new File("."));
		conll.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		conll.setMultiSelectionEnabled(true);

		return conll;
	}

	/**
	 * Prend le chemin d'un fichier et retourne son contenu en String.
	 * 
	 * @param path
	 *            Chemin du fichier en question. Dans le cas d'un File[], faire
	 *            "x[i].getPath()" en parametre.
	 * @return Un String correspondant au contenu du fichier.
	 * @throws IOException
	 */
	public static String ArrayToString(String path) throws IOException {

		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String coco = encoding.decode(ByteBuffer.wrap(encoded)).toString();
		return coco;

	}

	/**
	 * Ouvre le dossier contenant le fichier donne en entree.
	 * 
	 * @param fichier
	 *            sans path.
	 * @throws IOException
	 */
	public static void Explorer(String fichier) throws IOException {

		File fil = new File(fichier);
		String toc = fil.getAbsolutePath();
		toc = toc.replaceAll(fil.getName(), "");
		Desktop.getDesktop().open(new File(toc));

	}

	/**
	 * Demande de confirmation d'ouverture d'un fichier. Si oui il s'ouvrira
	 * avec NotePad. Si non il ne fera rien.
	 * 
	 * @param fichier
	 *            La variable String du fichier, avec ou sans path.
	 * @throws IOException
	 */
	public static void DemanderOuvrir(String fichier) throws IOException {
		int choix = JOptionPane.showConfirmDialog(null, "Le fichier " + fichier
				+ " a ete cree !\n" + "Voulez-vous le voir sous blocnote?",
				"SRCMF", JOptionPane.YES_NO_OPTION);
		switch (choix) {
		case (0):

			File file = new File(fichier);
			String fic = file.getAbsolutePath();
			String cmd = " notepad " + fic;
			Shell.system(cmd);
			break;
		case (1):
			break;
		}
	}

	/**
	 * Methode qui permet de parser un repertoire et de retrouver ses fichiers
	 * 
	 * @param file
	 * @param all
	 * @param extension
	 */
	public static void findFilesRecursively(File file, Collection<File> all,
			final String extension) {
		// Liste des fichiers correspondant a l'extension souhaitee
		final File[] children = file.listFiles(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().endsWith(extension);
			}
		});
		if (children != null) {
			// Pour chaque fichier recupere, on appelle a nouveau la methode
			for (File child : children) {
				all.add(child);
				findFilesRecursively(child, all, extension);
			}
		}
	}

	/**
	 * Lire le contenu d'un file et le retourne en String
	 * 
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/**
	 * Ouvre un file chooser et rend son path en String
	 * 
	 * @return le path du fichier en string
	 * @throws IOException
	 */
	public static String fcPath() throws IOException {
		// JOptionPane.showMessageDialog(null, "Selectionnez un fichier.");
		String path = "";
		JFileChooser jfc = new JFileChooser();
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File fichier = jfc.getSelectedFile();
			path = fichier.getAbsolutePath();
		}
		return path;
	}

	/**
	 * Prend un string et met chaque ligne en element d'une liste.
	 * 
	 * @param str
	 * @return Une liste du string
	 * @throws IOException
	 */
	public static List<String> StringToList(String str) throws IOException {
		BufferedReader lecteur = Tools.StringToBufferedReader(str);
		String texte = lecteur.toString();
		List<String> liste = new ArrayList<String>();

		while ((texte = lecteur.readLine()) != null) {
			liste.add(texte);
		}
		lecteur.close();
		return liste;
	}
	
	/**
	 * Parcours une liste et en retourne une autre sans redondance basée sur une
	 * valeur de colonne.
	 * 
	 * @param liste
	 *            La liste du fichier intégral
	 * @param colonne
	 *            La colonne servant de référence, et sur laquelle on ne veut
	 *            pas de redondance.
	 * @return La liste sans redondance basée sur l'élément donné.
	 * @throws IOException
	 */
	public static List<String> ListeNonRedondante(List<String> liste,
			int colonne) throws IOException {
		List<String> listef = new ArrayList<String>();
		for (String e : liste) {
			if (e.length() != 0) {
				String[] ligne = e.split("\t");
				if (listef.contains(ligne[colonne])) {
					continue;
				}
				listef.add(ligne[colonne]);
			}
		}

		return listef;
	}
	


	
	/**
	 * Parcours une liste et en retourne une autre avec redondance basée sur une
	 * valeur de colonne.
	 * 
	 * @param liste
	 *            La liste du fichier intégral
	 * @param colonne
	 *            La colonne servant de référence, et sur laquelle on ne veut
	 *            pas de redondance.
	 * @return La liste sans redondance basée sur l'élément donné.
	 * @throws IOException
	 */
	public static List<String> ListeRedondante(List<String> liste,
			int colonne) throws IOException {
		List<String> listef = new ArrayList<String>();
		for (String e : liste) {
			if (e.length() != 0) {
				String[] ligne = e.split("\t");
				listef.add(ligne[colonne]);
			}
		}

		return listef;
	}

	/**
	 * Prend un pat, le transforme en fichier puis le parcours et le met dans
	 * une liste par ligne.
	 * 
	 * @param path
	 *            le chemin du fichier
	 * @return une liste à instancier
	 * @throws IOException
	 */
	public static List<String> path2liste(String path) throws IOException {
		List<String> liste = new ArrayList<String>();
		File range = new File(path);
		BufferedReader lecteurrange = new BufferedReader(new FileReader(range));
		String texterange = lecteurrange.toString();
		while ((texterange = lecteurrange.readLine()) != null) {
			liste.add(texterange);
		}
		lecteurrange.close();
		return liste;
	}

	/**
	 * Donne la date, l'heure et la seconde dans un string.
	 * 
	 * @return string du temps
	 * @throws IOException
	 */
	public static String time() throws IOException {
		Date now = new Date(); // java.util.Date, NOT java.sql.Date or
								// java.sql.Timestamp!
		// String format1 = new
		// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(now);
		String format2 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
				.format(now);
		// String format3 = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		return format2;
	}

	/**
	 * Prend un dossier et en retourne une liste de chemins de tous les fichiers
	 * qui y sont presents. Pour une version retournant les Files du dossier,
	 * voir dir2listefiles()
	 * 
	 * @param dir_path
	 * @return liste de String
	 * @throws Exception
	 */
	public static List<String> dir2listepaths(String dir_path) throws Exception {
		List<String> liste_path_files = new ArrayList<String>();
		File dir = new File(dir_path);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		for (File file : files) {
//		for(int i = 0; i < files.length; i++){
			liste_path_files.add(file.getAbsolutePath());
		}
		return liste_path_files;
	}
	
	/**
	 * Prend un dossier et en retourne un tableau de chemins de tous les fichiers
	 * qui y sont presents. 
	 * @see dir2listepath pour une versio en List<String>
	 * @param dir_path
	 * @return tableau contenant les chemins absolus
	 * @throws Exception
	 */
	public static String[] dir2arrayPaths(String dir_path) throws Exception {
		List<String> liste_path_files = new ArrayList<String>();
		File dir = new File(dir_path);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		int current = 0;
		int total = 0;
		for (File file : files) {
			current++;
			Tools.printProgress(total, current);
//		for(int i = 0; i < files.length; i++){
			liste_path_files.add(file.getAbsolutePath());
		}
		String[] arrayPaths = new String[liste_path_files.size()];
		liste_path_files.toArray(arrayPaths);
		return arrayPaths;
	}

	/**
	 * Prend un dossier et en retourne une liste de chemins de tous les fichiers
	 * qui y sont presents. Pour une version retournant les Files du dossier,
	 * voir dir2listefiles()
	 * 
	 * @param dir_path
	 * @return liste de String
	 * @throws Exception
	 */
	public static List<File> dir2listefiles(String dir_path) throws Exception {
		List<File> liste_path_files = new ArrayList<File>();
		File dir = new File(dir_path);
		File[] files = dir.listFiles();
		for (File file : files) {
			liste_path_files.add(file);
		}
		return liste_path_files;
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSpecialRegexChars(String str) {

		return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
	}

	/**
	 * donne dans un string la date et l'heure actuelle dans un format simple.
	 * 
	 * @return date simple au format string
	 */
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// get current date time with Date()
		Date date = new Date();
		// System.out.println(dateFormat.format(date));

		/*
		 * //get current date time with Calendar() Calendar cal =
		 * Calendar.getInstance();
		 * System.out.println(dateFormat.format(cal.getTime()));
		 */
		return dateFormat.format(date);
	}
	
	/**
	 * Snippet in order to create a temporary file (i.e in RAM)
	 * @param pattern
	 * @param suffix need a dot before (i.e ".txt")
	 * @param content
	 * @return absolute path of temp file
	 * @throws IOException
	 */
	public static String tempFile(String pattern, String suffix, String content) throws IOException {
		File temp = File.createTempFile(pattern, suffix);
		temp.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
	    out.write(content);
	    out.close();
	    return temp.getAbsolutePath();
	}
	
	/**
	 * Show progress every 10 percents.
	 * Can be used for lists, arrays, or everything that have a total and a current integer indicators.
	 * @param total
	 * @param current
	 */
	public static void printProgress(int total, int current){
		int i = current;
		int size = total/20;
		if(i == size){
			System.out.print("\r[=-------------------]5%");
		}else{
			if(i == size*2){
				System.out.print("\r[==------------------]10%");
			}else{
				if(i == size*3){
					System.out.print("\r[===-----------------]15%");
				}else{
					if(i == size*4){
						System.out.print("\r[====----------------]20%");
					}else{
						if(i == size*5){
							System.out.print("\r[=====---------------]25%");
						}else{
							if(i == size*6){
								System.out.print("\r[======--------------]30%");
							}else{
								if(i == size*7){
									System.out.print("\r[=======-------------]35%");
								}else{
									if(i == size*8){
										System.out.print("\r[========------------]40%");
									}else{
										if(i == size*9){
											System.out.print("\r[=========-----------]45%");
										}else{
											if(i == size*10){
												System.out.print("\r[==========----------]50%");
											}else{ 
												if(i == size*11){
													System.out.print("\r[===========---------]55%");
											}else{ 
												if(i == size*12){
												System.out.print("\r[============--------]60%");
											}else{
												if(i == size*13){
												System.out.print("\r[=============-------]65%");
											}else{
												if(i == size*14){
												System.out.print("\r[==============------]70%");
											}else{
												if(i == size*15){
												System.out.print("\r[===============-----]75%");
											}else{
												if(i == size*16){
												System.out.print("\r[================----]80%");
											}else{
												if(i == size*17){
												System.out.print("\r[=================---]85%");
											}else{
												if(i == size*18){
												System.out.print("\r[==================--]90%");
											}else{ 
												if(i == size*19){
												System.out.print("\r[===================-]95%");
											}else{
												if(i == size*20){
												System.out.print("\r[====================]100%");
											}
											}
											}}}}}}}}}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param txt
	 * @return
	 * @throws IOException
	 */
	public static String asciiArt(String txt) throws IOException{
		String res = "";
		BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("Dialog", Font.PLAIN, 24));
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(txt, 6, 24);
		//ImageIO.write(image, "png", new File("text.png"));

		for (int y = 0; y < 32; y++) {
		    StringBuilder sb = new StringBuilder();
		    for (int x = 0; x < 144; x++)
		        sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
		    if (sb.toString().trim().isEmpty()) continue;
		    //System.out.println(sb);
		    res += sb + "\n";
		}
		return res;
	}
	
	/**
	 * check if a file or a directory exists.
	 * @param path
	 * @return
	 */
	public static boolean dirFileExists(String path) {
		File file=new File(path);
		boolean exists = file.exists();
		return exists;
	}
	
	/**
	 * Create a directory only if does not exists.
	 * @param path
	 * @throws IOException
	 */
	public static void createDir(String path) throws IOException{
		
		File theDir = new File(path);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + path);
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        System.out.println("Security issue to create directory : "+ path);
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
	}
	
	/**
	 * Create all the directories in the path if they do not exist
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
		absolutePath = absolutePath.replaceFirst("/", "");
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
			createDir(path);
			
		}
	}
	
	/**
	 * Add a suffix to a file name but keep the same path, same pattern, and same extension.
	 * For instance : /home/owimbowe.txt --> /home/owimboweSUPER.txt
	 * @param absolutePath
	 * @param suffix
	 * @return the new absolute path with new name and same pattern, same extension.
	 * @throws Exception
	 */
	public static String addFileSuffix(String absolutePath, String suffix) throws Exception{
		String path = FilenameUtils.getPath(absolutePath);
		String baseName = FilenameUtils.getBaseName(absolutePath);
		String extension = FilenameUtils.getExtension(absolutePath);
		String newName = String.format("/%s/%s%s.%s", path, baseName, suffix, extension);
		return newName;
	}
	
	/**
	 * Melange une liste au hasard.
	 * @param list
	 */
	public static void shuffleList (List<String> list){
		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));
	}
	
	/**
	 * Reproduit la commande Unix "cat fic1 fic2...". Necessite les chemins dans le tableau.
	 * @param paths
	 * @params separateur Permet de séparer les contenus par un \n\n par exemple.
	 * @return le contenu concaténé en String
	 * @throws Exception
	 */
	public static String cat(String paths[], String separateur) throws Exception{
		StringBuilder strBuilder = new StringBuilder();
		for(int i = 0; i < paths.length ; i++){
			if(i==0){
				strBuilder.append(Tools.readFile(paths[i]));
				continue;
			}
			strBuilder.append(separateur + Tools.readFile(paths[i]));
		}
		return strBuilder.toString();
	}
	
	/**
	 * Permet d'accéder à un fichier contenu dans le jar, pour en rendre un String de son contenu.
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String accessRessourceFile(String path) throws Exception{
		InputStream in = getClass().getResourceAsStream(path); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder();
		String aux = "";

		while ((aux = reader.readLine()) != null) {
		    builder.append(aux + "\n");
		}
		String text = builder.toString();
		return text;
	}
	
	/**
	 * Récupère la liste non redondante des éléments d'une colonne d'un TSV. Ne conserve pas le saut de ligne. 
	 * @param content
	 * @param colonne
	 * @return
	 * @throws Exception
	 */
	public static List<String> getColumnElements(String content, int colonne) throws Exception{
		List<String> lines = Tools.StringToList(content);
		List<String> tags = Tools.ListeNonRedondante(lines, colonne);
		for(int i =0; i < tags.size(); i ++){
			if(tags.get(i).equals("\n"))tags.remove(i);
		}
		return tags;
	}
	
	/**
	 * Récupère la liste non redondante des éléments de deux colonnes d'un TSV. Ne conserve pas le saut de ligne. 
	 * @param content
	 * @param colonne
	 * @return
	 * @throws Exception
	 */
	public static List<String> getColumnElements(String content, int colonne1, int colonne2) throws Exception{
		List<String> lines = Tools.StringToList(content);
		
		List<String> listef = new ArrayList<String>();
		for (String e : lines) {
			if((e.length() == 0)||(e.equals("\n")))continue;
			String[] ligne = e.split("\t");
			if (listef.contains(ligne[colonne1] + "\t" + ligne[colonne2])) {
				continue;
			}
			listef.add(ligne[colonne1] + "\t" + ligne[colonne2]);
		}
		return listef;
	}
	
	/**
	 * replace a column values given the column index. Override all these values with one value.
	 * ex: column 7 becomes "hello"
	 * @param str
	 * @param replace
	 * @param col
	 * @return
	 * @throws Exception
	 */
	public static String remplaceColValue(String str, String replace, int col) throws Exception{
		List<String> lines = Tools.StringToList(str);
		StringBuilder sb = new StringBuilder();
		for(String line : lines){
			if(line.length() == 0){
				sb.append("\n");
				continue;
			}
			String[] cols = line.split("\t");
			Joiner joiner = Joiner.on("\t");
			cols[col] = replace;
			sb.append(joiner.join(cols) + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * override and replace a column values given the column index.
	 * Uses a list of values with have to match the number of non empty lines. 
	 * @param str
	 * @param replaces
	 * @param col
	 * @return
	 * @throws Exception
	 */
	public static String remplaceColValue(String str, List<String> replaces, int col) throws Exception{
		List<String> lines = Tools.StringToList(str);
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(String line : lines){
			if(line.length() == 0){
				sb.append("\n");
				continue;
			}
			String[] cols = line.split("\t");
			Joiner joiner = Joiner.on("\t");
			cols[col] = replaces.get(i);
			i++;
			sb.append(joiner.join(cols) + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * add one column to a tsv.
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String addColValue(String str) throws Exception{
		List<String> lines = Tools.StringToList(str);
		StringBuilder sb = new StringBuilder();
		for(String line : lines){
			if(line.length() == 0){
				sb.append("\n");
				continue;
			}
			sb.append("_\t" + line + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * copy a directory and all its content (dir and files) to another directory
	 * @param sourcePath String path of the dir to copy
	 * @param destPath String path of the dir to create
	 */
	public static void copyDirToAnotherDir(String sourcePath, String destPath){
		File source = new File(sourcePath);
		File dest = new File(destPath);
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	/**
	 * returns a list of file paths of every files under the root
	 * @param path
	 * @param listToFill
	 */
	public static void listFilesAndSubfiles( String path, List<String> listToFill ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                listFilesAndSubfiles( f.getAbsolutePath(), listToFill );
            }
            else {
                listToFill.add(f.getAbsolutePath());
            }
        }
    }
	
	/**
	 * returns the list of directories contained given a root directory
	 * @param dirPath
	 * @return list of directories paths
	 */
	public static List<String> listDirs( String dirPath ) {
		File file = new File(dirPath);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		List<String> directoriesList = new ArrayList<String>();
		for(String dir : directories)directoriesList.add(dir);
		return directoriesList;
    }
	

	
	/**
	 * counts the number of occurrences of str in findStr
	 * @param str
	 * @param findStr
	 * @return
	 */
	public static int countOccurrences(String str, String findStr){
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = str.indexOf(findStr,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex += findStr.length();
		    }
		}
		return count;
	}
	
	/**
	 * from an absolute path returns the relative path based on the current location (baseLocation)
	 * @param absolutePath
	 * @param baseLocation
	 * @return
	 */
	public static String relativisePath(String absolutePath, String baseLocation){
		String relative = new File(baseLocation).toURI().relativize(new File(absolutePath).toURI()).getPath();
		return relative;
	}
	
	/**
	 * replace the last occurrence of a substring in a string
	 * @param str
	 * @param strToReplace 
	 * @param strToInsert
	 * @return
	 */
	public static String replaceLastOccurrence(String str, String strToReplace, String strToInsert){
		int ind = str.lastIndexOf(strToReplace);
		if( ind>=0 )
		    str = new StringBuilder(str).replace(ind, ind+1,strToInsert).toString();
		return str;
	}
	
	/**
	 * Prend un dossier et en retourne une liste de chemins de tous les fichiers
	 * qui y sont presents. Pour une version retournant les Files du dossier,
	 * voir dir2listefiles()
	 * 
	 * @param dir_path
	 * @return liste de String
	 * @throws Exception
	 */
	public void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	}
	
	public static List<String> sortListString(List<String> list){
		// Sorting
		Collections.sort(list, new Comparator<String>() {
		        @Override
		        public int compare(String fruit2, String fruit1)
		        {

		            return  fruit1.compareTo(fruit2);
		        }
		    });
		return list;
	}
	

	
	/**
	 * walk through a directory and returns a map which contains for each key (each directory) the list of
	 * files in it. 
	 * @param dirPath
	 * @return map key = directory (or root), value = list of the files/dirs contained in it
	 */
	public static HashMap<String, List<String>> dir2map(String dirPath){
		List<String[]> tuples = new ArrayList<String[]>();
		dirName = dirPath;
    	dir2tuples(new File(dirPath), dirName, tuples);
    	HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    	for(String[] tuple : tuples){
    		List<String> value = map.get(tuple[0]);
    		if(value == null) value = new ArrayList<String>();
    		value.add(tuple[1]);
    		map.put(tuple[0], value);
    	}
    	return map;
	}
	
	/**
	 * walk through a directory and return a list of tuples {location, file/dir name}
	 * directly modify the given tuples list (so no return statement needed)
	 * @param node
	 * @param entry
	 * @param tuples
	 */
	private static void dir2tuples (File node, String entry, List<String[]> tuples) {
		String[] tuple = {entry,node.getAbsolutePath()};//.replace(dirName, "")};
		tuples.add(tuple);
		if(node.isDirectory()){
			String[] subNote = node.list();
			Arrays.sort(subNote);
			for(String filename : subNote){
				if(node.toString().equals(dirName)){
					dir2tuples(new File(node, filename), node.toString().replace(dirName, "root"), tuples);
				}else{
					dir2tuples(new File(node, filename), node.toString().replace(dirName+File.separator, ""), tuples);
				}
			}
		}
	}
	
	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
}
