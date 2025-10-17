package swingTest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel {

	Object [][]data;     //2차원 배열 데이터(행, 열) object는 최상위 자료형
	String []columnName;  //칼럼이름이 문자열로 1차원
	
	int rows, cols;
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnName.length;   //4개의 필드
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;         
		//5개의 레코드 (입력해 놓은 행의 갯수) 하나의 데이터를 알고 싶다면 data[]사용 0행부터 시작
	}
	
	//select count(*) from customer
	public void getRowCount(ResultSet rsScroll) {
		try {
			rsScroll.last();        //마지막으로 이동
			rows=rsScroll.getRow(); //전체 Row의 갯수를 리턴 rows=4개의 레코드
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return data[arg0][arg1];   //낱개씩 리턴
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnName[column];
	}
	
	
	public void setData(ResultSet rs) {
		try {
			ResultSetMetaData rsmd;
			
			rsmd=rs.getMetaData();
			cols=rsmd.getColumnCount();  //5개의 컬럼(필드)
			columnName=new String[cols];
			for(int i=0; i<cols; i++)
				columnName[i]=rsmd.getColumnName(i+1);
			data=new Object[rows][cols];
			int r=0, c;
			
			//데이터배열에 한개씩 한개씩 채우는 역할
			while(rs.next()){
				for( c=0; c<cols; c++){
						data[r][c]=rs.getObject(c+1);
					}
				r++;
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
