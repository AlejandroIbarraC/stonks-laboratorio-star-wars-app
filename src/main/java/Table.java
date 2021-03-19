import javax.swing.*;
import java.util.List;

public class Table {
    JFrame f;

    Table(List<List<String>> data) {
        f = new JFrame();
        String columns[] = {"Nombre", "Altura (m)", "Año de Nacimiento"};

        String[][] array = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            array[i] = row.toArray(new String[row.size()]);
        }

        JTable jt = new JTable(array, columns);
        jt.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jt);
        f.add(sp);
        f.setSize(300,400);
        f.setVisible(true);
    }
}