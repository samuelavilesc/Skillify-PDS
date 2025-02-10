package com.pds.skillify.ui;

import java.awt.*;
import javax.swing.*;
import java.util.Set;

import com.pds.skillify.model.User;
import com.pds.skillify.ui.controller.CommunityWindowController;

@SuppressWarnings("serial")
public class CommunityWindow extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
        Image img = logo.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(img);
        JLabel logoLabel = new JLabel(resizedLogo, SwingConstants.CENTER);
        topPanel.add(logoLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Usuario");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        centerPanel.add(usernameField, gbc);
        
        add(centerPanel, BorderLayout.CENTER);

        listModel = new DefaultListModel<>();
        usersList = new JList<>(listModel);
        usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersList.setCellRenderer(new UserCellRenderer());

        JScrollPane scrollPane = new JScrollPane(usersList);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 40, 300));
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

	private static class UserCellRenderer extends JPanel implements ListCellRenderer<User> {
        private JLabel picLabel;
        private JLabel usernameLabel;
        private JLabel emailLabel;

        public UserCellRenderer() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            picLabel = new JLabel();
            add(picLabel, BorderLayout.WEST);

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            usernameLabel = new JLabel();
            usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD));
            emailLabel = new JLabel();

            textPanel.add(usernameLabel);
            textPanel.add(emailLabel);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
            if (user != null) {
                ImageIcon profilePic = user.getProfilePic();
                Image img = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                picLabel.setIcon(new ImageIcon(img));
                usernameLabel.setText(user.getUsername());
                emailLabel.setText(user.getEmail());
            }

            if (isSelected) {
                setBackground(new Color(173, 216, 230));
            } else {
                setBackground(Color.WHITE);
            }
            setOpaque(true);
            return this;
        }
    }
}
