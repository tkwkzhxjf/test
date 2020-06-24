package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.RequestBook;

public class RequestDAO {

	// 자료요청 테이블 전체보기
	public ArrayList<RequestBook> getRequestTbl() {
		ArrayList<RequestBook> arrayList = new ArrayList<RequestBook>();
		Connection con=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs =null;
		try {
			con = DBUtil.getConnection();
			String query = "select * from RequestTBL;";
			preparedStatement = con.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				arrayList.add(new RequestBook(rs.getString("title"), rs.getString("content"), rs.getString("name"),rs.getString("date")
						,rs.getInt("No")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}

		return arrayList;

	}
	


}
