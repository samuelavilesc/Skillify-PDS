package com.pds.skillify.ui;

import javax.swing.*;

import com.pds.skillify.model.User;
import com.pds.skillify.ui.controller.AchievementsWindowController;
import com.pds.skillify.controller.Controller;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class AchievementsWindow extends JFrame {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 650;

	private JLabel lblTitulo, lblAvatar;
	private JPanel panelLogros, panelEstadisticas;
	private JLabel lblHorasEstudio, lblRachaActual, lblMejorRacha;
	private JLabel lblHorasValor, lblRachaActualValor, lblMejorRachaValor;

	private User user;
	private Controller controller;

	public AchievementsWindow(User user) {
		this.user = user;
		this.controller = Controller.getInstance();
		initialize();
		actualizarLogros();
		new AchievementsWindowController(this);
		setVisible(true);
	}

	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());

		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

	
		lblTitulo = new JLabel("Logros de " + user.getUsername());
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

		
		ImageIcon avatarIcon;
		if (user != null && user.getProfilePic() != null) {
			avatarIcon = new ImageIcon(user.getProfilePic().getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		} else {
			avatarIcon = new ImageIcon(getClass().getResource("/user.png"));
		}

		lblAvatar = new JLabel(avatarIcon);

		
		JPanel tituloPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 10, 0, 10);
		tituloPanel.add(lblTitulo, gbc);

		gbc.gridx = 1;
		tituloPanel.add(lblAvatar, gbc);

		topPanel.add(tituloPanel, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);

		
		panelLogros = new JPanel();
		panelLogros.setLayout(new BoxLayout(panelLogros, BoxLayout.Y_AXIS));
		panelLogros.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JScrollPane scrollPane = new JScrollPane(panelLogros, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);

		add(scrollPane, BorderLayout.CENTER);

		
		panelEstadisticas = new JPanel(new GridLayout(2, 3, 20, 5)); 
																		
		panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Estadísticas del Usuario"));

		long horasEstudio = user.getActiveTimeInHours();
		int rachaActual = user.getCurrentLoginStreak();
		int mejorRacha = user.getBestLoginStreak();

		
		lblHorasEstudio = new JLabel("Horas de estudio", SwingConstants.CENTER);
		lblRachaActual = new JLabel("Racha actual", SwingConstants.CENTER);
		lblMejorRacha = new JLabel("Mejor racha", SwingConstants.CENTER);

	
		lblHorasValor = new JLabel(String.valueOf(horasEstudio), SwingConstants.CENTER);
		lblRachaActualValor = new JLabel(String.valueOf(rachaActual), SwingConstants.CENTER);
		lblMejorRachaValor = new JLabel(String.valueOf(mejorRacha), SwingConstants.CENTER);

		
		Font valorFont = new Font("Arial", Font.BOLD, 22);
		lblHorasValor.setFont(valorFont);
		lblRachaActualValor.setFont(valorFont);
		lblMejorRachaValor.setFont(valorFont);

		
		Font tituloFont = new Font("Arial", Font.BOLD, 14);
		lblHorasEstudio.setFont(tituloFont);
		lblRachaActual.setFont(tituloFont);
		lblMejorRacha.setFont(tituloFont);

		
		panelEstadisticas.add(lblHorasEstudio);
		panelEstadisticas.add(lblRachaActual);
		panelEstadisticas.add(lblMejorRacha);

		panelEstadisticas.add(lblHorasValor);
		panelEstadisticas.add(lblRachaActualValor);
		panelEstadisticas.add(lblMejorRachaValor);

		
		add(panelEstadisticas, BorderLayout.SOUTH);
	}

	/**
	 * Actualiza y muestra los logros del usuario en la interfaz gráfica.
	 * Obtiene los cursos completados por el usuario desde el objeto {@link User} y los muestra en el panel de logros.
	 * Si el usuario no ha completado ningún curso, se muestra un mensaje indicando que no hay logros desbloqueados.
	 * Para cada curso completado, se muestra un ícono de medalla junto con el nombre del curso.
	 * 
	 * Este método también se encarga de limpiar el panel de logros antes de agregar los nuevos elementos
	 * para evitar duplicados.
	 * 
	 * @see User#getCompletedCourses()
	 * @see Controller#getCourseNameById(Long)
	 */
	public void actualizarLogros() {
		panelLogros.removeAll(); 

		
		Set<Long> cursosCompletados = user.getCompletedCourses();

		if (cursosCompletados.isEmpty()) {
			JLabel noLogros = new JLabel("Aún no hay logros desbloqueados.");
			noLogros.setFont(new Font("Arial", Font.ITALIC, 14));
			noLogros.setForeground(Color.GRAY);
			noLogros.setHorizontalAlignment(SwingConstants.CENTER);
			panelLogros.add(noLogros);
		} else {
			ImageIcon iconoMedalla = new ImageIcon(getClass().getResource("/achievement.png"));

			
			List<String> nombresCursos = cursosCompletados.stream().map(controller::getCourseNameById) 
																										
																										
					.collect(Collectors.toList());

			for (String nombreCurso : nombresCursos) {
				JPanel panelCurso = new JPanel(new BorderLayout());
				panelCurso.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				JLabel lblMedalla = new JLabel(
						new ImageIcon(iconoMedalla.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
				lblMedalla.setHorizontalAlignment(SwingConstants.CENTER);

				JLabel lblNombreCurso = new JLabel(nombreCurso, SwingConstants.CENTER);
				lblNombreCurso.setFont(new Font("Arial", Font.BOLD, 16));

				panelCurso.add(lblMedalla, BorderLayout.WEST);
				panelCurso.add(lblNombreCurso, BorderLayout.CENTER);
				panelLogros.add(panelCurso);
			}
		}

		panelLogros.revalidate();
		panelLogros.repaint();
	}
}
