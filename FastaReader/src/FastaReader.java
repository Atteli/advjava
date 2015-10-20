import javax.management.openmbean.InvalidOpenTypeException;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by XL on 18.10.2015.
 */
public class FastaReader {
    private ArrayList<Sequence> seqList;
    private int maxHdrLength = 0;
    private int seqLength = 0;

    public void setMaxHdrLength(int length) {
        this.maxHdrLength = length;
    }

    public int getMaxHdrLength() {
        return this.maxHdrLength;
    }

    public void setSeqLength(int length) {
        this.seqLength = length;
    }

    public int getSeqLength() {
        return this.seqLength;
    }

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
                se.setHeader(((char) headertest + reader.readLine()));
                if(getMaxHdrLength() < se.getHeader().length()) {
                    setMaxHdrLength(se.getHeader().length());
                }
            } else {
                String temp = (char)headertest + line;
                //System.out.println("temp: " + temp);
                char[] temp2 = temp.toCharArray();
                //System.out.print("chararray temp2: ");
                //Tools.printCharArray(temp2);
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
                    Tools.printArrayList(temp3);
                    System.out.println();
                    Tools.printArrayList(se.getContent());
                    System.out.println();
                    setSeqLength(se.getContent().size());
                }
            }
        }
        return 0;
    }

    public void write (int alignmentScatter) {

        System.out.println("Max Hdr Length: " + getMaxHdrLength());
        System.out.println("Seq Length: " + getSeqLength());
        if(alignmentScatter < 1) {
            System.out.println("Invalid Alignment Scatter Value. Using minimal scattering (1)");
            alignmentScatter = 1;
        }

        int rangeA = 1;
        int rangeB = alignmentScatter;
        int digitA = 0;
        int digitB = 0;
        while(rangeA < getSeqLength()) {
            if(rangeB > getSeqLength()) {
                rangeB = getSeqLength();
            }
            digitA = (getMaxHdrLength() + 3 + Integer.toString(rangeA).length());
            digitB = ((rangeB - rangeA) + 1 - Integer.toString(rangeA).length());
            if(digitB < digitA) {
                digitB = digitB + Integer.toString(digitA).length();
            }
            System.out.printf("%"+ digitA + "s", rangeA);
            System.out.printf("%" + digitB + "s", rangeB + "\n");
            for(int i = 0; i < seqList.size(); i++) {
                System.out.printf("%-"+ (getMaxHdrLength() + 3) +"s", seqList.get(i).getHeader());
                for(int j = rangeA; j < rangeB && j < getSeqLength(); j++) {
                    System.out.print(seqList.get(i).getContent().get(j).getFlavor());
                }
                System.out.print("\n");
            }
            rangeA = rangeB + 1;
            rangeB += alignmentScatter;
            System.out.print("\n");
        }
    }


    public static void main(String[] args) throws IOException {
        FastaReader fasta = new FastaReader();
        fasta.read(new BufferedReader(new FileReader("fastareader/data/exemplary_RNA_aln.fa")));

        for(int x = 0; x < fasta.seqList.size(); x++) {
            Tools.printSequence(fasta.seqList.get(x));
            System.out.println();
        }


        //fasta.write(60);
    }
}
