package com.pds.skillify.ui;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.components.CourseRenderer;
import com.pds.skillify.ui.controller.MainWindowController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 650;
	private static final int SCROLLBAR_ROUNDNESS = 15;
	private static final int ICON_SIZE = 40;
	private static final int COURSE_CELL_HEIGHT = 90;
	private static final int SCROLLING_SPEED = 8;

	private JList<Course> courseList;
	private DefaultListModel<Course> courseListModel;
	private JButton settingsButton, profileButton, communityButton, importButton, logoutButton;
	private JLabel welcomeLabel;
	private User actualUser;

	public MainWindow() {
		actualUser = Controller.getInstance().getCurrentUser();
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
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

		ImageIcon avatarIcon;
		if (actualUser != null && actualUser.getProfilePic() != null) {
			Image img = actualUser.getProfilePic().getImage().getScaledInstance(ICON_SIZE, ICON_SIZE,
					Image.SCALE_SMOOTH);
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
		courseList.setFixedCellHeight(COURSE_CELL_HEIGHT);

		JScrollPane scrollPane = new JScrollPane(courseList);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Customize the scrollbar
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(SCROLLING_SPEED);
		verticalScrollBar.setBackground(Color.WHITE);
		verticalScrollBar.setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.trackColor = Color.WHITE; // Fondo del scrollBar
				this.thumbColor = new Color(220, 220, 220); // Color del scrollBar en sí
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				return new JButton() {
					@Override
					public Dimension getPreferredSize() {
						return new Dimension(0, 0);
					}
				};
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return new JButton() {
					@Override
					public Dimension getPreferredSize() {
						return new Dimension(0, 0);
					}
				};
			}

			// Para hacer el scrollBar con bordes redondos
			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(thumbColor);
				g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height,
						SCROLLBAR_ROUNDNESS, SCROLLBAR_ROUNDNESS);
			}

			@Override
			protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(trackColor);
				g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
			}
		});

		importButton = new JButton("Importar curso");

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(importButton);

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}

	private JButton createIconButton(ImageIcon icon) {
		Image img = icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
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

}
