

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class listCardsInBox
 */
public class listCardsInBox extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String url = "jdbc:mysql://localhost:3306/hogehoge";
    public static final String user = "root";
    public static final String password = "";
    public static final String cardsql = "SELECT * FROM card WHERE owner = \"";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listCardsInBox() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println("get now");
        String boxID_owner = request.getParameter("findByBoxIdEqual");
        
        System.out.println(boxID_owner);

        //DBへのコネクションを作成する
        try {
            PreparedStatement ps = null;
            Connection conn = null;
            //JDBCドライバをロードする
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/btc?user=root&password=");
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement(); //PreparedStatementに直すマン
                ResultSet rs = stmt.executeQuery(cardsql + boxID_owner + "\"");
                conn = DriverManager.getConnection(url, user, password);
                
                //検索対象の文字
                //String boxID_owner = "Bxnym2vv";
                
                //DBからデータ取得
                while(rs.next()) {
                    String cardId = rs.getString("cardId");
                    String message = rs.getString("Message");
                    String type = rs.getString("Type");
                    String tags = rs.getString("tags");
                    long metrics = rs.getLong("metrics");
                    String owner = rs.getString("owner");
                    out.write("{" + "\"result\":true,\"data\":[{");
                    out.write("\"cardId\":\"" + cardId + "\",\"message\":\"" + message + "\",\"type\":\"" + type + "\",\"tag\":\"" + tags + "\",\"metrics\":\"" + metrics + "\",\"owner\":\"" + owner + "\"}");
                }
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } 
    }
    
}
