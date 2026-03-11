package restaurant_menu;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;

public class test extends JFrame {

    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
        new menuDatabase().getConnection();
        new test();
    }

    public test() {

        setTitle("Restaurant Menu");
        setSize(1000, 1000);
        setVisible(true);
//        setLayout(null);
        setLayout(new GridBagLayout());

//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowDeactivated(WindowEvent we) {
//                super.windowDeactivated(we); //To change body of generated methods, choose Tools | Templates.
//                System.exit(0);
//            }
//        });
        addItems();
        pack();
    }

    String[] name = {"Pizza", "Paneer Tikka", "Manchurian", "Avocado Sandwich", "Paneer Stick", "Paneer Tufani", "Paneer Handi", "Garlic Naan", "Butter Milk"};

    int[] price = {100, 200, 150, 150, 100, 100, 100, 50, 40};
    int[] quantity = new int[name.length];
    boolean[] active = new boolean[name.length];

    private void addItems() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font font = new Font("Calibri", Font.PLAIN, 22);
        Font hFont = new Font("Calibri", Font.BOLD, 26);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        JLabel itemH = new JLabel("ITEM");
        itemH.setFont(hFont);
        add(itemH, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        JLabel priceH = new JLabel("PRICE");
        priceH.setFont(hFont);
        add(priceH, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        JLabel qtyH = new JLabel("Quantity");
        qtyH.setFont(hFont);
        add(qtyH, gbc);

        gbc.gridx = 1;
        gbc.gridy = 18;
        JButton btnOrder = new JButton("ORDER");
        btnOrder.setFont(hFont);
        btnOrder.addActionListener((e) -> {
            dispose();
            new order(name, price, quantity, active);
            });
        add(btnOrder, gbc);

        gbc.gridx = 1;
        gbc.gridy = 19;
        JButton btnClear = new JButton("CLEAR");
        btnClear.setFont(hFont);
        add(btnClear, gbc);

        for (int i = 0; i < name.length; i++) {
            int finalI = i;
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.weightx = 2;
            JCheckBox cb = new JCheckBox(name[i]);
            cb.setFont(font);
            cb.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    if (ie.getStateChange() == ItemEvent.SELECTED) {
//                        System.out.println("selected");
                    } else if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    }

                    active[finalI] = ie.getStateChange() == ItemEvent.SELECTED;
                }
            });
            add(cb, gbc);

            gbc.gridx = 1;
            gbc.gridy = i + 1;
            gbc.weightx = 2;
            JLabel priceLbl = new JLabel("" + price[i]);
            priceLbl.setFont(font);
            add(priceLbl, gbc);

            gbc.gridx = 4;
            gbc.gridy = i + 1;
            gbc.weightx = 0.1;
            JTextField qty = new JTextField("" + quantity[finalI]);
            qty.setFont(font);
            add(qty, gbc);

            gbc.gridx = 2;
            gbc.gridy = i + 1;
            gbc.weightx = 0.01;
            JButton btn1 = new JButton("+");
            btn1.addActionListener((e) -> {
                quantity[finalI] += 1;
                qty.setText("" + quantity[finalI]);
            });
            add(btn1, gbc);

            gbc.gridx = 3;
            gbc.gridy = i + 1;
            gbc.weightx = 0.01;
            JButton btn2 = new JButton("-");
            btn2.addActionListener((e) -> {
                if (quantity[finalI] > 0) {
                    quantity[finalI] -= 1;
                    qty.setText("" + quantity[finalI]);
                }
            });
            add(btn2, gbc);
        }
    }
}
