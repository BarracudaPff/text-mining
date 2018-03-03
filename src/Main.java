import v2v.V2v;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Start program");

        V2v v = new V2v();
        for (File file : new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\alt.atheism").listFiles()) {
            v.addFileToDict(file);
        }
        v.addFileToDict(new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\alt.atheism\\49960"));
    }
}

