//Class for interface between user

package com.gmail.absolutevanillahelp.encryption.ui;

import java.awt.*;

import javax.swing.*;

import com.gmail.absolutevanillahelp.encryption.util.InputPackage;

public class SimpleOptionPane extends JOptionPane {

	private static final long serialVersionUID = 1L;
	
	public static int getButtonInput(String message, String title, Object[] objects) {
		
		return showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, null);
	}
	
	public static InputPackage getTextInput(Component parentComponent, String message, String title, int optionType, int questionType) {
		
		JLabel label = new JLabel(message);
		JTextField textField = new JTextField(20);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(1, 15)));
		panel.add(label);
		panel.add(textField);
		panel.add(Box.createRigidArea(new Dimension(1, 10)));
		
		return new InputPackage(showConfirmDialog(parentComponent, panel, title, optionType, questionType), textField.getText());
	}
	
	public static InputPackage getTextInput(String message, String title) {
		
		return getTextInput(null, message, title, OK_CANCEL_OPTION, QUESTION_MESSAGE);
	}
	
	public static int outputOptionedText(Component parentComponent, String message, String title, int optionType, int questionType, Icon icon, Object[] objects, Object initialValue, int rows, int columns, boolean wrapText) {
	
		JTextArea textArea = new JTextArea(message, rows, columns);
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setLineWrap(wrapText);
		
		return showOptionDialog(null, new JScrollPane(textArea), title, optionType, questionType, null, objects, null);
	}
	
	public static int outputOptionedText(String message, String title, Object[] objects, int rows, int columns) {
		
		return outputOptionedText(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, null, rows, columns, true);
	}
}
