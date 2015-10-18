import java.util.ArrayList;

/**
 * Created by XL on 18.10.2015.
 */
public class Sequence {
    private ArrayList<String> header;
    private ArrayList<Nucleotide> content;

    public Sequence() {
        this.header = new ArrayList<String>();
        this.content = new ArrayList<Nucleotide>();
    }

    public ArrayList<String> getHeader() {
        return header;
    }

    public void setHeader(ArrayList<String> header) {
        this.header = header;
    }

    public ArrayList<Nucleotide> getContent() {
        return content;
    }

    public void setContent(ArrayList<Nucleotide> content) {
        this.content = content;
    }
}
