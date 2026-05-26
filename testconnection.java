import java.sql.Connection;
import java.sql.DriverManager;

public class testconnection {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://ep-quiet-hill-ap6cftb2-pooler.c-7.us-east-1.aws.neon.tech/neondb?sslmode=require";

        String user = "neondb_owner";

        String password = "npg_Wo5pL7yutxCR";

        try {

            Connection con = DriverManager.getConnection(url, user, password);

            System.out.println("Connected Successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}