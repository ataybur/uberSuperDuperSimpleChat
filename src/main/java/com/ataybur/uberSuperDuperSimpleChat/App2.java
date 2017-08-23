package com.ataybur.uberSuperDuperSimpleChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class App2 extends JFrame {

	private static final long serialVersionUID = -4663004917873094539L;

	static Scanner sc = new Scanner(System.in);
	static String s;
	static JTextArea textfield;

	public void findingTriangle() {

		JFrame jf = new JFrame();
		textfield = new JTextArea(5, 20);
		textfield.setEditable(false);
		JPanel panel = new JPanel();
		JLabel jl = new JLabel("Server");
		jf.setSize(500, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(panel);
		panel.add(jl);
		panel.add(textfield);
	}

	public static void main(String[] args) {
		App2 app = new App2();
		app.findingTriangle();
		try {
			app.exp();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exp() throws IOException {

		// Selector: multiplexor of SelectableChannel objects
		Selector selector = Selector.open(); // selector is open here

		// ServerSocketChannel: selectable channel for stream-oriented listening
		// sockets
		ServerSocketChannel crunchifySocket = ServerSocketChannel.open();
		InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 1111);

		// Binds the channel's socket to a local address and configures the
		// socket to listen for connections
		crunchifySocket.bind(crunchifyAddr);

		// Adjusts this channel's blocking mode.
		crunchifySocket.configureBlocking(false);

		int ops = crunchifySocket.validOps();
		SelectionKey selectKy = crunchifySocket.register(selector, ops, null);

		// Infinite loop..
		// Keep server running
		while (true) {

			// Selects a set of keys whose corresponding channels are ready for
			// I/O operations
			selector.select();

			// token representing the registration of a SelectableChannel with a
			// Selector
			Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
			Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();

			while (crunchifyIterator.hasNext()) {
				SelectionKey myKey = crunchifyIterator.next();

				// Tests whether this key's channel is ready to accept a new
				// socket connection
				if (myKey.isAcceptable()) {
					SocketChannel crunchifyClient = crunchifySocket.accept();

					// Adjusts this channel's blocking mode to false
					crunchifyClient.configureBlocking(false);

					// Operation-set bit for read operations
					crunchifyClient.register(selector, SelectionKey.OP_READ);

					// Tests whether this key's channel is ready for reading
				} else if (myKey.isReadable()) {

					SocketChannel crunchifyClient = (SocketChannel) myKey.channel();
					ByteBuffer crunchifyBuffer = ByteBuffer.allocate(256);
					crunchifyClient.read(crunchifyBuffer);
					String result = new String(crunchifyBuffer.array()).trim();
					textfield.append(result+System.lineSeparator());

					if (result.equals("q")) {
						// if (result.equals("Crunchify")) {
						crunchifyClient.close();
					}
				}
				crunchifyIterator.remove();
			}
		}
	}

}
