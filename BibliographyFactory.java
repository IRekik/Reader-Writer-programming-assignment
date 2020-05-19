import java.io.*;
import java.util.*;

public class BibliographyFactory {

    public static void deleter2(File file) {
        file.delete();
    }
    
    public static boolean arrayListVerifier(ArrayList <File> mahmoud, String hamoudi) {
    	for (int q=0;q<mahmoud.size();q++) {
    		
    		if ((mahmoud.get(q)).getName().equals(hamoudi)) {
    			return true;
    		
    		}	
    	}
    	return false;
    }
    
    public static void fileDeleter() {
        for (int z = 1; z < 11; z++) {
            File file1 = new File("IEEE" + z + ".json");
            File file2 = new File("ACM" + z + ".json");
            File file3 = new File("NJ" + z + ".json");
            if (file1.delete()) {
                System.out.println(file1.getName() + " deleted.");
            } else {
                System.out.println(file1.getName() + " has already been deleted.");
            }
            if (file2.delete()) {
                System.out.println(file2.getName() + " deleted.");
            } else {
                System.out.println(file2.getName() + " has already been deleted.");
            }
            if (file3.delete()) {
                System.out.println(file3.getName() + "deleted.");
            } else {
                System.out.println(file3.getName() + "has already been deleted");
            }
        }
    }

    public static int processFilesForValidation(String fileName, int k) {
        PrintWriter write = null;
        int failCounter = 0;
        String holder = "";
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> label = new ArrayList<String>();
        String immediateData = "";
        String IEEE = "";
        String ACM = "";
        String NJ = "";
        Scanner sc = null;
        String author = "";
        String journal = "";
        String title = "";
        String year = "";
        String volume = "";
        String number = "";
        String pages = "";
        String keywords = "";
        String doi = "";
        String ISSN = "";
        String month = "";
        try {
            sc = new Scanner(new FileInputStream(fileName));
        } catch (Exception e) {

        }
        int counter = 0;
        boolean bool = false;
        int articleCount = 1;
        while (sc.hasNextLine()) {
            bool = false;
            holder = sc.nextLine();
            if (holder.length() != 0) {
                if (holder.charAt(0) != '@' && holder != "" && holder.indexOf('{') != -1) {
                    label.add(holder.substring(0, holder.indexOf('=')));
                    data.add(holder.substring((holder.indexOf('=')) + 2, holder.indexOf('}')));
                    immediateData = holder.substring((holder.indexOf('=')) + 2, holder.indexOf('}'));
                    bool = true;
                    counter++;
                }
            }
            if (bool == true) {
                if (immediateData.equals("")) {
                    System.out.println("Error: Detected empty field!\n*****************************\nProblem detected with input file: " + fileName + "\nField "
                            + label.get(counter - 1) + " is empty. Processing stopped at this point. Other empty fields may be present as well!");

                    sc.close();
                    failCounter++;

                    return k;

                }
            }
            if (holder.length() != 0) {
                if (holder.charAt(0) == '}') {
                    author = data.get(label.indexOf("author"));
                    journal = data.get(label.indexOf("journal"));
                    title = data.get(label.indexOf("title"));
                    year = data.get(label.indexOf("year"));
                    volume = data.get(label.indexOf("volume"));
                    number = data.get(label.indexOf("number"));
                    pages = data.get(label.indexOf("pages"));
                    keywords = data.get(label.indexOf("keywords"));
                    doi = data.get(label.indexOf("doi"));
                    ISSN = data.get(label.indexOf("ISSN"));
                    month = data.get(label.indexOf("month"));
                    IEEE = (author + ". " + title + ", " + journal + ", vol." + volume + ",no. " + number + ", p." + pages + ", " + month + " " + year);
                    ACM = "[" + articleCount + "]\t" + author + " et al. " + year + ". " + title + ". " + journal
                            + ". " + volume + ", " + number + " (" + year + "), " + pages + ". DOI:https://doi.org/"
                            + doi;
                    String newAuthor = author.replaceAll("and", "&");
                    NJ = (newAuthor + ". " + title + ". " + journal + ". " + volume + ", " + pages + "(" + year
                            + ")");
                    articleCount++;
                    try {
                        write = new PrintWriter(new FileOutputStream("IEEE" + k + ".json", true));
                        write.println(IEEE);
                        write.close();
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                    }
                    try {
                        write = new PrintWriter(new FileOutputStream("ACM" + k + ".json", true));
                        write.println(ACM);
                        write.close();
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                    }
                    try {
                        write = new PrintWriter(new FileOutputStream("NJ" + k + ".json", true));
                        write.println(NJ);
                        write.close();
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                    }
                    label.clear();
                    data.clear();
                    counter = 0;
                }
            }

        }
        sc.close();
        return 0;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to BibliographyFactory!");
        System.out.println("");
        Scanner scanner = null;
        PrintWriter pw = null, px = null, pz = null;
        String fileName = null;
        String createdFileName1 = null;
        String createdFileName2 = null;
        String createdFileName3 = null;
        ArrayList<File> IEEE = new ArrayList<>();
        ArrayList<File> ACM = new ArrayList<>();
        ArrayList<File> NJ = new ArrayList<>();

        int i = 1;
        int j = 1;
        // Open all the files
        try {
            for (i = 1; i < 11; i++) {
                fileName = ("Latex" + i + ".bib");
                scanner = new Scanner(new FileInputStream(fileName));

            }

        } // Catch block used if the file does not open or exist
        catch (FileNotFoundException e) {
            System.out.println("\nCould not open file Latex" + i + ".bib for reading.\n\nPlease check if file exists! Program will terminate after closing any opened files.");
            scanner.close();
            System.exit(0);
        }
        for (j = 1; j < 11; j++) {
            try {
                createdFileName1 = ("IEEE" + j + ".json");
                File a = new File(createdFileName1);
                pw = new PrintWriter(a);
                IEEE.add(a);
            } catch (FileNotFoundException e) {
                System.out.println("The system has not been able to create " + createdFileName1);
                fileDeleter();
                System.out.println("\n\nSystem will now close");
                System.exit(0);
            }
            pw.close();
            try {
                createdFileName2 = ("ACM" + j + ".json");
                File b = new File(createdFileName2);
                pw = new PrintWriter(b);
                ACM.add(b);
            } catch (FileNotFoundException e) {
                System.out.println("The system has not been able to create " + createdFileName2);
                fileDeleter();
                System.out.println("\n\nSystem will now close");
                System.exit(0);
            }
            pw.close();
            try {
                createdFileName3 = ("NJ" + j + ".json");
                File c = new File(createdFileName3);
                pw = new PrintWriter(c);
                NJ.add(c);
            } catch (FileNotFoundException e) {
                System.out.println("The system has not been able to create " + createdFileName3);
                fileDeleter();
                System.out.println("\n\nSystem will now close");
                System.exit(0);
            }
            pw.close();
        }
        
        String argument = "";
        int deleteFileCounter=0;
        for (int k = 1; k < 11; k++) {
            argument = "Latex" + k + ".bib";
            if (processFilesForValidation(argument, k) != 0) {
            	deleteFileCounter++;
                NJ.get(k - 1).deleteOnExit();
                IEEE.get(k - 1).deleteOnExit();
                ACM.get(k - 1).deleteOnExit();
                NJ.set(k - 1,new File("emptyFile1"));
                IEEE.set(k - 1,new File("emptyFile2"));
                ACM.set(k - 1,new File("emptyFile3"));
                
            }

        }
        System.out.println("");
        System.out.println("A total of "+deleteFileCounter+" files were invalid, and could not be processed. All other " + (10-deleteFileCounter) + " \"Valid\" files have been created.");
        System.out.println("");
        System.out.println("Please enter the name of one of the files that you need to review:");
        Scanner k = new Scanner(System.in);
        String user;
        BufferedReader read = null;
        int n = 0;
        user=k.next();
        String content="";
        if (arrayListVerifier(IEEE,user) || arrayListVerifier(ACM,user) || arrayListVerifier(NJ,user)) {
        	try {
        		read = new BufferedReader(new FileReader(user));
        		System.out.println("Here are the contents of the succesfully created Jason File:"+user);
        		while (true) {
        			content=read.readLine();
        			if (content==null) {
        				break;
        			}
        			System.out.println(content);
        			System.out.println("");
        		}
        	}
        	catch (IOException e) {
        		System.out.println(e);
        	}
        }
        else {
        	System.out.println("Could not open input file. File does not exist; possibly it could not be created.\n\nHowever, you be allowed another chance to enter another file name.\nPlease enter the name of one "
        			+ "of the files that you need to review:");
        	user=k.next();
        	if (arrayListVerifier(IEEE,user) || arrayListVerifier(ACM,user) || arrayListVerifier(NJ,user)) {
             	try {
             		read = new BufferedReader(new FileReader(user));
             		System.out.println("Here are the contents of the succesfully created Jason File:"+user);
             		while (true) {
             			content=read.readLine();
             			if (content==null) {
             				break;
             			}
             			System.out.println(content);
             			System.out.println("");
             			
             		}
             	}
             	catch (IOException e) {
             		System.out.println(e);
             	}
             }
             else {
             	System.out.println("\n\nCould not open input file again! Either does not exist or could not be created.\nSorry! I am unable to display your desired files! Program will exit!");
             	System.exit(0);
             }
        }
        read.close();
        System.out.println("\nGoodbye! Hope you have enjoyed creating the needed files using BlibliographyFactory.");
	    
    }

}
