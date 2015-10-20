import java.util.ArrayList;

/**
 * Created by XL on 18.10.2015.
 */
public class Sequence {
    private String header;
    private ArrayList<Nucleotide> content;

    public Sequence() {
        this.header = "";
        this.content = new ArrayList<Nucleotide>();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArrayList<Nucleotide> getContent() {
        return content;
    }

    public void setContent(ArrayList<Nucleotide> content) {
        this.content = content;
    }
}
