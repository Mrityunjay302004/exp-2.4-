import java.sql.*;

public class FetchEmployee {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CompanyDB";
        String user = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EmpID, Name, Salary FROM Employee");

            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(empId + "\t" + name + "\t" + salary);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// sql code

CREATE DATABASE IF NOT EXISTS CompanyDB;
USE CompanyDB;

CREATE TABLE IF NOT EXISTS Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Salary DECIMAL(10,2) NOT NULL
);

-- Sample Data
INSERT INTO Employee (EmpID, Name, Salary) VALUES
(101, 'Amit', 50000),
(102, 'Sneha', 60000),
(103, 'Ravi', 55000);
