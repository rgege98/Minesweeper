package hu.minesweeper.ui;

import hu.minesweeper.data.RecordService;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class SaveFrame extends JFrame{
    private RecordService data;
    private int seconds;
    private String difficulty;

    private JTextField nameField = new JTextField(10);
    private JButton savebutton = new JButton("Save");

    private void initComponents(){

        JTextField timeField = new JTextField(10);
        JTextField difficultyField = new JTextField(8);

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        JLabel nameLabel = new JLabel("Name:");
        JLabel timeLabel = new JLabel("Time: ");
        JLabel difficultyLabel = new JLabel("Difficulty: ");

        timeField.setText(Integer.toString(seconds));
        difficultyField.setText(difficulty);
        timeField.setEditable(false);
        difficultyField.setEditable(false);

        p1.setLayout(new BorderLayout());
        p2.add(savebutton);
        p3.add(nameLabel);
        p3.add(nameField);
        p4.add(timeLabel);
        p4.add(timeField);
        p5.add(difficultyLabel);
        p5.add(difficultyField);
        p1.add(p3, BorderLayout.NORTH);
        p1.add(p4, BorderLayout.CENTER);
        p1.add(p5, BorderLayout.SOUTH);
        this.add(p1, BorderLayout.CENTER);
        this.add(p2, BorderLayout.SOUTH);
    }


    @SuppressWarnings("unchecked")
    public SaveFrame(int sec, String diff) {
        this.seconds = sec;
        this.difficulty = diff;
        setTitle("Save Highscore");
        setSize(300, 155);
        setLocation(200, 50);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();

        ActionListener al = new SaveButtonListener();
        savebutton.addActionListener(al);

        data = new RecordService();
    }



    public class SaveButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            data.addRecord(nameField.getText(), difficulty , seconds);
            try {

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("records.dat"));
                oos.writeObject(data.getRecords());
                oos.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            dispose();
        }

    }
}
