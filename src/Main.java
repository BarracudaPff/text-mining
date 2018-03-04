import v2v.TextInfo;
import v2v.V2v;
import weka.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Start program");
        /*System.out.println("done 1");
        TextInfo[] textInfo = startDict();//119938

        int i = 0;
        for (TextInfo info : textInfo) {
            if (info != null)
                i++;
        }
        System.out.println(i);

        System.out.println("done 2");*/
        /*createARFF();
        fillARFF(textInfo);

        System.out.println("done 3");
        WithWeka weka = new WithWeka();
        weka.loadArff("./test.arff");
        weka.clusterData();*/

        ArrayList<Integer>[] table = new ArrayList[5];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ArrayList<Integer>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("output.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                String[] mas = line.split(" ");
                table[Integer.parseInt(mas[4])].add(Integer.parseInt(mas[1]));
                line = br.readLine();
            }
        }
        for (ArrayList<Integer> arrayList : table) {
            System.out.print("[");
            for (Integer o : arrayList) {
                if (o < 998)
                    System.out.print("1." + o + ", ");
                else if (o < 998 + 1000)
                    System.out.print("2." + (o - 1000) + ", ");
                else if (o < 998 + 2000)
                    System.out.print("3." + (o - 2000) + ", ");
                else if (o < 998 + 2998)
                    System.out.print("4." + (o - 2998) + ", ");
                else if (o < 998 + 2998 + 991)
                    System.out.print("5." + (o - 2998 - 991) + ", ");
            }
            System.out.println("]");
        }
        double maxTP = 0;
        double[] TP = new double[5];
        for (ArrayList<Integer> list : table) {
            for (Integer o : list) {
                if (o < 998)
                    TP[0]++;
                else if (o < 998 + 1000)
                    TP[1]++;
                else if (o < 998 + 2000)
                    TP[2]++;
                else if (o < 998 + 2998)
                    TP[3]++;
                else if (o < 998 + 2998 + 991)
                    TP[4]++;
            }
        }
/*
Length 0 is 998
Length 1 is 1000
Length 2 is 1000
Length 3 is 998
Length 4 is 991
 */
    }

    private static void fillARFF(TextInfo[] textInfo) throws IOException {
        String filename = "test.arff";
        FileWriter fwriter = new FileWriter(filename, true); //true will append the new instance
        for (TextInfo info : textInfo) {
            if (info == null)
                continue;
            byte[] vector = new byte[V2v.dictionary.size()];
            /*try {
                if (info.getTextVector().keySet() == null)
                    System.out.println(info.getTextVector().keySet());
            } catch (NullPointerException e) {
                System.out.println(info == null);
                //System.out.println(info.getTextVector());
            }*/
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

    private static void createARFF() throws IOException {
        /*ArrayList<Attribute> atts = new ArrayList<>();
        for (int att = 0; att < V2v.dictionary.size(); att++) {
            atts.add(new Attribute("Attribute" + att, att));
        }

        Instances data = new Instances("MyRelation", atts);*/
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

    private static TextInfo[] startDict() throws IOException {
        File root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.graphics");
        V2v v = new V2v();
        for (File file : root.listFiles()) {
            v.addFileToDict(file);
        }
        System.out.println("Length 0 is " + root.listFiles().length);
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\alt.atheism");
        for (File file : root.listFiles()) {
            v.addFileToDict(file);
        }
        System.out.println("Length 1 is " + root.listFiles().length);
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.os.ms-windows.misc");
        for (File file : root.listFiles()) {
            v.addFileToDict(file);
        }
        System.out.println("Length 2 is " + root.listFiles().length);
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.sys.ibm.pc.hardware");
        for (File file : root.listFiles()) {
            v.addFileToDict(file);
        }
        System.out.println("Length 3 is " + root.listFiles().length);
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.sys.mac.hardware");
        for (File file : root.listFiles()) {
            v.addFileToDict(file);
        }

        System.out.println("Length 4 is " + root.listFiles().length);
///////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("Size dict -> " + V2v.dictionary.size());
///////////////////////////////////////////////////////////////////////////////////////////////
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\alt.atheism");
        TextInfo[] textInfo = new TextInfo[5000];
        for (int i = 0; i < root.listFiles().length; i++) {
            textInfo[i] = new TextInfo(root.listFiles()[i]);
        }
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.graphics");
        for (int i = 1000; i < 1000 + root.listFiles().length; i++) {
            textInfo[i] = new TextInfo(root.listFiles()[i - 1000]);
        }
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.os.ms-windows.misc");
        for (int i = 2000; i < 2000 + root.listFiles().length; i++) {
            textInfo[i] = new TextInfo(root.listFiles()[i - 2000]);
        }
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.sys.ibm.pc.hardware");
        for (int i = 3000; i < 3000 + root.listFiles().length; i++) {
            textInfo[i] = new TextInfo(root.listFiles()[i - 3000]);
        }
        root = new File("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\comp.sys.mac.hardware");
        for (int i = 4000; i < 4000 + root.listFiles().length; i++) {
            textInfo[i] = new TextInfo(root.listFiles()[i - 4000]);
        }

        return textInfo;
    }
}