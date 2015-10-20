import java.io.*;
import java.util.ArrayList;

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

            if(hdr) { //header? +reading
                if(!firstHeader) {
                    se = new Sequence();
                }
                firstHeader = false;
                se.setHeader(((char) headertest + reader.readLine()));
                if(getMaxHdrLength() < se.getHeader().length()) {
                    setMaxHdrLength(se.getHeader().length());
                }
            } else { //sequence reading
                String temp = (char)headertest + line;

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
                    setSeqLength(se.getContent().size());
                    seqList.add(se);
                }
            }
        }
        return 0;
    }

    public void write (int alignmentScatter) { //try to prettyprint alignment; works for alignmentscatter >0
        if(alignmentScatter < 0) {
            System.out.println("Posstible Scatters: > 0");
            alignmentScatter = 1;
        }
        int rangeA = 0;
        int rangeB = 0;
        int digitA = 0;
        int digitB = 0;
        while(rangeA < getSeqLength()) {
            rangeB += alignmentScatter;
            if(rangeB > getSeqLength()) {
                rangeB = getSeqLength();
            }
            digitA = (getMaxHdrLength() + 3 + Integer.toString(rangeA).length());
            digitB = ((rangeB - rangeA) + 1 - Integer.toString(rangeA).length());
            if(digitB < digitA) { //prevent printf crash when scatter is too low
                digitB = digitB + Integer.toString(digitA).length();
            }
            System.out.printf("%"+ digitA + "s", rangeA + 1); //positions, e.g. 1 - 60
            System.out.printf("%" + digitB + "s", rangeB + "\n");
            for(int i = 0; i < seqList.size(); i++) {
                System.out.printf("%-"+ (getMaxHdrLength() + 3) +"s", seqList.get(i).getHeader());
                for(int j = rangeA; j < rangeB && j < getSeqLength(); j++) {
                    System.out.print(seqList.get(i).getContent().get(j).getFlavor());
                }
                System.out.print("\n");
            }
            rangeA = rangeB;

            System.out.print("\n");
        }
    }

}
