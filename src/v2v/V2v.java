package v2v;

import java.io.*;
import java.util.*;

public class V2v {
    private static File file;
    public static Map<String, Integer> dictionary;
    private Map<String, Map<String, Boolean>> textVectors;
    int count;

    public V2v() {
        dictionary = new HashMap<>();
        count = 0;
    }

    public void addFileToDict(File path) throws IOException {
        this.file = path;
        //int size = dictionary.size();
        try (Scanner scan = new Scanner(path)) {
            scan.useDelimiter(" +");
            //System.out.println("!");
            skipHeader(scan);

            int i = 0;
            while (scan.hasNext()) {
                if (dictionary.size() < 50000) {
                    dictionary.put(scan.next().replaceAll("(\\n)|([\\p{P}])", ""), i);
                } else scan.next();
                i++;
            }
        }
        //System.out.println(dictionary.size() + " " + (dictionary.size() - size));
    }

    protected static Scanner skipHeader(Scanner scan) throws FileNotFoundException {
        try {
            while (!scan.next().replaceAll("(\\n)|([\\p{P}])", "").matches(".*Lines.*")) {
            }
            scan.nextLine();
        } catch (NoSuchElementException e) {
            Scanner scan2 = new Scanner(file);
            scan.useDelimiter(" +");
            scan = skipHeader2(scan2);
            //System.out.println(file);
        }
        return scan;
    }

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
