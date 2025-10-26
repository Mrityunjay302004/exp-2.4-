import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/ProductDB";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            con.setAutoCommit(false); // Enable transaction handling
            Scanner sc = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("\n1. Create  2. Read  3. Update  4. Delete  5. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("ProductID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("ProductName: ");
                        String name = sc.nextLine();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();

                        String insertSQL = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                            ps.setInt(1, id);
                            ps.setString(2, name);
                            ps.setDouble(3, price);
                            ps.setInt(4, qty);
                            ps.executeUpdate();
                            con.commit();
                            System.out.println("Product added successfully.");
                        } catch (SQLException e) {
                            con.rollback();
                            System.out.println("Error! Transaction rolled back.");
                            e.printStackTrace();
                        }
                        break;

                    case 2: // Read
                        String selectSQL = "SELECT * FROM Product";
                        try (Statement stmt = con.createStatement();
                             ResultSet rs = stmt.executeQuery(selectSQL)) {
                            while (rs.next()) {
                                System.out.println(rs.getInt("ProductID") + "\t" +
                                        rs.getString("ProductName") + "\t" +
                                        rs.getDouble("Price") + "\t" +
                                        rs.getInt("Quantity"));
                            }
                        }
                        break;

                    case 3: // Update
                        System.out.print("Enter ProductID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Price: ");
                        double newPrice = sc.nextDouble();
                        System.out.print("New Quantity: ");
                        int newQty = sc.nextInt();

                        String updateSQL = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
                        try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
                            ps.setDouble(1, newPrice);
                            ps.setInt(2, newQty);
                            ps.setInt(3, updateId);
                            ps.executeUpdate();
                            con.commit();
                            System.out.println("Product updated successfully.");
                        } catch (SQLException e) {
                            con.rollback();
                            System.out.println("Error! Transaction rolled back.");
                            e.printStackTrace();
                        }
                        break;

                    case 4: // Delete
                        System.out.print("Enter ProductID to delete: ");
                        int delId = sc.nextInt();
                        String deleteSQL = "DELETE FROM Product WHERE ProductID = ?";
                        try (PreparedStatement ps = con.prepareStatement(deleteSQL)) {
                            ps.setInt(1, delId);
                            ps.executeUpdate();
                            con.commit();
                            System.out.println("Product deleted successfully.");
                        } catch (SQLException e) {
                            con.rollback();
                            System.out.println("Error! Transaction rolled back.");
                            e.printStackTrace();
                        }
                        break;

                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }

            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// sql code

CREATE DATABASE IF NOT EXISTS ProductDB;
USE ProductDB;

CREATE TABLE IF NOT EXISTS Product (
    ProductID INT PRIMARY KEY,
    ProductName VARCHAR(50) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Quantity INT NOT NULL
);

-- Sample Data
INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES
(1, 'Laptop', 50000, 10),
(2, 'Mouse', 500, 50),
(3, 'Keyboard', 800, 30);
