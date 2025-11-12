package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstatement = null;
		Statement statement = null;
		ResultSet resulset = null;
		
		
		String[][]posts = {
				{ "1003", "2023-02-08","昨日は徹夜でした・・","13" },
	            { "1002", "2023-02-08","お疲れ様です！","12" },
	            { "1003", "2023-02-09","今日も頑張ります！","18" },
	            { "1001", "2023-02-09","無理は禁物ですよ！","17" },
	            { "1002", "2023-02-10","明日から連休ですね！","20" }
		};
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/challenge_java",
	                "root",
	                "N.yosida1994"
					);
			System.out.println("データベース接続成功"+con);
			
			//SQLクリエを準備
			String sql = "INSERT INTO posts(user_id,posted_at,post_content,likes) VALUES(?,?,?,?);";
			pstatement = con.prepareStatement(sql);
			
			//リストの1行目から順に読み込む
			int rowCnt = 0;
			for( int i = 0; i< posts.length; i++) {
				//SQLクリエの？部分をリストデータに置き換え
				pstatement.setString(1, posts[i][0]);//ユーザーid
				pstatement.setString(2, posts[i][1]);//投稿日
				pstatement.setString(3, posts[i][2]);//投稿内容
				pstatement.setString(4, posts[i][3]);//いいね数
				rowCnt += pstatement.executeUpdate();
			}
				//SQLクリエを実行（DBMSに送信）
				System.out.println("レコード追加を実行します");
				
				System.out.println( rowCnt + "件のレコードが追加されました");
			  
	
			 //SQLクリエを準備
				int target = 1002;
				String sql2 = "SELECT * FROM posts WHERE user_id ="+target;
	            statement = con.createStatement();

            
            //SQLクリエを実行（DBMSに送信）
            ResultSet result = statement.executeQuery(sql2);
            System.out.println("ユーザーidが"+target+"のレコードを検索しました");
            
            //SQLクリエ実行結果を抽出
            while(result.next()) {
            	String at = result.getString("posted_at");
            	String post = result.getString("post_content");
            	int like = result.getInt("likes");
            	System.out.println(result.getRow() + "件目：投稿日時=" +at + "/投稿内容=" + post + "/いいね数＝"+like
            	);
            }
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
		}	

	}

}
