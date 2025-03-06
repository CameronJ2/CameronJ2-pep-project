import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Controller.SocialMediaController;
import Util.ConnectionUtil;
import io.javalin.Javalin;
import Util.FileUtil;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }

    public static void databaseSetup() {
        String sql = FileUtil.parseSQLFile("SocialMedia.sql");
        try (Connection conn = ConnectionUtil.getConnection()) {
            String[] statements = sql.split(";");
            
            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty()) {
                    try (PreparedStatement ps = conn.prepareStatement(statement)) {
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
