package hu.minesweeper.ui;

import hu.minesweeper.data.RecordService;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class RecordFrame extends JFrame {
    private RecordService data;

    private void initComponents() {
        this.setLayout(new BorderLayout());
        JTable table = new JTable(data);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        this.add(scrollPane, BorderLayout.CENTER);

        table.setAutoCreateRowSorter(true);

    }


    @SuppressWarnings("unchecked")
    public RecordFrame() {
        super("Leaderboard");

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        data = new RecordService();

        setMinimumSize(new Dimension(400, 200));
        initComponents();
    }

}
