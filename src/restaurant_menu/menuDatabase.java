package restaurant_menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class menuDatabase {

    final String url = "jdbc:derby://localhost:1527/loginsystem";
    final String username = "root";
    final String password = "root";

    public menuDatabase() {

    }

    public ArrayList<HashMap> getOrders() {
        ArrayList<HashMap> list = new ArrayList<>();
        try {
            Connection con = new menuDatabase().getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * from customers");

            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>();
//                System.out.println("Order ID : " + rs.getLong("order_id"));
//                System.out.println("Items : " + rs.getString("items"));
//                System.out.println("Quantity : " + rs.getLong("quantity"));
//                System.out.println("price : " + rs.getLong("price"));
                map.put("oid", rs.getLong("order_id"));
                map.put("items", rs.getString("items"));
                map.put("price", rs.getLong("price"));
                map.put("qty", rs.getLong("quantity"));
                map.put("id", rs.getLong("id"));

                list.add(map);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    Connection getConnection() {
        Connection dm = null;
        try {
            dm = DriverManager.getConnection(url, username, password);

            String tableQuery
                    = "CREATE TABLE customers ("
                    + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "items VARCHAR(255), "
                    + "price VARCHAR(255),"
                    + "quantity VARCHAR(255)"
                    + ")";
            Statement st = dm.createStatement();
            st.execute(tableQuery);
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("X0Y32")) {
                System.out.println("table already created!!");
            } else {
                ex.printStackTrace();
                System.out.println("database connection error");
            }
        }
        return dm;
    }

}
