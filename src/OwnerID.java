import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OwnerID {
	public static final String url = "jdbc:mysql://localhost:3306/hogehoge";
    public static final String user = "root";
    public static final String password = "";
    public static final String cardsql = "SELECT * FROM card";
    
    public static void main (String[] args) {
        try {
            try {
                try {
                	FileReader fr;
                	fr = new FileReader("box2card.tsv");
                    BufferedReader br = new BufferedReader(fr);

                    //読み込んだファイルを１行ずつ処理する
                    String line;
                    StringTokenizer token;
                	PreparedStatement ps1 = null;
                    Connection conn1 = null;
                	//JDBCドライバをロードする
                	Class.forName("com.mysql.jdbc.Driver");
                    //DBへのコネクションを作成する
                    conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/hogehoge?user=root&password=");
                    conn1.setAutoCommit(false);
					while ((line = br.readLine()) != null) {
						//区切り文字<TAB>で分割する
					    token = new StringTokenizer(line,"\t");
					    String boxId = token.nextToken();
					    String cardId = token.nextToken();
		                ps1 = conn1.prepareStatement("UPDATE card SET owner = '" + boxId + "' WHERE cardId = '" + cardId + "';");
		                System.out.println("UPDATE card SET owner = '" + boxId + "' WHERE cardId = '" + cardId + "';");
		                ps1.executeUpdate();
						conn1.commit();
						ps1.close();
					}
					//終了処理
	                br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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