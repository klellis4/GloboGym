package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

/**
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
    /**
     * The Border layout 1.
     */
    BorderLayout borderLayout1 = new BorderLayout();
    /**
     * The Tool bar.
     */
    JToolBar toolBar = new JToolBar();
    /**
     * The Panel.
     */
    JPanel panel = new JPanel();
    /**
     * The Card layout 1.
     */
    CardLayout cardLayout1 = new CardLayout();

    /**
     * The Notes b.
     */
    public JButton notesB = new JButton();
    /**
     * The Daily items panel.
     */
    public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
    /**
     * The Files panel.
     */
    public ResourcesPanel filesPanel = new ResourcesPanel();
    /**
     * The Agenda b.
     */
    public JButton agendaB = new JButton();
    /**
     * The Tasks b.
     */
    public JButton tasksB = new JButton();
    /**
     * The Classes b.
     */
    public JButton classesB = new JButton();
    /**
     * The Files b.
     */
    public JButton filesB = new JButton();
    /**
     * The Current b.
     */
    JButton currentB = null;
    /**
     * The Border 1.
     */
    Border border1;

    /**
     * Instantiates a new Work panel.
     */
    public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

    /**
     * Jb init.
     *
     * @throws Exception the exception
     */
    void jbInit() throws Exception {
		border1 =
			BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(
					BevelBorder.LOWERED,
					Color.white,
					Color.white,
					new Color(124, 124, 124),
					new Color(178, 178, 178)),
				BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(Color.white);

		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		panel.setLayout(cardLayout1);

		agendaB.setBackground(Color.white);
		agendaB.setMaximumSize(new Dimension(60, 80));
		agendaB.setMinimumSize(new Dimension(30, 30));

		agendaB.setFont(new java.awt.Font("Dialog", 1, 10));
		agendaB.setPreferredSize(new Dimension(50, 50));
		agendaB.setBorderPainted(false);
		agendaB.setContentAreaFilled(false);
		agendaB.setFocusPainted(false);
		agendaB.setHorizontalTextPosition(SwingConstants.CENTER);
		agendaB.setText(Local.getString("Agenda"));
		agendaB.setVerticalAlignment(SwingConstants.TOP);
		agendaB.setVerticalTextPosition(SwingConstants.BOTTOM);
		agendaB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendaB_actionPerformed(e);
			}
		});
		agendaB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/agenda.png")));
		agendaB.setOpaque(false);
		agendaB.setMargin(new Insets(0, 0, 0, 0));
		agendaB.setSelected(true);

		classesB.setBackground(Color.white);
		classesB.setMaximumSize(new Dimension(60, 80));
		classesB.setMinimumSize(new Dimension(30, 30));

		classesB.setFont(new java.awt.Font("Dialog", 1, 10));
		classesB.setPreferredSize(new Dimension(50, 50));
		classesB.setBorderPainted(false);
		classesB.setContentAreaFilled(false);
		classesB.setFocusPainted(false);
		classesB.setHorizontalTextPosition(SwingConstants.CENTER);
		classesB.setText(Local.getString("Classes"));
		classesB.setVerticalAlignment(SwingConstants.TOP);
		classesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		classesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classesB_actionPerformed(e);
			}
		});
		classesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/events.png")));
		classesB.setOpaque(false);
		classesB.setMargin(new Insets(0, 0, 0, 0));
		//classesB.setSelected(true);

		tasksB.setSelected(true);
		tasksB.setFont(new java.awt.Font("Dialog", 1, 10));
		tasksB.setMargin(new Insets(0, 0, 0, 0));
		tasksB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		tasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		tasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tasksB_actionPerformed(e);
			}
		});
		tasksB.setVerticalAlignment(SwingConstants.TOP);
		tasksB.setText(Local.getString("Tasks"));
		tasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tasksB.setFocusPainted(false);
		tasksB.setBorderPainted(false);
		tasksB.setContentAreaFilled(false);
		tasksB.setPreferredSize(new Dimension(50, 50));
		tasksB.setMinimumSize(new Dimension(30, 30));
		tasksB.setOpaque(false);
		tasksB.setMaximumSize(new Dimension(60, 80));
		tasksB.setBackground(Color.white);

		notesB.setFont(new java.awt.Font("Dialog", 1, 10));
		notesB.setBackground(Color.white);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(60, 80));
		notesB.setMinimumSize(new Dimension(30, 30));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(60, 50));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.TOP);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesB_actionPerformed(e);
			}
		});
		notesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

		filesB.setSelected(true);
		filesB.setMargin(new Insets(0, 0, 0, 0));
		filesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/files.png")));
		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		filesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesB_actionPerformed(e);
			}
		});
		filesB.setFont(new java.awt.Font("Dialog", 1, 10));
		filesB.setVerticalAlignment(SwingConstants.TOP);
		filesB.setText(Local.getString("Resources"));
		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
		filesB.setFocusPainted(false);
		filesB.setBorderPainted(false);
		filesB.setContentAreaFilled(false);
		filesB.setPreferredSize(new Dimension(50, 50));
		filesB.setMinimumSize(new Dimension(30, 30));
		filesB.setOpaque(false);
		filesB.setMaximumSize(new Dimension(60, 80));
		filesB.setBackground(Color.white);
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(filesPanel, "FILES");
		toolBar.add(agendaB, null);
		toolBar.add(classesB, null);
		toolBar.add(tasksB, null);
		toolBar.add(notesB, null);
		toolBar.add(filesB, null);
		currentB = agendaB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		toolBar.setBorder(null);
		panel.setBorder(null);
		dailyItemsPanel.setBorder(null);
		filesPanel.setBorder(null);

	}

    /**
     * Select panel.
     *
     * @param pan the pan
     */
    public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES"))
				notesB_actionPerformed(null);
			else if (pan.equals("TASKS"))
				tasksB_actionPerformed(null);
			else if (pan.equals("CLASSES"))
				classesB_actionPerformed(null);
			else if (pan.equals("FILES"))
				filesB_actionPerformed(null);
		}
	}

    /**
     * Agenda b action performed.
     *
     * @param e the e
     */
    public void agendaB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(agendaB);
		Context.put("CURRENT_PANEL", "AGENDA");
	}

    /**
     * Notes b action performed.
     *
     * @param e the e
     */
    public void notesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

    /**
     * Tasks b action performed.
     *
     * @param e the e
     */
    public void tasksB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(tasksB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

    /**
     * Classes b action performed.
     *
     * @param e the e
     */
    public void classesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("CLASSES");
		setCurrentButton(classesB);
		Context.put("CURRENT_PANEL", "CLASSES");
	}

    /**
     * Files b action performed.
     *
     * @param e the e
     */
    public void filesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "FILES");
		setCurrentButton(filesB);
		Context.put("CURRENT_PANEL", "FILES");
	}

    /**
     * Sets current button.
     *
     * @param cb the cb
     */
    void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);
	}
}