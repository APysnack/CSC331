package teachAsst;

import java.awt.BorderLayout;
//allows you to store height and width, nothing else
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;
//allows you to ask different questions of the OS
import java.awt.Toolkit;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.border.*;

public class TeacherPage extends JFrame {

	JPanel last;
	JPanel current;
	JPanel temp;
	dbConnection conn;

//---------------------------------------------------------------------//
// 							Teacher Home Page
//---------------------------------------------------------------------//

	public TeacherPage(dbConnection dbConn) {
		this.setSize(900, 550);
		this.conn = dbConn;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Teacher Assistant");
		JPanel main_window = new JPanel();
		JLabel hmpgLbl = new JLabel("Teacher Homepage");

		JButton clsAtndBtn = new JButton("Class Attendance");
		JButton clsAsgnmtBtn = new JButton("Class Assignments");
		JButton clsGrdBtn = new JButton("Class Grades");
		JButton clsBhvrBtn = new JButton("Log Behavior");
		JButton lgOutBtn = new JButton("Log Out");

		main_window.add(hmpgLbl);

		main_window.add(clsAtndBtn);
		main_window.add(clsAsgnmtBtn);
		main_window.add(clsGrdBtn);
		main_window.add(clsBhvrBtn);
		main_window.add(lgOutBtn);

		this.add(main_window);
		this.setVisible(true);
		current = main_window;

//---------------------------------------------------------------------//
//					Home Page Button Functionality
//---------------------------------------------------------------------//
		
		clsAtndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// teacher home page turned invisible
				current.setVisible(false);

				temp = last;
				last = current;
				current = clsAtndPnl();

				current.setVisible(true);
				add(current);
			}
		});

		clsAsgnmtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				temp = last;
				last = current;
				current = clsAsgnmtPnl();

				current.setVisible(true);
				add(current);
			}
		});

		clsGrdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("Grades");
			}
		});

		clsBhvrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("Behavior");
			}
		});

		lgOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
				BuildGUI home = new BuildGUI();
			}
		}); // end log out button function
	} // end teacher page


//---------------------------------------------------------------------//
//						Class Attendance Page
//---------------------------------------------------------------------//
	
	public JPanel clsAtndPnl() {
		JPanel clsAtndPnl = new JPanel();
		JButton vwAtndBtn = new JButton("view attendance");
		JButton rcrdAtndBtn = new JButton("record attendance");
		JButton backBtn = new JButton("Back");

		clsAtndPnl.add(rcrdAtndBtn);
		clsAtndPnl.add(vwAtndBtn);
		clsAtndPnl.add(backBtn);

//---------------------------------------------------------------------//
//				Class Attendance Button Functionality
//---------------------------------------------------------------------//
		
		rcrdAtndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				temp = last;
				last = current;
				current = rcrdAtndPnl();

				add(current);
				current.setVisible(true);
			}
		});

		vwAtndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				temp = last;
				last = current;
				current = vwAtndPnl();

				add(current);
				current.setVisible(true);
			}
		});

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				current = last;
				last = temp;

				current.setVisible(true);
			}
		});

		return clsAtndPnl;
	} // end class Attendance Panel

	public JPanel rcrdAtndPnl() {
		JPanel rcrdAtndPnl = new JPanel();
		JButton backBtn = new JButton("Back");

		rcrdAtndPnl.add(backBtn);

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);
				current = last;
				current.setVisible(true);
				last = temp;
			}
		});

		return rcrdAtndPnl;

	}

	public JPanel vwAtndPnl() {
		JPanel vwAtndPnl = new JPanel();
		JButton backBtn = new JButton("Back");

		vwAtndPnl.add(backBtn);

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);
				current = last;
				current.setVisible(true);
				last = temp;
			}
		});

		return vwAtndPnl;

	}

	public JPanel clsAsgnmtPnl() {
		JPanel clsAsgnmtPnl = new JPanel();
		JButton newAsgnmtBtn = new JButton("Create New Assignment");
		JButton delAsgnmtBtn = new JButton("Delete Assignment");
		JButton vwAsgnmtBtn = new JButton("View Assignments");
		JButton backBtn = new JButton("Back");

		clsAsgnmtPnl.add(newAsgnmtBtn);
		clsAsgnmtPnl.add(delAsgnmtBtn);
		clsAsgnmtPnl.add(vwAsgnmtBtn);
		clsAsgnmtPnl.add(backBtn);
		
		vwAsgnmtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				temp = last;
				last = current;
				current = vwAsgnmtPnl();

				add(current);
				current.setVisible(true);
			}
		});

		newAsgnmtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				temp = last;
				last = current;
				current = newAsgnmtPnl();

				add(current);
				current.setVisible(true);
			}
		});
		
		delAsgnmtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);
				
				temp = last;
				last = current;
				current = delAsgnmtPnl();
			}
		});

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				current = last;
				last = temp;

				current.setVisible(true);
			}
		});

		return clsAsgnmtPnl;
	}
	
	public JPanel vwAsgnmtPnl() {
		
		current.setVisible(false);
		
		JPanel vwAsgnmtPnl = new JPanel(new GridLayout(3, 1, 2, 2));
		
		JButton backBtn = new JButton("Back");
	
		JTable table = conn.getJTable("assignments");
		JScrollPane scrollPane = new JScrollPane(table);
		
		JPanel pad = new JPanel();
		pad.setBorder(new EmptyBorder(10, 10, 10, 10));
	
		vwAsgnmtPnl.add(scrollPane);
		vwAsgnmtPnl.add(pad);
		vwAsgnmtPnl.add(backBtn);
		
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				current = last;
				last = temp;

				current.setVisible(true);
			}
		});
		
		return vwAsgnmtPnl;
	}
	
	public JPanel newAsgnmtPnl() {
		JPanel newAsgnmtPnl = new JPanel();
		current.setVisible(false);
		
		JLabel asgnmtIDLbl = new JLabel("Assignment ID");
		JTextField asgnmtIDFld = new JTextField("", 10);
		JButton crtAsgnmtBtn = new JButton("Create new assignment");
		JLabel asgnmtTtlLbl = new JLabel("Title");
		JTextField asgnmtTtlFld = new JTextField("", 10);
		JLabel asgnmtDtlLbl = new JLabel("Assignment Details");
		JLabel asgnmtPtLbl = new JLabel("Assignment Points");
		JTextField asgnmtPtFld = new JTextField("", 5);
		JLabel asgnmtDateLbl = new JLabel("Due Date (YYYY-MM-DD)");
		JTextField asgnmtDateFld = new JTextField("", 10);
		
		JTextArea asgnmtDtlArea = new JTextArea(5, 20);
		asgnmtDtlArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(asgnmtDtlArea); 
		
		JButton backBtn = new JButton("Back");
		
		newAsgnmtPnl.add(asgnmtIDLbl);
		newAsgnmtPnl.add(asgnmtIDFld);
		newAsgnmtPnl.add(asgnmtTtlLbl);
		newAsgnmtPnl.add(asgnmtTtlFld);
		newAsgnmtPnl.add(asgnmtDtlLbl);
		newAsgnmtPnl.add(asgnmtDtlArea);
		newAsgnmtPnl.add(asgnmtPtLbl);
		newAsgnmtPnl.add(asgnmtPtFld);
		newAsgnmtPnl.add(asgnmtDateLbl);
		newAsgnmtPnl.add(asgnmtDateFld);
		newAsgnmtPnl.add(crtAsgnmtBtn);
		newAsgnmtPnl.add(backBtn);
		
		crtAsgnmtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int id = Integer.parseInt(asgnmtIDFld.getText());
				String title = asgnmtTtlFld.getText();
				String details = asgnmtDtlArea.getText();
				int points = Integer.parseInt(asgnmtPtFld.getText());
				String date = asgnmtDateFld.getText();
				
				conn.addAssignment(id, title, details, points, date);
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				current = last;
				last = temp;

				current.setVisible(true);
			}
		});
		
		return newAsgnmtPnl;
	}
	
//---------------------------------------------------------------------//
//						Delete Assignment Page
//---------------------------------------------------------------------//
	
	public JPanel delAsgnmtPnl() {
		JPanel delAsgnmtPnl = new JPanel();
		current.setVisible(false);
		
		JLabel delAsgnmtLbl = new JLabel("Select Assignment to Delete (cannot be undone)");
		JTextField delAsgnmtFld = new JTextField("", 10);
		JButton delAsgnmtBtn = new JButton("Delete Assignment");
		JButton backBtn = new JButton("Back");

		delAsgnmtPnl.add(delAsgnmtLbl);
		delAsgnmtPnl.add(delAsgnmtFld);
		delAsgnmtPnl.add(delAsgnmtBtn);
		delAsgnmtPnl.add(backBtn);
		
		add(delAsgnmtPnl);
		
		delAsgnmtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				conn.removeRow("assignments", delAsgnmtFld.getText());
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				current = last;
				last = temp;

				current.setVisible(true);
			}
		});
		
		return delAsgnmtPnl;
	}
	

}
