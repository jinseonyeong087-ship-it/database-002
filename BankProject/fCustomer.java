package BankProject;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class fCustomer implements ActionListener {
	//상속
	private JFrame frame;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JComboBox jcb1;
	
	JButton btnMoveFirst, btnMovePrev, btnMoveNext, btnMoveLast, btnInsertItem, btnDeleteItem, btnSaveItem, btnPrintItem, btnCloseWindow;
	
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "system";
	String password = "StrongPwd!123";
	Connection conn = null;
	PreparedStatement pstmtin, pstmtse, pstmtup, pstmtde;
	PreparedStatement pstmtseScroll;
	Statement stmt = null;
	ResultSet rs, rsScroll;
	
	public static String customer_dist[] = {"개인고객", "기업고객"};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fCustomer window = new fCustomer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * DB 연결
	 */
	public void dbconn() {
		try {
			Class.forName(driver);   //1단계
			conn = DriverManager.getConnection(url, user, password);  //연결
			System.out.println("db연결성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 초기화
	 */
	public void init() {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf4.setText("");
		jcb1.setSelectedIndex(0);
	}
	
	/**
	 * 생성자
	 */
	public fCustomer() {
		initialize();
		dbconn();
		init();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("고객관리");
		frame.getContentPane().setLayout(null);
		
		// 툴바 버튼들 ************************************************************
		btnMoveFirst = new JButton("");
		btnMoveFirst.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\FIRST.GIF"));
		btnMoveFirst.setBounds(27, 20, 23, 23);
		frame.getContentPane().add(btnMoveFirst);
		
		btnMovePrev = new JButton("");
		btnMovePrev.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\PREV.GIF"));
		btnMovePrev.setBounds(57, 20, 23, 23);
		frame.getContentPane().add(btnMovePrev);
		
		btnMoveNext = new JButton("");
		btnMoveNext.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\NEXT.GIF"));
		btnMoveNext.setBounds(87, 20, 23, 23);
		frame.getContentPane().add(btnMoveNext);
		
		btnMoveLast = new JButton("");
		btnMoveLast.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\LAST.GIF"));
		btnMoveLast.setBounds(117, 20, 23, 23);
		frame.getContentPane().add(btnMoveLast);
		
		btnInsertItem = new JButton("");
		btnInsertItem.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\INSERT.GIF"));
		btnInsertItem.setBounds(147, 20, 23, 23);
		frame.getContentPane().add(btnInsertItem);
		
		btnDeleteItem = new JButton("");
		btnDeleteItem.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\DELETE.GIF"));
		btnDeleteItem.setBounds(177, 20, 23, 23);
		frame.getContentPane().add(btnDeleteItem);
		
		btnSaveItem = new JButton("");
		btnSaveItem.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\SAVE.GIF"));
		btnSaveItem.setBounds(207, 20, 23, 23);
		frame.getContentPane().add(btnSaveItem);
		
		btnPrintItem = new JButton("");
		btnPrintItem.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\PRINT.GIF"));
		btnPrintItem.setBounds(237, 20, 23, 23);
		frame.getContentPane().add(btnPrintItem);
		
		btnCloseWindow = new JButton("");
		btnCloseWindow.setIcon(new ImageIcon("C:\\java_source\\BankProject\\src\\TOOLBAR\\EXIT.GIF"));
		btnCloseWindow.setBounds(267, 20, 23, 23);
		frame.getContentPane().add(btnCloseWindow);
		
		// 텍스트필드 ************************************************************
		tf1 = new JTextField();
		tf1.setBounds(132, 77, 116, 21);
		frame.getContentPane().add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setBounds(132, 127, 116, 21);
		frame.getContentPane().add(tf2);
		tf2.setColumns(10);
		
		tf3 = new JTextField();
		tf3.setBounds(132, 177, 450, 21);
		frame.getContentPane().add(tf3);
		tf3.setColumns(10);
		
		tf4 = new JTextField();
		tf4.setBounds(132, 227, 116, 21);
		frame.getContentPane().add(tf4);
		tf4.setColumns(10);
		
		// 라벨 ************************************************************
		JLabel lbNewLabel = new JLabel("고객번호");
		lbNewLabel.setBounds(27, 80, 57, 15);
		frame.getContentPane().add(lbNewLabel);
		
		JLabel lbNewLabel_1 = new JLabel("고객이름");
		lbNewLabel_1.setBounds(27, 130, 57, 15);
		frame.getContentPane().add(lbNewLabel_1);
		
		JLabel lbNewLabel_2 = new JLabel("주소");
		lbNewLabel_2.setBounds(27, 180, 57, 15);
		frame.getContentPane().add(lbNewLabel_2);
		
		JLabel lbNewLabel_3 = new JLabel("전화번호");
		lbNewLabel_3.setBounds(27, 230, 57, 15);
		frame.getContentPane().add(lbNewLabel_3);
		
		// 콤보박스 ************************************************************
		jcb1 = new JComboBox();
		jcb1.setBounds(132, 276, 116, 23);
		frame.getContentPane().add(jcb1);
		
		for (int i = 0; i < customer_dist.length; i++) {
			jcb1.addItem(customer_dist[i]);
		}
		
		JLabel lbNewLabel_4 = new JLabel("고객구분");
		lbNewLabel_4.setBounds(27, 280, 57, 15);
		frame.getContentPane().add(lbNewLabel_4);
		
		// 버튼 이벤트 리스너 ************************************************************
		btnMoveFirst.addActionListener(this);
		btnMovePrev.addActionListener(this);
		btnMoveNext.addActionListener(this);
		btnMoveLast.addActionListener(this);
		btnInsertItem.addActionListener(this);
		btnDeleteItem.addActionListener(this);
		btnSaveItem.addActionListener(this);
		btnPrintItem.addActionListener(this);
		btnCloseWindow.addActionListener(this);
		
		// ✅ 창 크기와 종료 설정 ************************************************************
		frame.setSize(700, 400); // 창 크기 지정
		frame.setLocationRelativeTo(null); // 화면 중앙에 표시
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X버튼 클릭 시 종료
	}
	
	public void callMoveFirst() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
    public void callMovePrev() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void callMoveNext() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void callMoveLast() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void callInsertItem() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void callDeleteItem() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void callSaveItem() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnMoveFirst) {
			callMoveFirst();	
		} 
		else if (e.getSource() == btnMovePrev) {
			callMovePrev();
		}
		else if (e.getSource() == btnMoveNext) {
			callMoveNext();
		}
		else if (e.getSource() == btnMoveLast) {
			callMoveLast();
		}
		else if (e.getSource() == btnInsertItem) {
			callInsertItem();
		}
		else if (e.getSource() == btnDeleteItem) {
			callDeleteItem();
		}
		else if (e.getSource() == btnSaveItem) {
			callSaveItem();
		}
		else if (e.getSource() == btnPrintItem) {
			System.out.println("프린트");
		}
		else if (e.getSource() == btnCloseWindow) {
			System.exit(0);
		}
	}
}
