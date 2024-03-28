import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class BFS_GUI extends JFrame {
    private JTextArea outputTextArea;
    private JTextField startNodeField;
    private JTextField targetNodeField;

    public BFS_GUI() {
        setTitle("Breadth-First Search GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel numNodesLabel = new JLabel("Number of nodes:");
        JTextField numNodesField = new JTextField();
        JLabel numEdgesLabel = new JLabel("Number of edges:");
        JTextField numEdgesField = new JTextField();
        JLabel startNodeLabel = new JLabel("Start node:");
        startNodeField = new JTextField();
        JLabel targetNodeLabel = new JLabel("Target node:");
        targetNodeField = new JTextField();

        inputPanel.add(numNodesLabel);
        inputPanel.add(numNodesField);
        inputPanel.add(numEdgesLabel);
        inputPanel.add(numEdgesField);
        inputPanel.add(startNodeLabel);
        inputPanel.add(startNodeField);
        inputPanel.add(targetNodeLabel);
        inputPanel.add(targetNodeField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numNodes = Integer.parseInt(numNodesField.getText());
                int numEdges = Integer.parseInt(numEdgesField.getText());
                Map<String, Node> nodes = new HashMap<>();

                for (int i = 0; i < numNodes; i++) {
                    String nodeName = JOptionPane.showInputDialog("Enter node name:");
                    nodes.put(nodeName, new Node(nodeName));
                }

                for (int i = 0; i < numEdges; i++) {
                    String edge = JOptionPane.showInputDialog("Enter edge in the format 'sourceNode -> targetNode':");
                    String[] nodeNames = edge.split(" -> ");
                    nodes.get(nodeNames[0]).getChildren().add(nodes.get(nodeNames[1]));
                }

                String startNodeName = startNodeField.getText();
                String targetNodeName = targetNodeField.getText();

                Node startNode = nodes.get(startNodeName);
                Node targetNode = nodes.get(targetNodeName);

                if (startNode == null || targetNode == null) {
                    outputTextArea.setText("Invalid node names.");
                } else {
                    String result = BreathFirstSearch(startNode, targetNode);
                    outputTextArea.setText(result);
                }
            }
        });

        outputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String BreathFirstSearch(Node start, Node target) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.add(current);

            if (current.getValue().equals(target.getValue())) {
                return "Found target: " + target.getValue() + "\nPath: " + getPath(parentMap, start, target);
            }

            for (Node child : current.getChildren()) {
                if (!visited.contains(child) && !queue.contains(child)) {
                    parentMap.put(child, current);
                    queue.add(child);
                }
            }
        }

        return "Target not found: " + target.getValue();
    }

    private String getPath(Map<Node, Node> parentMap, Node start, Node target) {
        List<String> path = new ArrayList<>();
        Node node = target;
        while (node != null) {
            path.add(node.getValue());
            node = parentMap.get(node);
        }
        Collections.reverse(path);
        return String.join(" -> ", path);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BFS_GUI bfsGui = new BFS_GUI();
                bfsGui.setVisible(true);
            }
        });
    }
}


