import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by XL on 18.10.2015.
 */
public class FastaReader {
    private ArrayList<Sequence> seqList;

    public FastaReader() {
        this.seqList = new ArrayList<Sequence>();
    }

    public int read (BufferedReader reader) throws IOException {
        String line = "";
        int headertest;
        Sequence se = new Sequence();
        boolean hdr;
        boolean firstHeader = true;
        while((hdr = ((char)(headertest = reader.read()) == '>')) || ((line = reader.readLine()) != null)) {

            if(hdr) {
                if(!firstHeader) {
                    seqList.add(se);
                    se = new Sequence();
                }
                firstHeader = false;
                se.getHeader().add((char)headertest + reader.readLine());
            } else {
                String temp = (char) headertest + line;
                char[] temp2 = temp.toCharArray();
                Nucleotide nuc = new Nucleotide();
                ArrayList<Nucleotide> temp3 = new ArrayList<Nucleotide>();
                for(int i = 0; i < temp2.length; i++) {
                    nuc.setFlavor(temp2[i]);
                    temp3.add(nuc);
                }

                if(se.getContent().isEmpty()) {
                    se.setContent(temp3);
                } else {
                    se.getContent().addAll(temp3);
                }
            }
        }
        return 0;
    }

    public static void main(String [] args) throws IOException {
        FastaReader fasta = new FastaReader();
        fasta.read(new BufferedReader(new FileReader("fastareader/data/exemplary_RNA_aln.fa")));
    }
}
