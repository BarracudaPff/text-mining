package v2v;

import org.tartarus.snowball.ext.PorterStemmer;

public class Stemming {
    PorterStemmer stemmer = new PorterStemmer();

    public Stemming(String input) {
        stemmer.setCurrent(input); //set string you need to stem
        stemmer.stem();  //stem the word
        System.out.println(stemmer.getCurrent());
    }
}
