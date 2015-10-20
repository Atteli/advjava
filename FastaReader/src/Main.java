import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by XL on 20.10.2015.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        FastaReader fasta = new FastaReader();
        fasta.read(new BufferedReader(new FileReader("fastareader/data/exemplary_RNA_aln.fa")));

        //System.out.println(fasta.seqList);
        //for(int x = 0; x < fasta.seqList.size(); x++) {
        //    Tools.printSequence(fasta.seqList.get(x));
        //    System.out.println();
        //}


        fasta.write(60); //fails for < 10
    }
}
