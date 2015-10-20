import java.util.ArrayList;

/**
 * Created by XL on 20.10.2015.
 */
public class Tools {

    public static void printIntArray(int[] a) {
        System.out.print("[");
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if(!(i == a.length - 1)) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

    public static void printIntMatrix(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    public static void printSequence(Sequence seq) {
        for(int i = 0; i < seq.getContent().size(); i++) {
            System.out.print(seq.getContent().get(i).getFlavor());
            System.out.println("\n");
        }
    }

    public static void printArrayList(ArrayList<Nucleotide> nuc) {
        for(int i = 0; i < nuc.size(); i++) {
            System.out.print(nuc.get(i).getFlavor());
            System.out.println("\n");
        }
    }
}
