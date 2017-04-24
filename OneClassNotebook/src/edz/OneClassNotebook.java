/**
 * Project: OneClassNotebook
 * File: OneClassNotebook.java
 * Date: Mar 16, 2017
 * Time: 11:56:37 PM
 */
package edz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * @author Edgar Zapeka, A00998715
 *
 */
public class OneClassNotebook extends JFrame {

	private final DefaultListModel<String> listModel;
	private JList<String> list;
	private JButton addButton;
	private JButton removeButton;

	public OneClassNotebook() {
		listModel = new DefaultListModel<String>();

		createUI();
	}

	private void createUI() {
		list = new JList<String>(listModel);
		addButton = new JButton("Add task");
		removeButton = new JButton("Remove selected");

		listModel.addElement("Task 1");
		listModel.addElement("Task 2");
		listModel.addElement("Task 3");
		listModel.addElement("Task 4");
		listModel.addElement("Task 5");

		JPanel panel = new JPanel();
		panel.add(addButton);
		panel.add(removeButton);

		add(panel, BorderLayout.NORTH);
		add(new JScrollPane(list), BorderLayout.CENTER);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newItem = JOptionPane.showInputDialog("Enter task name: ");

				if (newItem != null) {
					if (list.getSelectedIndex() == -1) {
						listModel.addElement(newItem);
					} else {
						listModel.add(list.getSelectedIndex(), newItem);
					}
				}
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listModel.remove(list.getSelectedIndex());
			}
		});

		setSize(350, 280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			// ignore it
		}
		OneClassNotebook appView = new OneClassNotebook();
		appView.setVisible(true);
	}

}
