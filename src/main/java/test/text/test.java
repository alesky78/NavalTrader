package test.text;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Arc2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class test {

	private JFrame frame;
	private JPanel inputPanel;
	private JLabel lblNewLabel;
	private JTextField trackField;
	private JButton btnNewButton;
	private JLabel lblNewLabel_1;
	private JTextField txtCiao;
	DrawTextPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 718, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new DrawTextPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		inputPanel = new JPanel();
		frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
		inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel = new JLabel("TRACK");
		inputPanel.add(lblNewLabel);
		
		trackField = new JTextField();
		trackField.setText("0.01");
		inputPanel.add(trackField);
		trackField.setColumns(10);
		
		btnNewButton = new JButton("refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.repaint();
			}
		});
		inputPanel.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("string");
		inputPanel.add(lblNewLabel_1);
		
		txtCiao = new JTextField();
		txtCiao.setText("21/02/1978");
		inputPanel.add(txtCiao);
		txtCiao.setColumns(10);
		
		Update thread = new Update();
		(new Thread(thread)).start();
	}

	
	class DrawTextPanel extends JPanel{
		
		public void paintComponent(Graphics graphicsPanel) {
			super.paintComponent(graphicsPanel);
			
			Graphics2D g2d = (Graphics2D) graphicsPanel;
			
			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			attributes.put(TextAttribute.TRACKING, Double.parseDouble(trackField.getText()));
			Font font = new Font("arial", Font.BOLD, 40);			
			Font font2 = font.deriveFont(attributes);
			graphicsPanel.setFont(font2);
			
			//draw the string
			int stringWidth = graphicsPanel.getFontMetrics().stringWidth(txtCiao.getText());		
			int stringHeight = graphicsPanel.getFontMetrics().getAscent();
			
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			int xStart = this.getWidth()/2-stringWidth/2;
			int yStart = this.getHeight()/2;			
			
			g2d.drawString(txtCiao.getText(),xStart,yStart);
			
			g2d.fill(new Arc2D.Float(xStart-stringHeight-5, yStart-stringHeight, stringHeight, stringHeight, 90, -time, Arc2D.PIE));
			
			
		}	
		
	}
	
	int time = 0;
	
	class Update implements Runnable{

		@Override
		public void run() {

			while(true){

				try {
					Thread.sleep(10);
					time = (time + 1)%360;
					panel.repaint();
					 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
}
