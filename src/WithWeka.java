import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.Arrays;


public class WithWeka {

    private Instances cpu = null;
    private SimpleKMeans kmeans;

    public WithWeka() {
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
        kmeans.setSeed(1);
        try {
            kmeans.setPreserveInstancesOrder(true);
            kmeans.setNumClusters(5);
            kmeans.buildClusterer(cpu);
            int[] assignments = kmeans.getAssignments();
            int i = 0;
            ArrayList<Integer>[] table = new ArrayList[6];
            for (int j = 0; j < 6; j++) {
                table[j] = new ArrayList<>();
            }
            for (int clusterNum : assignments) {
                table[clusterNum].add(i);
                System.out.printf("Instance %d -> Cluster %d\n", i, clusterNum);
                i++;
            }
            for (ArrayList<Integer> integers : table) {
                System.out.print("[");
                for (Integer integer : integers) {
                    System.out.print((integer % 1000 + 1) + "." + (integer / 1000 * 1000) + ", ");
                }
                System.out.print("]");
                System.out.println();
            }

        } catch (Exception e1) {
        }
    }
}
