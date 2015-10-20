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
                    nuc = new Nucleotide();
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

    public void write (Writer writer) throws IOException {
        try {
            for (int i = 0; i < seqList.size(); i++) {
                writer.write(seqList.get(i).getHeader() + "\n");
                for(int j = 0; j < seqList.get(i).getContent().size(); j++) {
                    writer.write(seqList.get(i).getContent().get(j).getFlavor());

                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't save File");
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) throws IOException {
        FastaReader fasta = new FastaReader();
        fasta.read(new BufferedReader(new FileReader("fastareader/data/exemplary_RNA_aln.fa")));
        fasta.write(new OutputStreamWriter(System.out));
    }
}
