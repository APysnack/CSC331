package teachAsst;

import java.awt.BorderLayout;
//allows you to store height and width, nothing else
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
//allows you to ask different questions of the OS
import java.awt.Toolkit;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.NumberFormat;
import javax.swing.border.*;

public class AdminPage extends JFrame implements ActionListener {
	
	
	JButton addUsrBtn, editUsrBtn, delUsrBtn;
	JPanel current;
	dbConnection conn;
	
	public AdminPage() {
		
	}
	
	public AdminPage(dbConnection dbConn) {
		this.conn = dbConn;
		this.setSize(900, 550);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Error");
		JPanel main_window = new JPanel();
		JLabel errorLbl = new JLabel("Admin Homepage");
		
		JButton addUsrBtn = new JButton("Add User");
		addUsrBtn.addActionListener(this);
		
		JButton editUsrBtn = new JButton("Edit User");
		editUsrBtn.addActionListener(this);
		
		JButton delUsrBtn = new JButton("Delete User");
		delUsrBtn.addActionListener(this);
		
		main_window.add(addUsrBtn);
		main_window.add(editUsrBtn);
		main_window.add(delUsrBtn);
		main_window.add(errorLbl);
		
		this.add(main_window);
		this.setVisible(true);
		this.current = main_window;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("Add User")) {
			current.setVisible(false);
			JPanel addUsrPnl = new JPanel();
			JLabel usrLbl = new JLabel("Create user name");
			JLabel pwLbl = new JLabel("Create user password");
			
			JButton crtUsrBtn = new JButton("Register New User");
			crtUsrBtn.addActionListener(this);
					
			JTextField usrTextFld = new JTextField("", 15);
			JTextField pwTextFld = new JTextField("", 15);
			
			addUsrPnl.add(usrLbl);
			addUsrPnl.add(usrTextFld);
			addUsrPnl.add(pwLbl);
			addUsrPnl.add(pwTextFld);
			addUsrPnl.add(crtUsrBtn);
			
			this.add(addUsrPnl);
			this.current = addUsrPnl;
			
			conn.createUser("PurelyDef", "SQLPassword", 2);
			
		}	
		
		if (event.getActionCommand().equals("Edit User")) {
			System.out.println("Edit");
		}	
		
		if (event.getActionCommand().equals("Delete User")) {
			System.out.println("Delete");
		}		
		
		if (event.getActionCommand().equals("Register New User")) {
			current.setVisible(false);
		}
		
		
	}

}
