package restaurant_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuComponent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewPage extends JFrame {

    public static void main(String[] args) {
        new ViewPage();
    }

    JPanel listPanel;

    public ViewPage() {

        setTitle("Restaurant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton newOrder = new JButton("New Order");
        JButton viewOrder = new JButton("View Orders");

        JPanel top = new JPanel();
        top.add(newOrder);
        top.add(viewOrder);
        add(top, BorderLayout.NORTH);

        // 1. Initialize the panel that will hold the orders
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // 2. Wrap listPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(listPanel);
        
        // 3. Optional but recommended: improve scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // 4. Add the scrollPane to the center of the frame
        add(scrollPane, BorderLayout.CENTER);


        newOrder.addActionListener(e -> {
            dispose();
            new test();
        });

        viewOrder.addActionListener(e -> loadOrders());

        setVisible(true);
    }

    private void loadOrders() {
        menuDatabase db = new menuDatabase();
        ArrayList<HashMap> list = db.getOrders();

        // IMPORTANT: Use the 'listPanel' defined as a class variable 
        // Do NOT create 'JPanel listPanel = new JPanel()' here
        listPanel.removeAll();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        Font hFont = new Font("Calibri", Font.BOLD, 24);
        Font tableFont = new Font("Calibri", Font.PLAIN, 18);

        java.util.LinkedHashMap<Long, ArrayList<HashMap<String, Object>>> groupedOrders = new java.util.LinkedHashMap<>();
        for (HashMap map : list) {
            long oid = (long) map.get("oid");
            groupedOrders.putIfAbsent(oid, new ArrayList<>());
            groupedOrders.get(oid).add(map);
        }

        for (Long oid : groupedOrders.keySet()) {
            ArrayList<HashMap<String, Object>> items = groupedOrders.get(oid);
            long orderTotal = 0;
            String[] columns = {"Item Name", "Price", "Qty", "Subtotal"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (HashMap<String, Object> item : items) {
                long p = (long) item.get("price");
                long q = (long) item.get("qty");
                long sub = p * q;
                orderTotal += sub;
                model.addRow(new Object[]{item.get("items"), p, q, sub});
            }

            JPanel orderBlock = new JPanel(new BorderLayout());
            orderBlock.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLUE), "Order Details"
            ));

            // Use setPreferredSize instead of setMaximumSize for better JScrollPane behavior
            int height = 60 + (items.size() * 25); // Header + row heights
            orderBlock.setPreferredSize(new Dimension(750, height));
            orderBlock.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));

            JLabel headerLbl = new JLabel(" ORDER ID: " + oid + " | TOTAL AMOUNT: ₹" + orderTotal);
            headerLbl.setFont(hFont);
            headerLbl.setOpaque(true);
            headerLbl.setBackground(new Color(230, 242, 255));
            orderBlock.add(headerLbl, BorderLayout.NORTH);

            JTable table = new JTable(model);
            table.setFont(tableFont);
            table.setRowHeight(25);
            table.setEnabled(false);

            JPanel tableContainer = new JPanel(new BorderLayout());
            tableContainer.add(table.getTableHeader(), BorderLayout.NORTH);
            tableContainer.add(table, BorderLayout.CENTER);
            orderBlock.add(tableContainer, BorderLayout.CENTER);

            listPanel.add(orderBlock);
            listPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        // This triggers the JScrollPane to update its scrollbars
        listPanel.revalidate();
        listPanel.repaint();
    }

}
