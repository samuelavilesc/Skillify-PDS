package com.pds.skillify.ui;

import java.awt.*;
import javax.swing.*;
import java.util.Set;

import com.pds.skillify.model.User;
import com.pds.skillify.ui.controller.CommunityWindowController;
import com.pds.skillify.ui.components.UserCellRenderer;

@SuppressWarnings("serial")
public class CommunityWindow extends JFrame {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 550;

	private JTextField usernameField;
	private JList<User> usersList;
	private DefaultListModel<User> listModel;

	public CommunityWindow() {
		initialize();
		new CommunityWindowController(this);
		setVisible(true);
	}

	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel topPanel = new JPanel(new BorderLayout());
		ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
		Image img = logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon resizedLogo = new ImageIcon(img);
		JLabel logoLabel = new JLabel(resizedLogo, SwingConstants.CENTER);
		topPanel.add(logoLabel);
		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel usernameLabel = new JLabel("Usuario");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		centerPanel.add(usernameLabel, gbc);

		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(WIDTH - 70, 30));
		gbc.gridy = 1;
		centerPanel.add(usernameField, gbc);

		add(centerPanel, BorderLayout.CENTER);

		listModel = new DefaultListModel<>();
		usersList = new JList<>(listModel);
		usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usersList.setCellRenderer(new UserCellRenderer());

		JScrollPane scrollPane = new JScrollPane(usersList);
		scrollPane.setPreferredSize(new Dimension(WIDTH - 70, 300));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.SOUTH);

	}

	public void updateUserList(Set<User> matchingUsers) {
		listModel.clear();
		for (User user : matchingUsers) {
			listModel.addElement(user);
		}
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public JList<User> getUsersList() {
		return usersList;
	}
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> new CommunityWindow().setVisible(true));
	}
}
