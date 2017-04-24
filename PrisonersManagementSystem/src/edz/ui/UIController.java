package edz.ui;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import edz.data.crime.Crime;
import edz.data.crime.CrimeDao;
import edz.data.location.Location;
import edz.data.location.LocationDao;
import edz.data.prisoner.Prisoner;
import edz.data.prisoner.PrisonerDao;
import edz.data.user.User;
import edz.data.user.UserDao;

public class UIController {

	public static class SignIn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
			UserDao uDao = UserDao.getInstance();
			User user = uDao.getUser(MainFrame.loginTextField.getText(), MainFrame.passwordTextField.getText());
			if (user != null){
				try {
					UserDialog dialog = new UserDialog(user);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					dialog.setVisible(true);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "fail");
			}
			} catch (Exception exception){
				exception.printStackTrace();
			}
			
		}
	}
	
	public static class Registration implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				RegistrationDialog dialog = new RegistrationDialog(null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				User user = dialog.getUser();
				UserDao uDao = UserDao.getInstance();
				if (user != null){
					uDao.add(user);
					JOptionPane.showMessageDialog(null, "User added successfully!");
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class EditUser implements ActionListener{
		
		private User user;
		private UserDialog userDialog;
		
		public EditUser(User user, UserDialog userDialog){
			this.userDialog = userDialog;
			this.user = user;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				RegistrationDialog dialog = new RegistrationDialog(user);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				User user = dialog.getUser();
				UserDao uDao = UserDao.getInstance();
				if (user != null){
					uDao.update(user);
				}
				userDialog.setData(user);
			} catch (Exception exception){
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class SaveUserData implements ActionListener{
		
		private User user;
		private JDialog dialog;
		
		public SaveUserData(User user, JDialog dialog){
			this.user = user;
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				UserDao uDao = UserDao.getInstance();
				uDao.update(user);
				dialog.dispose();
			} catch(Exception exception){
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class PrisonersListDialog implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				PrisonersDialog dialog = new PrisonersDialog(PrisonerDao.getInstance().getPrisonersList());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class AddPrisoner implements ActionListener{
		
		PrisonersDialog prisonersDialog;
		
		public AddPrisoner(PrisonersDialog prisonersDialog){
			this.prisonersDialog = prisonersDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AddPrisonerDialog dialog = new AddPrisonerDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				
				Prisoner prisoner = dialog.getPrisoner();
				if (prisoner != null){
					PrisonerDao.getInstance().add(prisoner);
				}
				
				prisonersDialog.setPrisonersList(PrisonerDao.getInstance().getPrisonersList());
			} catch (Exception exception) {
				exception.printStackTrace();
			}			
		}
	}
	
	public static class DeletePrisoner implements ActionListener{
		
		private PrisonersDialog prisonersDialog;
		
		public DeletePrisoner(PrisonersDialog prisonersDialog){
			this.prisonersDialog = prisonersDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				Prisoner prisoner = prisonersDialog.getSelectedPrisoner();
				PrisonerDao.getInstance().delete(prisoner);
				prisonersDialog.setPrisonersList(PrisonerDao.getInstance().getPrisonersList());
			} catch(Exception exception){
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class EditPrisoner implements ActionListener{
		
		private PrisonersDialog prisonersDialog;
		
		public EditPrisoner(PrisonersDialog prisonersDialog){
			this.prisonersDialog = prisonersDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Prisoner prisoner = prisonersDialog.getSelectedPrisoner();
				
				EditPrisonerDialog dialog = new EditPrisonerDialog(prisoner);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				
				prisoner = dialog.getPrisoner();
				PrisonerDao.getInstance().update(prisoner);
				prisonersDialog.setPrisonersList(PrisonerDao.getInstance().getPrisonersList());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class CrimesListDialog implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CrimesDialog dialog = new CrimesDialog(CrimeDao.getInstance().getCrimesList());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}	
	}
	
	public static class AddCrime implements ActionListener {
		
		private CrimesDialog crimesDialog;
		
		public AddCrime(CrimesDialog crimesDialog){
			this.crimesDialog = crimesDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AddCrimeDialog dialog = new AddCrimeDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				
				Crime c = dialog.getCrime();
				if (c != null){
					CrimeDao.getInstance().add(c);
				}
				crimesDialog.setCrimeList(CrimeDao.getInstance().getCrimesList());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class DeleteCrime implements ActionListener{
		
		private CrimesDialog dialog;
		
		public DeleteCrime(CrimesDialog dialog){
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				CrimeDao.getInstance().delete(dialog.getSelectedCrime());
				dialog.setCrimeList(CrimeDao.getInstance().getCrimesList());
			} catch (Exception exception){
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class EditCrime implements ActionListener{
		
		private CrimesDialog crimesDialog;
		
		public EditCrime(CrimesDialog crimesDialog){
			this.crimesDialog = crimesDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				EditCrimeDialog dialog = new EditCrimeDialog(crimesDialog.getSelectedCrime());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				
				Crime c = dialog.getCrime();
				if (c != null){
					CrimeDao.getInstance().update(c);
					crimesDialog.setCrimeList(CrimeDao.getInstance().getCrimesList());
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class LocationsListDialog implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				LocationsDialog dialog = new LocationsDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class AddLocation implements ActionListener{
		
		private LocationsDialog lDialog;
		
		public AddLocation(LocationsDialog lDialog){
			this.lDialog = lDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AddLocationDialog dialog = new AddLocationDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				
				Location l = dialog.getLocationValue();
				if (l != null){
					LocationDao.getInstance().add(l);
					lDialog.setLocationsList(LocationDao.getInstance().getLocationList());
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class EditLocation implements ActionListener {
		
		private LocationsDialog locationDialog;
		
		public EditLocation(LocationsDialog locationDialog){
			this.locationDialog = locationDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				EditLocationDialog dialog = new EditLocationDialog(locationDialog.getSelectedLocation());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				
				Location l = dialog.getLocationValue();
				if(l != null){
					LocationDao.getInstance().update(l);
					locationDialog.setLocationsList(LocationDao.getInstance().getLocationList());
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class DeleteLocation implements ActionListener{
		
		private LocationsDialog locationDialog;
		
		public DeleteLocation(LocationsDialog locationDialog){
			this.locationDialog = locationDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				LocationDao.getInstance().delete(locationDialog.getSelectedLocation());
				locationDialog.setLocationsList(LocationDao.getInstance().getLocationList());
			} catch (Exception exception){
				exception.printStackTrace();
			}
		}
		
	}
	
	public static class Search implements ActionListener{
		
		private String searchText;
		
		public Search(String text){
			searchText = text;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				SearchDialog dialog = new SearchDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}
}
