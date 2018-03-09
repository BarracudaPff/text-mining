package v2v;

import org.tartarus.snowball.ext.PorterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static v2v.V2v.skipHeader;

/**
 * Words to vector class.
 */
public class TextInfo {

    private Map<String, Integer> textVector;
    public String title;
    private File file;
    private PorterStemmer stemmer;

    public TextInfo(File file) throws FileNotFoundException {
        textVector = new HashMap<>();
        this.file = file;
        this.title = file.getName();
        stemmer = new PorterStemmer();
        v2v();
        fillFile();
    }

    private void fillFile() throws FileNotFoundException {
        FileReader reader = new FileReader(file);
        Scanner scan = new Scanner(reader);
        scan.useDelimiter(" +");
        skipHeader(scan);

        String word;
        while (scan.hasNext()) {
            word = scan.next().replaceAll("(\\n)|([\\p{P}])|[0-9]|(\\s)", "");
            stemmer.setCurrent(word);
            stemmer.stem();
            word = stemmer.getCurrent();

            if (word.length() > 2 && !V2v.dictionaryStop.contains(word)) {
                textVector.put(word, 1);
            }
        }
    }

    private void v2v() throws FileNotFoundException {
        textVector = new HashMap<>();
        for (String s : V2v.dictionary.keySet()) {
            textVector.put(s, 0);
        }
    }

    public Map<String, Integer> getTextVector() {
        return textVector;
    }
}
