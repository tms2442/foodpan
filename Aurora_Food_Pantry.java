package aurora_food_pantry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import aurora_food_pantry.Aurora_Food_Pantry.SortByCompany;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
* Aurora Food Pantry by Half Empty
* 
* @lastModifiedBy	Rafael
* @modified		11/29/2018 
* @version		0.51
*/

public class Aurora_Food_Pantry extends Application {
	Scene scene00, scene01, scene02;
	

	ObservableList<Volunteer> entries = FXCollections.observableArrayList(getDBData());
    ListView<Volunteer> listVolunteers = new ListView<Volunteer>();
    ListView<String> listv = new ListView<String>();
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Aurora Food Pantry");
		BorderPane bPaneLogin = new BorderPane();
		HBox hBox0Top = new HBox();
		hBox0Top.setPadding(new Insets(10, 10, 10, 10));
		Label lblSceneZeroTitle = new Label("Aurora Food Pantry");
		lblSceneZeroTitle.setTextFill(Color.web("#e2620d"));
		lblSceneZeroTitle.setFont(Font.font("Courier, New", FontWeight.BOLD,25));
		HBox hBox0Bottom = new HBox();
		hBox0Bottom.setPadding(new Insets(10, 10, 10, 10));
		hBox0Bottom.setStyle("-fx-background-color: #372001");
		Button btnLogin = new Button("Login");
		
		GridPane gridLogin = new GridPane();
		gridLogin.setAlignment(Pos.CENTER);
		gridLogin.setHgap(10);
		gridLogin.setPadding(new Insets(30, 30, 30, 30));
		gridLogin.setStyle("-fx-border-color:gray;");
		
		TextField username = new TextField();
		TextField password = new TextField();
		Label lblUsername = new Label("Username");
		Label lblPassword = new Label("Password");
		Label lblLoginErr = new Label();
		lblUsername.setStyle("-fx-font-weight: bold;");
		lblPassword.setStyle("-fx-font-weight: bold;");
		lblLoginErr.setStyle("-fx-text-fill: red;");
		
		gridLogin.addRow(1, lblUsername);
		gridLogin.addRow(2, username);
		gridLogin.addRow(3, new Text(""));
		gridLogin.addRow(4, lblPassword);
		gridLogin.addRow(5, password);
		gridLogin.addRow(6, lblLoginErr);
		
		hBox0Top.getChildren().add(lblSceneZeroTitle);
		hBox0Bottom.getChildren().add(btnLogin);
		bPaneLogin.setCenter(gridLogin);
		bPaneLogin.setTop(hBox0Top);
		bPaneLogin.setBottom(hBox0Bottom);
		
		scene00 = new Scene(bPaneLogin, 400, 400);
		primaryStage.setScene(scene00);
		
		listVolunteers.setItems(entries);
		
		/*Scene 1 - the Volunteer Database */
		
		BorderPane bPaneHome = new BorderPane();
		bPaneHome.setStyle("-fx-background-color: #739e25");
		
		VBox vBoxSceneOne = new VBox();
		vBoxSceneOne.setPadding(new Insets(10, 10, 10, 10));
		vBoxSceneOne.setStyle("-fx-background-color: #FFF");
		
		HBox hBoxSceneOneRow1 = new HBox(10);
		hBoxSceneOneRow1.setAlignment(Pos.CENTER);
		hBoxSceneOneRow1.setPadding(new Insets(10, 10, 10, 10));
		
		HBox hBoxSceneOne = new HBox(10);
		hBoxSceneOne.setAlignment(Pos.CENTER);
		hBoxSceneOne.setPadding(new Insets(10, 10, 10, 10));
		
		Label lblSceneOneTitle = new Label("Aurora Food Pantry");
		lblSceneOneTitle.setTextFill(Color.web("#e2620d"));
		Label lblWelcome = new Label("Welcome, username");
		lblWelcome.setFont(Font.font("Courier, New",14));
		lblSceneOneTitle.setFont(Font.font("Courier, New", FontWeight.BOLD,25));
		Button btnAddNewVolunteer = new Button("Add a new volunteer");
		TextField tfSearch = new TextField();
		tfSearch.setPromptText("Search");
		
		ComboBox<String> cboSearch = new ComboBox<>();
		cboSearch.getItems().addAll("Company", "User", "Court ordered", "Start date", "End date");
		cboSearch.getSelectionModel().selectFirst();
		Button btnLogout = new Button("Logout");
		btnLogout.setOnAction(e -> primaryStage.setScene(scene00));
		
		Button btnSubmitSearch = new Button("Start Search");
		Button btnDeleteVol = new Button("Remove Volunteer");
		
		hBoxSceneOneRow1.getChildren().addAll(lblSceneOneTitle, lblWelcome, btnLogout);
		hBoxSceneOne.getChildren().addAll(btnDeleteVol, btnAddNewVolunteer, tfSearch, cboSearch, btnSubmitSearch);
		vBoxSceneOne.getChildren().addAll(hBoxSceneOneRow1, hBoxSceneOne);
		bPaneHome.setTop(vBoxSceneOne);

		ArrayList<Volunteer> volun = new ArrayList<Volunteer>(getDBData());
		btnSubmitSearch.setOnAction(e -> {

			searchResults(cboSearch.getValue(), tfSearch.getText(), volun);
			if(tfSearch.getText().equals("")) {
				bPaneHome.setCenter(listVolunteers);
			}else
				bPaneHome.setCenter(listv);
		});

       bPaneHome.setCenter(listVolunteers);
        
		scene01 = new Scene(bPaneHome);
		
		/*Scene 2 - the Volunteer Registration*/
		
		Label lblSceneTwoTitle = new Label("Volunteer Registration");
		lblSceneTwoTitle.setFont(Font.font("Courier, New", FontWeight.BOLD,25));
		lblSceneTwoTitle.setTextFill(Color.web("#739e25"));
		
		GridPane gridVolunteerForm = new GridPane();
		gridVolunteerForm.setAlignment(Pos.CENTER);
		gridVolunteerForm.setHgap(10);
		gridVolunteerForm.setPadding(new Insets(30, 30, 30, 30));
		
		HBox hBoxVolunteerForm = new HBox(10);
		
		BorderPane bPaneVolunteerForm = new BorderPane();
		bPaneVolunteerForm.setPadding(new Insets(30, 30, 30, 30));
		bPaneVolunteerForm.setStyle("-fx-background-color: #EEE");
		bPaneVolunteerForm.setTop(lblSceneTwoTitle);
		bPaneVolunteerForm.setCenter(gridVolunteerForm);
		bPaneVolunteerForm.setBottom(hBoxVolunteerForm);
		
		Label lblFirstName = new Label("First Name"); lblFirstName.setStyle("-fx-font-weight: bold");
		Label lblLastName = new Label("Last Name"); lblLastName.setStyle("-fx-font-weight: bold");
		Label lblDob = new Label("Date of Birth"); lblDob.setStyle("-fx-font-weight: bold");
		Label lblAffiliation = new Label("Affiliation"); lblAffiliation.setStyle("-fx-font-weight: bold");
		Label lblRetired = new Label("Retired"); lblRetired.setStyle("-fx-font-weight: bold");
		Label lblPhone = new Label("Phone"); lblPhone.setStyle("-fx-font-weight: bold");
		Label lblEmail = new Label("Email"); lblEmail.setStyle("-fx-font-weight: bold");
		Label lblStreet = new Label("Street"); lblStreet.setStyle("-fx-font-weight: bold");
		Label lblCity = new Label("City"); lblCity.setStyle("-fx-font-weight: bold");
		Label lblState = new Label("State"); lblState.setStyle("-fx-font-weight: bold");
		Label lblZip = new Label("Zip"); lblZip.setStyle("-fx-font-weight: bold");
		Label lblEmergencyName = new Label("Emergency Contact Name"); lblEmergencyName.setStyle("-fx-font-weight: bold");
		Label lblEmergencyPhone = new Label("Emergency Phone"); lblEmergencyPhone.setStyle("-fx-font-weight: bold");
		
		TextField firstName = new TextField();
		TextField lastName = new TextField();
		DatePicker dob = new DatePicker(LocalDate.of(2000, 1, 1));
		TextField affiliation = new TextField();
		CheckBox retired = new CheckBox();
		TextField phone = new TextField();
		TextField email = new TextField();
		TextField street = new TextField();
		TextField city = new TextField();
		TextField state = new TextField();
		state.setPrefWidth(100);
		TextField zip = new TextField();
		zip.setPrefWidth(60);
		TextField emergencyName = new TextField();
		TextField emergencyPhone = new TextField();
		Button btnCancelVolunteer = new Button("Cancel");
		btnCancelVolunteer.setOnAction(e -> primaryStage.setScene(scene01));
		Button btnSaveVolunteer = new Button("Save");
		
		gridVolunteerForm.addRow(1, lblFirstName, lblLastName);
		gridVolunteerForm.addRow(2, firstName, lastName);
		gridVolunteerForm.addRow(3, new Text(""));
		gridVolunteerForm.addRow(4, lblDob);
		gridVolunteerForm.addRow(5, dob);
		gridVolunteerForm.addRow(6, new Text(""));
		gridVolunteerForm.addRow(7, lblAffiliation, lblRetired);
		gridVolunteerForm.addRow(8, affiliation, retired);
		gridVolunteerForm.addRow(9, new Text(""));
		gridVolunteerForm.addRow(10, lblPhone);
		gridVolunteerForm.addRow(11, phone);
		gridVolunteerForm.addRow(12, new Text(""));
		gridVolunteerForm.addRow(13, lblEmail);
		gridVolunteerForm.addRow(14, email);
		gridVolunteerForm.addRow(15, new Text(""));
		gridVolunteerForm.addRow(16, lblStreet, lblCity, lblState, lblZip);
		gridVolunteerForm.addRow(17, street, city, state, zip);
		gridVolunteerForm.addRow(18, new Text(""));
		gridVolunteerForm.addRow(19, lblEmergencyName, lblEmergencyPhone);
		gridVolunteerForm.addRow(20, emergencyName, emergencyPhone);
		hBoxVolunteerForm.getChildren().addAll(btnCancelVolunteer, btnSaveVolunteer);
		
		scene02 = new Scene(bPaneVolunteerForm);
		
		primaryStage.show();
		
		btnSaveVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
					Volunteer v1 = new Volunteer();
					v1.setFirstName(firstName.getText());
					v1.setLastName(lastName.getText());
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String stringDOB = dob.getValue().format(dtf);
					v1.setDob(stringDOB);
					v1.setAffiliation(affiliation.getText());
					//v1.setRetired(retired.isSelected());
					v1.setPhone(phone.getText());
					v1.setEmail(email.getText());
					v1.setStreet(street.getText());
					v1.setCity(city.getText());
					v1.setState(state.getText());
					v1.setEmergencyName(emergencyName.getText());
					v1.setEmergencyPhone(emergencyPhone.getText());
					v1.setZip(zip.getText());;
					LocalDate localDate = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String stringLocalDate = localDate.format(formatter);
					v1.setStartDate(stringLocalDate);
					v1.setEndDate("0000-00-00");
					v1.setSearchParam("Company");
					
					entries.add(v1);
					
					
			    	
			    	try {
			    		String DBPath = "127.0.0.1/pantry";
				    	String fName = "";
				    	String dbInfo = "jdbc:mysql://127.0.0.1/pantry";
				    	Connection conn = DriverManager.getConnection(dbInfo, "root", "");
			    		
				    	
						Statement st = conn.createStatement();
						
						ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM volunteer");
						
						rs.next();
						int count = rs.getInt(1);
						int finalc = count + 1;
						
						
						String insertInTable = " INSERT INTO volunteer (VolunteerID, Fname, Lname, DOB, Affiliation, Phone, "
								+ "Email, Street, City, State_, Zip, EmergencyNa, EmergencyPh, Startdate, Enddate)"
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
						PreparedStatement prep = conn.prepareStatement(insertInTable);
						prep.setString(1, String.valueOf(finalc));
						prep.setString(2, v1.getFirstName());
						prep.setString(3, v1.getLastName());
						prep.setString(4, v1.getDob());
						prep.setString(5, v1.getAffiliation());
						prep.setString(6, v1.getPhone());
						prep.setString(7, v1.getEmail());
						prep.setString(8, v1.getStreet());
						prep.setString(9, v1.getCity());
						prep.setString(10, v1.getState());
						prep.setString(11, v1.getZip());
						prep.setString(12, v1.getEmergencyName());
						prep.setString(13, v1.getEmergencyPhone());
						prep.setString(14, v1.getStartDate());
						prep.setString(15, v1.getEndDate());
						
						prep.execute();
						
						rs.close();
						conn.close();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
					
					primaryStage.setScene(scene01);
			}
		});
		
		
		btnLogin.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				//primaryStage.setScene(scene01);
				if (logValidation(username.getText(), password.getText())) {
					primaryStage.setScene(scene01);
					lblWelcome.setText("Welcome, " + username.getText());
				} else {
					//primaryStage.setScene(scene00);
					lblLoginErr.setText("Incorrect username/password");
				}
				if (checkadmin(username.getText())) {
					btnDeleteVol.setVisible(true);
				} else {
					btnDeleteVol.setVisible(false);
				}
			}
		});
		
		btnAddNewVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				primaryStage.setScene(scene02);		
			}
		});
		
		btnDeleteVol.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				deleteVolInfo();
				
			}
			
		});
		
	}
	
	public void deleteVolInfo() {
		
		
		
	}
	
	public boolean checkadmin(String user) {
		
		String DBPath = "127.0.0.1/pantry";
    	String fName = "";
    	
    	DBconnect DB = new DBconnect(DBPath, fName);
    	
    	try {
    		
    		String SQL = "SELECT Username, isadmin FROM `employee`";
    		ResultSet resultSet;
    		
    		resultSet = DB.doQuery(SQL);
    		
    		while (resultSet.next()) {
    			if(user.equals(resultSet.getString("Username")) && resultSet.getBoolean("isadmin")) {
    				return true;
    			}
    		}
    		
    				 
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		
		return false;
	}
	
	public boolean logValidation(String user, String pass) {
		
		String DBPath = "127.0.0.1/pantry";
    	String fName = "";
    	
    	DBconnect DB = new DBconnect(DBPath, fName);
    	
try {
    		
    		String SQL = "SELECT Username, Passwd FROM `employee`";
    		ResultSet resultSet;
    		
    		resultSet = DB.doQuery(SQL);
    		
    		while (resultSet.next()) {
    			if(user.equals(resultSet.getString("Username"))) {
    				if(pass.equals(resultSet.getString("Passwd"))) {
    					return true;
    				}
    			}
    		}
    		
    				 
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return false;
	}
	
	public void searchResults(String co, String txt, ArrayList<Volunteer> volun) {
		ObservableList<String> k = FXCollections.observableArrayList();
		Collections.sort(volun, new SortByCompany());
		if (co == "Company") {
			System.out.println("List of users by company");
			for (int i = 0; i < volun.size(); i++) {
				if(volun.get(i).getAffiliation().equalsIgnoreCase(txt)) {
					k.add(volun.get(i).getFirstName() + " " + volun.get(i).getAffiliation());
				}
			}
		} else if (co == "User") {
			System.out.println("List of users by name entered");
			for (int i = 0; i < volun.size(); i++) {
				if (volun.get(i).getFirstName().equalsIgnoreCase(txt)) {	
					k.add(volun.get(i).getFirstName());
				}
			}
		} else if (co == "Court ordered") {
			System.out.println("List of users who are court ordered");
		}
		listv.setItems(k);
	}

   
    public void SortByStartDate() {
    	/*public int compare(Volunteer v1, Volunteer v2) {
    		if (v1.getStartDate().compareTo(v2.getStartDate()) > 0) return 1;
    		else if (v1.getStartDate().compareTo(v2.getStartDate()) < 0) return -1;
    		else return 0;
    	}
    	*/
    	
    	
    	
    }
    
    class SortByCompany implements Comparator<Volunteer> {
    	public int compare(Volunteer v1, Volunteer v2) {
    		int val = String.CASE_INSENSITIVE_ORDER.compare(v1.getAffiliation(), v2.getAffiliation());
    		 if (val == 0) {
    	            val = v1.getFirstName().compareTo(v2.getFirstName());
    	        }
    	        return val;
    	}
    }
    
    public static ArrayList<Volunteer> getDBData() {
    	ArrayList<Volunteer> vol = new ArrayList<>();
    	
    	String DBPath = "127.0.0.1/pantry";
    	String fName = "";
    	
    	DBconnect DB = new DBconnect(DBPath, fName);
    	
    	try {
    		
    		String SQL = "SELECT VolunteerID, Affiliation, City, DOB, Email, EmergencyNa, EmergencyPH, "
    				+ "Enddate, Fname, Lname, Phone, Startdate, State_, Street, Zip FROM `volunteer` ";
    		ResultSet resultSet;
    		
    		resultSet = DB.doQuery(SQL);
    		
    		while(resultSet.next()) {
    			Volunteer volunteer = new Volunteer();
    			volunteer.setId(resultSet.getInt("VolunteerID"));
    			volunteer.setAffiliation(resultSet.getString("Affiliation"));
    			volunteer.setCity(resultSet.getString("City"));
    			volunteer.setDob(resultSet.getString("DOB"));
    			volunteer.setEmail(resultSet.getString("Email"));
    			volunteer.setEmergencyName(resultSet.getString("EmergencyNa"));
    			volunteer.setEmergencyPhone(resultSet.getString("EmergencyPH"));
    			volunteer.setEndDate(resultSet.getString("Enddate"));
    			volunteer.setFirstName(resultSet.getString("Fname"));
    			volunteer.setLastName(resultSet.getString("Lname"));
    			volunteer.setPhone(resultSet.getString("Phone"));
    			volunteer.setStartDate(resultSet.getString("Startdate"));
    			volunteer.setState(resultSet.getString("State_"));
    			volunteer.setStreet(resultSet.getString("Street"));
    			volunteer.setZip(resultSet.getString("Zip"));
    			
    			vol.add(volunteer);
    		}

    				 
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return (vol);
    }

	public static void main(String[] args) throws SQLException {
		launch(args);
	}
}
