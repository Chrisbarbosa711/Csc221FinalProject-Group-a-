package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SuppressWarnings("serial")
public class SearchBox extends JFrame {
    private JTextField searchable = new JTextField(30);
    private JButton searchB = new JButton("Search");
    private JTable result = new JTable();
    private JPanel panel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(result);

    public void main(String[] args) {
        new SearchBox("SearchBox Example");
    }

    public SearchBox(String title) throws HeadlessException {
        super(title);
        setSize(600, 600);
        setResizable(true);
        addComponents();
        setTable();
        setVisible(true);
    }

    private void addComponents() {
        panel.add(searchable);
        panel.add(searchB);
        panel.add(scrollPane);
        add(panel);
    }
    //will add some sort of connection to a database to get the data. etc.
    private void setTable() {
        // Add your table configuration logic here
        // Example:
        result.setModel(new DefaultTableModel(
            new Object[][] {
                { "Row 1", "Column 1" },
                { "Row 2", "Column 2" },
            },
            new String[] {
                "Column 1", "Column 2"
            }
        ));
    }
}
