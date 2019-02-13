package View;

import Models.DepartmentInfo;
import Models.DepartmentInfoModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;


public class DepartmentsInfoPanel extends JFrame
{
    public DepartmentsInfoPanel(ArrayList<DepartmentInfo> departmentInfoArrayList, int width, int height)
    {
        super();
        JPanel main = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setSize(width, height);

        TableModel model = new DepartmentInfoModel(departmentInfoArrayList);
        JTable table = new JTable(model);
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        main.add(new JScrollPane(table), c);
        add(main);
        setVisible(true);
    }
}



class FindDepartmentInfo extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;

    FindDepartmentInfo(Frame parent) {
        super(parent, true);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 10, 5, 10));
        content = new JPanel(new GridLayout(4, 1));
        content.setBorder(new EmptyBorder(1, 1, 5, 1));
        gui.add(content, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(4));
        gui.add(buttons, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        buttons.add(ok);
        ok.addActionListener(e->{
            result = OK_OPTION;
            setVisible(false);
        });

        JButton cancel = new JButton("Cancel");
        buttons.add(cancel);
        cancel.addActionListener(e->{
            result = CANCEL_OPTION;
            setVisible(false);
        });
        setContentPane(gui);
        setSize(360, 210);
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Department name");
        content.add(label1);
        JTextField textField1 = new JTextField(15);
        content.add(textField1);
        JLabel label2 = new JLabel("Date");
        content.add(label2);
        JFormattedTextField ftf = new JFormattedTextField();
        ftf.setValue(new Date());
        content.add(ftf);

        setVisible(true);
        return result;
    }
}
