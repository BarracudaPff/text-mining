import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class WithWeka {

    private Instances cpu = null;
    private SimpleKMeans kmeans;

    private int clustN;
    private int[] assignments;

    public WithWeka(int n) {
        clustN = n;
    }

    public void loadArff(String arffInput) {
        DataSource source = null;
        try {
            source = new DataSource(arffInput);
            cpu = source.getDataSet();
        } catch (Exception e1) {
        }
    }

    public void clusterData() {
        kmeans = new SimpleKMeans();
        kmeans.setSeed(10);
        try {
            kmeans.setPreserveInstancesOrder(true);
            kmeans.setNumClusters(clustN);
            kmeans.buildClusterer(cpu);
            assignments = kmeans.getAssignments();

            fillFile("output.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillFile(String file) throws FileNotFoundException, UnsupportedEncodingException {
        int i = 0;
        ArrayList<Integer>[] table = new ArrayList[6];
        for (int j = 0; j < 6; j++) {
            table[j] = new ArrayList<>();
        }
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        for (int clusterNum : assignments) {
            table[clusterNum].add(i);
            writer.printf("Instance %d -> Cluster %d\n", i, clusterNum);
            i++;
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
    }
}
