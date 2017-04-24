package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JList;

public class SearchDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton cancelButton;
	private JButton btnSearch;
	

	public SearchDialog() {
		setUI();
		addEventHandlers();
	}
	
	private void setUI(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow][]", "[][grow][]"));
		{
			textField = new JTextField();
			contentPanel.add(textField, "cell 0 0,growx");
			textField.setColumns(10);
		}
		{
			btnSearch = new JButton("Search");
			contentPanel.add(btnSearch, "cell 1 0");
		}
		{
			JList list = new JList();
			contentPanel.add(list, "cell 0 1 2 2,grow");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}	
	}
	
	private void addEventHandlers(){
		btnSearch.addActionListener(new UIController.Search(textField.getText()));
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SearchDialog.this.dispose();
			}
			
		});
	}

}
