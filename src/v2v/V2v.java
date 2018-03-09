package v2v;

import org.tartarus.snowball.ext.PorterStemmer;

import java.io.*;
import java.util.*;

public class V2v {
    private static File file;
    public static Map<String, Integer> dictionary;
    public static Set<String> dictionaryStop;

    private static int index;
    private PorterStemmer stemmer;

    public V2v() throws FileNotFoundException {
        V2v.index = 0;
        dictionary = new HashMap<>();
        stemmer = new PorterStemmer();
        initDictionaryStop();
    }

    /**
     * Add new words to dictionary from documents in path folder.
     *
     * @param path Folder in main data path
     * @throws IOException
     */
    public void addFileToDict(File path) throws IOException {
        file = path;
        FileReader reader = new FileReader(path);
        Scanner scan = new Scanner(reader);
        scan.useDelimiter(" +");
        skipHeader(scan);

        String word;
        while (scan.hasNext()) {
            word = scan.next().replaceAll("(\\n)|([\\p{P}])|[0-9]|(\\s)", "");
            stemmer.setCurrent(word);
            stemmer.stem();
            word = stemmer.getCurrent();

            if (word.length() > 2 && !dictionaryStop.contains(word)) {
                if (!dictionary.containsKey(word))
                    V2v.index++;
                dictionary.put(word, index);
            }
        }
    }

    /**
     * Skipping header up to "Lines:"
     *
     * @param scan
     * @return
     * @throws FileNotFoundException
     */
    static Scanner skipHeader(Scanner scan) throws FileNotFoundException {
        try {
            while (!scan.next().replaceAll("(\\n)|([\\p{P}])|[0-9]|(\\s)", "").matches(".*Lines.*")) {
            }
            scan.nextLine();
        } catch (NoSuchElementException e) {
            Scanner scan2 = new Scanner(file);
            scan.useDelimiter(" +");
            scan = skipHeader2(scan2);
        }
        return scan;
    }

    /**
     * If no "Lines:", skipping header up to "Date:"
     *
     * @param scan
     * @return
     */
    private static Scanner skipHeader2(Scanner scan) {
        try {
            while (!scan.next().replaceAll("(\\n)|([\\p{P}])|[0-9]|(\\s)", "").matches(".*Date.*")) {
            }
            scan.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Level 2: " + file);
        }
        return scan;
    }

    private void initDictionaryStop() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(".\\dict"));
        dictionaryStop = new HashSet<>(Arrays.asList(scan.nextLine().split(", ")));
    }
}
