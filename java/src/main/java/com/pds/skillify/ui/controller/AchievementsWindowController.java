package com.pds.skillify.ui.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.pds.skillify.ui.AchievementsWindow;
import com.pds.skillify.ui.MainWindow;

public class AchievementsWindowController {

	private AchievementsWindow view;

	public AchievementsWindowController(AchievementsWindow view) {
		this.view = view;
		initializeControllers();
	}

	private void initializeControllers() {
		handleWindowClosing();
	}

	private void handleWindowClosing() {
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new MainWindow();
			}
		});
	}

}
