package com.ataybur.uberSuperDuperSimpleChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4663004917873094539L;

	static Scanner sc = new Scanner(System.in);
	static String s;
	static JTextField textfield;
	static JButton jButton;

	public void findingTriangle() {

		JFrame jf = new JFrame();
		textfield = new JTextField("", 30);
		jButton = new JButton("Click");
		jButton.addActionListener(new App());
		JPanel panel = new JPanel();
		JLabel jl = new JLabel("Triangle Area Calculator");
		jf.setSize(500, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(panel);
		panel.add(jl);
		panel.add(textfield);
		panel.add(jButton);
		textfield.addActionListener(this);
	}

	public static void main(String[] args) {
		App app = new App();
		app.findingTriangle();

	}

	public void actionPerformed(ActionEvent e) {
		s = textfield.getText();
		textfield.setText("");
		System.out.println("input: " + s);
		try {
			send(s);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void send(String str) throws IOException {
		InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 1111);
		SocketChannel crunchifyClient = SocketChannel.open(crunchifyAddr);
		byte[] message = new String(str).getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(message);
		crunchifyClient.write(buffer);
		buffer.clear();
		if (str.equalsIgnoreCase("q")) {
			crunchifyClient.close();
		}
	}

}
