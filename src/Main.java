import v2v.TextInfo;
import v2v.V2v;
import weka.core.*;

import java.io.*;

public class Main {

    private File root;
    private V2v v;
    private TextInfo[] textInfo;
    private int start;

    private static Main instance;

    public Main() {
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data");
        v = new V2v();
        textInfo = new TextInfo[5000];
        start = 0;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Start program");

        instance = new Main();

        instance.initDict("alt.atheism");
        instance.initDict("comp.graphics");
        instance.initDict("comp.os.ms-windows.misc");
        instance.initDict("comp.sys.ibm.pc.hardware");
        instance.initDict("comp.sys.mac.hardware");

        System.out.println("Dictionary created with size - " + V2v.dictionary.size());

        instance.createTextVector("alt.atheism");
        instance.createTextVector("comp.graphics");
        instance.createTextVector("comp.os.ms-windows.misc");
        instance.createTextVector("comp.sys.ibm.pc.hardware");
        instance.createTextVector("comp.sys.mac.hardware");

        System.out.println("Text vectors created");

        instance.createARFF();
        instance.fillARFF();

        System.out.println("ARFF file created and filled");

        WithWeka weka = new WithWeka(5);
        weka.loadArff("./test.arff");
        weka.clusterData();
    }

    /*
      Length 0 is 998
      Length 1 is 1000
      Length 2 is 1000
      Length 3 is 998
      Length 4 is 991
     */

    /**
     * Filling arff file with data from text vectors.
     *
     * @throws IOException
     */
    private void fillARFF() throws IOException {
        String filename = "test.arff";
        FileWriter fwriter = new FileWriter(filename, true);
        for (TextInfo info : textInfo) {
            if (info == null)
                continue;
            byte[] vector = new byte[V2v.dictionary.size()];
            for (String s : info.getTextVector().keySet()) {
                if (V2v.dictionary.containsKey(s))
                    if (info.getTextVector().get(s))
                        vector[V2v.dictionary.get(s)] = 1;
                    else
                        vector[V2v.dictionary.get(s)] = 0;
            }
            for (int i = 0; i < vector.length; i++) {
                fwriter.write(vector[i] + ",");
            }

            fwriter.write('\n');
        }
        fwriter.close();
    }

    /**
     * Creating arff file with field from dictionary.
     *
     * @throws IOException
     */
    private void createARFF() throws IOException {
        FastVector atts = new FastVector();
        for (int att = 0; att < V2v.dictionary.size(); att++) {
            atts.addElement(new Attribute("Attribute" + att, att));
        }
        Instances data = new Instances("MyRelation", atts, 0);
        BufferedWriter writer = new BufferedWriter(new FileWriter("./test.arff"));
        writer.write(data.toString());
        writer.flush();
        writer.close();
    }

    /**
     * Add new words to dictionary from documents in path folder.
     *
     * @param path Folder in main data path
     * @throws IOException
     */
    private void initDict(String path) throws IOException {
        File dirPath = new File(root, path);
        for (File file : dirPath.listFiles()) {
            v.addFileToDict(file);
        }
        System.out.println("Length |" + path + "| is " + dirPath.listFiles().length);
    }

    /**
     * Creating word-vectors using main dictionary from documents in path folder.
     *
     * @param path Folder in main data path
     * @throws FileNotFoundException
     */
    private void createTextVector(String path) throws FileNotFoundException {
        File dirPath = new File(root, path);
        int _start = start;
        for (int i = _start; i < _start + dirPath.listFiles().length; i++) {
            textInfo[i] = new TextInfo(dirPath.listFiles()[i - start]);
            start++;
        }
    }
}