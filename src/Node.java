import java.util.ArrayList;
import java.util.List;

public class Node {
    String value;
    List<Node> children;

    public Node(String value, List<Node> children){
        this.value = value;
        this.children = children;
    }

    public Node(String value){
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }
}
