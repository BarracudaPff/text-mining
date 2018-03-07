package v2v;

import java.io.*;
import java.util.*;

public class V2v {
    private static File file;
    public static Map<String, Integer> dictionary;

    public V2v() {
        dictionary = new HashMap<>();
    }

    /**
     * Add new words to dictionary from documents in path folder.
     *
     * @param path Folder in main data path
     * @throws IOException
     */
    public void addFileToDict(File path) throws IOException {
        this.file = path;
        try (Scanner scan = new Scanner(path)) {
            scan.useDelimiter(" +");
            skipHeader(scan);

            int i = 0;
            String word;
            while (scan.hasNext()) {
                word = scan.next().replaceAll("(\\n)|([\\p{P}])", "");
                if (word.length() > 2) {
                    dictionary.put(word, i);
                    i++;
                }
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
    protected static Scanner skipHeader(Scanner scan) throws FileNotFoundException {
        try {
            while (!scan.next().replaceAll("(\\n)|([\\p{P}])", "").matches(".*Lines.*")) {
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
    protected static Scanner skipHeader2(Scanner scan) {
        try {
            while (!scan.next().replaceAll("(\\n)|([\\p{P}])", "").matches(".*Date.*")) {
            }
            scan.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Level 2: " + file);
        }
        return scan;
    }
}
