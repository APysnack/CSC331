package teachAsst;

import java.awt.BorderLayout;
import java.util.ArrayList;
//allows you to store height and width, nothing else
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;
//allows you to ask different questions of the OS
import java.awt.Toolkit;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.NumberFormat;
import javax.swing.border.*;

public class AdminPage extends JFrame {

	JButton addUsrBtn, editUsrBtn, delUsrBtn;
	JPanel current;
	dbConnection conn;

	// error_flag keys:
	// [0 : success, 1 : UsrDoesNotExist, 2: Duplicate User, 3 : too short]
	int error_flag = -1;

// ------------------------------------------------------------------------ //
//                            Main Admin Page
// ------------------------------------------------------------------------ //

	public AdminPage() {

	}

	public AdminPage(dbConnection dbConn) {
		String[] usrTypes = { "Student", "Teacher", "Admin" };
		this.conn = dbConn;
		this.setSize(900, 550);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Admin View");
		JPanel main_window = new JPanel();

		JLabel adminLbl = new JLabel("Admin Homepage");
		JButton addUsrBtn = new JButton("Add User");
		JButton editUsrBtn = new JButton("Edit User");
		JButton delUsrBtn = new JButton("Delete User");
		JButton viewUsrBtn = new JButton("View Users");

		main_window.add(adminLbl);
		main_window.add(addUsrBtn);
		main_window.add(editUsrBtn);
		main_window.add(delUsrBtn);
		main_window.add(viewUsrBtn);

		this.add(main_window);
		this.setVisible(true);
		this.current = main_window;

// ------------------------------------------------------------------------ //
//                        Create new User Page
// ------------------------------------------------------------------------ //

		addUsrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);
				JPanel addUsrPnl = new JPanel();

				JComboBox usrMenu = new JComboBox(usrTypes);
				usrMenu.setSelectedIndex(0);

				JLabel usrLbl = new JLabel("Create user name");
				JLabel pwLbl = new JLabel("Create user password");

				JButton crtUsrBtn = new JButton("Register New User");
				crtUsrBtn.addActionListener(this);
				
				JButton backBtn = new JButton("Back");
				backBtn.addActionListener(this);

				JTextField usrTextFld = new JTextField("", 15);
				JTextField pwTextFld = new JTextField("", 15);

				addUsrPnl.add(usrLbl);
				addUsrPnl.add(usrTextFld);
				addUsrPnl.add(pwLbl);
				addUsrPnl.add(pwTextFld);
				addUsrPnl.add(crtUsrBtn);
				addUsrPnl.add(usrMenu);
				addUsrPnl.add(backBtn);

				add(addUsrPnl);
				current = addUsrPnl;

				if (error_flag == 0) {
					JLabel success = new JLabel("User successfully added");
					addUsrPnl.add(success);
				}

				if (error_flag == 2) {
					JLabel nameTaken = new JLabel("ERROR: \n" + "That name is already registered to another user \n"
							+ "Please try a different Username");
					addUsrPnl.add(nameTaken);

				}

				if (error_flag == 3) {
					JLabel tooShort = new JLabel(
							"ERROR: \n" + "Please ensure your name and password are a minimum of 5 characters");
					addUsrPnl.add(tooShort);
				}

//------------------------------------------------------------------------ //
// 						Create User Button Response
//------------------------------------------------------------------------ //
				usrMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
					}
				});

				backBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
						AdminPage admn = new AdminPage(dbConn);
					}
				});
				
				crtUsrBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						int privilege = 0;

						if (usrMenu.getSelectedItem().equals("Student")) {
							privilege = 3;
						} else if (usrMenu.getSelectedItem().equals("Teacher")) {
							privilege = 2;
						} else {
							privilege = 1;
						}

						if (usrTextFld.getText().length() < 5 || pwTextFld.getText().length() < 5) {
							error_flag = 3;
							return;
						}

						boolean success = conn.createUser(usrTextFld.getText(), pwTextFld.getText(), privilege);

						if (success) {
							error_flag = 0;
						} else {
							error_flag = 2;
							return;
						}
					}
				});
			}
		}); // create user page end

// ------------------------------------------------------------------------ //
//                                 Edit User
// ------------------------------------------------------------------------ //

		editUsrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				JPanel editUsrPnl = new JPanel();

				JLabel oldIdLbl = new JLabel("Enter a User ID to Edit");
				JTextField oldIdTxtBox = new JTextField("", 15);

				JLabel newIdLbl = new JLabel("Enter a new User ID");
				JTextField newIdTxtBox = new JTextField("", 15);

				JLabel newPWLbl = new JLabel("Enter a new Password");
				JTextField newPWTxtBox = new JTextField("", 15);

				JLabel menuLbl = new JLabel("Set user's privilege");

				JComboBox usrMenu = new JComboBox(usrTypes);
				usrMenu.setSelectedIndex(0);

				JButton editSbmtBtn = new JButton("Submit");
				editSbmtBtn.addActionListener(this);
				
				JButton backBtn = new JButton("Back");
				backBtn.addActionListener(this);

				editUsrPnl.add(oldIdLbl);
				editUsrPnl.add(oldIdTxtBox);
				editUsrPnl.add(newIdLbl);
				editUsrPnl.add(newIdTxtBox);
				editUsrPnl.add(newPWLbl);
				editUsrPnl.add(newPWTxtBox);
				editUsrPnl.add(menuLbl);
				editUsrPnl.add(usrMenu);
				editUsrPnl.add(editSbmtBtn);
				editUsrPnl.add(backBtn);

				if (error_flag == 0) {
					JLabel success = new JLabel("User successfully edited");
					editUsrPnl.add(success);
				}

				if (error_flag == 1) {
					JLabel usrDNE = new JLabel("This user does not exist");
					editUsrPnl.add(usrDNE);
				}

				if (error_flag == 2) {
					JLabel nameTaken = new JLabel("ERROR: \n" + "That name is already registered to another user \n"
							+ "Please try a different Username");
					editUsrPnl.add(nameTaken);

				}

				if (error_flag == 3) {
					JLabel tooShort = new JLabel(
							"ERROR: \n" + "Please ensure your name and password are a minimum of 5 chars");
					editUsrPnl.add(tooShort);
				}

				current = editUsrPnl;
				add(editUsrPnl);

//------------------------------------------------------------------------ //
//					Edit User Button Response
//------------------------------------------------------------------------ //

				editSbmtBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						int privilege = 0;

						if (usrMenu.getSelectedItem().equals("Student")) {
							privilege = 3;
						} else if (usrMenu.getSelectedItem().equals("Teacher")) {
							privilege = 2;
						} else {
							privilege = 1;
						}

						error_flag = dbConn.editUser(oldIdTxtBox.getText(), newIdTxtBox.getText(),
								newPWTxtBox.getText(), privilege);
						return;
					}

				}); // end submit edit button

				usrMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
					}
				});
				
				backBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
						AdminPage admn = new AdminPage(dbConn);
					}
				});

			}
		}); // edit user page end

// ------------------------------------------------------------------------ //
//     						 Delete User Page
//------------------------------------------------------------------------ //
		delUsrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				JPanel delUsrPnl = new JPanel();
				JLabel delUsrHdr = new JLabel("Warning: Deleting a user cannot be undone");
				JLabel delUsrId = new JLabel("Enter User ID");
				JTextField usrIdTxtBox = new JTextField("", 15);
				JButton delUsrBtn = new JButton("Delete User");
				delUsrBtn.addActionListener(this);

				JButton backBtn = new JButton("Back");
				backBtn.addActionListener(this);

				delUsrPnl.add(delUsrHdr);
				delUsrPnl.add(delUsrId);
				delUsrPnl.add(usrIdTxtBox);
				delUsrPnl.add(delUsrBtn);
				delUsrPnl.add(backBtn);

				if (error_flag == 0) {
					JLabel success = new JLabel("User successfully removed");
					delUsrPnl.add(success);
				}

				if (error_flag == 1) {
					JLabel usrDNE = new JLabel("This user does not exist");
					delUsrPnl.add(usrDNE);
				}

				current = delUsrPnl;
				add(delUsrPnl);


				delUsrBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						error_flag = dbConn.removeUser(usrIdTxtBox.getText());
					}
				});
				
				backBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
						AdminPage admn = new AdminPage(dbConn);
					}
				});


			}
		}); // delete user page end

		viewUsrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);
				JTable table = dbConn.getJTable();

				JPanel viewTblPnl = new JPanel(new GridLayout(3, 1, 2, 2));

				JPanel pad = new JPanel();
				pad.setBorder(new EmptyBorder(10, 10, 10, 10));

				JButton backBtn = new JButton("Back");
				backBtn.addActionListener(this);

				JScrollPane scrollPane = new JScrollPane(table);
				viewTblPnl.add(scrollPane);
				viewTblPnl.add(pad);
				viewTblPnl.add(backBtn);
				add(viewTblPnl);

				backBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
						AdminPage admn = new AdminPage(dbConn);
					}

				});

			}
		});

	} // end admin page function

} // end class
