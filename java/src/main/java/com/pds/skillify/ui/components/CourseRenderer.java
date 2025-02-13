package com.pds.skillify.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.Course;

@SuppressWarnings("serial")
public class CourseRenderer extends JPanel implements ListCellRenderer<Course> {
	private static final int MAX_LENGTH_DESCRIPTION = 65;
	
	private JLabel courseIcon, courseTitle, courseDescription;
	private JProgressBar progressBar;

	public CourseRenderer() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(7, 7, 7, 7));

        ImageIcon icon = new ImageIcon(getClass().getResource("/curso.png"));
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        courseIcon = new JLabel(new ImageIcon(img));

        courseTitle = new JLabel();
        courseTitle.setFont(new Font("Arial", Font.BOLD, 14));
        courseTitle.setHorizontalAlignment(JLabel.LEFT);

        courseDescription = new JLabel();
        courseDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        courseDescription.setForeground(Color.DARK_GRAY);
        courseDescription.setHorizontalAlignment(JLabel.LEFT);

        progressBar = new JProgressBar();
        progressBar.setForeground(new Color(50, 205, 50));
        progressBar.setPreferredSize(new Dimension(100, 8));
        
        JPanel textPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 0, 2, 0);
        textPanel.add(courseTitle, gbc);
        
        gbc.gridy = 1;
        textPanel.add(courseDescription, gbc);
        
        JPanel containerPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(courseIcon, BorderLayout.WEST);
        leftPanel.add(textPanel, BorderLayout.CENTER);
        
        containerPanel.add(leftPanel, BorderLayout.CENTER);
        containerPanel.add(progressBar, BorderLayout.SOUTH);
        
        add(containerPanel, BorderLayout.CENTER);
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
