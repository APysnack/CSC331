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
	// create user [0 : success, 1 : Duplicate User, 2 : too short]
	int error_flag = -1;

// ------------------------------------------------------------------------ //
//                            Main Admin Page
// ------------------------------------------------------------------------ //

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
		this.setTitle("Admin View");
		JPanel main_window = new JPanel();

		JLabel errorLbl = new JLabel("Admin Homepage");
		JButton addUsrBtn = new JButton("Add User");
		JButton editUsrBtn = new JButton("Edit User");
		JButton delUsrBtn = new JButton("Delete User");

		main_window.add(addUsrBtn);
		main_window.add(editUsrBtn);
		main_window.add(delUsrBtn);
		main_window.add(errorLbl);

		this.add(main_window);
		this.setVisible(true);
		this.current = main_window;

// ------------------------------------------------------------------------ //
//                         Create new User
// ------------------------------------------------------------------------ //

		addUsrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);
				JPanel addUsrPnl = new JPanel();

				String[] usrTypes = { "Student", "Teacher", "Admin" };
				JComboBox usrMenu = new JComboBox(usrTypes);
				usrMenu.setSelectedIndex(0);

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
				addUsrPnl.add(usrMenu);

				add(addUsrPnl);
				current = addUsrPnl;

				if (error_flag == 0) {
					JLabel success = new JLabel("User successfully added");
					addUsrPnl.add(success);
				}

				if (error_flag == 1) {
					JLabel nameTaken = new JLabel("ERROR: \n" + "That name is already registered to another user \n"
							+ "Please try a different Username");
					addUsrPnl.add(nameTaken);

				}

				if (error_flag == 2) {
					JLabel tooShort = new JLabel(
							"ERROR: \n" + "Please ensure your name and password are a minimum of 3 chars");
					addUsrPnl.add(tooShort);
				}

				usrMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
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

						if (usrTextFld.getText().length() < 3 || pwTextFld.getText().length() < 3) {
							error_flag = 2;
							return;
						}

						boolean success = conn.createUser(usrTextFld.getText(), pwTextFld.getText(), privilege);

						if (success) {
							error_flag = 0;
						} else {
							error_flag = 1;
							return;
						}
					}
				});
			}
		}); // end create user action

// ------------------------------------------------------------------------ //
//                                 Edit User
// ------------------------------------------------------------------------ //

		editUsrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				current.setVisible(false);

				JPanel editUsrPnl = new JPanel();
				JLabel editLbl = new JLabel("Enter a User ID to Edit");
				JButton editSbmtBtn = new JButton("Submit");
				JTextField idTxtBox = new JTextField("", 15);
				editUsrPnl.add(editLbl);
				editUsrPnl.add(idTxtBox);
				editUsrPnl.add(editSbmtBtn);
				add(editUsrPnl);

				editSbmtBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						System.out.println(idTxtBox.getText());
					}

				}); // end submit edit button
			}
		}); // end edit user action

	} // end admin page function

} // end class
