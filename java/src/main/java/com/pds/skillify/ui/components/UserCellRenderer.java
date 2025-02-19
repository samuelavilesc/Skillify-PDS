package com.pds.skillify.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.pds.skillify.model.User;

@SuppressWarnings("serial")
public class UserCellRenderer extends JPanel implements ListCellRenderer<User> {

	private JLabel picLabel;
	private JLabel usernameLabel;
	private JPanel textPanel;

	public UserCellRenderer() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		picLabel = new JLabel();
		add(picLabel, BorderLayout.WEST);

		textPanel = new JPanel(new GridBagLayout());
		textPanel.setBackground(Color.WHITE);

		usernameLabel = new JLabel();
		usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;

		textPanel.add(usernameLabel, gbc);
		add(textPanel, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (user != null) {
			ImageIcon profilePic = user.getProfilePic();
			Image img = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(img));
			usernameLabel.setText(user.getUsername());
		}

		int hoveredIndex = list.getMousePosition() != null ? list.locationToIndex(list.getMousePosition()) : -1;

		if (index == hoveredIndex) {
			setBackground(new Color(200, 230, 255));
			textPanel.setBackground(new Color(200, 230, 255));
		} else if (isSelected) {
			setBackground(new Color(173, 216, 230));
			textPanel.setBackground(new Color(173, 216, 230));
		} else {
			setBackground(Color.WHITE);
			textPanel.setBackground(Color.WHITE);
		}

		setOpaque(true);
		return this;
	}

}
