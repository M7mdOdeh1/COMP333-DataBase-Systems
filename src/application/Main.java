package application;

import java.io.File;
import application.User;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import javax.management.openmbean.KeyAlreadyExistsException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

	private ArrayList<User> user_data;
	private ObservableList<User> user_dataList;
	private ObservableList<Phone> phone_dataList;
	private ObservableList<Item> item_dataList;
	private ObservableList<Warehouse> warehouse_dataList;
	private ObservableList<Cart> cart_dataList;
	private ObservableList<AddToCart> addToCart_dataList;
	private ObservableList<DeliveryCompany> delivery_dataList;
	private ObservableList<Orders> order_dataList;
	private ObservableList<OrderedItem> orderedItems_dataList;
	private ObservableList<OrderedItem> customerOrderedItems_dataList;


	private User currentUser;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "1200089";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "SportShop";
	private Connection con;

	Rectangle rec = new Rectangle(0, 0, 100, 100);

	Stage stage = new Stage();

	@Override
	public void start(Stage primaryStage) {
		try {

			// readInfoFromFile();
			user_data = new ArrayList<User>();
			item_dataList = FXCollections.observableArrayList();
			warehouse_dataList = FXCollections.observableArrayList();
			phone_dataList = FXCollections.observableArrayList();
			cart_dataList = FXCollections.observableArrayList();
			addToCart_dataList = FXCollections.observableArrayList();
			delivery_dataList = FXCollections.observableArrayList();
			order_dataList = FXCollections.observableArrayList();
			orderedItems_dataList = FXCollections.observableArrayList();
			customerOrderedItems_dataList = FXCollections.observableArrayList();
			
			getUserData();
			getPhoneData();
			getWarehouseData();
			getItemData();
			getCartData();
			getAddToCartData();
			getDeliveryCompanyData();
			getOrdersData();
			getOrderedItemData();

			
			user_dataList = FXCollections.observableArrayList(user_data);
			
			Scene scene = new Scene(loginPage());

			stage.setMaximized(true);
			stage.setScene(scene);
			stage.setTitle("Online Sport Shop");
			stage.setIconified(true);
			stage.getIcons().add(new Image("images/a.png"));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connectDB() throws ClassNotFoundException, SQLException {

		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection(dbURL, p);
	}

	public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}

	}

	//////////////////////////////////////////////////////////////////////////////////
	// Pages

	public BorderPane loginPage() {
		BorderPane root = new BorderPane();
		fade(root, 1100, 0, 1);

		ImageView img1 = new ImageView();
		ImageView img2 = new ImageView();

		rec.setArcHeight(50);
		rec.setArcWidth(20);

		StackPane sp = new StackPane();
		sp.setPadding(new Insets(40, 0, 0, 0));
		Text text = new Text(500, 100, "Login Page");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 65));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		VBox vbox1 = new VBox(20);
		DropShadow shadow = new DropShadow();

		Button btn1 = new Button("Login");
		btn1.setPrefSize(400, 110);
		btn1.setShape(rec);
		btn1.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn1.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn1.setEffect(shadow);
				btn1.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn1.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn1.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn1.setEffect(null);
				btn1.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn1.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		/////////////////////
		Button btn2 = new Button("Register");
		btn2.setPrefSize(400, 110);
		btn2.setShape(rec);
		btn2.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn2.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn2.setEffect(shadow);
				btn2.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn2.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn2.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn2.setEffect(null);
				btn2.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn2.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});
		
		
		
		/////////////////////////////////////////////
		HBox h1 = new HBox();
		
		
		Label l1 = new Label("Enter Name:          ");
		l1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l1.setTextFill(Color.ORANGERED);
		
		TextField tf1 = new TextField();
		tf1.setPrefColumnCount(25);
		
		Text err1=new Text();
		
		
		h1.getChildren().addAll(l1, tf1,err1);
		

		//////////////////////////////////
		HBox h2 = new HBox();
		
		
		Label l2 = new Label("Enter Email:           ");
		l2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l2.setTextFill(Color.ORANGERED);
		
		TextField tf2 = new TextField();
		tf2.setPrefColumnCount(25);
		
		Text err2=new Text();
		
		h2.getChildren().addAll(l2, tf2,err2);

		//////////////////////////////////
		HBox h3 = new HBox();
		
		
		Label l3 = new Label("Enter Password:       ");
		l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l3.setTextFill(Color.ORANGERED);
		
		PasswordField pf3 = new PasswordField();
		pf3.setPrefColumnCount(25);
		
		Text err3=new Text();
		
		h3.getChildren().addAll(l3, pf3,err3);

		//////////////////////////////////////////////////////////////
		HBox h4 = new HBox();
		
		Label l4 = new Label("Confirm Password:     ");
		l4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l4.setTextFill(Color.ORANGERED);
		
		PasswordField pf4 = new PasswordField();
		pf4.setPrefColumnCount(25);
		
		Text err4=new Text();
		
		h4.getChildren().addAll(l4, pf4,err4);

		/////////////////////////////////////////////////////////////

		HBox h5 = new HBox();
		
		Label l5 = new Label("Enter Address:        ");
		l5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l5.setTextFill(Color.ORANGERED);
		
		ComboBox<String> cb5 = new ComboBox<>();
		cb5.getItems().addAll("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron", "Bethlehem", "Tubas", "Tulkarm",
				"Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem");
		cb5.setPrefSize(200, 15);
		
		Text err5=new Text();
		
		h5.getChildren().addAll(l5, cb5,err5);
		

		////////////////////////////////////////////////////////////

		HBox h6 = new HBox();
		
		Label l6 = new Label("Enter Phone Number: ");
		l6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l6.setTextFill(Color.ORANGERED);
		
		TextField tf6 = new TextField();
		tf6.setPrefColumnCount(25);
		
		Text err6=new Text();
		
		h6.getChildren().addAll(l6, tf6,err6);
		

		//////////////////////////////////////////////////////////

		HBox h7 = new HBox();
		Text er = new Text();

		h7.getChildren().addAll(er);

		//////////////////////////////////////////////

		GridPane gp = new GridPane();

		gp.setHgap(20);
		gp.setVgap(30);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(0, 0, 0, 0));

		///////////////////////////////////////////////////////////////////////
		Button btn4_login = new Button("Login");
		btn4_login.setPrefSize(250, 90);
		btn4_login.setShape(rec);
		btn4_login.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn4_login.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn4_login.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4_login.setEffect(shadow);
				btn4_login.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4_login.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn4_login.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn4_login.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4_login.setEffect(null);
				btn4_login.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4_login.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		////////////////////////////////////////////////////////////////////////////
		Button btn4_2 = new Button("Back");
		btn4_2.setPrefSize(250, 90);
		btn4_2.setShape(rec);
		btn4_2.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn4_2.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn4_2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4_2.setEffect(shadow);
				btn4_2.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4_2.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn4_2.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn4_2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4_2.setEffect(null);
				btn4_2.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4_2.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});
		HBox h8 = new HBox();
		h8.getChildren().addAll(btn4_2);
		h8.setSpacing(15);

		////////////////////////////////////////////

		Button btn4_register = new Button("Register");
		btn4_register.setPrefSize(250, 90);
		btn4_register.setShape(rec);
		btn4_register.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn4_register.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn4_register.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4_register.setEffect(shadow);
				btn4_register.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4_register.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn4_register.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn4_register.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4_register.setEffect(null);
				btn4_register.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4_register.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		/////////////////////////////////////////////////////

		vbox1.getChildren().addAll(btn1, btn2);
		vbox1.setAlignment(Pos.CENTER);

		root.setPadding(new Insets(0, 20, 20, 20));
		root.setStyle(
				"-fx-background-image:url('images/k5.png'); -fx-background-size:cover,auto;-fx-decorator-color:blue;");
		root.setTop(sp);
		root.setCenter(vbox1);
		root.setRight(img1);
		root.setLeft(img2);

		CheckBox ch = new CheckBox("Full Screen");
		ch.setFont(Font.font("arial", FontWeight.BOLD, 12));
		root.setBottom(ch);
		ch.setOnAction(e -> {
			if (ch.isSelected())
				stage.setFullScreen(true);
			else
				stage.setFullScreen(false);
		});
		
		btn1.setOnAction(e -> { // Login1 button
			gp.getChildren().clear();
			
			h2.getChildren().clear();
			h3.getChildren().clear();
			
			h2.getChildren().addAll(l2, tf2,err2);
			
			h3.getChildren().addAll(l3, pf3,err3);
			tf2.setEditable(true);
			pf3.setEditable(true);
			er.setText(" ");

			gp.add(h2, 0, 0);
			gp.add(h3, 0, 1);
			gp.add(h7, 0, 2);
			gp.add(h8, 0, 3);

			l2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
			l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
			h8.getChildren().removeAll(btn4_register, btn4_login);
			h8.getChildren().add(btn4_login);
			h8.setPadding(new Insets(0,0,0,0));
			
			root.setCenter(gp);

		});

		btn2.setOnAction(e -> { // Register1 button
			gp.getChildren().clear();

			gp.add(h1, 0, 0);
			gp.add(h2, 0, 1);
			gp.add(h3, 0, 2);
			gp.add(h4, 0, 3);
			gp.add(h5, 0, 4);
			gp.add(h6, 0, 5);
			gp.add(h7, 0, 6);
			gp.add(h8, 0, 7);

			l2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
			l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
			root.setCenter(gp);

			h8.getChildren().removeAll(btn4_login, btn4_register);
			h8.getChildren().add(btn4_register);
		});

		btn4_2.setOnAction(e -> { // back button
			er.setText(" ");
			tf1.clear();
			tf2.clear();
			pf3.clear();
			pf4.clear();
			cb5.getSelectionModel().clearSelection();
			tf6.clear();
			
			err1.setText("");
			err2.setText("");
			err3.setText("");
			err4.setText("");
			err5.setText("");
			err6.setText("");
			// cb7.getSelectionModel().clearSelection();
			root.setCenter(vbox1);
		});

		btn4_login.setOnAction(e -> { // login2 button
			
			if (tf2.getText().equals("") || pf3.getText().equals("")) {
				// please fill all fields
				er.setText("* Error: Please fill all fields !");
				er.setFill(Color.RED);
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
				if(tf2.getText().equals("")) {
					err2.setText("  "+"*");
					err2.setFill(Color.RED);
					err2.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err2.setText("");
				}
				if(pf3.getText().equals("")) {
					err3.setText("  "+"*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err3.setText("");
				}
			} 
			else {
				boolean validEmail = false, validPassword = false;

				for (User user : user_dataList) {
					if (user.getEmail().equals(tf2.getText())) {
						currentUser = user;
						validEmail = true;
						break;
					}
				}
				if (validEmail) {
					if (currentUser.getPassword().equals(pf3.getText())) {
						validPassword = true;
						if (currentUser.getPermission().equals("Admin")) {
							// go to admin page
							stage.getScene().setRoot(AdminPage());
						} else {
							// go to the customer page
							stage.getScene().setRoot(customerPage());

						}

					} 
					else {
						pf3.clear();
						// wrong password
						err2.setText("");
						er.setText("* Error: Wrong Password !");
						er.setFill(Color.RED);
						er.setStyle(
								"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
										+ "-fx-text-fill:#ff0000");
						err3.setText("  "+"*");
						err3.setFill(Color.RED);
						err3.setStyle(
								"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
										+ "-fx-text-fill:#ff0000");
					}

				} else {
					// email not registered
					tf2.setText("");
					err3.setText("");
					er.setText("* Error: This Email Not Registered !");
					er.setFill(Color.RED);
					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					err2.setText("  "+"*");
					err2.setFill(Color.RED);
					err2.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}

			}

		});

		btn4_register.setOnAction(e -> {// register2
			er.setText(" ");
			err1.setText(" ");
			err2.setText(" ");
			err3.setText(" ");
			err4.setText(" ");
			err5.setText(" ");
			err6.setText(" ");
			if (tf1.getText().equals("") || tf2.getText().equals("") || pf3.getText().equals("")
					|| pf4.getText().equals("") || cb5.getSelectionModel().isEmpty() || tf6.getText().equals("")) {

				// please fill all fields
				er.setText("* Error: Please fill all fields !");
				er.setFill(Color.RED);
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
				if(tf1.getText().equals("")) {
					err1.setText("  "+"*");
					err1.setFill(Color.RED);
					err1.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err1.setText("");
				}
				if(tf2.getText().equals("")) {
					err2.setText("  "+"*");
					err2.setFill(Color.RED);
					err2.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err2.setText("");
				}
				if(pf3.getText().equals("")) {
					err3.setText("  "+"*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err3.setText("");
				}
				if(pf4.getText().equals("")) {
					err4.setText("  "+"*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err4.setText("");
				}
				if(cb5.getSelectionModel().isEmpty()) {
					err5.setText("  "+"*");
					err5.setFill(Color.RED);
					err5.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err5.setText("");
				}
				if(tf6.getText().equals("")) {
					err6.setText("  "+"*");
					err6.setFill(Color.RED);
					err6.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err6.setText("");
				}

			} else {
				boolean isExist = false;
				for (User user : user_dataList) {
					if (tf2.getText().equals(user.getEmail())) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					// this email is already exist
					er.setText("* Error: email is already exist !");
					er.setFill(Color.RED);
					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					tf2.clear();
					err2.setText("  "+"*");
					err2.setFill(Color.RED);
					err2.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					
				} else if (pf3.getText().equals(pf4.getText())) {// user added successfully
					int userNo = user_dataList.get(user_dataList.size() - 1).getUserNo() + 1;
					User u = new User(userNo, tf1.getText(), tf2.getText(), pf4.getText(),
							cb5.getSelectionModel().getSelectedItem(), tf6.getText(), "Customer");
					
					er.setText(" The Customer "+tf1.getText()+" Added Successfully :)");
					er.setFill(Color.MEDIUMSPRINGGREEN);
					er.setStyle("-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
							+ "-fx-text-fill:MEDIUMSPRINGGREEN");
					gp.getChildren().clear();

					gp.add(h1, 0, 0);
					gp.add(h2, 0, 1);
					gp.add(h3, 0, 2);
					gp.add(h4, 0, 3);
					gp.add(h5, 0, 4);
					gp.add(h6, 0, 5);
					gp.add(h7, 0, 6);
					
					h8.getChildren().removeAll(btn4_2, btn4_register);
					h8.getChildren().add(btn4_2);
					h8.setPadding(new Insets(0,0,0,120));
					gp.add(h8, 0, 7);
					
					tf1.clear();
					tf2.clear();
					pf3.clear();
					pf4.clear();
					cb5.getSelectionModel().clearSelection();
					tf6.clear();
					
					tf1.setEditable(false);
					tf2.setEditable(false);
					pf3.setEditable(false);
					pf4.setEditable(false);
					cb5.setEditable(false);
					tf6.setEditable(false);
					
					cart_dataList.add(new Cart(u.getUserNo(), 0, 0));
					insertUserData(u);
					user_dataList.add(u);
					phone_dataList.add(new Phone(u.getPhone(),u.getUserNo()));
				} else {
					pf3.clear();
					pf4.clear();
					// passwords doesn't match
					er.setText("* Error: Passwords doesnt match !");
					er.setFill(Color.RED);
					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");

					pf3.setText("");
					pf4.setText("");
					err3.setText("  "+"*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					err4.setText("  "+"*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
			}

			
		});

		return root;
	}

	// for Admin
	@SuppressWarnings("unchecked")
	public BorderPane AdminPage() {
		BorderPane root = new BorderPane();
		fade(root, 1100, 0, 1);

		ImageView img1 = new ImageView();
		ImageView img2 = new ImageView();

		;
		rec.setArcHeight(50);
		rec.setArcWidth(20);

		StackPane sp = new StackPane();
		sp.setPadding(new Insets(40, 0, 0, 0));
		Text text = new Text(500, 100, "Online Sport Shop");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 65));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		VBox vbox1 = new VBox(20);
		DropShadow shadow = new DropShadow();

		Button btn1 = new Button("Users");
		btn1.setPrefSize(320, 90);
		btn1.setShape(rec);
		btn1.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn1.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn1.setEffect(shadow);
				btn1.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn1.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn1.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn1.setEffect(null);
				btn1.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn1.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		Button btn2 = new Button("Items");
		btn2.setPrefSize(320, 90);
		btn2.setShape(rec);
		btn2.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn2.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn2.setEffect(shadow);
				btn2.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn2.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn2.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn2.setEffect(null);
				btn2.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn2.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		Button btn3 = new Button("Purchases");
		btn3.setPrefSize(320, 90);
		btn3.setShape(rec);
		btn3.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn3.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn3.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn3.setEffect(shadow);
				btn3.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn3.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn3.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn3.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn3.setEffect(null);
				btn3.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn3.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		Button btn4 = new Button("Log Out");
		btn4.setPrefSize(320, 90);
		btn4.setShape(rec);
		btn4.setFont(Font.font("arial", FontWeight.BOLD, 30));
		btn4.setStyle("-fx-border-style:solid; -fx-border-width:2");

		btn4.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4.setEffect(shadow);
				btn4.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4.setStyle("-fx-border-style:solid; -fx-border-width:2");

				btn4.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: YELLOW; -fx-Border-width:4;"
						+ "-fx-text-fill: YELLOW");
			}
		});
		btn4.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btn4.setEffect(null);
				btn4.setFont(Font.font("arial", FontWeight.BOLD, 30));
				btn4.setStyle("-fx-border-style:solid; -fx-border-width:2");
			}
		});

		vbox1.getChildren().addAll(btn1, btn2, btn3, btn4);
		vbox1.setAlignment(Pos.CENTER);

		root.setPadding(new Insets(0, 20, 20, 20));
		root.setStyle(
				"-fx-background-image:url('images/k5.png'); -fx-background-size:cover,auto;-fx-decorator-color:blue;");
		root.setTop(sp);
		root.setCenter(vbox1);
		root.setRight(img1);
		root.setLeft(img2);

		CheckBox ch = new CheckBox("Full Screen");
		ch.setFont(Font.font("arial", FontWeight.BOLD, 12));
		root.setBottom(ch);
		ch.setOnAction(e -> {
			if (ch.isSelected())
				stage.setFullScreen(true);
			else
				stage.setFullScreen(false);
		});

		btn1.setOnAction(e -> { // user button
			stage.getScene().setRoot(userPage());
		});

		btn2.setOnAction(e -> { // items button
			stage.getScene().setRoot(itemPage());
		});

		btn3.setOnAction(e -> { // buy button
			stage.getScene().setRoot(addToCartPage());
		});

		btn4.setOnAction(e -> {
			stage.getScene().setRoot(loginPage());
		});
		return root;
	}

	@SuppressWarnings("unchecked")
	public BorderPane userPage() {

		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp2.setAlignment(Pos.CENTER_LEFT);
		gp2.setPadding(new Insets(100, 0, 0, 0));

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "User Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<User> user_table = new TableView<User>(); // table

		user_table.setEditable(true);
		user_table.setPrefWidth(920);
		user_table.setPrefHeight(500);
		user_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(user_table);
		
		
		
		TableColumn<User, Integer> userNo = new TableColumn<User, Integer>("userNo");
		userNo.setMaxWidth(50);
		userNo.setCellValueFactory(new PropertyValueFactory<User, Integer>("userNo"));
		userNo.setStyle("-fx-alignment: CENTER;");

		HBox h3 = new HBox();
		
		Label l3 = new Label("Enter Name:          ");
		l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l3.setTextFill(Color.ORANGERED);
		
		TextField tf3 = new TextField();
		
		Text err3=new Text(" ");
		
		h3.getChildren().addAll(l3, tf3,err3);
		
		
		
		TableColumn<User, String> userNameCol = new TableColumn<User, String>("Name");
		userNameCol.setMinWidth(100);
		userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
		userNameCol.setStyle("-fx-alignment: CENTER;");

		userNameCol.setCellFactory(TextFieldTableCell.<User>forTableColumn()); // turn cell to
																				// textfield

		userNameCol.setOnEditCommit((CellEditEvent<User, String> t) -> {
			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue()); // display
																												// only
			updateUserInfo(t.getRowValue().getUserNo(), t.getNewValue(), "userName");

		});

		HBox h4 = new HBox();
		
		Label l4 = new Label("Enter Email:           ");
		l4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l4.setTextFill(Color.ORANGERED);
		
		TextField tf4 = new TextField();
		
		Text err4=new Text(" ");
		
		h4.getChildren().addAll(l4, tf4,err4);

		TableColumn<User, String> userEmailCol = new TableColumn<User, String>("Email");
		userEmailCol.setMinWidth(200);
		userEmailCol.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
		userEmailCol.setStyle("-fx-alignment: CENTER;");

		userEmailCol.setCellFactory(TextFieldTableCell.<User>forTableColumn()); // turn cell to
		// textfield

		userEmailCol.setOnEditCommit((CellEditEvent<User, String> t) -> {
			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue()); // display

			updateUserInfo(t.getRowValue().getUserNo(), t.getNewValue(), "userEmail");

		});

		HBox h5 = new HBox();
		
		Label l5 = new Label("Enter Password:       ");
		l5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l5.setTextFill(Color.ORANGERED);
		
		PasswordField pf5 = new PasswordField();
		
		Text err5=new Text(" ");
		
		h5.getChildren().addAll(l5, pf5,err5);

		HBox h6 = new HBox();
		
		Label l6 = new Label("Confirm Password:     ");
		l6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l6.setTextFill(Color.ORANGERED);
		
		PasswordField pf6 = new PasswordField();
		
		Text err6=new Text(" ");
		
		h6.getChildren().addAll(l6, pf6,err6);

		TableColumn<User, String> userPasswordCol = new TableColumn<User, String>("Password");
		userPasswordCol.setMinWidth(150);
		userPasswordCol.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		userPasswordCol.setStyle("-fx-alignment: CENTER;");

		userPasswordCol.setCellFactory(TextFieldTableCell.<User>forTableColumn()); // turn cell to
		// textfield

		userPasswordCol.setOnEditCommit((CellEditEvent<User, String> t) -> {
			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPassword(t.getNewValue()); // display
																													// only
			updateUserInfo(t.getRowValue().getUserNo(), t.getNewValue(), "userPassword");

		});

		HBox h7 = new HBox();
		
		Label l7 = new Label("Enter Address:        ");
		l7.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l7.setTextFill(Color.ORANGERED);
		
		ComboBox<String> cb7 = new ComboBox<>();
		cb7.getItems().addAll("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron", "Bethlehem", "Tubas", "Tulkarm",
				"Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem");
		
		Text err7=new Text(" ");
		
		h7.getChildren().addAll(l7, cb7,err7);

		TableColumn<User, String> userAddressCol = new TableColumn<User, String>("Address");
		userAddressCol.setMinWidth(150);
		userAddressCol.setCellValueFactory(new PropertyValueFactory<User, String>("Address"));
		userAddressCol.setStyle("-fx-alignment: CENTER;");

		// turn cell to combo box
		userAddressCol.setCellFactory(
				ComboBoxTableCell.<User, String>forTableColumn("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron",
						"Bethlehem", "Tubas", "Tulkarm", "Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem"));
		// edit Address
		userAddressCol.setOnEditCommit((CellEditEvent<User, String> t) -> {
			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue()); // display
																													// only
			updateUserInfo(t.getRowValue().getUserNo(), t.getNewValue(), "userAddress");

		});

		HBox h8 = new HBox();
		
		Label l8 = new Label("Enter Phone Number: ");
		l8.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l8.setTextFill(Color.ORANGERED);
		
		TextField tf8 = new TextField();
		
		Text err8=new Text(" ");
		
		h8.getChildren().addAll(l8, tf8,err8);

		// edit phone
		TableColumn<User, String> userPhoneCol = new TableColumn<User, String>("Phone");
		userPhoneCol.setMinWidth(150);
		userPhoneCol.setCellValueFactory(new PropertyValueFactory<User, String>("Phone"));
		userPhoneCol.setStyle("-fx-alignment: CENTER;");

		userPhoneCol.setCellFactory(TextFieldTableCell.<User>forTableColumn()); // turn cell to
		// textfield

		userPhoneCol.setOnEditCommit((CellEditEvent<User, String> t) -> {

			updatePhoneInfo(t.getRowValue().getUserNo(), t.getRowValue().getPhone(), t.getNewValue());

			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhone(t.getNewValue()); // display
																												// only

		});

		HBox h9 = new HBox();
		
		Label l9 = new Label("Enter Permission:      ");
		l9.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l9.setTextFill(Color.ORANGERED);
		
		ComboBox<String> cb9 = new ComboBox<>();
		cb9.getItems().addAll("Customer", "Admin");
		
		Text err9=new Text(" ");
		
		h9.getChildren().addAll(l9, cb9,err9);

		TableColumn<User, String> userPermissionCol = new TableColumn<User, String>("Permission");
		userPermissionCol.setMinWidth(100);
		userPermissionCol.setCellValueFactory(new PropertyValueFactory<User, String>("Permission"));
		userPermissionCol.setStyle("-fx-alignment: CENTER;");

		// convert cell to ComboBox
		userPermissionCol.setCellFactory(ComboBoxTableCell.<User, String>forTableColumn("Customer", "Admin"));

		// edit permission
		userPermissionCol.setOnEditCommit((CellEditEvent<User, String> t) -> {
			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPermission(t.getNewValue()); // display
			// only
			updateUserInfo(t.getRowValue().getUserNo(), t.getNewValue(), "userPermission");

		});

		user_table.setItems(user_dataList);

		user_table.getColumns().addAll(userNo, userNameCol, userEmailCol, userPasswordCol, userAddressCol, userPhoneCol,
				userPermissionCol);

		HBox h10 = new HBox();
		Button b10_1 = new Button("Insert");
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h10.getChildren().addAll(b10_1, b10_2, b10_3);
		h10.setSpacing(30);

		Label er = new Label("");
		er.setPrefSize(310, 200);
		er.setAlignment(Pos.CENTER);
		gp2.add(er, 1, 9);

		// Insert Button
		b10_1.setOnAction(e10_1 -> {
			boolean isEmailExist = false;
			for (User user : user_dataList) {
				if (tf4.getText().equals(user.getEmail()))
					isEmailExist = true;
			}
			// fill textfeild
			if (tf3.getText().equals("") || tf4.getText().equals("") || pf5.getText().equals("")
					|| pf6.getText().equals("") || cb7.getSelectionModel().isEmpty() || tf8.getText().equals("")
					|| cb9.getSelectionModel().isEmpty()) {
				er.setText("Error: Please Fill All Fields !");

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
				if(tf3.getText().equals("")) {
					err3.setText("  "+"*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err3.setText("  ");
				}
				
				if(tf4.getText().equals("")) {
					err4.setText("  "+"*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err4.setText("  ");
				}
				
				if(pf5.getText().equals("")) {
					err5.setText("  "+"*");
					err5.setFill(Color.RED);
					err5.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err5.setText("  ");
				}
				
				if(pf6.getText().equals("")) {
					err6.setText("  "+"*");
					err6.setFill(Color.RED);
					err6.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err6.setText("  ");
				}
				
				if(cb7.getSelectionModel().isEmpty()) {
					err7.setText("  "+"*");
					err7.setFill(Color.RED);
					err7.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err7.setText("  ");
				}
				
				if(tf8.getText().equals("")) {
					err8.setText("  "+"*");
					err8.setFill(Color.RED);
					err8.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err8.setText("  ");
				}
				
				if(cb9.getSelectionModel().isEmpty()) {
					err9.setText("  "+"*");
					err9.setFill(Color.RED);
					err9.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err9.setText("  ");
				}
			}
			// email exist
			else if (isEmailExist) {
				err3.setText("  ");
				err5.setText("  ");
				err6.setText("  ");
				err7.setText("  ");
				err8.setText("  ");
				err9.setText("  ");
				if(isEmailExist) {
					er.setText("Error: This Email is Already Exist !");

					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					tf4.setText("");
					err4.setText("  "+"*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
			}
			// password dosen't match
			else if (!pf5.getText().equals(pf6.getText())) {
				err3.setText("  ");
				err4.setText("  ");
				err7.setText("  ");
				err8.setText("  ");
				err9.setText("  ");
				if(!pf5.getText().equals(pf6.getText())) {
					er.setText("Error: Passwords doesnt match !");

					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");

					pf5.setText("");
					pf6.setText("");
					
					err5.setText("  "+"*");
					err5.setFill(Color.RED);
					err5.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					err6.setText("  "+"*");
					err6.setFill(Color.RED);
					err6.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err5.setText("  ");
					err6.setText("  ");
				}
			}
			 else {
				
				String x7 = cb7.getSelectionModel().getSelectedItem();
				String x9 = cb9.getSelectionModel().getSelectedItem();

				int userNO;
				if (user_dataList.isEmpty())
					userNO = 1;
				else
					userNO = user_dataList.get(user_dataList.size() - 1).getUserNo() + 1;

				User u = new User(userNO, tf3.getText(), tf4.getText(), pf6.getText(), x7, tf8.getText(), x9);
				user_dataList.add(u);
				insertUserData(u);

				cart_dataList.add(new Cart(userNO,0,0));
				
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:MEDIUMSPRINGGREEN");

				er.setText("the " + x9 + ": " + tf3.getText() + " was added successfully");

				tf3.setText("");
				tf4.setText("");
				pf5.setText("");
				pf6.setText("");
				cb7.getSelectionModel().clearSelection();
				tf8.setText("");
				cb9.getSelectionModel().clearSelection();
				
				err3.setText("  ");
				err4.setText("  ");
				err5.setText("  ");
				err6.setText("  ");
				err7.setText("  ");
				err8.setText("  ");
				err9.setText("  ");
			}

		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete button
		b10_2.setOnAction(e10_2 -> {
			try {
				ObservableList<User> selectedRows;
				selectedRows = user_table.getSelectionModel().getSelectedItems();
				if(selectedRows.isEmpty()) {
					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
					er.setText("Error: please select what do you want to delete");
				}
				else {
					er.setText("");
					er.setStyle("");
					ArrayList<User> rows = new ArrayList<>(selectedRows);
					rows.forEach(row -> {
						user_table.getItems().remove(row);
						deleteUser(row);
						user_table.refresh();
						// remove its phones
						ObservableList<Phone> removeList =FXCollections.observableArrayList();
						
						
						for (Phone phone : phone_dataList) {
							if (phone.getUserNo() == row.getUserNo()) {
								removeList.add(phone);
							}
						}
						removeList.forEach(r->{
							phone_dataList.remove(r);
						});
						
						ObservableList<Cart> removeList2 =FXCollections.observableArrayList();
						// remove its cart
						for (Cart cart : cart_dataList) {
							if (cart.getUserNo() == row.getUserNo()) {
								removeList2.add(cart);
								break;
							}
						}
						removeList2.forEach(r->{
							cart_dataList.remove(r);
						});
						
						ObservableList<AddToCart> removeList3 =FXCollections.observableArrayList();
						// delete all items in the cart
						for (AddToCart c : addToCart_dataList) {
							if (c.getUserNo() == row.getUserNo()) {

								// return the product in the cart to the quantity
								for (Item i : item_dataList) {
									if (i.getItemID() == c.getItemID()) {
										int newq = Integer.parseInt(i.getQuantity()) + c.getQuantity();
										i.setQuantity(newq);
										updateItemInfo(c.getItemID(), String.valueOf(newq), "quantity");
									}
								}
								removeList3.add(c);
								deleteAddToCart(c);

							}
						}
						removeList3.forEach(r->{
							addToCart_dataList.remove(r);
						});
					});
					
					
				}
			}
			catch(Exception e) {
				
			}
			
			
		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (User row : user_dataList) {
				deleteUser(row);
				user_table.refresh();
			}
			user_table.getItems().removeAll(user_dataList);
			phone_dataList.clear();
			cart_dataList.clear();
			addToCart_dataList.clear();
		});
		//////////////////////////////////////////////////////////////////////////////////

		gp.setHgap(2);
		gp.setVgap(10);

		gp2.setHgap(20);
		gp2.setVgap(30);

		gp.add(sp, 1, 1);
		gp.add(h2, 1, 2);
		gp2.add(h3, 1, 1);
		gp2.add(h4, 1, 2);
		gp2.add(h5, 1, 3);
		gp2.add(h6, 1, 4);
		gp2.add(h7, 1, 5);
		gp2.add(h8, 1, 6);
		gp2.add(h9, 1, 7);
		gp2.add(h10, 1, 8);

/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(AdminPage());
		});

		Button btn6 = new Button("User Phones", new ImageView("images/phone.png"));
		btn6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn6.setStyle("-fx-background-color:transparent");
		btn6.setTextFill(Color.WHITE);
		btn6.setContentDisplay(ContentDisplay.RIGHT);

		btn6.setOnAction(e -> {
			stage.getScene().setRoot(phonePage());
		});

		HBox h12 = new HBox();
		h12.getChildren().addAll(btn6, btn5);
		h12.setSpacing(40);
		h12.setAlignment(Pos.CENTER);

		gp.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(90, 100, 60, 0));
		root.setCenter(gp);
		root.setBottom(h12);
		root.setRight(gp2);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	@SuppressWarnings("unchecked")
	public BorderPane phonePage() {

		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp2.setAlignment(Pos.CENTER_LEFT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Phones Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<Phone> phone_table = new TableView<Phone>(); // Item table

		phone_table.setEditable(true);
		phone_table.setPrefWidth(500);
		phone_table.setPrefHeight(500);
		phone_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(phone_table);

		TableColumn<Phone, Integer> userNoCol = new TableColumn<Phone, Integer>("userNo");
		userNoCol.setMinWidth(100);
		userNoCol.setCellValueFactory(new PropertyValueFactory<Phone, Integer>("userNo"));
		userNoCol.setStyle("-fx-alignment: CENTER;");

		HBox h3 = new HBox();
		
		Label l3 = new Label("Enter userNo:     ");
		l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l3.setTextFill(Color.ORANGERED);
		
		TextField tf3 = new TextField();
		
		Text err3=new Text("");
		
		
		h3.getChildren().addAll(l3, tf3,err3);

		TableColumn<Phone, String> userPhoneCol = new TableColumn<Phone, String>("userPhone");
		userPhoneCol.setMinWidth(380);
		userPhoneCol.setCellValueFactory(new PropertyValueFactory<Phone, String>("userPhone"));
		userPhoneCol.setStyle("-fx-alignment: CENTER;");

		userPhoneCol.setCellFactory(TextFieldTableCell.<Phone>forTableColumn()); // turn cell to
																					// textfield

		userPhoneCol.setOnEditCommit((CellEditEvent<Phone, String> t) -> {
			updatePhoneInfo(t.getRowValue().getUserNo(), t.getRowValue().getUserPhone(), t.getNewValue());

			((Phone) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUserPhone(t.getNewValue()); // display
																													// //
																													// only

		});

		HBox h4 = new HBox();
		
		Label l4 = new Label("Enter userPhone:  ");
		l4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l4.setTextFill(Color.ORANGERED);
		
		TextField tf4 = new TextField();
		
		Text err4=new Text("");
		
		
		h4.getChildren().addAll(l4, tf4,err4);

		Label er = new Label("");
		er.setPrefSize(330, 200);
		er.setAlignment(Pos.CENTER);
		gp2.add(er, 1, 12);

		phone_table.setItems(phone_dataList);

		phone_table.getColumns().addAll(userNoCol, userPhoneCol);

		HBox h11 = new HBox();
		Button b10_1 = new Button("Insert");
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h11.getChildren().addAll(b10_1, b10_2, b10_3);
		h11.setSpacing(30);

		// Insert Button
		b10_1.setOnAction(e10_1 -> {

			if (tf3.getText().equals("") || tf4.getText().equals("")) {
				er.setText("    Error: Please Fill All Fields !");

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
				if(tf3.getText().equals("")) {
					err3.setText("  "+"*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err3.setText("");
				}
				
				if(tf4.getText().equals("")) {
					err4.setText("  "+"*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err4.setText("");
				}
			}
			else { // insert Phone
				err3.setText("");
				err4.setText("");
				// to check if the userNo is valid(exists)
				boolean validUserNo = false;
				for (User user : user_dataList) {
					if (String.valueOf(user.getUserNo()).equals(tf3.getText())) {
						validUserNo = true;
						break;
					}
				}
				if (validUserNo) {
					Phone p = new Phone(tf4.getText(), Integer.parseInt(tf3.getText()));

					boolean isPhoneExist = false;
					for (Phone phone : phone_dataList) {
						if (p.getUserPhone().equals(phone.getUserPhone())) {
							isPhoneExist = true;
							break;
						}
					}

					if (isPhoneExist) {
						er.setText("    Error: This Phone is already Exists !");

						er.setStyle(
								"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
										+ "-fx-text-fill:#ff0000");
					} else {
						//check if the user have no phone in the user table
						for (User u : user_dataList) {
							if(u.getUserNo()==p.getUserNo() && u.getPhone().equals("")){
								u.setPhone(p.getUserPhone());
							}
						}
						phone_dataList.add(p);
						insertPhoneData(p);
						er.setStyle(
								"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
										+ "-fx-text-fill:MEDIUMSPRINGGREEN");

						er.setText("The Phone: " + p.getUserPhone() + " was added successfully to userNo= "
								+ p.getUserNo());

						tf3.clear();
						tf4.clear();
					}

				} else {
					er.setText("    Error: Invalid userNo !");

					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}

			}

		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete button
		b10_2.setOnAction(e10_2 -> {
			ObservableList<Phone> selectedRows = phone_table.getSelectionModel().getSelectedItems();
			ArrayList<Phone> rows = new ArrayList<>(selectedRows);
			if(rows.isEmpty()) {
				er.setText("Error: Please select what do you want to delete");
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			}
			else {
				er.setText("");
				er.setStyle("");
				rows.forEach(row -> {
					
					boolean exist=false;
					User u2=null;
					//when delete a phone which is assigned to a User in user Table
					for (User u : user_dataList) {
						if(u.getUserNo()==row.getUserNo() && u.getPhone().equals(row.getUserPhone())) {
							u2=u;
							for (Phone phone : phone_dataList) {
								if(phone.getUserNo()==u.getUserNo() && !phone.getUserPhone().equals(u.getPhone())) {
									u.setPhone(phone.getUserPhone());
									exist=true;
									break;
								}
							}
						}
					}
					if(!exist){
						u2.setPhone("");
					}
					
					phone_table.getItems().remove(row);
					deletePhone(row);
					phone_table.refresh();
				});
			}
			
		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (Phone row : phone_dataList) {
				deletePhone(row);
				phone_table.refresh();
			}
			phone_table.getItems().removeAll(phone_dataList);
		});
		//////////////////////////////////////////////////////////////////////////////////

		gp.setHgap(2);
		gp.setVgap(10);

		gp2.setHgap(20);
		gp2.setVgap(30);

		gp.add(sp, 1, 4);
		gp.add(h2, 1, 7);
		gp2.add(h3, 1, 8);
		gp2.add(h4, 1, 9);
		gp2.add(h11, 1, 13);

/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.TOP_CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(userPage());
		});

		btn5.setPadding(new Insets(80, 0, 0, 10));
		gp2.setPadding(new Insets(0, 20, 0, 0));
		gp.setPadding(new Insets(0, 130, 0, 0));
		gp.setAlignment(Pos.CENTER_RIGHT);
		root.setPadding(new Insets(100, 80, 100, 0));
		root.setCenter(gp);
		root.setBottom(btn5);
		root.setRight(gp2);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	@SuppressWarnings("unchecked")
	public BorderPane itemPage() {

		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp2.setAlignment(Pos.CENTER_LEFT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Item Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<Item> cart_table = new TableView<Item>(); // Item table

		cart_table.setEditable(true);
		cart_table.setPrefWidth(980);
		cart_table.setPrefHeight(500);
		cart_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(cart_table);

		TableColumn<Item, Integer> itemIDCol = new TableColumn<Item, Integer>("itemID");
		itemIDCol.setMaxWidth(50);
		itemIDCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemID"));
		itemIDCol.setStyle("-fx-alignment: CENTER;");

		HBox h3 = new HBox();
		
		Label l3 = new Label("Enter item Name:     ");
		l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l3.setTextFill(Color.ORANGERED);
		
		TextField tf3 = new TextField();
		
		Text err3=new Text();
		
		h3.getChildren().addAll(l3, tf3,err3);

		TableColumn<Item, String> itemNameCol = new TableColumn<Item, String>("itemName");
		itemNameCol.setMinWidth(100);
		itemNameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		itemNameCol.setStyle("-fx-alignment: CENTER;");

		itemNameCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn()); // turn cell to
																				// textfield

		itemNameCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setItemName(t.getNewValue()); // display
																													// only
			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "itemName");

		});

		HBox h4 = new HBox();
		
		Label l4 = new Label("Enter brand:           ");
		l4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l4.setTextFill(Color.ORANGERED);

		ComboBox<String> cb4 = new ComboBox<>();
		cb4.getItems().addAll("Nike", "Addidas", "Skechers", "Reebok", "Diadora", "UnderArmor", "Puma");
		
		Text err4=new Text();
		
		h4.getChildren().addAll(l4, cb4,err4);

		TableColumn<Item, String> brandNameCol = new TableColumn<Item, String>("brand");
		// brandNameCol.setMinWidth(100);
		brandNameCol.setMaxWidth(170);
		brandNameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("brand"));
		brandNameCol.setStyle("-fx-alignment: CENTER;");

		// convert cell to ComboBox
		brandNameCol.setCellFactory(ComboBoxTableCell.<Item, String>forTableColumn("Nike", "Addidas", "Skechers",
				"Reebok", "Diadora", "UnderArmor", "Puma"));

		brandNameCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBrand(t.getNewValue()); // display

			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "brand");

		});

		HBox h5 = new HBox();
		
		Label l5 = new Label("Enter Color:            ");
		l5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l5.setTextFill(Color.ORANGERED);

		ComboBox<String> cb5 = new ComboBox<>();
		cb5.getItems().addAll("Red", "Blue", "Green", "Yellow", "Orange", "Purple", "Black", "White", "Grey", "Pink");
		
		Text err5=new Text();
		
		h5.getChildren().addAll(l5, cb5,err5);
		

		TableColumn<Item, String> colorCol = new TableColumn<Item, String>("color");
		colorCol.setMinWidth(150);
		colorCol.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
		colorCol.setStyle("-fx-alignment: CENTER;");

		colorCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn()); // turn cell to
		// textfield
		colorCol.setCellFactory(ComboBoxTableCell.<Item, String>forTableColumn("Red", "Blue", "Green", "Yellow",
				"Orange", "Purple", "Black", "White", "Grey", "Pink"));
		colorCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColor(t.getNewValue()); // display
																												// only
			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "color");

		});

		HBox h6 = new HBox();
		
		Label l6 = new Label("Enter Category:        ");
		l6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l6.setTextFill(Color.ORANGERED);

		ComboBox<String> cb6 = new ComboBox<>();
		cb6.getItems().addAll("Boot", "Short", "T-Shirt", "Cap", "Pant", "Bag", "Gloves");
		
		Text err6=new Text();
		
		h6.getChildren().addAll(l6, cb6,err6);

		TableColumn<Item, String> categoryCol = new TableColumn<Item, String>("category");
		categoryCol.setMinWidth(150);
		categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
		categoryCol.setStyle("-fx-alignment: CENTER;");

		// turn cell to text field
		categoryCol.setCellFactory(ComboBoxTableCell.<Item, String>forTableColumn("Boot", "Short", "T-Shirt", "Cap",
				"Pant", "Bag", "Gloves"));

		// edit category
		categoryCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCategory(t.getNewValue()); // display
																													// only
			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "category");
		});

		HBox h7 = new HBox();
		
		Label l7 = new Label("Enter item size:        ");
		l7.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l7.setTextFill(Color.ORANGERED);
		
		TextField tf7 = new TextField();
		
		Text err7=new Text();
		
		h7.getChildren().addAll(l7, tf7,err7);

		// edit phone
		TableColumn<Item, String> sizeCol = new TableColumn<Item, String>("size");
		sizeCol.setMinWidth(150);
		sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("size"));
		sizeCol.setStyle("-fx-alignment: CENTER;");

		sizeCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn()); // turn cell to
		// textfield

		sizeCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());

		sizeCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {

			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "size");

			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setSize(Double.parseDouble(t.getNewValue())); // display
			// only

		});

		HBox h8 = new HBox();
		
		Label l8 = new Label("Enter item price:       ");
		l8.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l8.setTextFill(Color.ORANGERED);
		
		TextField tf8 = new TextField();
		
		Text err8=new Text();
		
		h8.getChildren().addAll(l8, tf8,err8);

		TableColumn<Item, String> priceCol = new TableColumn<Item, String>("price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("price"));
		priceCol.setStyle("-fx-alignment: CENTER;");

		priceCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		// edit permission
		priceCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setPrice(Double.parseDouble(t.getNewValue())); // display
			// only
			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "price");
			// t.getNewValue(),"userPermission");

		});

		HBox h9 = new HBox();
		
		Label l9 = new Label("Enter quantity:         ");
		l9.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l9.setTextFill(Color.ORANGERED);
		
		TextField tf9 = new TextField();
		
		Text err9=new Text();
		
		h9.getChildren().addAll(l9, tf9,err9);

		TableColumn<Item, String> quantityCol = new TableColumn<Item, String>("quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
		quantityCol.setStyle("-fx-alignment: CENTER;");

		// convert cell to ComboBox
		quantityCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());

		// edit permission
		quantityCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setQuantity(Integer.parseInt(t.getNewValue())); // display
			// only
			updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "quantity");
			// t.getNewValue(),"userPermission");

		});

		HBox h10 = new HBox();
		
		Label l10 = new Label("Enter Warhouse ID:   ");
		l10.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l10.setTextFill(Color.ORANGERED);
		
		TextField tf10 = new TextField();
		
		Text err10=new Text();
		
		h10.getChildren().addAll(l10, tf10,err10);

		Label er = new Label("");
		er.setPrefSize(230, 100);
		er.setAlignment(Pos.CENTER);
		gp2.add(er, 1, 10);

		TextArea er2 = new TextArea();
		er2.setVisible(false);
		er2.setPrefSize(400, 300);
		// er2.setTAlignment(Pos.CENTER);
		gp2.add(er2, 1, 10);

		TableColumn<Item, String> warehouseIDCol = new TableColumn<Item, String>("warehouseID");
		warehouseIDCol.setMinWidth(100);
		warehouseIDCol.setCellValueFactory(new PropertyValueFactory<Item, String>("warehouseID"));
		warehouseIDCol.setStyle("-fx-alignment: CENTER;");

		// convert cell to ComboBox
		warehouseIDCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());

		// edit permission
		warehouseIDCol.setOnEditCommit((CellEditEvent<Item, String> t) -> {

			boolean validWarehouse = false;
			for (Warehouse warehouse : warehouse_dataList) {
				if (String.valueOf(warehouse.getWarehouseID()).equals(t.getNewValue())) {
					validWarehouse = true;
					break;
				}

			}
			if (validWarehouse) {
				((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setWarehouseID(Integer.parseInt(t.getNewValue())); // display
				// only

				updateItemInfo(t.getRowValue().getItemID(), t.getNewValue(), "warehouseID");
				er2.setText("");
				er2.setVisible(false);
			} else {
				er2.setVisible(true);
				// print an error

				String w = "    Valid warehouses: ";
				for (Warehouse warehouse : warehouse_dataList) {
					w += warehouse.getWarehouseID() + ", ";

				}

			}

		});

		cart_table.setItems(item_dataList);

		cart_table.getColumns().addAll(itemIDCol, itemNameCol, brandNameCol, colorCol, categoryCol, sizeCol, priceCol,
				quantityCol, warehouseIDCol);

		HBox h11 = new HBox();
		Button b10_1 = new Button("Insert");
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h11.getChildren().addAll(b10_1, b10_2, b10_3);
		h11.setSpacing(30);

		// Insert Button
		b10_1.setOnAction(e10_1 -> {
			

			if (tf3.getText().equals("") || cb4.getSelectionModel().isEmpty() || cb5.getSelectionModel().isEmpty() || cb6.getSelectionModel().isEmpty() ||
					tf7.getText().equals("") || tf8.getText().equals("") || tf9.getText().equals("") || tf10.getText().equals("")) {
				er.setText("    Error: Please Fill All Fields !");

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
				if(tf3.getText().equals("")) {
					err3.setText("  "+"*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err3.setText("");
				}
				
				if(cb4.getSelectionModel().isEmpty()) {
					err4.setText("  "+"*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err4.setText("");
				}
				
				if(cb5.getSelectionModel().isEmpty()) {
					err5.setText("  "+"*");
					err5.setFill(Color.RED);
					err5.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err5.setText("");
				}
				
				if(cb6.getSelectionModel().isEmpty()) {
					err6.setText("  "+"*");
					err6.setFill(Color.RED);
					err6.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err6.setText("");
				}
				
				if(tf7.getText().equals("")) {
					err7.setText("  "+"*");
					err7.setFill(Color.RED);
					err7.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err7.setText("");
				}
				
				if(tf8.getText().equals("")) {
					err8.setText("  "+"*");
					err8.setFill(Color.RED);
					err8.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err8.setText("");
				}
				
				if(tf9.getText().equals("")) {
					err9.setText("  "+"*");
					err9.setFill(Color.RED);
					err9.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err9.setText("");
				}
				if(tf10.getText().equals("")) {
					err10.setText("  "+"*");
					err10.setFill(Color.RED);
					err10.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err10.setText("");
				}
				
			} else { // insert Item
				String brand4 = cb4.getSelectionModel().getSelectedItem();
				String color5 = cb5.getSelectionModel().getSelectedItem();
				String category6 = cb6.getSelectionModel().getSelectedItem();
				int itemID;
				if (item_dataList.isEmpty())
					itemID = 1;
				else
					itemID = item_dataList.get(item_dataList.size() - 1).getItemID() + 1;

				Item i = new Item(itemID, tf3.getText(), brand4, color5, category6, Double.parseDouble(tf7.getText()),
						Double.parseDouble(tf8.getText()), Integer.parseInt(tf9.getText()),
						Integer.parseInt(tf10.getText()));

				// check if the warehouseID is exists in warehouse table
				boolean validWarehouse = false;
				for (Warehouse warehouse : warehouse_dataList) {
					if (String.valueOf(warehouse.getWarehouseID()).equals(i.getWarehouseID()))
						validWarehouse = true;
				}
				if (validWarehouse) {
					err3.setText("");
					err4.setText("");
					err5.setText("");
					err6.setText("");
					err7.setText("");
					err8.setText("");
					err9.setText("");
					err10.setText("");
					er2.setVisible(false);
					item_dataList.add(i);
					insertItemData(i);
					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:MEDIUMSPRINGGREEN");

					er.setText("the " + category6 + ": " + tf3.getText() + " was added successfully");
					tf3.setText("");
					cb4.getSelectionModel().clearSelection();
					cb5.getSelectionModel().clearSelection();
					cb6.getSelectionModel().clearSelection();
					tf7.setText("");
					tf8.setText("");
					tf9.setText("");
					tf10.setText("");
				} else {
					err3.setText("");
					err4.setText("");
					err5.setText("");
					err6.setText("");
					err7.setText("");
					err8.setText("");
					err9.setText("");
					tf10.setText("");
//					er.setText("Error: Invalid WarehouseID !");
//
//					er.setStyle(
//							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
//									+ "-fx-text-fill:#ff0000");

					//er2.setVisible(true);
					// print an error

					String w = "    Valid warehouses: ";
					for (Warehouse warehouse : warehouse_dataList) {
						w += warehouse.getWarehouseID() + ", ";

					}
					er.setText("    Error: Please Enter Valid Warehouse !" + "\n" + w);

					er.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");

				}

			}

		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete button
		b10_2.setOnAction(e10_2 -> {
			err3.setText("");
			err4.setText("");
			err5.setText("");
			err6.setText("");
			err7.setText("");
			err8.setText("");
			err9.setText("");
			err10.setText("");
			er.setText("");
			er.setStyle("");
			
			
			ObservableList<Item> selectedRows = cart_table.getSelectionModel().getSelectedItems();
			ArrayList<Item> rows = new ArrayList<>(selectedRows);
			if(rows.isEmpty()) {
				er.setText("Error: Please select what do you want to delete");
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			}
			else {
				er.setText("");
				er.setStyle("");
				rows.forEach(row -> {
					ObservableList<AddToCart> removeList = FXCollections.observableArrayList();;
					for (AddToCart a : addToCart_dataList) {
						if(a.getItemID()==a.getItemID()) {
							removeList.add(a);
						}
					}
					removeList.forEach(r->{
						addToCart_dataList.remove(r);
					});
					
					cart_table.getItems().remove(row);
					deleteItem(row);
					cart_table.refresh();
				});
			}
			
		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (Item row : item_dataList) {
				deleteItem(row);
				cart_table.refresh();
			}
			addToCart_dataList.clear();
			cart_table.getItems().removeAll(item_dataList);
		});
		//////////////////////////////////////////////////////////////////////////////////

		gp.setHgap(2);
		gp.setVgap(10);

		gp2.setHgap(20);
		gp2.setVgap(30);

		gp.add(sp, 1, 0);
		gp.add(h2, 1, 1);
		gp2.add(h3, 1, 1);
		gp2.add(h4, 1, 2);
		gp2.add(h5, 1, 3);
		gp2.add(h6, 1, 4);
		gp2.add(h7, 1, 5);
		gp2.add(h8, 1, 6);
		gp2.add(h9, 1, 7);
		gp2.add(h10, 1, 8);
		gp2.add(h11, 1, 9);

/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		HBox hb = new HBox(); 
		
		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.TOP_CENTER);
		//btn5.setPrefWidth(160);
		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(AdminPage());
		});
		
		Button btn6 = new Button("Warehouses", new ImageView("images/warehouse.png"));
		btn6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 23));
		btn6.setStyle("-fx-background-color:transparent");
		btn6.setTextFill(Color.WHITE);
		btn6.setContentDisplay(ContentDisplay.RIGHT);
		//btn6.setPrefSize(100, 160);
		btn6.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(warehousePage());
		});

		
		gp2.setPadding(new Insets(120, 0, 0, 0));
		hb.setPadding(new Insets(10, 100, 100, 160));
		gp.setPadding(new Insets(0, 0, 20, 0));


		btn5.setPadding(new Insets(0, 0, 10, 0));
		
		hb.getChildren().addAll(btn6, btn5);
		hb.setSpacing(40);
		hb.setAlignment(Pos.TOP_CENTER);
		
		gp.setAlignment(Pos.CENTER);
	//	root.setPadding(new Insets(0, 0, 100, 0));
		root.setCenter(gp);
		root.setBottom(hb);
		root.setRight(gp2);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	public BorderPane warehousePage() {

		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp2.setAlignment(Pos.CENTER_LEFT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Warehouse Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<Warehouse> warehouse_table = new TableView<Warehouse>(); // table
		warehouse_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		warehouse_table.setEditable(true);
		warehouse_table.setPrefWidth(615);
		warehouse_table.setPrefHeight(400);

		h2.getChildren().add(warehouse_table);

		TableColumn<Warehouse, Integer> warehouseIDCol = new TableColumn<Warehouse, Integer>("warehouseID");
		warehouseIDCol.setMinWidth(200);
		warehouseIDCol.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("warehouseID"));
		warehouseIDCol.setStyle("-fx-alignment: CENTER;");

		HBox h3 = new HBox();
		
		Label l3 = new Label("Enter warehouseName: ");
		l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l3.setTextFill(Color.ORANGERED);
		
		TextField tf3 = new TextField();
		
		Text err3=new Text();
		
		h3.getChildren().addAll(l3, tf3,err3);

		TableColumn<Warehouse, String> warehouseNameCol = new TableColumn<Warehouse, String>("warehouseName");
		warehouseNameCol.setMinWidth(200);
		warehouseNameCol.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("warehouseName"));
		warehouseNameCol.setStyle("-fx-alignment: CENTER;");

		warehouseNameCol.setCellFactory(TextFieldTableCell.<Warehouse>forTableColumn()); // turn cell to
		// textfield

		warehouseNameCol.setOnEditCommit((CellEditEvent<Warehouse, String> t) -> {
			((Warehouse) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setWarehouseName(t.getNewValue()); // display
			updateWarehouseInfo(t.getRowValue().getWarehouseID(), t.getNewValue(), "warehouseName");

		});

		HBox h4 = new HBox();
		
		Label l4 = new Label("Enter warehouseLocation:");
		l4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l4.setTextFill(Color.ORANGERED);
		
		ComboBox<String> cb4 = new ComboBox<>();
		cb4.getItems().addAll("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron", "Bethlehem", "Tubas", "Tulkarm",
				"Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem");
		
		Text err4=new Text();
		
		h4.getChildren().addAll(l4, cb4,err4);

		TableColumn<Warehouse, String> warehouseLocationCol = new TableColumn<Warehouse, String>("warehouseLocation");
		warehouseLocationCol.setMinWidth(200);
		warehouseLocationCol.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("warehouseLocation"));
		warehouseLocationCol.setStyle("-fx-alignment: CENTER;");

		warehouseLocationCol.setCellFactory(
				ComboBoxTableCell.<Warehouse, String>forTableColumn("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron",
						"Bethlehem", "Tubas", "Tulkarm", "Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem"));
		// textfield

		warehouseLocationCol.setOnEditCommit((CellEditEvent<Warehouse, String> t) -> {
			((Warehouse) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setWarehouseLocation(t.getNewValue()); // display

			updateWarehouseInfo(t.getRowValue().getWarehouseID(), t.getNewValue(), "warehouseLocation");

		});

		warehouse_table.setItems(warehouse_dataList);

		warehouse_table.getColumns().addAll(warehouseIDCol, warehouseNameCol, warehouseLocationCol);

		HBox h10 = new HBox();
		Button b10_1 = new Button("Insert");
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h10.getChildren().addAll(b10_1, b10_2, b10_3);
		h10.setSpacing(30);

		Label er = new Label("");
		er.setPrefSize(310, 200);
		er.setAlignment(Pos.CENTER);
		gp2.add(er, 1, 10);

		// Insert Button
		b10_1.setOnAction(e10_1 -> {
			if (tf3.getText().equals("") || cb4.getSelectionModel().isEmpty()) {
				er.setText("    Error: Please Fill All Fields !");

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
				if(tf3.getText().equals("")) {
					err3.setText("*");
					err3.setFill(Color.RED);
					err3.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err3.setText("");
				}
				
				if(cb4.getSelectionModel().isEmpty()) {
					err4.setText("*");
					err4.setFill(Color.RED);
					err4.setStyle(
							"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
									+ "-fx-text-fill:#ff0000");
				}
				else {
					err4.setText("");
				}
			

			} else {

				int warehouseID;
				if (warehouse_dataList.isEmpty())
					warehouseID = 1;
				else
					warehouseID = warehouse_dataList.get(warehouse_dataList.size() - 1).getWarehouseID() + 1;

				Warehouse u = new Warehouse(warehouseID, tf3.getText(), cb4.getSelectionModel().getSelectedItem());
				warehouse_dataList.add(u);
				insertWarehouseData(u);

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:MEDIUMSPRINGGREEN");

				er.setText("the warehouse: " + tf3.getText() + ", with ID: " + warehouseID + " was added successfully");

				tf3.setText("");
				cb4.getSelectionModel().clearSelection();
			}

		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete button
		b10_2.setOnAction(e10_2 -> {
			ObservableList<Warehouse> selectedRows = warehouse_table.getSelectionModel().getSelectedItems();
			ArrayList<Warehouse> rows = new ArrayList<>(selectedRows);
			er.setText("");
			er.setStyle("");
			err3.setText("");
			err4.setText("");
			if(rows.isEmpty()) {
				er.setText("Error: Please select what do you want to delete");
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			}
			else {
				rows.forEach(row -> {
					warehouse_table.getItems().remove(row);
					deleteWarehouse(row);
					warehouse_table.refresh();
				});
			}
			
		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (Warehouse row : warehouse_dataList) {
				deleteWarehouse(row);
				warehouse_table.refresh();
			}
			warehouse_table.getItems().removeAll(warehouse_dataList);
		});
		//////////////////////////////////////////////////////////////////////////////////

		gp.setHgap(2);
		gp.setVgap(10);

		gp2.setHgap(20);
		gp2.setVgap(30);
		
		gp.add(sp, 1, 1);
		gp.add(h2, 1, 2);
		h3.setSpacing(30);
		h4.setSpacing(18);
		gp2.add(h3, 1, 2);
		gp2.add(h4, 1, 3);
		gp2.add(h10, 1, 9);

/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(itemPage());
		});

		gp2.setPadding(new Insets(150, 0, 0, 0));
		gp.setPadding(new Insets(0, 100, 0, 0));
		gp.setAlignment(Pos.CENTER_RIGHT);
		root.setPadding(new Insets(90, 100, 60, 0));
		root.setCenter(gp);
		root.setBottom(btn5);
		root.setRight(gp2);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	public BorderPane addToCartPage() {

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Items In Cart Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<AddToCart> addToCart_table = new TableView<AddToCart>(); // Item table

		addToCart_table.setEditable(true);
		addToCart_table.setPrefWidth(790);
		addToCart_table.setPrefHeight(750);
		addToCart_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(addToCart_table);

		TableColumn<AddToCart, Integer> userNoCol = new TableColumn<AddToCart, Integer>("userNo");
		userNoCol.setMinWidth(120);
		userNoCol.setCellValueFactory(new PropertyValueFactory<AddToCart, Integer>("userNo"));
		userNoCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<AddToCart, Integer> itemIDCol = new TableColumn<AddToCart, Integer>("itemID");
		itemIDCol.setMinWidth(120);
		itemIDCol.setCellValueFactory(new PropertyValueFactory<AddToCart, Integer>("itemID"));
		itemIDCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<AddToCart, Integer> quantityCol = new TableColumn<AddToCart, Integer>("quantity");
		quantityCol.setMinWidth(120);
		quantityCol.setCellValueFactory(new PropertyValueFactory<AddToCart, Integer>("quantity"));
		quantityCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<AddToCart, Double> totalPriceCol = new TableColumn<AddToCart, Double>("itemPrice");
		totalPriceCol.setMinWidth(200);
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<AddToCart, Double>("itemPrice"));
		totalPriceCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<AddToCart, Double> finalPriceCol = new TableColumn<AddToCart, Double>("finalPrice");
		finalPriceCol.setMinWidth(210);
		finalPriceCol.setCellValueFactory(new PropertyValueFactory<AddToCart, Double>("finalPrice"));
		finalPriceCol.setStyle("-fx-alignment: CENTER;");

		addToCart_table.setItems(addToCart_dataList);

		// System.out.println(addToCart_dataList);
		addToCart_table.getColumns().addAll(userNoCol, itemIDCol, quantityCol, totalPriceCol, finalPriceCol);
		HBox h11 = new HBox();
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h11.getChildren().addAll(b10_2, b10_3);
		h11.setSpacing(30);
		Text er=new Text("");
		er.setStyle(
				"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
						+ "-fx-text-fill:#ff0000");
		// delete button
		b10_2.setOnAction(e10_2 -> {
			ObservableList<AddToCart> selectedRows = addToCart_table.getSelectionModel().getSelectedItems();
			ArrayList<AddToCart> rows = new ArrayList<>(selectedRows);
			if(rows.isEmpty()) {
				er.setText("Error: Please select what do you want to delete");
				er.setFill(Color.RED);
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			} else {
				er.setText("");
				er.setStyle("");
				rows.forEach(row -> {

					for (Cart cart : cart_dataList) {
						if (cart.getUserNo() == row.getUserNo()) {
							cart.setNoOfItems(0);
							cart.setTotalPrice(0);
							updateCartInfo(cart.getUserNo(), String.valueOf(cart.getNoOfItems()), "noOfItems");
							updateCartInfo(cart.getUserNo(), String.valueOf(cart.getNoOfItems()), "totalPrice");
						}

					}
					addToCart_table.getItems().remove(row);
					deleteAddToCart(row);
					addToCart_table.refresh();
				});

			}
		});
//		//////////////////////////////////////////////////////////////////////////////////
//
		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (AddToCart row : addToCart_dataList) {
				deleteAddToCart(row);
				addToCart_table.refresh();
			}
			for (Cart cart : cart_dataList) {
				cart.setNoOfItems(0);
				cart.setTotalPrice(0);
				updateCartInfo(cart.getUserNo(), String.valueOf(cart.getNoOfItems()), "noOfItems");
				updateCartInfo(cart.getUserNo(), String.valueOf(cart.getNoOfItems()), "totalPrice");

			}
			addToCart_table.getItems().removeAll(addToCart_dataList);
		});

		gp.add(sp, 0, 0);
		gp.add(addToCart_table, 0, 2);
		gp.setVgap(20);
		gp.setPadding(new Insets(0, 0, 0, 70));
		/////////////////////////////////////////////

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		VBox v10 = new VBox();
		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(AdminPage());
		});

		Button btn6 = new Button("Carts", new ImageView("images/carts.png"));
		btn6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn6.setStyle("-fx-background-color:transparent");
		btn6.setTextFill(Color.WHITE);
		btn6.setContentDisplay(ContentDisplay.RIGHT);

		btn6.setOnAction(e -> {
			stage.getScene().setRoot(cartPage());
		});

		Button btn7 = new Button("Orders", new ImageView("images/order.png"));
		btn7.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn7.setStyle("-fx-background-color:transparent");
		btn7.setTextFill(Color.WHITE);
		btn7.setContentDisplay(ContentDisplay.RIGHT);

		btn7.setOnAction(e -> {
			stage.getScene().setRoot(orderPage());
		});
		
		HBox h12 = new HBox();
		h12.getChildren().addAll(btn7, btn6, btn5);
		h12.setSpacing(30);
		h12.setAlignment(Pos.CENTER_LEFT);
		h12.setPadding(new Insets(0, 0, 0, 70));
		
		v10.getChildren().addAll(h11,er, h12);
		h11.setAlignment(Pos.CENTER_LEFT);
		// btn5.setPadding(new Insets(0, 0,0, 240));
		v10.setAlignment(Pos.CENTER_LEFT);
		v10.setPadding(new Insets(40, 0, 0, 350));
		v10.setSpacing(40);

		gp.setPadding(new Insets(0, 0, 0, 0));
		gp.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100, 80, 20, 0));
		root.setCenter(gp);
		root.setBottom(v10);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}
	
	public BorderPane cartPage() {

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Cart Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<Cart> cart_table = new TableView<Cart>(); // Item table

		cart_table.setEditable(true);
		cart_table.setPrefWidth(500);
		cart_table.setPrefHeight(500);
		cart_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(cart_table);

		TableColumn<Cart, Integer> userNoCol = new TableColumn<Cart, Integer>("userNo");
		userNoCol.setMinWidth(160);
		userNoCol.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("userNo"));
		userNoCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<Cart, Integer> noOfItemsCol = new TableColumn<Cart, Integer>("noOfItems");
		noOfItemsCol.setMinWidth(160);
		noOfItemsCol.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("noOfItems"));
		noOfItemsCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<Cart, Double> totalPriceCol = new TableColumn<Cart, Double>("totalPrice");
		totalPriceCol.setMinWidth(160);
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<Cart, Double>("totalPrice"));
		totalPriceCol.setStyle("-fx-alignment: CENTER;");

		cart_table.setItems(cart_dataList);

		cart_table.getColumns().addAll(userNoCol, noOfItemsCol, totalPriceCol);

		gp.add(sp, 0, 0);
		gp.add(cart_table, 0, 2);
		gp.setVgap(20);
		gp.setPadding(new Insets(0, 0, 0, 70));
/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.TOP_CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(addToCartPage());
		});

		btn5.setPadding(new Insets(80, 0, 0, 10));
		gp.setPadding(new Insets(0, 130, 0, 0));
		gp.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100, 80, 100, 0));
		root.setCenter(gp);
		root.setBottom(btn5);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	@SuppressWarnings("unchecked")
	public BorderPane orderPage() {

		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp2.setAlignment(Pos.CENTER_LEFT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Order Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<Orders> Orders_table = new TableView<Orders>(); // Item table

		Orders_table.setEditable(true);
		Orders_table.setPrefWidth(800);
		Orders_table.setPrefHeight(400);
		Orders_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(Orders_table);

		TableColumn<Orders, Integer> orderIDCol = new TableColumn<Orders, Integer>("orderID");
		orderIDCol.setMinWidth(100);
		orderIDCol.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("orderID"));
		orderIDCol.setStyle("-fx-alignment: CENTER;");


		TableColumn<Orders, java.util.Date> confirmationDateCol = new TableColumn<Orders, java.util.Date>("confirmationDate");
		confirmationDateCol.setMinWidth(290);
		confirmationDateCol.setCellValueFactory(new PropertyValueFactory<Orders, java.util.Date>("confirmationDate"));
		confirmationDateCol.setStyle("-fx-alignment: CENTER;");


		
		TableColumn<Orders, Double> totalPriceCol = new TableColumn<Orders, Double>("totalPrice");
		totalPriceCol.setMinWidth(130);
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<Orders, Double>("totalPrice"));
		totalPriceCol.setStyle("-fx-alignment: CENTER;");


		TableColumn<Orders, Integer> userNoCol = new TableColumn<Orders, Integer>("userNo");
		userNoCol.setMinWidth(130);
		userNoCol.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("userNo"));
		userNoCol.setStyle("-fx-alignment: CENTER;");
		
		
		
		TableColumn<Orders, String> deliveryIDCol = new TableColumn<Orders, String>("deliveryID");
		deliveryIDCol.setMinWidth(130);
		deliveryIDCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("deliveryID"));
		deliveryIDCol.setStyle("-fx-alignment: CENTER;");
		
		Label er = new Label("");
		er.setPrefSize(310, 200);
		er.setAlignment(Pos.CENTER);
		
		TextArea er2 = new TextArea();
		er2.setVisible(false);
		er2.setPrefSize(50, 20);
		er2.setPrefSize(400, 300);
		// er2.setTAlignment(Pos.CENTER);
		
		
		deliveryIDCol.setCellFactory(TextFieldTableCell.<Orders>forTableColumn());

		// edit warehouse
		deliveryIDCol.setOnEditCommit((CellEditEvent<Orders, String> t) -> {

			boolean validDelivery = false;
			for (DeliveryCompany delivery : delivery_dataList) {
				if (String.valueOf(delivery.getDeliveryID()).equals(t.getNewValue())) {
					validDelivery = true;
					break;
				}

			}
			if (validDelivery) {
				((Orders) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setDeliveryID(Integer.parseInt(t.getNewValue())); // display


				updateOrder(t.getRowValue().getOrderID(), t.getNewValue(), "deliveryID");
				er2.setText("");
				er2.setVisible(false);
			} else {
				er2.setVisible(true);
				// print an error

				String w = "    Valid dliveryIDs: ";
				for (DeliveryCompany d : delivery_dataList) {
					w += d.getDeliveryID() + ", ";

				}
				er.setText("");
				er2.setText("    Error: Please Enter Valid deliveryID !" + "\n" + w);

				er2.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			}

		});
		
		
		
		Orders_table.setItems(order_dataList);

		Orders_table.getColumns().addAll(orderIDCol, confirmationDateCol, totalPriceCol,userNoCol,deliveryIDCol );

		HBox h11 = new HBox();
		
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h11.getChildren().addAll(b10_2, b10_3);
		h11.setSpacing(30);

		VBox v12 = new VBox();
		DatePicker datePicker = new DatePicker();
		v12.setAlignment(Pos.CENTER);
		v12.setPadding(new Insets(0,10,0,0));
		
		Text text2 = new Text(500, 100, "");
		text2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
		text2.setFill(Color.WHITE);
		
		Text text3 = new Text(500, 100, "");
		text3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
		text3.setFill(Color.WHITE);
		
		Text text4 = new Text(500, 100, "");
		text4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
		text4.setFill(Color.WHITE);
		
		Text text5 = new Text(500, 100, "");
		text5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
		text5.setFill(Color.WHITE);
		v12.getChildren().addAll(datePicker,text2,text3,text4,text5);


		datePicker.setOnAction(e->{
			
			ObservableList<Orders> orderAfterDate_dataList = FXCollections.observableArrayList();
			Date selectedDate = java.sql.Date.valueOf(datePicker.getValue());
			for (Orders orders : order_dataList) {			
				if(selectedDate.compareTo(orders.getConfirmationDate())<=0) {
					orderAfterDate_dataList.add(orders);
				}
			}
			
			Orders_table.setItems(orderAfterDate_dataList);
			
			
			String SQL;
		

			try {
				connectDB();
				System.out.println("Connection established");

				SQL = "SELECT  Sum(O.totalPrice) AS TotalIncome, Count(*) AS Counts "
						+ "FROM orders O "
						+ "WHERE O.confirmationDate >= '"+ java.sql.Date.valueOf(datePicker.getValue()) +"';";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);

				if (rs.next()) {
					text2.setText("Total Income after this date = "+ rs.getDouble(1));
					text3.setText("No. Of Orders after this date = "+ rs.getInt(2));
				}
				
				SQL = "SELECT   Sum(O2.quantity), Count(*) "
						+ "FROM orders O, orderedItem O2 "
						+ "WHERE O.orderID= O2.orderID AND O.confirmationDate >= '"+java.sql.Date.valueOf(datePicker.getValue())+"';";
				rs = stmt.executeQuery(SQL);
				
				if (rs.next()) {
					text4.setText("No. Of Items Sold = "+ rs.getDouble(1));
					text5.setText("different Type of Items Sold = "+ rs.getInt(2));
				}


				rs.close();
				stmt.close();

				con.close();
				System.out.println("Connection Closed");
				
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			
		});
		
		 
		
		//////////////////////////////////////////////////////////////////////////////////

		// delete button
		b10_2.setOnAction(e10_2 -> {
			ObservableList<Orders> selectedRows = Orders_table.getSelectionModel().getSelectedItems();
			ArrayList<Orders> rows = new ArrayList<>(selectedRows);
			if(selectedRows.isEmpty()) {
				er.setText("Error: Please select what do you want to delete");
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			}
			else {
				er.setText("");
				er.setStyle("");
			rows.forEach(row -> {
				ObservableList<OrderedItem> removeList = FXCollections.observableArrayList();
				for (OrderedItem o : orderedItems_dataList) {
					if(o.getOrderID()==row.getOrderID()) {
						removeList.add(o);
					}
				}
				removeList.forEach(r->{
					orderedItems_dataList.remove(r);
				});
				
				Orders_table.getItems().remove(row);
				deleteOrders(row);
				Orders_table.refresh();		
				
			});
			}
		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (Orders row : order_dataList) {
				
				deleteOrders(row);
				Orders_table.refresh();
			}
			Orders_table.getItems().removeAll(order_dataList);
			orderedItems_dataList.clear();
		});
		//////////////////////////////////////////////////////////////////////////////////

		gp.setHgap(2);
		gp.setVgap(20);

		gp2.setHgap(20);
		gp2.setVgap(6);

		gp.add(sp, 1, 3);
		gp.add(h2, 1, 4);
		gp2.add(h11, 1, 5);
		gp2.add(er, 1, 6);
//		gp2.add(er2, 1, 8);

		

		/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		HBox h6 = new HBox();
		
		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.TOP_CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(addToCartPage());
		});
		
		Button btn6 = new Button("Oredered Items", new ImageView("images/ordered.png"));
		btn6.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn6.setStyle("-fx-background-color:transparent");
		btn6.setTextFill(Color.WHITE);
		btn6.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.TOP_CENTER);
		
		btn6.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(orderedItemPage());
		});
		
		Button btn7 = new Button("Delivery", new ImageView("images/delivery.png"));
		btn7.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn7.setStyle("-fx-background-color:transparent");
		btn7.setTextFill(Color.WHITE);
		btn7.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn7, Pos.TOP_CENTER);

		btn7.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(deliveryCompanyPage());
		});
		
		h6.getChildren().addAll(btn6,btn7,btn5);
		h6.setAlignment(Pos.CENTER);
		h6.setSpacing(20);

		er2.setPrefSize(20, 20);
		//h6.setPadding(new Insets(80, 0, 0, 10));
		gp2.setPadding(new Insets(0, 0, 0, 350));
//		gp.setPadding(new Insets(0, 0, 0, 60));
		gp.setAlignment(Pos.TOP_CENTER);
		gp2.setAlignment(Pos.TOP_LEFT);
		root.setPadding(new Insets(0, 0, 50, 0));
		root.setTop(gp);
		root.setBottom(h6);
		root.setRight(v12);
		root.setCenter(gp2);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	@SuppressWarnings("unchecked")
	public BorderPane deliveryCompanyPage() {

		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp2.setAlignment(Pos.CENTER_LEFT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Delivery Company Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<DeliveryCompany> delivery_table = new TableView<DeliveryCompany>(); // table

		delivery_table.setEditable(true);
		delivery_table.setPrefWidth(795);
		delivery_table.setPrefHeight(400);
		delivery_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(delivery_table);

		TableColumn<DeliveryCompany, Integer> deliveryIDCol = new TableColumn<DeliveryCompany, Integer>("deliveryID");
		deliveryIDCol.setMinWidth(200);
		deliveryIDCol.setCellValueFactory(new PropertyValueFactory<DeliveryCompany, Integer>("deliveryID"));
		deliveryIDCol.setStyle("-fx-alignment: CENTER;");

		HBox h3 = new HBox();
		
		Label l3 = new Label("Enter deliveryName: ");
		l3.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l3.setTextFill(Color.ORANGERED);
		
		TextField tf3 = new TextField();
		
		
		h3.getChildren().addAll(l3, tf3);

		TableColumn<DeliveryCompany, String> deliveryNameCol = new TableColumn<DeliveryCompany, String>("deliveryName");
		deliveryNameCol.setMinWidth(200);
		deliveryNameCol.setCellValueFactory(new PropertyValueFactory<DeliveryCompany, String>("deliveryName"));
		deliveryNameCol.setStyle("-fx-alignment: CENTER;");

		deliveryNameCol.setCellFactory(TextFieldTableCell.<DeliveryCompany>forTableColumn()); // turn cell to
		// textfield

		deliveryNameCol.setOnEditCommit((CellEditEvent<DeliveryCompany, String> t) -> {
			((DeliveryCompany) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setDeliveryName(t.getNewValue()); // display
			updateDeliveryCompany(t.getRowValue().getDeliveryID(), t.getNewValue(), "deliveryName");

		});

		HBox h4 = new HBox();
		Label l4 = new Label("Enter Delivery Company Location:");
		l4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l4.setTextFill(Color.ORANGERED);
		ComboBox<String> cb4 = new ComboBox<>();
		cb4.getItems().addAll("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron", "Bethlehem", "Tubas", "Tulkarm",
				"Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem");
		h4.getChildren().addAll(l4, cb4);

		TableColumn<DeliveryCompany, String> locationCol = new TableColumn<DeliveryCompany, String>("location");
		locationCol.setMinWidth(200);
		locationCol.setCellValueFactory(new PropertyValueFactory<DeliveryCompany, String>("location"));
		locationCol.setStyle("-fx-alignment: CENTER;");

		locationCol.setCellFactory(
				ComboBoxTableCell.<DeliveryCompany, String>forTableColumn("Ramallah & Al-Bireh", "Jenin", "Nablus", "Hebron",
						"Bethlehem", "Tubas", "Tulkarm", "Qalqiliya", "Jericho & Al Aghwar", "Salfit", "Jerusalem"));
		// textfield

		locationCol.setOnEditCommit((CellEditEvent<DeliveryCompany, String> t) -> {
			((DeliveryCompany) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setLocation(t.getNewValue()); // display

			updateDeliveryCompany(t.getRowValue().getDeliveryID(), t.getNewValue(), "location");

		});
		
		
		HBox h5 = new HBox();
		Label l5 = new Label("Enter noOfVehicles:         ");
		l5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		l5.setTextFill(Color.ORANGERED);
		TextField tf5 = new TextField();
		
		h5.getChildren().addAll(l5, tf5);

		TableColumn<DeliveryCompany, String> noOfVehiclesCol = new TableColumn<DeliveryCompany, String>("noOfVehicles");
		noOfVehiclesCol.setMinWidth(178);
		noOfVehiclesCol.setCellValueFactory(new PropertyValueFactory<DeliveryCompany, String>("noOfVehicles"));
		noOfVehiclesCol.setStyle("-fx-alignment: CENTER;");

		// convert cell to ComboBox
		noOfVehiclesCol.setCellFactory(TextFieldTableCell.<DeliveryCompany>forTableColumn());

		// edit permission
		noOfVehiclesCol.setOnEditCommit((CellEditEvent<DeliveryCompany, String> t) -> {
			((DeliveryCompany) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setNoOfVehicles(Integer.parseInt(t.getNewValue())); // display
			// only
			updateDeliveryCompany(t.getRowValue().getDeliveryID(), t.getNewValue(), "noOfVehicle");

		});

		delivery_table.setItems(delivery_dataList);

		delivery_table.getColumns().addAll(deliveryIDCol, deliveryNameCol, locationCol, noOfVehiclesCol);

		HBox h10 = new HBox();
		Button b10_1 = new Button("Insert");
		Button b10_2 = new Button("Delete");
		Button b10_3 = new Button("Delete All");
		h10.getChildren().addAll(b10_1, b10_2, b10_3);
		h10.setSpacing(30);

		Label er = new Label("");
		er.setPrefSize(310, 200);
		er.setAlignment(Pos.CENTER);
		gp2.add(er, 1, 9);

		// Insert Button
		b10_1.setOnAction(e10_1 -> {
			if (tf3.getText().equals("") || cb4.getSelectionModel().isEmpty() || tf5.getText().equals("")) {
				er.setText("    Error: Please Fill All Fields !");

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");

			} else {
				er.setText("");
				er.setStyle("");
				int deliveryID;
				if (delivery_dataList.isEmpty())
					deliveryID = 1;
				else
					deliveryID = delivery_dataList.get(delivery_dataList.size() - 1).getDeliveryID() + 1;

				DeliveryCompany u = new DeliveryCompany(deliveryID, tf3.getText(), cb4.getSelectionModel().getSelectedItem(),
						Integer.parseInt(tf5.getText()));
				delivery_dataList.add(u);
				insertDeliveryCompanyData(u);

				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:MEDIUMSPRINGGREEN");

				er.setText("the delivery: " + tf3.getText() + ", with ID: " + deliveryID + " was added successfully");

				tf3.setText("");
				cb4.getSelectionModel().clearSelection();
				tf5.setText("");
			}

		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete button
		b10_2.setOnAction(e10_2 -> {
			ObservableList<DeliveryCompany> selectedRows = delivery_table.getSelectionModel().getSelectedItems();
			ArrayList<DeliveryCompany> rows = new ArrayList<>(selectedRows);
			if(rows.isEmpty()) {
				er.setText("Error: Please select what do you want to delete");
				er.setStyle(
						"-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
								+ "-fx-text-fill:#ff0000");
			}
			rows.forEach(row -> {
				delivery_table.getItems().remove(row);
				deleteDeliveryCompany(row);
				delivery_table.refresh();
			});
		});
		//////////////////////////////////////////////////////////////////////////////////

		// delete all button
		b10_3.setOnAction(e10_3 -> {
			for (DeliveryCompany row : delivery_dataList) {
				deleteDeliveryCompany(row);
				delivery_table.refresh();
			}
			delivery_table.getItems().removeAll(delivery_dataList);
		});
		//////////////////////////////////////////////////////////////////////////////////

		gp.setHgap(2);
		gp.setVgap(10);

		gp2.setHgap(20);
		gp2.setVgap(30);

		gp.add(sp, 1, 1);
		gp.add(h2, 1, 2);
		
		h3.setSpacing(100);
		h4.setSpacing(10);
		h5.setSpacing(50);
		
		gp2.add(h3, 1, 4);
		gp2.add(h4, 1, 5);
		gp2.add(h5, 1, 6);
		gp2.add(h10, 1, 7);

/////////////////////////////////////////////		

		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(orderPage());
		});

		gp2.setPadding(new Insets(100, 0, 0, 0));
		gp.setPadding(new Insets(0, 100, 0, 0));
		gp.setAlignment(Pos.CENTER_RIGHT);
		root.setPadding(new Insets(90, 100, 60, 0));
		root.setCenter(gp);
		root.setBottom(btn5);
		root.setRight(gp2);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}

	public BorderPane orderedItemPage() {

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "Ordered Items Table");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<OrderedItem> order_table = new TableView<OrderedItem>(); // Item table

		order_table.setEditable(true);
		order_table.setPrefWidth(913);
		order_table.setPrefHeight(750);
		order_table.setStyle("-fx-border-color:white; -fx-border-width:1;");
		h2.getChildren().add(order_table);

		TableColumn<OrderedItem, Integer> orderIDCol = new TableColumn<OrderedItem, Integer>("orderID");
		orderIDCol.setMinWidth(100);
		orderIDCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Integer>("orderID"));
		orderIDCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Integer> itemIDCol = new TableColumn<OrderedItem, Integer>("itemID");
		itemIDCol.setMinWidth(200);
		itemIDCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Integer>("itemID"));
		itemIDCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Integer> quantityCol = new TableColumn<OrderedItem, Integer>("quantity");
		quantityCol.setMinWidth(200);
		quantityCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Integer>("quantity"));
		quantityCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Double> totalPriceCol = new TableColumn<OrderedItem, Double>("itemPrice");
		totalPriceCol.setMinWidth(200);
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Double>("itemPrice"));
		totalPriceCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Double> finalPriceCol = new TableColumn<OrderedItem, Double>("finalPrice");
		finalPriceCol.setMinWidth(200);
		finalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Double>("finalPrice"));
		finalPriceCol.setStyle("-fx-alignment: CENTER;");

		order_table.setItems(orderedItems_dataList);

		// System.out.println(addToCart_dataList);
		order_table.getColumns().addAll(orderIDCol, itemIDCol, quantityCol, totalPriceCol, finalPriceCol);
		
		
//		//////////////////////////////////////////////////////////////////////////////////


		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(orderPage());
		});


		gp.setVgap(10);
		gp.add(sp, 1, 1);
		gp.add(h2, 1, 3);

		gp.setPadding(new Insets(0, 0, 0, 0));
		gp.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100, 80, 20, 0));
		root.setCenter(gp);
		root.setBottom(btn5);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}
	
	////////////////////////////////////////////////////
	// for Customer
	public BorderPane customerPage() {
		BorderPane root = new BorderPane();
		fade(root, 1100, 0, 1);
		// top
		HBox h = new HBox();
		HBox h1 = new HBox();
		h1.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");
		h1.setPrefSize(100, 90);

		Text t1 = new Text("Online Sport Shop");
		t1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35));
		t1.setFill(Color.WHITE);
		t1.setStroke(Color.BLACK);
		t1.setStrokeWidth(1.8);
		//t1.setFont(Font.font("arial", FontWeight.BOLD, 30));

		h1.getChildren().add(t1);

		h1.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		Button bt = new Button("Log Out");
		bt.setFont(Font.font("arial", FontWeight.BOLD, 20));
		bt.setOnAction(l -> {
			stage.getScene().setRoot(loginPage());
		});

		h2.getChildren().add(bt);
		h2.setAlignment(Pos.CENTER_RIGHT);
		h2.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		h.getChildren().addAll(h1, h2);
		h.setSpacing(500);
		h.setAlignment(Pos.CENTER);
		h.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");
		// h.setStyle("-fx-background-color: ROYALBLUE;-fx-Border-color: Black;
		// -fx-Border-width:4;");
		// h.setStyle("-fx-border-style:solid; -fx-border-width:2");
		h.setPadding(new Insets(0, 0, 0, 500));
		root.setTop(h);

		// left
		VBox vl = new VBox();
		vl.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		VBox vl1 = new VBox();
		//vl1.setStyle("-fx-background-color: ROYALBLUE");
		vl1.setPrefSize(220, 90);

		Text tl2 = new Text("Category");
		// t2.setStyle("-fx-border-style:solid; -fx-border-width:2");
		tl2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35));
		tl2.setFill(Color.WHITE);
		tl2.setStroke(Color.BLACK);
		tl2.setStrokeWidth(1.8);
		//tl2.setFont(Font.font("arial", FontWeight.BOLD, 30));
		vl1.setPadding(new Insets(20, 0, 0, 10));
		vl1.setAlignment(Pos.CENTER);
		vl1.getChildren().add(tl2);

		//////////
		VBox vl2 = new VBox();
		//vl2.setStyle("-fx-background-color: ROYALBLUE");
		vl2.setPrefSize(220, 800);
		vl2.setPadding(new Insets(70, 0, 0, 0));

		ListView<Item> lv = new ListView<>();
		ObservableList<Item> catItem = FXCollections.observableArrayList();

		Button b_boot = new Button("Boots");
		b_boot.setPrefSize(230, 50);
		b_boot.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_boot.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_boot.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				if (a.getCategory().equals("Boot")) {
					catItem.add(a);
				}
			}

			lv.setItems(catItem);
			lv.refresh();
		});

		Button b_short = new Button("Shorts");
		b_short.setPrefSize(230, 60);
		b_short.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_short.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_short.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				if (a.getCategory().equals("Short")) {
					catItem.add(a);
				}
			}
			lv.setItems(catItem);
			lv.refresh();
		});

		Button b_Tshirt = new Button("T-Shirts");
		b_Tshirt.setPrefSize(220, 60);
		b_Tshirt.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_Tshirt.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_Tshirt.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				if (a.getCategory().equals("T-Shirt")) {
					catItem.add(a);
				}
			}
			lv.setItems(catItem);
			lv.refresh();
		});

		Button b_cap = new Button("Caps");
		b_cap.setPrefSize(220, 60);
		b_cap.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_cap.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_cap.setOnAction(e -> {
			catItem.clear();
			for (Item a : item_dataList) {
				if (a.getCategory().equals("Cap")) {
					catItem.add(a);
				}
			}
			lv.setItems(catItem);
			lv.refresh();
		});

		Button b_pant = new Button("Pants");
		b_pant.setPrefSize(220, 60);
		b_pant.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_pant.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_pant.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				if (a.getCategory().equals("Pant")) {
					catItem.add(a);
				}
			}

			lv.setItems(catItem);
			lv.refresh();
		});

		Button b_bag = new Button("Bags");
		b_bag.setPrefSize(220, 60);
		b_bag.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_bag.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_bag.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				if (a.getCategory().equals("Bag")) {
					catItem.add(a);
				}
			}
			lv.setItems(catItem);
			lv.refresh();
		});

		Button b_gloves = new Button("Gloves");
		b_gloves.setPrefSize(220, 60);
		b_gloves.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_gloves.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_gloves.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				if (a.getCategory().equals("Gloves")) {
					catItem.add(a);
				}
			}
			lv.setItems(catItem);
			lv.refresh();
		});
		
		Button b_all = new Button("All");
		b_all.setPrefSize(220, 60);
		b_all.setFont(Font.font("arial", FontWeight.BOLD, 20));
		b_all.setStyle("-fx-border-style:solid; -fx-border-width:2");

		b_all.setOnAction(e -> {
			catItem.clear();

			for (Item a : item_dataList) {
				catItem.add(a);
			}
			lv.setItems(catItem);
			lv.refresh();
			 
		});


		vl2.getChildren().addAll(b_boot, b_short, b_Tshirt, b_cap, b_pant, b_bag, b_gloves, b_all);

		vl.getChildren().addAll(vl1, vl2);
		root.setLeft(vl);

		// to show the current user cart only
		ObservableList<AddToCart> customeraddToCart = FXCollections.observableArrayList();

		for (AddToCart addToCart : addToCart_dataList) {
			if (addToCart.getUserNo() == currentUser.getUserNo()) {
				customeraddToCart.add(addToCart);
			}
		}

		
		VBox vr4 = new VBox();
		vr4.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");
//		vr2.setPrefSize(220, 800);
		vr4.setPadding(new Insets(0, 0, 30, 0));
		Text tx4= new Text("Total Price= 0");
		tx4.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
		tx4.setFill(Color.WHITE);
		tx4.setStroke(Color.BLACK);
		tx4.setStrokeWidth(0.1);
		vr4.getChildren().add(tx4);
		vr4.setAlignment(Pos.CENTER);
		tx4.setTextAlignment(TextAlignment.CENTER);
		
		for (Cart c : cart_dataList) {
			if (c.getUserNo() == currentUser.getUserNo()) {
				tx4.setText("Total Price = " + c.getTotalPrice());
			}
		}
		
		// center
		VBox p = new VBox();

		Text tp = new Text();
		tp.setStyle("-fx-border-color:FORESTGREEN;-fx-font-size:12;-fx-font-weight:bold;-fx-background-color: White;"
				+ "-fx-text-fill:#ff0000");

		lv.setItems(item_dataList);
		lv.setPrefSize(1000, 600); // 547
		
		lv.setStyle("-fx-border-style:solid; -fx-border-width:2");
		p.getChildren().addAll(lv, tp);
		p.setPadding(new Insets(0,0,0,0));

		ListView<AddToCart> lvr = new ListView<>(); // for right
		lv.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {
					// Use ListView's getSelected Item
					Item currentItemSelected = lv.getSelectionModel().getSelectedItem();
					if (Integer.parseInt(currentItemSelected.getQuantity()) == 0) {
						tp.setText(" Error: you can't add this item (this item out of stock) ");
					} else {
						int newQ = Integer.parseInt(currentItemSelected.getQuantity()) - 1;
						String s = String.valueOf(newQ);
						currentItemSelected.setQuantity(Integer.parseInt(currentItemSelected.getQuantity()) - 1);
						updateItemInfo(currentItemSelected.getItemID(), s, "quantity");

						
						boolean [] exist = {false};
						addToCart_dataList.forEach(row->{
							// if this item is already in the cart, increment the quantity
							if (row.getItemID() == currentItemSelected.getItemID()
									&& row.getUserNo() == currentUser.getUserNo()) {

								updateAddToCart_quantity(row, row.getQuantity() + 1);
								row.setQuantity(row.getQuantity() + 1);
								row.setFinalPrice(row.getQuantity() * row.getItemPrice());
								exist[0] = true;
							}
						});
						


						
						customeraddToCart.forEach(row->{
							// if this item is already in the cart, increment the quantity
							if (row.getItemID() == currentItemSelected.getItemID()
									&& row.getUserNo() == currentUser.getUserNo()) {
								row.setQuantity(row.getQuantity());
								row.setFinalPrice(row.getQuantity() * row.getItemPrice());
							}
						});
						
						if (!exist[0]) {
							// if this item is not in the cart add it to the cart
							AddToCart a = new AddToCart(currentUser.getUserNo(), currentItemSelected.getItemID(), 1,
									Double.parseDouble(currentItemSelected.getPrice()),
									Double.parseDouble(currentItemSelected.getPrice()));

							// add to all user cart
							addToCart_dataList.add(a);
							insertAddToCartData(a);
							// add to current user cart
							customeraddToCart.add(a);
						}

						for (Cart c : cart_dataList) {
							if (c.getUserNo() == currentUser.getUserNo()) {
								c.setNoOfItems(c.getNoOfItems() + 1);
								c.setTotalPrice(
										c.getTotalPrice() + Double.parseDouble(currentItemSelected.getPrice()));

								updateCartInfo(currentUser.getUserNo(), String.valueOf(c.getNoOfItems()),
										"noOfItems");
								updateCartInfo(currentUser.getUserNo(), String.valueOf(c.getTotalPrice()),
										"totalPrice");

								tx4.setText("Total Price = " + c.getTotalPrice());
							}
						}
						

						lvr.refresh();
						lv.refresh();
					}


				}
			}
		});

		root.setCenter(p);

		// right
		VBox vr = new VBox();
		vr.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		VBox vr1 = new VBox();
		//vr1.setStyle("-fx-background-color: ROYALBLUE");
		vr1.setPrefSize(308, 90);

		Text tr1 = new Text("My Cart");
		tr1.setStyle("-fx-border-style:solid; -fx-border-width:2");
		tr1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35));
		tr1.setFill(Color.WHITE);
		tr1.setStroke(Color.BLACK);
		tr1.setStrokeWidth(1.8);
		//tr1.setFont(Font.font("arial", FontWeight.BOLD, 30));
		vr1.setPadding(new Insets(20, 10, 0, 0));
		vr1.setAlignment(Pos.CENTER);
		vr1.getChildren().add(tr1);

		//////////
		VBox vr2 = new VBox();
		//vr2.setStyle("-fx-background-color: ROYALBLUE");
		vr2.setPrefSize(220, 800);
		vr2.setPadding(new Insets(70, 0, 0, 0));

		lvr.setItems(customeraddToCart);
		lvr.setPrefSize(1000, 400); // 547
		lvr.setStyle("-fx-border-style:solid; -fx-border-width:2");

		vr2.getChildren().addAll(lvr);
//		
		VBox vr3 = new VBox();
		Button b_rdel = new Button("Delete from cart");
		
		b_rdel.setOnAction(k -> {
			AddToCart a = lvr.getSelectionModel().getSelectedItem();
			
			item_dataList.forEach(row->{
				if (row.getItemID() == a.getItemID()) {
					int newq = Integer.parseInt(row.getQuantity()) + a.getQuantity();
					row.setQuantity(newq);
					updateItemInfo(a.getItemID(), String.valueOf(newq), "quantity");
				}
			});
						
			deleteAddToCart(a);
			addToCart_dataList.remove(a);
			customeraddToCart.remove(a);
			

			cart_dataList.forEach(row->{
				if (row.getUserNo() == currentUser.getUserNo()) {
					row.setNoOfItems(row.getNoOfItems() - a.getQuantity());
					row.setTotalPrice(row.getTotalPrice() - a.getFinalPrice());

					updateCartInfo(currentUser.getUserNo(), String.valueOf(row.getNoOfItems()), "noOfItems");
					updateCartInfo(currentUser.getUserNo(), String.valueOf(row.getTotalPrice()), "totalPrice");
					tx4.setText("Total Price = "+row.getTotalPrice());
				}		
				
			});
			


			lv.refresh();
			lvr.refresh();
		});

		Button b_rbuy = new Button("Buy");
		
		b_rbuy.setOnAction(b_buy->{
			
			if (!customeraddToCart.isEmpty()) {
				java.util.Date date = new Date();

				double[] totalPrice = { 0 };

				cart_dataList.forEach(row -> {
					if (currentUser.getUserNo() == row.getUserNo()) {
						System.out.println(row.getTotalPrice());
						totalPrice[0] = row.getTotalPrice();
						row.setNoOfItems(0);
						row.setTotalPrice(0);
					}
				});

				int orderID;
				if (order_dataList.isEmpty())
					orderID = 1;
				else
					orderID = order_dataList.get(order_dataList.size() - 1).getOrderID() + 1;

				order_dataList.add(new Orders(orderID, date, totalPrice[0], currentUser.getUserNo(), 1));
				insertOrdersData(new Orders(orderID, date, totalPrice[0], currentUser.getUserNo(), 1));

				System.out.println("order: " + order_dataList);

				ObservableList<AddToCart> removeList = FXCollections.observableArrayList();

				customeraddToCart.forEach(row -> {
					orderedItems_dataList.add(new OrderedItem(orderID, row.getItemID(), row.getQuantity(),
							row.getItemPrice(), row.getFinalPrice()));
					insertOrderedItemData(new OrderedItem(orderID, row.getItemID(), row.getQuantity(),
							row.getItemPrice(), row.getFinalPrice()));
					deleteAddToCart(row);
					removeList.add(row);
				});

				removeList.forEach(row -> {
					addToCart_dataList.remove(row);
				});
				removeList.clear();



				tx4.setText("Total Price = 0");
				customeraddToCart.clear();
				updateCartInfo(currentUser.getUserNo(), String.valueOf(0), "noOfItems");
				updateCartInfo(currentUser.getUserNo(), String.valueOf(0), "totalPrice");
			}

			
		});



		Button b_orderitempage=new Button("My Order Item");
		b_orderitempage.setOnAction(ff->{
			stage.getScene().setRoot(customerOrderedItemPage());
		});
		vr3.setAlignment(Pos.CENTER);
		vr3.setSpacing(5);
		vr3.setPadding(new Insets(10,0,10,0));
		b_rbuy.setPrefSize(100,70);
		b_rdel.setPrefHeight(70);
		
		vr3.getChildren().addAll(b_rdel, b_rbuy,b_orderitempage);

		vr.setPadding(new Insets(0,0,0,0));
		
		vr.getChildren().addAll(vr1, vr2, vr4, vr3);
		root.setRight(vr);

		return root;
	}

	@SuppressWarnings("unchecked")
	public BorderPane customerOrderedItemPage() {

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER_RIGHT);

		StackPane sp = new StackPane();
		Text text = new Text(500, 100, "My Ordered Items");
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		sp.getChildren().add(text);
		sp.setAlignment(Pos.CENTER);

		HBox h2 = new HBox();
		TableView<OrderedItem> order_table = new TableView<OrderedItem>(); // Item table

		order_table.setEditable(true);
		order_table.setPrefWidth(750);
		order_table.setPrefHeight(750);

		h2.getChildren().add(order_table);

		TableColumn<OrderedItem, Integer> orderIDCol = new TableColumn<OrderedItem, Integer>("orderID");
		orderIDCol.setMinWidth(120);
		orderIDCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Integer>("orderID"));
		orderIDCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Integer> itemIDCol = new TableColumn<OrderedItem, Integer>("itemID");
		itemIDCol.setMinWidth(120);
		itemIDCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Integer>("itemID"));
		itemIDCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Integer> quantityCol = new TableColumn<OrderedItem, Integer>("quantity");
		quantityCol.setMinWidth(120);
		quantityCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Integer>("quantity"));
		quantityCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Double> totalPriceCol = new TableColumn<OrderedItem, Double>("itemPrice");
		totalPriceCol.setMinWidth(200);
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Double>("itemPrice"));
		totalPriceCol.setStyle("-fx-alignment: CENTER;");

		TableColumn<OrderedItem, Double> finalPriceCol = new TableColumn<OrderedItem, Double>("finalPrice");
		finalPriceCol.setMinWidth(210);
		finalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderedItem, Double>("finalPrice"));
		finalPriceCol.setStyle("-fx-alignment: CENTER;");

		

		customerOrderedItems_dataList.clear();
		getCurrentUserOrderedItem();
		
		
		
		order_table.setItems(customerOrderedItems_dataList);

		// System.out.println(addToCart_dataList);
		order_table.getColumns().addAll(orderIDCol, itemIDCol, quantityCol, totalPriceCol, finalPriceCol);
		
		
//		//////////////////////////////////////////////////////////////////////////////////


		BorderPane root = new BorderPane();
		fade(root, 750, 0, 1);

		Button btn5 = new Button("Back", new ImageView("images/rback.png"));
		btn5.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
		btn5.setStyle("-fx-background-color:transparent");
		btn5.setTextFill(Color.WHITE);
		btn5.setContentDisplay(ContentDisplay.RIGHT);
		BorderPane.setAlignment(btn5, Pos.CENTER);

		btn5.setOnAction(e -> { // return to main page button
			stage.getScene().setRoot(customerPage());
		});


		gp.setVgap(10);
		gp.add(sp, 1, 1);
		gp.add(h2, 1, 3);

		gp.setPadding(new Insets(0, 0, 0, 0));
		gp.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100, 80, 20, 0));
		root.setCenter(gp);
		root.setBottom(btn5);
		root.setStyle("-fx-background-image:url('/images/k3.jpg'); -fx-background-size:cover,auto");

		// fade effect fade(root, 1100, 0, 1);

		return root;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////

	// User

	private void insertUserData(User u) {

		try {

			System.out.println(u.getPermission() + ":  " + u.getName() + " was inserted successfuly.");

			connectDB();
			ExecuteStatement("Insert into user (userNo,userName,userEmail,userPassword,userAddress,"
					+ "userPermission) values(" + u.getUserNo() + ",'" + u.getName() + "','" + u.getEmail() + "','"
					+ u.getPassword() + "','" + u.getAddress() + "','" + u.getPermission() + "');");

			ExecuteStatement(
					"Insert into phone (userNo, userPhone) values(" + u.getUserNo() + ",'" + u.getPhone() + "');");

			ExecuteStatement("Insert into cart (userNo) values(" + u.getUserNo() + ");");

			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getUserData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT *, (SELECT userPhone FROM phone WHERE userNo = u.userNo LIMIT 1)"
				+ " FROM user u;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			/////////
			user_data.add(new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(7), rs.getString(6)));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void deleteUser(User row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from user where userNo=" + row.getUserNo() + ";");
			connectDB();
			ExecuteStatement("delete from  user where userNo=" + row.getUserNo() + ";");

			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateUserInfo(int userNo, String name, String attribute) {

		try {
			String statement = ("update  user set " + attribute + "= '" + name + "' where userNo = " + userNo);
			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////

	// Phone
	private void getPhoneData() throws SQLException, ClassNotFoundException {
		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM phone p;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			phone_dataList.add(new Phone(rs.getString(1), Integer.parseInt(rs.getString(2))));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void insertPhoneData(Phone p) {

		try {
			connectDB();
			System.out.println("Connection Established");

			ExecuteStatement("Insert into Phone values('" + p.getUserPhone() + "'," + p.getUserNo() + ");");
			System.out.println(
					"Phone Number: " + p.getUserPhone() + " was inserted successfuly to userNo= " + p.getUserNo());

			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updatePhoneInfo(int userNo, String oldPhone, String newPhone) {

		try {

			String statement = ("update  phone set  userPhone= '" + newPhone + "'  where userNo = " + userNo
					+ " AND userPhone = '" + oldPhone + "'");

			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void deletePhone(Phone row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from phone where userNo=" + row.getUserNo() + " " + "AND userPhone ='"
					+ row.getUserPhone() + "';");
			connectDB();
			ExecuteStatement("delete from phone where userNo=" + row.getUserNo() + " " + "AND userPhone ='"
					+ row.getUserPhone() + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////

	// Item

	private void insertItemData(Item i) {

		try {

			System.out.println(i.getCategory() + ":  " + i.getItemName() + " was inserted successfuly.");

			connectDB();
			ExecuteStatement("Insert into item values(" + i.getItemID() + ",'" + i.getItemName() + "','" + i.getBrand()
					+ "','" + i.getColor() + "','" + i.getCategory() + "'," + i.getSize() + "," + i.getPrice() + ","
					+ i.getQuantity() + "," + i.getWarehouseID() + ");");

			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getItemData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM Item I;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			/////////
			item_dataList
					.add(new Item(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), Double.parseDouble(rs.getString(6)), Double.parseDouble(rs.getString(7)),
							Integer.parseInt(rs.getString(8)), Integer.parseInt(rs.getString(9))));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void deleteItem(Item row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from item where itemID=" + row.getItemID() + ";");
			connectDB();
			ExecuteStatement("delete from  item where itemID=" + row.getItemID() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateItemInfo(int itemID, String name, String attribute) {

		try {
			String statement;
			if (attribute.equals("size") || attribute.equals("price")) {
				double s = Double.parseDouble(name);
				statement = ("update  item set " + attribute + "= " + s + " where itemID = " + itemID);

			} else if (attribute.equals("quantity") || attribute.equals("warehouseID")) {
				int s = Integer.parseInt(name);
				statement = ("update  item set " + attribute + "= " + s + " where itemID = " + itemID);

			} else {
				statement = ("update  item set " + attribute + "= '" + name + "' where itemID = " + itemID);
			}
			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////

	// AddToCart

	private void getAddToCartData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM addToCart a;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			/////////
			double finalPrice = Double.parseDouble(rs.getString(4)) * Double.parseDouble(rs.getString(3));

			addToCart_dataList.add(new AddToCart(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
					Integer.parseInt(rs.getString(3)), Double.parseDouble(rs.getString(4)), finalPrice));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void deleteAddToCart(AddToCart row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from addToCart where userNo=" + row.getUserNo() + "" + " And itemID = "
					+ row.getItemID() + ";");
			connectDB();
			ExecuteStatement("delete from addToCart where userNo=" + row.getUserNo() + "" + " And itemID = "
					+ row.getItemID() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void insertAddToCartData(AddToCart i) {

		try {

			System.out.println(i.getItemID() + " was inserted successfuly to userNo: " + i.getUserNo());

			connectDB();
			System.out.println("Insert into addToCart values(" + i.getUserNo() + "," + i.getItemID() + ","
					+ i.getQuantity() + "," + i.getItemPrice() + "," + i.getFinalPrice() + ");");
			ExecuteStatement("Insert into addToCart values(" + i.getUserNo() + "," + i.getItemID() + ","
					+ i.getQuantity() + "," + i.getItemPrice() + "," + i.getFinalPrice() + ");");

			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateAddToCart_quantity(AddToCart a, int newQ) {

		try {
			String statement;

			statement = ("update  AddToCart set  quantity = " + newQ + " where itemID = " + a.getItemID()
					+ " AND userNo= " + a.getUserNo() + ";");

			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////

	// Warehouse

	private void getWarehouseData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM warehouse w;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			/////////
			warehouse_dataList.add(new Warehouse(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3)));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void insertWarehouseData(Warehouse i) {

		try {

			System.out.println(i.getWarehouseName() + " was inserted successfuly");

			connectDB();
			System.out.println("Insert into warehouse values(" + i.getWarehouseID() + ",'" + i.getWarehouseName()
					+ "','" + i.getWarehouseLocation() + "');");
			ExecuteStatement("Insert into warehouse values(" + i.getWarehouseID() + ",'" + i.getWarehouseName() + "','"
					+ i.getWarehouseLocation() + "');");

			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void deleteWarehouse(Warehouse row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from warehouse where warehouseID=" + row.getWarehouseID() + ";");
			connectDB();
			ExecuteStatement("delete from warehouse where warehouseID=" + row.getWarehouseID() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateWarehouseInfo(int warehouseID, String name, String attribute) {

		try {
			String statement = ("update  warehouse set " + attribute + "= '" + name + "' where warehouseID = "
					+ warehouseID);
			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////

	// cart
	
	private void getCartData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM  cart c;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			/////////
			cart_dataList.add(new Cart(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
					Double.parseDouble(rs.getString(3))));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void insertCartData(Cart c) {

		try {

			connectDB();
			ExecuteStatement("Insert into cart values(" + c.getUserNo() + "," + c.getNoOfItems() + ","
					+ c.getTotalPrice() + ");");

			System.out.println("cart was inserted successfuly to userNo: " + c.getUserNo());
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateCartInfo(int userNo, String name, String attribute) {

		try {
			String statement = "";
			if (attribute.equals("totalPrice")) {
				double s = Double.parseDouble(name);
				statement = ("update  cart set " + attribute + "= " + s + " where userNo = " + userNo);

			} else if (attribute.equals("noOfItems")) {
				int s = Integer.parseInt(name);
				statement = ("update  cart set " + attribute + "= " + s + " where userNo = " + userNo);

			}

			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////
	// Delivery Company

	private void getDeliveryCompanyData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM  deliveryCompany d;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {

			delivery_dataList.add(new DeliveryCompany(Integer.parseInt(rs.getString(1)), rs.getString(2),
					rs.getString(3), Integer.parseInt(rs.getString(4))));
		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void insertDeliveryCompanyData(DeliveryCompany c) {

		try {

			connectDB();
			ExecuteStatement("Insert into deliveryCompany values(" + c.getDeliveryID() + ",'" + c.getDeliveryName()
					+ "','" + c.getLocation() + "'," + Integer.parseInt(c.getNoOfVehicles()) + ");");

			System.out.println(c.getDeliveryName() + ", with ID: " + c.getDeliveryID() + " was inserted successfuly");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void deleteDeliveryCompany(DeliveryCompany row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from deliveryCompany where deliveryID=" + row.getDeliveryID() + ";");
			connectDB();
			ExecuteStatement("delete from deliveryCompany where deliveryID=" + row.getDeliveryID() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateDeliveryCompany(int deliveryID, String name, String attribute) {

		try {
			String statement;
			if (attribute.equals("noOfVehicle")) {
				int s = Integer.parseInt(name);
				statement = ("update  deliveryCompany set " + attribute + "= " + s + " where deliveryID = "
						+ deliveryID);

			} else {
				statement = ("update  deliveryCompany set " + attribute + "= '" + name + "' where deliveryID = "
						+ deliveryID);
			}
			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	/////////////////////////////////////////////////////////////////////////
	// Orders
	private void getOrdersData() throws SQLException, ClassNotFoundException, ParseException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM  orders d;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = rs.getString(2);
			Date date = dateFormat.parse(dateString);
			
			order_dataList.add(new Orders(Integer.parseInt(rs.getString(1)), date,
					Double.parseDouble(rs.getString(3)), Integer.parseInt(rs.getString(4)),
					Integer.parseInt(rs.getString(5))));

		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	
	private void insertOrdersData(Orders o) {

		try {

			connectDB();
			java.util.Date utilDate = o.getConfirmationDate();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
			ExecuteStatement("Insert into orders values(" + o.getOrderID() + ",'" + sqlDate + "',"
					+Double.parseDouble(o.getTotalPrice())+","+o.getUserNo()+","+o.getDeliveryID() + ");");

			System.out.println("Insert into orders values(" + o.getOrderID() + ",'" + sqlDate + "',"
					+Double.parseDouble(o.getTotalPrice())+","+o.getUserNo()+","+o.getDeliveryID() + ");");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void deleteOrders(Orders row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from orders where orderID=" + row.getOrderID() + ";");
			connectDB();
			ExecuteStatement("delete from orders where orderID=" + row.getOrderID() + ";");
			con.close();
			System.out.println("Connection closed");

			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateOrder(int orderID, String name, String attribute) {

		try {
			String statement;
			
			statement = ("update  orders set " + attribute + "= " + Integer.parseInt(name) + " where orderID = "
					+ orderID);
			
			
			connectDB();
			System.out.println("Connection established");
			System.out.println(statement);
			ExecuteStatement(statement);
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	
	////////////////////////////////////////////////////////////////////////
	//Ordered Items (with same struc
	
	private void getOrderedItemData() throws SQLException, ClassNotFoundException, ParseException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT * FROM  orderedItem o;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {
			
			orderedItems_dataList.add(new OrderedItem(Integer.parseInt(rs.getString(1)), 
					Integer.parseInt(rs.getString(2)),Integer.parseInt(rs.getString(3)),
					Double.parseDouble(rs.getString(4)),Double.parseDouble(rs.getString(5))));

		}

		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed");

	}

	private void insertOrderedItemData(OrderedItem o) {

		try {

			connectDB();
			
			
			ExecuteStatement("Insert into orderedItem values(" + o.getOrderID() + "," + o.getItemID()
							+ ","+o.getQuantity()+","+o.getItemPrice()+","+o.getFinalPrice()+ ");");

			System.out.println("Insert into orderedItem values(" + o.getOrderID() + "," + o.getItemID()
								+ ","+o.getQuantity()+","+o.getItemPrice()+","+o.getFinalPrice()+ ");");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getCurrentUserOrderedItem() {
		try {
			connectDB();

			String SQL = "SELECT o1.orderID,o1.itemID,o1.quantity,o1.itemPrice,o1.finalPrice "
					+ "FROM OrderedItem o1, Orders o2, Cart c "
					+ "where o1.orderID = o2.orderID  AND o2.userNo = c.userNo " + "AND c.userNo= "
					+ currentUser.getUserNo() + ";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {

				/////////
				customerOrderedItems_dataList.add(new OrderedItem(Integer.parseInt(rs.getString(1)),
						Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
						Double.parseDouble(rs.getString(4)), Double.parseDouble(rs.getString(5))));
			}

			rs.close();
			stmt.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/////////////////////////////////////////////////////////////////////////
	

	// Additional in java fx
	
	
	private void fade(Node node, long time, int start, int end) {
		FadeTransition ft = new FadeTransition(Duration.millis(time), node);
		ft.setFromValue(Color.ORANGE.getBlue());
		ft.setToValue(end);
		ft.play();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
