package com.pds.skillify.ui;

import com.pds.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.controller.MainWindowController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 650;
	private static final int MAX_LENGTH_DESCRIPTION = 65;
	
	private JList<Course> courseList;
	private DefaultListModel<Course> courseListModel;
	private JButton settingsButton, profileButton, communityButton, importButton, logoutButton;
	private JLabel welcomeLabel;
	private User actualUser;

	public MainWindow() {
		actualUser = Controller.getInstance().getActualUser();
		initialize();
		new MainWindowController(this);
		setVisible(true);
	}

	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		String welcomeMessage = (actualUser != null) ? "Bienvenido, " + actualUser.getUsername() + "!" : "Bienvenido!";
		welcomeLabel = new JLabel(welcomeMessage);
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

		ImageIcon avatarIcon;
		if (actualUser != null && actualUser.getProfilePic() != null) {
			Image img = actualUser.getProfilePic().getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			avatarIcon = new ImageIcon(img);
		} else {
			avatarIcon = new ImageIcon(getClass().getResource("/user.png"));
		}

		JPanel iconsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		profileButton = createIconButton(avatarIcon);
		settingsButton = createIconButton(new ImageIcon(getClass().getResource("/settings.png")));
		communityButton = createIconButton(new ImageIcon(getClass().getResource("/community_icon.png")));
		logoutButton = createIconButton(new ImageIcon(getClass().getResource("/logout.png")));

		iconsPanel.add(profileButton);
		iconsPanel.add(communityButton);
		iconsPanel.add(settingsButton);
		iconsPanel.add(logoutButton);

		topPanel.add(welcomeLabel, BorderLayout.WEST);
		topPanel.add(iconsPanel, BorderLayout.EAST);
		getContentPane().add(topPanel, BorderLayout.NORTH);

		courseListModel = new DefaultListModel<>();
		updateCoursesList();

		courseList = new JList<>(courseListModel);
		courseList.setCellRenderer(new CourseRenderer());
		courseList.setFixedCellHeight(90);

		JScrollPane scrollPane = new JScrollPane(courseList);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		importButton = new JButton("Importar curso");

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(importButton);

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}

	private JButton createIconButton(ImageIcon icon) {
		Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		JButton button = new JButton(new ImageIcon(img));
		button.setBorder(null);
		button.setContentAreaFilled(false);
		return button;
	}

	public void updateCoursesList() {
		courseListModel.clear();
		Controller.getInstance().getAllCurrentUserCourses().forEach(courseListModel::addElement);
	}

	public JButton getSettingsButton() {
		return settingsButton;
	}

	public JButton getProfileButton() {
		return profileButton;
	}

	public JButton getImportButton() {
		return importButton;
	}

	public JButton getCommunityButton() {
		return communityButton;
	}

	public JList<Course> getCourseList() {
		return courseList;
	}

	public JButton getLogoutButton() {
		return logoutButton;
	}

	private class CourseRenderer extends JPanel implements ListCellRenderer<Course> {
		private JLabel courseIcon, courseTitle, courseDescription;
		private JProgressBar progressBar;

		public CourseRenderer() {
			setLayout(new BorderLayout());
			setBorder(new EmptyBorder(5, 10, 5, 10));

			ImageIcon icon = new ImageIcon(getClass().getResource("/curso.png"));
			Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			courseIcon = new JLabel(new ImageIcon(img));

			courseTitle = new JLabel();
			courseTitle.setFont(new Font("Arial", Font.BOLD, 14));

			courseDescription = new JLabel();
			courseDescription.setFont(new Font("Arial", Font.PLAIN, 12));
			courseDescription.setForeground(Color.DARK_GRAY);

			progressBar = new JProgressBar();
			progressBar.setForeground(new Color(50, 205, 50));
			progressBar.setPreferredSize(new Dimension(100, 12));

			JPanel textPanel = new JPanel(new BorderLayout());
			textPanel.add(courseTitle, BorderLayout.NORTH);
			textPanel.add(courseDescription, BorderLayout.CENTER);
			textPanel.add(progressBar, BorderLayout.SOUTH);

			add(courseIcon, BorderLayout.WEST);
			add(textPanel, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Course> list, Course course, int index,
				boolean isSelected, boolean cellHasFocus) {

			courseTitle.setText(course.getName());

			String description = course.getDescription();
			if (description.length() > MAX_LENGTH_DESCRIPTION) {
				description = description.substring(0, MAX_LENGTH_DESCRIPTION) + "...";
			}
			courseDescription.setText(description);

			progressBar.setValue(Controller.getInstance().getCurrentUsersProgressInCourse(course));

			setBackground(isSelected ? new Color(220, 220, 220) : Color.WHITE);
			return this;
		}
	}
}
