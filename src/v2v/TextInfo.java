package v2v;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static v2v.V2v.skipHeader;

/**
 * Words to vector class.
 */
public class TextInfo {

    private Map<String, Boolean> textVector;
    public String title;
    private File file;

    public TextInfo(File file) throws FileNotFoundException {
        textVector = new HashMap<>();
        this.file = file;
        this.title = file.getName();
        fillFile();
        v2v();
    }

    private void fillFile() throws FileNotFoundException {
        try (Scanner scan = new Scanner(file)) {
            scan.useDelimiter(" +");
            skipHeader(scan);

            while (scan.hasNext()) {
                textVector.put(scan.next().replaceAll("(\\n)|([\\p{P}])", ""), false);
            }
        }
    }

    private void v2v() throws FileNotFoundException {
        for (String s : textVector.keySet()) {
            if (V2v.dictionary.containsKey(s) && s.length() > 1)
                textVector.replace(s, true);
        }
    }

    public Map<String, Boolean> getTextVector() {
        return textVector;
    }
}
