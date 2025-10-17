package swingTest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;




public class test implements ActionListener{

	private JFrame frame;
	private JTextField txtNo;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtTel;
	private JTable table;
	public JButton btnTotal, btnAdd, btnDel, btnSearch, btnCancel;
	String driver="oracle.jdbc.OracleDriver";
	String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user="system";
	String password="StrongPwd!123";
	Connection conn=null;
	PreparedStatement pstmtin, pstmtse, pstmtup, pstmtde;
	PreparedStatement pstmtseScroll;
	Statement stmt=null;
	ResultSet rs, rsScroll;
	
	private static final int NONE=0;
	private static final int ADD=1;
	private static final int DELETE=2;
	private static final int SEARCH=3;
	private static final int TOTAL=4;
	
	int cmd=NONE;
	
	MyModel model=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
		dbconn();
		init();
	}
	
	private void init() {
		txtNo.setText("");
		txtName.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtNo.setEditable(false);
		txtName.setEditable(false);
		txtEmail.setEditable(false);
		txtTel.setEditable(false);
	}
	private void dbconn() {
		try {
			Class.forName(driver);   //1단계
			conn=DriverManager.getConnection(url, user, password);  //연결
			System.out.println("db연결");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("고객관리");
		frame.setBounds(100, 100, 627, 355);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("번호");
		lblNewLabel.setBounds(38, 34, 57, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(38, 88, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("이메일");
		lblNewLabel_2.setBounds(38, 142, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("전화번호");
		lblNewLabel_3.setBounds(38, 196, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtNo = new JTextField();
		txtNo.setBounds(112, 31, 116, 21);
		frame.getContentPane().add(txtNo);
		txtNo.setColumns(10);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(112, 85, 116, 21);
		frame.getContentPane().add(txtName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(112, 139, 116, 21);
		frame.getContentPane().add(txtEmail);
		
		txtTel = new JTextField();
		txtTel.setColumns(10);
		txtTel.setBounds(112, 193, 116, 21);
		frame.getContentPane().add(txtTel);
		
		
		btnTotal = new JButton("전체보기");
		btnTotal.setBounds(21, 263, 97, 23);
		frame.getContentPane().add(btnTotal);
		
		btnAdd = new JButton("추가");
		btnAdd.setBounds(139, 263, 97, 23);
		frame.getContentPane().add(btnAdd);
		
		btnDel = new JButton("삭제");
		btnDel.setBounds(257, 263, 97, 23);
		frame.getContentPane().add(btnDel);
		
		btnSearch = new JButton("검색");
		btnSearch.setBounds(375, 263, 97, 23);
		frame.getContentPane().add(btnSearch);
		
		btnCancel = new JButton("취소");
		btnCancel.setBounds(493, 263, 97, 23);
		frame.getContentPane().add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(257, 34, 331, 188);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnTotal.addActionListener(this);
		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);
		btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	
	public void setText(int command) {
		switch (command) {
		case ADD:
			txtNo.setEditable(true);
			txtName.setEditable(true);
			txtEmail.setEditable(true);
			txtTel.setEditable(true);
			break;
		case DELETE:
			txtNo.setEditable(true);
			txtName.setEditable(false);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			break;
		case SEARCH:
			txtNo.setEditable(false);
			txtName.setEditable(true);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			break;
		case NONE:
			init();
			break;
	    }
		setBtn(command);
	}
	public void setBtn(int command) {
		btnTotal.setEnabled(false);
		btnAdd.setEnabled(false);
		btnDel.setEnabled(false);
		btnSearch.setEnabled(false);
		switch(command) {
		case ADD:
			btnAdd.setEnabled(true);
			cmd=ADD;
			break;
		case DELETE:
			btnDel.setEnabled(true);
			cmd=DELETE;
			break;
		case SEARCH:
			btnSearch.setEnabled(true);
			cmd=SEARCH;
			break;
		case TOTAL:
			btnTotal.setEnabled(true);
			cmd=TOTAL;
			break;
		case NONE:
			btnAdd.setEnabled(true);
			btnDel.setEnabled(true);
			btnSearch.setEnabled(true);
			btnTotal.setEnabled(true);
			cmd=NONE;
			break;
		}
	}
	// 추가부분**********************************************************************
    public void add() {
    	try {
    		String sql="insert into customer(code, name, email, tel) values(?,?,?,?)";
			String strNo=txtNo.getText();
			String strName=txtName.getText();
			String strEmail=txtEmail.getText();
			String strTel=txtTel.getText();
			if(strNo.length()<1 || strName.length()<1) {
				JOptionPane.showMessageDialog(null, "번호와 이름은 필수입니다");
				return;
			}
			pstmtin=conn.prepareStatement(sql);
			pstmtin.setString(1, strNo);
			pstmtin.setString(2, strName);
			pstmtin.setString(3, strEmail);
			pstmtin.setString(4, strTel);
			int r=pstmtin.executeUpdate();
		    if(r>=1) JOptionPane.showMessageDialog(null, "성공");
		    else JOptionPane.showMessageDialog(null, "실패");
		    
		} catch (Exception e) {
			e.printStackTrace();
		}	
    	total();
	}
    
    // 삭제부분**********************************************************************
    public void del() {
    	try {
    		String sql=" delete from customer where code=?";
			String strNo=txtNo.getText();
			if(strNo.length()<1) {
				JOptionPane.showMessageDialog(null, "번호는 필수입니다");
				return;
			}
			pstmtde=conn.prepareStatement(sql);
			pstmtde.setInt(1, Integer.valueOf(strNo)); //문자열을 정수로 변환하기 방법
			int r=pstmtde.executeUpdate();
		    if(r>=1) JOptionPane.showMessageDialog(null, "삭제성공");
		    else JOptionPane.showMessageDialog(null, "삭제실패");
		    
		} catch (Exception e) {
			e.printStackTrace();
		}	
    	total();
	}
    
    // 전체검색**********************************************************************
    public void total() {
    	try {
    		String sql=" select * from customer";
    		pstmtse=conn.prepareStatement(sql);     //정적인 문장통
    		pstmtseScroll=conn.prepareStatement(sql,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);    //동적인 문장통
    		
    		rs=pstmtse.executeQuery();              //정적인 결과통
    		rsScroll=pstmtseScroll.executeQuery();  //동적인 결과통
    		
    		if(model==null) model=new MyModel();
    		model.getRowCount(rsScroll);            //row개수저장
    		model.setData(rs);                      //data[][]배열에 한개씩 한개씩 저장되는 동장
    		table.setModel(model);                  //
    		table.updateUI();
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    // 부분검색**********************************************************************
    public void search() {
    	try {
    		String sql=null;
    		String strName=txtName.getText();
    		if(strName.length()<1) {
				JOptionPane.showMessageDialog(null, "이름은 필수입니다");
				return;
    		}
    		sql=" select * from customer where name like '%"+strName+"%'";
    		pstmtse=conn.prepareStatement(sql);     //정적인 문장통
    		pstmtseScroll=conn.prepareStatement(sql,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);    //동적인 문장통
    		
    		//pstmtse.setString(1, "%" + strName + "%");
    		//pstmtseScroll.setString(1, "%" + strName + "%");
    		
    		rs=pstmtse.executeQuery();              //정적인 결과통
    		rsScroll=pstmtseScroll.executeQuery();  //동적인 결과통
    		
    		if(model==null) model=new MyModel();
    		model.getRowCount(rsScroll);            //row개수저장
    		model.setData(rs);                      //data[][]배열에 한개씩 한개씩 저장되는 동장
    		table.setModel(model);                  
    		table.updateUI();
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnTotal) {
			setText(TOTAL);
			frame.setTitle("전체내용보기");
			total();
		    System.out.println("전체보기");
		}
		else if(e.getSource()==btnAdd) {
			if(cmd!=ADD) {
				frame.setTitle("데이터추가");
				setText(ADD);
				return;
			}
			add();  //db에 추가
			frame.setTitle("데이터추가되었음");
			System.out.println("추가");
		}
		else if(e.getSource()==btnDel) {
			if(cmd!=DELETE) {
				frame.setTitle("데이터삭제");
				setText(DELETE);
				return;
			}
			del();   //db에 삭제
			frame.setTitle("데이터삭제되었음");
			System.out.println("삭제");
		}
		else if(e.getSource()==btnSearch) {
			if(cmd!=SEARCH) {
				frame.setTitle("데이터검색");
				
				setText(SEARCH);
				return;
			} 
			search(); //db에서 검색
			frame.setTitle("데이터검색되었음");
			System.out.println("검색");
		}
		setText(NONE);
		
	}
}
