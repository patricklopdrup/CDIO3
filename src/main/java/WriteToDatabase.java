import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Path("regnskab")
public class WriteToDatabase {

    private Connection createConnection() throws DALException {
        try {
            return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185098?"
                    + "user=s185098&password=Y4u3yF0PIq8BxTnnKSWJG");
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public void databaseConnectionTest(String category, String productName, double price, int amount, String date, String nameOfPurchaser) throws DALException {
        try(Connection c = createConnection()) {
            PreparedStatement prest = c.prepareStatement("insert into RegnskabsDatabase(Category, Productname, Price, Amount, DateOfPurchase, NameOfPurchaser) values(?,?,?,?,?,?)");
            prest.setString(1, category);
            prest.setString(2, productName);
            prest.setDouble(3, price);
            prest.setInt(4, amount);
            prest.setString(5, date);
            prest.setString(6, nameOfPurchaser);
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @POST
    public void sendToDatabase(@FormParam("category") String category,
                               @FormParam("productName") String name,
                               @FormParam("price") double price,
                               @FormParam("amount") int amount,
                               @FormParam("dateOfPurchase") String date,
                               @FormParam("buyersName") String nameOfPurchaser) throws DALException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            databaseConnectionTest(category, name, price, amount, date, nameOfPurchaser);
            System.out.println("hej");
        } catch (DALException e) {
            throw new DALException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        WriteToDatabase db = new WriteToDatabase();

        try {
            db.databaseConnectionTest("hej", "sko", 5, 3, "111212", "athu");
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
