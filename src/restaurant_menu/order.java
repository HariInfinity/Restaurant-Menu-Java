package restaurant_menu;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class order extends JFrame {

    String[] name;
    int[] price;
    int[] quantity;
    boolean[] active;
    
    long orderId = System.currentTimeMillis();

    public order(String[] name, int[] price, int[] quantity, boolean[] active) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.active = active;

        setTitle("Order Page");
        setSize(1000, 1000);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font font = new Font("Calibri", Font.PLAIN, 22);
        Font hFont = new Font("Calibri", Font.BOLD, 26);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        JLabel itemHead = new JLabel("ITEM");
        itemHead.setFont(hFont);
        add(itemHead, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        JLabel priceHead = new JLabel("PRICE");
        priceHead.setFont(hFont);
        add(priceHead, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        JLabel qtyHead = new JLabel("QUANTITY");
        qtyHead.setFont(hFont);
        add(qtyHead, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        JLabel totalHead = new JLabel("TOTAL");
        totalHead.setFont(hFont);
        add(totalHead, gbc);

        int row = 1;
        int grandTotal = 0;
        for (int i = 0; i < name.length; i++) {
            if (active[i] && quantity[i] > 0) {

                int itemTotal = price[i] * quantity[i];
                grandTotal += itemTotal;

                gbc.gridx = 0;
                gbc.gridy = row;
                JLabel item = new JLabel(name[i]);
                item.setFont(font);
                add(item, gbc);

                gbc.gridx = 1;
                JLabel p = new JLabel(String.valueOf(price[i]));
                p.setFont(font);
                add(p, gbc);

                gbc.gridx = 2;
                JLabel q = new JLabel(String.valueOf(quantity[i]));
                q.setFont(font);
                add(q, gbc);

                gbc.gridx = 3;
                JLabel r = new JLabel(String.valueOf(itemTotal));
                r.setFont(font);
                add(r, gbc);

                row++;
            }
        }

        gbc.gridx = 2;
        gbc.gridy = row + 1;
        JLabel gtLabel = new JLabel("GRAND TOTAL");
        gtLabel.setFont(hFont);
        add(gtLabel, gbc);

        gbc.gridx = 3;
        JLabel gtValue = new JLabel(String.valueOf(grandTotal));
        gtValue.setFont(hFont);
        add(gtValue, gbc);

        gbc.gridx = 3;
        gbc.gridy = row + 2;
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setFont(hFont);
        submitBtn.addActionListener(e -> {
           menuDatabase db = new menuDatabase();
    Connection con = db.getConnection();

    String insertQuery =
        "INSERT INTO customers (order_id, items, price, quantity) VALUES (?, ?, ?, ?)";

    try {
        PreparedStatement ps = con.prepareStatement(insertQuery);

        long orderId = System.currentTimeMillis(); // unique ID for this order

        for (int i = 0; i < name.length; i++) {
            if (active[i] && quantity[i] > 0) {
                ps.setLong(1, orderId);            // unique order ID
                ps.setString(2, name[i]);
                ps.setString(3, String.valueOf(price[i]));
                ps.setString(4, String.valueOf(quantity[i]));

                ps.executeUpdate();
            }
        }

        ps.close();
        con.close();

        System.out.println("Order saved successfully! Order ID: " + orderId);

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
        });
        add(submitBtn, gbc);

        pack();
        setVisible(true);
    }
}
