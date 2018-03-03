package v2v;

import java.io.*;
import java.util.*;

public class V2v {
    private Set<String> dictionary = new HashSet<>();

    public V2v() {
        dictionary = new HashSet<>();
    }

    public void addFileToDict(File path) throws IOException {
        int size = dictionary.size();
        try (Scanner scan = new Scanner(path)) {
            scan.useDelimiter(" +");
            skipHeader(scan);

            while (scan.hasNext()) {
                dictionary.add(scan.next());
            }
        }
        System.out.println(dictionary.size() + " " + (dictionary.size() - size));
    }

    private void skipHeader(Scanner scan) {
        while (!scan.next().matches(".*\\nLines:.*")) {
        }
        scan.nextLine();
    }
}
