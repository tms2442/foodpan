package aurora_food_pantry;

import java.awt.List;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
* @modified		12/3/2018 
* @version		0.6
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
		PasswordField password = new PasswordField();
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
		
		Button btnUpdateVolunteer = new Button("Update Volunteer");
		Button btnDeleteVolunteer = new Button("Delete Volunteer");
		
		TextField tfSearch = new TextField();
		tfSearch.setPromptText("Search");
		
		ComboBox<String> cboSearch = new ComboBox<>();
		cboSearch.getItems().addAll("Company", "User", "Court ordered", "Newest Volunteer");
		cboSearch.getSelectionModel().selectFirst();
		Button btnLogout = new Button("Logout");
		btnLogout.setOnAction(e -> {primaryStage.setScene(scene00); username.clear(); password.clear(); lblLoginErr.setText(""); });
		
		Button btnSubmitSearch = new Button("Start Search");
		
		
		hBoxSceneOneRow1.getChildren().addAll(lblSceneOneTitle, lblWelcome, btnLogout);
		hBoxSceneOne.getChildren().addAll(btnAddNewVolunteer, btnUpdateVolunteer, btnDeleteVolunteer, tfSearch, cboSearch, btnSubmitSearch);
		vBoxSceneOne.getChildren().addAll(hBoxSceneOneRow1, hBoxSceneOne);
		bPaneHome.setTop(vBoxSceneOne);

		ArrayList<Volunteer> volun = new ArrayList<Volunteer>(getDBData());
		btnSubmitSearch.setOnAction(e -> {

			searchResults(cboSearch.getValue(), tfSearch.getText(), volun);
			if(!cboSearch.getValue().equals("Newest Volunteer") && tfSearch.getText().equals("") && !cboSearch.getValue().equals("Court ordered") ) {
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
		
		
		Label lblId = new Label("ID");
		lblId.setStyle("-fx-font-weight: bold");
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
		Label lblordered = new Label("Court Ordered"); lblordered.setStyle("-fx-font-weight: bold");
		
		Label lblFirstNameErr = new Label();
		Label lblLastNameErr = new Label();
		Label lblAffiliationErr = new Label();
		Label lblPhoneErr = new Label();
		Label lblEmailErr = new Label();
		Label lblStreetErr = new Label();
		Label lblCityErr = new Label();
		Label lblStateErr = new Label();
		Label lblZipErr = new Label();
		Label lblEmergencyNameErr = new Label();
		Label lblEmergencyPhoneErr = new Label();
		
		lblFirstNameErr.setStyle("-fx-text-fill: red;");
		lblLastNameErr.setStyle("-fx-text-fill: red;");
		lblAffiliationErr.setStyle("-fx-text-fill: red;");
		lblPhoneErr.setStyle("-fx-text-fill: red;");
		lblEmailErr.setStyle("-fx-text-fill: red;");
		lblStreetErr.setStyle("-fx-text-fill: red;");
		lblCityErr.setStyle("-fx-text-fill: red;");
		lblStateErr.setStyle("-fx-text-fill: red;");
		lblZipErr.setStyle("-fx-text-fill: red;");
		lblEmergencyNameErr.setStyle("-fx-text-fill: red;");
		lblEmergencyPhoneErr.setStyle("-fx-text-fill: red;");
		
		TextField id = new TextField();
		id.setPrefWidth(10);
		TextField firstName = new TextField();
		TextField lastName = new TextField();
		DatePicker dob = new DatePicker(LocalDate.of(2000, 1, 1));
		TextField affiliation = new TextField();
		CheckBox retired = new CheckBox();
		CheckBox courtordered = new CheckBox();
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
		Button btnUPVolunteer = new Button("Update");
		Button btnDelVolunteer = new Button("Delete");
		Button btnSearchInfo = new Button("Get info");
		
		gridVolunteerForm.addRow(1, lblId, lblFirstName, lblLastName);
		gridVolunteerForm.addRow(2, id, firstName, lastName);
		gridVolunteerForm.addRow(3, new Text(""), lblFirstNameErr, lblLastNameErr);
		gridVolunteerForm.addRow(4, lblDob);
		gridVolunteerForm.addRow(5, dob);
		gridVolunteerForm.addRow(6, new Text(""));
		gridVolunteerForm.addRow(7, lblAffiliation, lblRetired, lblordered);
		gridVolunteerForm.addRow(8, affiliation, retired, courtordered);
		gridVolunteerForm.addRow(9, lblAffiliationErr);
		gridVolunteerForm.addRow(10, lblPhone);
		gridVolunteerForm.addRow(11, phone);
		gridVolunteerForm.addRow(12, lblPhoneErr);
		gridVolunteerForm.addRow(13, lblEmail);
		gridVolunteerForm.addRow(14, email);
		gridVolunteerForm.addRow(15, lblEmailErr);
		gridVolunteerForm.addRow(16, lblStreet, lblCity, lblState, lblZip);
		gridVolunteerForm.addRow(17, street, city, state, zip);
		gridVolunteerForm.addRow(18, lblStreetErr, lblCityErr, lblStateErr, lblZipErr);
		gridVolunteerForm.addRow(19, lblEmergencyName, lblEmergencyPhone);
		gridVolunteerForm.addRow(20, emergencyName, emergencyPhone);
		gridVolunteerForm.addRow(21, lblEmergencyNameErr, lblEmergencyPhoneErr);
		hBoxVolunteerForm.getChildren().addAll(btnCancelVolunteer, btnSaveVolunteer, btnUPVolunteer, btnDelVolunteer, btnSearchInfo);
		
		scene02 = new Scene(bPaneVolunteerForm);
		
		primaryStage.show();
		
		btnSaveVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				if(firstName.getText().isEmpty()) lblFirstNameErr.setText("Enter first name");
				if(lastName.getText().isEmpty()) lblLastNameErr.setText("Enter last name");
				if(affiliation.getText().isEmpty()) lblAffiliationErr.setText("Enter company affiliated");
				if(phone.getText().isEmpty()) lblPhoneErr.setText("Enter phone number");
				if(email.getText().isEmpty()) lblEmailErr.setText("Enter email address");
				if(street.getText().isEmpty()) lblStreetErr.setText("Enter street");
				if(city.getText().isEmpty()) lblCityErr.setText("Enter city");
				if(state.getText().isEmpty()) lblStateErr.setText("Enter state");
				if(zip.getText().isEmpty()) lblZipErr.setText("Enter zip");
				if(emergencyName.getText().isEmpty()) lblEmergencyNameErr.setText("Enter contact name");
				if(emergencyPhone.getText().isEmpty()) lblEmergencyPhoneErr.setText("Enter contact phone");
				else{
					Volunteer v1 = new Volunteer();
					v1.setFirstName(firstName.getText());
					v1.setLastName(lastName.getText());
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String stringDOB = dob.getValue().format(dtf);
					v1.setDob(stringDOB);
					v1.setAffiliation(affiliation.getText());
					v1.setRetired(retired.isSelected());
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
					v1.setCourtOrdered(courtordered.isSelected());
					
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
								+ "Email, Street, City, State_, Zip, EmergencyNa, EmergencyPh, Startdate, Enddate, retired, courtordered)"
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
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
						prep.setBoolean(16, v1.isRetired());
						prep.setBoolean(17, v1.getCourtOrdered());
						
						prep.execute();
						
						rs.close();
						conn.close();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					

			    	ObservableList<Volunteer> entries = FXCollections.observableArrayList(getDBData());
					listVolunteers.setItems(entries);
					primaryStage.setScene(scene01);
					primaryStage.setScene(scene01);
				}
					
			}
		});
		
		btnUPVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				Volunteer v1 = new Volunteer();
				v1.setFirstName(firstName.getText());
				v1.setLastName(lastName.getText());
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String stringDOB = dob.getValue().format(dtf);
				v1.setDob(stringDOB);
				v1.setAffiliation(affiliation.getText());
				v1.setRetired(retired.isSelected());
				v1.setPhone(phone.getText());
				v1.setEmail(email.getText());
				v1.setStreet(street.getText());
				v1.setCity(city.getText());
				v1.setState(state.getText());
				v1.setEmergencyName(emergencyName.getText());
				v1.setEmergencyPhone(emergencyPhone.getText());
				v1.setZip(zip.getText());
				;
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

					String insertInTable = " update volunteer set VolunteerID=?, Fname=?, Lname=?, DOB=?, Affiliation=?, Phone=?, "
							+ "Email=?, Street=?, City=?, State_=?, Zip=?, EmergencyNa=?, EmergencyPh=?, Startdate=?, Enddate=?, retired=?, courtordered=?"
							+ " where VolunteerID='" + v1.getId() + "'";

					PreparedStatement prep = conn.prepareStatement(insertInTable);

					prep.setInt(1, v1.getId());;
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
					prep.setBoolean(16, v1.isRetired());
					prep.setBoolean(17, v1.getCourtOrdered());

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Info Dialog");
					alert.setContentText("Volunteer has been Updated.");
					alert.showAndWait();

					prep.execute();

					conn.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				ObservableList<Volunteer> entries = FXCollections.observableArrayList(getDBData());
				listVolunteers.setItems(entries);
				primaryStage.setScene(scene01);
				primaryStage.setScene(scene01);
			}
		});
		
		btnDelVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				try {
					String DBPath = "127.0.0.1/pantry";
					String fName = "";
					String dbInfo = "jdbc:mysql://127.0.0.1/pantry";
					Connection conn = DriverManager.getConnection(dbInfo, "root", "");

					Statement st = conn.createStatement();

					String insertInTable = "delete from volunteer where VolunteerID = ?";

					PreparedStatement prep = conn.prepareStatement(insertInTable);

					prep.setString(1, id.getText());

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Info Dialog");
					alert.setContentText("Volunteer has been deleted successfully.");
					alert.showAndWait();

					prep.execute();

					conn.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//refreshTable();
				ObservableList<Volunteer> entries = FXCollections.observableArrayList(getDBData());
				listVolunteers.setItems(entries);
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
					btnUpdateVolunteer.setVisible(true);
					btnDeleteVolunteer.setVisible(true);
				} else {
					btnUpdateVolunteer.setVisible(false);
					btnDeleteVolunteer.setVisible(false);
				}
			}
		});
		
		btnUpdateVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				id.clear();
				firstName.clear();
				lastName.clear();
				affiliation.clear();
				retired.setSelected(false);
				courtordered.setSelected(false);
				phone.clear();
				email.clear();
				street.clear();
				city.clear();
				state.clear();
				zip.clear();
				emergencyName.clear();
				emergencyPhone.clear();
				lblFirstNameErr.setText("");
				lblLastNameErr.setText("");
				lblAffiliationErr.setText("");
				lblPhoneErr.setText("");
				lblEmailErr.setText("");
				lblStreetErr.setText("");
				lblCityErr.setText("");
				lblStateErr.setText("");
				lblZipErr.setText("");
				lblEmergencyNameErr.setText("");
				lblEmergencyPhoneErr.setText("");
				lblId.setVisible(true);
				id.setVisible(true);
				btnUPVolunteer.setVisible(true);
				btnSaveVolunteer.setVisible(false);
				btnDelVolunteer.setVisible(false);
				primaryStage.setScene(scene02);		
			}
		});
		
		btnDeleteVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				id.clear();
				firstName.clear();
				lastName.clear();
				affiliation.clear();
				retired.setSelected(false);
				courtordered.setSelected(false);
				phone.clear();
				email.clear();
				street.clear();
				city.clear();
				state.clear();
				zip.clear();
				emergencyName.clear();
				emergencyPhone.clear();
				lblFirstNameErr.setText("");
				lblLastNameErr.setText("");
				lblAffiliationErr.setText("");
				lblPhoneErr.setText("");
				lblEmailErr.setText("");
				lblStreetErr.setText("");
				lblCityErr.setText("");
				lblStateErr.setText("");
				lblZipErr.setText("");
				lblEmergencyNameErr.setText("");
				lblEmergencyPhoneErr.setText("");
				lblId.setVisible(true);
				id.setVisible(true);
				btnUPVolunteer.setVisible(false);
				btnSaveVolunteer.setVisible(false);
				btnDelVolunteer.setVisible(true);
				primaryStage.setScene(scene02);		
			}
		});
		
		btnAddNewVolunteer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				id.clear();
				firstName.clear();
				lastName.clear();
				affiliation.clear();
				retired.setSelected(false);
				courtordered.setSelected(false);
				phone.clear();
				email.clear();
				street.clear();
				city.clear();
				state.clear();
				zip.clear();
				emergencyName.clear();
				emergencyPhone.clear();
				lblFirstNameErr.setText("");
				lblLastNameErr.setText("");
				lblAffiliationErr.setText("");
				lblPhoneErr.setText("");
				lblEmailErr.setText("");
				lblStreetErr.setText("");
				lblCityErr.setText("");
				lblStateErr.setText("");
				lblZipErr.setText("");
				lblEmergencyNameErr.setText("");
				lblEmergencyPhoneErr.setText("");
				lblId.setVisible(false);
				id.setVisible(false);
				btnUPVolunteer.setVisible(false);
				btnSaveVolunteer.setVisible(true);
				btnDelVolunteer.setVisible(false);
				primaryStage.setScene(scene02);	
			}
		});
		
		btnSearchInfo.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				ArrayList<Volunteer> volun = new ArrayList<Volunteer>(getDBData());
				HashMap<String, Volunteer> vol = new HashMap<String, Volunteer>();
				for(Volunteer volunteer : volun) {
					vol.put(String.valueOf(volunteer.getId()), volunteer);
				}
				String key = id.getText();
				if(vol.containsKey(key)) {
					System.out.println(vol.get(key).getLastName());
					firstName.setText(vol.get(key).getFirstName());
					lastName.setText(vol.get(key).getLastName());
					affiliation.setText(vol.get(key).getAffiliation());
					retired.setSelected(vol.get(key).isRetired());
					courtordered.setSelected(vol.get(key).getCourtOrdered());
					phone.setText(vol.get(key).getPhone());
					email.setText(vol.get(key).getEmail());
					street.setText(vol.get(key).getStreet());
					city.setText(vol.get(key).getCity());
					state.setText(vol.get(key).getState());
					zip.setText(vol.get(key).getZip());
					emergencyName.setText(vol.get(key).getEmergencyName());
					emergencyPhone.setText(vol.get(key).getEmergencyPhone());
				} else
					System.out.println("No");
				
			}
		});
		
	}
	
	
	
	//public void editVolInfo() {
	//	
	//	
	//	
	//}

	
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
		Collections.sort(volun, new SortById());
		if (co == "Company") {
			Collections.sort(volun, new SortByCompany());
			System.out.println("List of users by company");
			for (int i = 0; i < volun.size(); i++) {
				if(volun.get(i).getAffiliation().equalsIgnoreCase(txt)) {
					k.add(volun.get(i).getId() + " " + volun.get(i).getFirstName() + " " + volun.get(i).getLastName() + " " + volun.get(i).getDob()
							 + " " + volun.get(i).getAffiliation() + " " + volun.get(i).getPhone() + " " + volun.get(i).getEmail() + " " + volun.get(i).getStreet()
							 + " " + volun.get(i).getCity() + " " + volun.get(i).getState() + " " + volun.get(i).getEmergencyName() + " " + volun.get(i).getEmergencyPhone()
							 + " " + volun.get(i).getZip() + " " + volun.get(i).getStartDate() + " " + volun.get(i).getEndDate());
				}
			}
		} else if (co == "User") {
			System.out.println("List of users by name entered");
			for (int i = 0; i < volun.size(); i++) {
				if (volun.get(i).getFirstName().equalsIgnoreCase(txt)) {	
					k.add(volun.get(i).getId() + " " + volun.get(i).getFirstName() + " " + volun.get(i).getLastName() + " " + volun.get(i).getDob()
							 + " " + volun.get(i).getAffiliation() + " " + volun.get(i).getPhone() + " " + volun.get(i).getEmail() + " " + volun.get(i).getStreet()
							 + " " + volun.get(i).getCity() + " " + volun.get(i).getState() + " " + volun.get(i).getEmergencyName() + " " + volun.get(i).getEmergencyPhone()
							 + " " + volun.get(i).getZip() + " " + volun.get(i).getStartDate() + " " + volun.get(i).getEndDate());
				}
			}
		} else if (co == "Court ordered") {
			System.out.println("List of users who are court ordered");
			for (int i = 0; i < volun.size(); i++) {
				if (volun.get(i).getCourtOrdered()) {
					k.add(volun.get(i).getId() + " " + volun.get(i).getFirstName() + " " + volun.get(i).getLastName() + " " + volun.get(i).getDob()
						 + " " + volun.get(i).getAffiliation() + " " + volun.get(i).getPhone() + " " + volun.get(i).getEmail() + " " + volun.get(i).getStreet()
						 + " " + volun.get(i).getCity() + " " + volun.get(i).getState() + " " + volun.get(i).getEmergencyName() + " " + volun.get(i).getEmergencyPhone()
						 + " " + volun.get(i).getZip() + " " + volun.get(i).getStartDate() + " " + volun.get(i).getEndDate());
				}
			}
				
		} else if (co == "Newest Volunteer") {
			Stack<Integer> vols = new Stack<Integer>();
			for (int i = 0; i < volun.size(); i++ ) {
				vols.push(volun.get(i).getId());
			}
			sortbyNew(vols);
			ListIterator<Integer> lt = vols.listIterator();
			
			
			while(lt.hasNext()) {
				lt.next();
			}
			
			while(lt.hasPrevious()) {
				int i = lt.previousIndex();
				//System.out.println(volun.get(i).getId() + " " + volun.get(i).getFirstName() + " " + volun.get(i).getLastName() + " " + volun.get(i).getDob()
				//		 + " " + volun.get(i).getAffiliation() + " " + volun.get(i).getPhone() + " " + volun.get(i).getEmail() + " " + volun.get(i).getStreet()
				//		 + " " + volun.get(i).getCity() + " " + volun.get(i).getState() + " " + volun.get(i).getEmergencyName() + " " + volun.get(i).getEmergencyPhone()
				//		 + " " + volun.get(i).getZip() + " " + volun.get(i).getStartDate() + " " + volun.get(i).getEndDate());
				k.add(volun.get(i).getId() + " " + volun.get(i).getFirstName() + " " + volun.get(i).getLastName() + " " + volun.get(i).getDob()
						 + " " + volun.get(i).getAffiliation() + " " + volun.get(i).getPhone() + " " + volun.get(i).getEmail() + " " + volun.get(i).getStreet()
						 + " " + volun.get(i).getCity() + " " + volun.get(i).getState() + " " + volun.get(i).getEmergencyName() + " " + volun.get(i).getEmergencyPhone()
						 + " " + volun.get(i).getZip() + " " + volun.get(i).getStartDate() + " " + volun.get(i).getEndDate());
				lt.previous();
			}
			
		}
		listv.setItems(k);
	}
	
	public static void sortedInsert(Stack<Integer> vol, Integer x) {
		
		if (vol.isEmpty()  || x > vol.peek()) {
			vol.push(x);
			return;
		}
		
		Integer temp = vol.pop();
		sortedInsert(vol, x);
		
		vol.push(temp);
		
		
	}
	
	public void sortbyNew(Stack<Integer> vol) {
		if(!vol.isEmpty())
		{
			Integer x = vol.pop();
			
			sortbyNew(vol);
			
			sortedInsert(vol, x);
		}
		
		
		
	}
   
    public void SortByStartDate() {
    	/*public int compare(Volunteer v1, Volunteer v2) {
    		if (v1.getStartDate().compareTo(v2.getStartDate()) > 0) return 1;
    		else if (v1.getStartDate().compareTo(v2.getStartDate()) < 0) return -1;
    		else return 0;
    	}
    	*/
    	
    	
    	
    
    }
    
    class SortById implements Comparator<Volunteer> {
		public int compare(Volunteer v1, Volunteer v2) {
			int val = Integer.compare(v1.getId(), v2.getId());
			if (val == 0) {
	            	val = v1.getFirstName().compareTo(v2.getFirstName());
	        	}
	        	return val;
		}
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
    				+ "Enddate, Fname, Lname, Phone, Startdate, State_, Street, Zip, retired, courtordered FROM `volunteer` ";
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
    			volunteer.setRetired(resultSet.getBoolean("retired"));
    			volunteer.setCourtOrdered(resultSet.getBoolean("courtordered"));
    			
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