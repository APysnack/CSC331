package teachAsst;

import java.awt.BorderLayout;
// allows you to store height and width, nothing else
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;
// allows you to ask different questions of the OS
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.*;
// note that "Frames" are just windows

public class BuildGUI extends JFrame implements ActionListener {

	JButton lgnBtn, getPwBtn;
	JLabel lgnLbl;
	JTextField usrField, pwField;
	JPanel currPanel;

//------------------------------------------------------------------//	
	public static void main(String[] args) {
		new BuildGUI();
	}

//------------------------------------------------------------------//

	public BuildGUI() {

		this.setSize(900, 550);

		// allows us to ask questions of the OS
		Toolkit tk = Toolkit.getDefaultToolkit();

		// holds width and height for screen to display window on
		Dimension dim = tk.getScreenSize();

		// dim.width returns width of screen.
		// gets relative size of screen and subtract size of window
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);

		// sets the location of the window to xpos and ypos as defined above
		this.setLocation(xPos, yPos);

		// sets window to terminate process when user hits X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// sets title of window
		this.setTitle("User Login");

		// creates a new panel
		JPanel main_window = new JPanel(new BorderLayout());

		JPanel lgn_panel = new JPanel();
		lgn_panel.setLayout(new GridLayout(4, 1, 2, 2));
		
		main_window.add(lgn_panel, BorderLayout.CENTER);

		JPanel pad = new JPanel();
		pad.setBorder(new EmptyBorder(80, 80, 80, 80));
		JPanel pad2 = new JPanel();
		pad2.setBorder(new EmptyBorder(80, 80, 80, 80));
		JPanel pad3 = new JPanel();
		pad3.setBorder(new EmptyBorder(80, 80, 80, 80));
		JPanel pad4 = new JPanel();
		pad4.setBorder(new EmptyBorder(80, 80, 80, 80));

		main_window.add(pad, BorderLayout.WEST);
		main_window.add(pad2, BorderLayout.EAST);
		main_window.add(pad3, BorderLayout.NORTH);
		main_window.add(pad4, BorderLayout.SOUTH);

		// puts panel inside of window
		this.add(main_window);

		lgnLbl = new JLabel("Please Enter your Login Information");

		
		usrField = new JTextField("Enter Username", 15);

		// Needs to handle so that the user can enter text without clearing
		usrField.addFocusListener(new FocusListener() {
			// if user clicks on UserName field, it sets the text to empty for use to input
			@Override
			public void focusGained(FocusEvent e) {

				if (usrField.getText().equals("Enter Username")) {
					usrField.setText("");
				}
			}

			// if user unclicks userName field it sets text to empty only if user hasnt
			// typed
			@Override
			public void focusLost(FocusEvent e) {
				if (usrField.getText().isEmpty()) {
					usrField.setText("Enter Username");
				}
			}
		});
		pwField = new JTextField("Enter Password", 15);
		
		pwField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (pwField.getText().equals("Enter Password")) {
					pwField.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
				if (pwField.getText().isEmpty()) {
					pwField.setText("Enter Password");
				}
			}
		});

		lgnBtn = new JButton("Login");
		// Action listener required, calls self
		lgnBtn.addActionListener(this);

		JPanel ctrLblPnl = new JPanel();
		ctrLblPnl.add(lgnLbl);
		
		
		lgn_panel.add(ctrLblPnl);
		lgn_panel.add(usrField);
		lgn_panel.add(pwField);
		lgn_panel.add(lgnBtn);
		this.currPanel = main_window;
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		// text must be same as the string in the button description
		if (event.getActionCommand().equals("Login")) {
			String userName = usrField.getText();
			String password = pwField.getText();
			
			try {
				dbConnection dbConn = new dbConnection();
				int privilege = dbConn.connectUser(userName, password);
				
				if(privilege == 1) {
					this.dispose();
					AdminPage admn = new AdminPage(dbConn);
				}
				
				else if(privilege == 2){
					this.dispose();
					TeacherPage tchr = new TeacherPage(dbConn);
				}
				
				else if(privilege == 3)
				{
					this.dispose();
					StudentPage stdnt = new StudentPage();
				}

				else {
					this.dispose();
					ErrorPage err = new ErrorPage();
				}
			}

			catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

}
