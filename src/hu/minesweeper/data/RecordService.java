package hu.minesweeper.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class RecordService extends AbstractTableModel {
    private List<Record> records = new ArrayList<Record>();

    public RecordService() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("records.dat"));
            this.setRecords((List<Record>)ois.readObject());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public int getColumnCount() {

        return 3;
    }

    @Override
    public int getRowCount() {

        return records.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Record record = records.get(rowIndex);
        switch(columnIndex) {
            case 0: return record.getName();
            case 1: return record.getDifficulty();
            default: return record.getTime();
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "Name";
            case 1: return "Difficulty";
            default: return "Time";
        }

    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex) {
            case 0: return String.class;
            case 1: return String.class;
            default: return Integer.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;

    }


    public void addRecord(String name, String difficulty, Integer time) {
        Record record = new Record(name, difficulty, time);
        records.add(record);
    }


}
