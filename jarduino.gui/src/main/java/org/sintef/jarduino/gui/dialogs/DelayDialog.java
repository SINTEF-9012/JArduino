package org.sintef.jarduino.gui.dialogs;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import org.sintef.jarduino.gui.InteractiveJArduinoDataGUIClientAdvanced;
import org.sintef.jarduino.gui.panels.ReuseLogPanel;


public class DelayDialog extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3077955259168765882L;
	private JButton btnOK, btnCancel;
	private JPanel contentPane;
	private JTextField textField;
	private InteractiveJArduinoDataGUIClientAdvanced ijdgca;
	private ReuseLogPanel reuseLogPanel;
	private Listener l;
	
	public DelayDialog(InteractiveJArduinoDataGUIClientAdvanced ijdgca, ReuseLogPanel reuseLogPanel) {
		this.ijdgca = ijdgca;
		this.reuseLogPanel = reuseLogPanel;
		
		ijdgca.disable();
		
		setTitle("Duration Dialog");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 315, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblEnterTheDurration = new JLabel("Enter the durration of the delay in Milliseconds");
		lblEnterTheDurration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterTheDurration.setBounds(10, 26, 286, 27);
		contentPane.add(lblEnterTheDurration);
		
		textField = new JTextField();
		textField.setBounds(51, 64, 188, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		l = new Listener();
		
		btnOK = new JButton("OK");
		btnOK.setBounds(51, 95, 89, 23);
		btnOK.addActionListener(l);
		
		contentPane.add(btnOK);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(150, 95, 89, 23);
		btnCancel.addActionListener(l);
		contentPane.add(btnCancel);
		setVisible(true);
	}
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOK){
				String s = textField.getText();
				try{
					int del = Integer.parseInt(s);
					reuseLogPanel.setDelay(del);
					ijdgca.enable();
					dispose();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(new JFrame(), "Please set the duration to a legal value", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(e.getSource() == btnCancel){
				ijdgca.enable();
				dispose();
			}
		}
		
	}
}
