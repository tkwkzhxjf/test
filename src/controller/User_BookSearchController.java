package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;

public class User_BookSearchController implements Initializable {
	@FXML
	ImageView imgV11;
	@FXML
	ImageView imgV12;
	@FXML
	ImageView imgV13;
	@FXML
	ImageView imgV21;
	@FXML
	ImageView imgV22;
	@FXML
	ImageView imgV23;
	@FXML
	ImageView imgV31;
	@FXML
	ImageView imgV32;
	@FXML
	ImageView imgV33;
	@FXML
	ListView<String> listV;
	@FXML
	Label lbTitle11;
	@FXML
	Label lbTitle12;
	@FXML
	Label lbTitle13;
	@FXML
	Label lbTitle21;
	@FXML
	Label lbTitle22;
	@FXML
	Label lbTitle23;
	@FXML
	Label lbTitle31;
	@FXML
	Label lbTitle32;
	@FXML
	Label lbTitle33;
	@FXML
	Button btnBack;
	private String listViewSelectItem;
	public Stage stage;
	ArrayList<Book> bookList;
	ArrayList<Label> bookTitleList = new ArrayList<Label>();
	ArrayList<ImageView> bookImageVList = new ArrayList<ImageView>();
	private String selectFileName;
	private String localUrl;
	private int bookCount;
	private int selectBook = -1;
	BookDAO dao = new BookDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		listV.setOnMousePressed(e -> listViewSelectIndexSetMethod());
		listV.setOnMouseClicked(e -> getCartegoryBook());
		listV.setItems(dao.categoryList);
		btnBack.setOnAction(e -> handleBtnBackAction(e));
		getBookSelectMethod();
	}

	private void getBookSelectMethod() {

		imgV11.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(0)));
		imgV12.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(1)));
		imgV13.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(2)));
		imgV21.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(3)));
		imgV22.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(4)));
		imgV23.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(5)));
		imgV31.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(6)));
		imgV32.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(7)));
		imgV33.setOnMouseClicked(e -> getBookInformationPopup(bookList.get(8)));

	}

	// 뒤로가기
	private void handleBtnBackAction(ActionEvent e) {
		Stage adminMain = null;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/user_Main.fxml"));
			Scene scene = new Scene(root);
			adminMain = new Stage();
			adminMain.setTitle("유저 메인");
			adminMain.setScene(scene);
			adminMain.setResizable(true);
			((Stage) btnBack.getScene().getWindow()).close();
			adminMain.show();
		} catch (IOException e1) {
		}
	}

	private void listViewSelectIndexSetMethod() {
		listViewSelectItem = listV.getSelectionModel().getSelectedItem();
	}

	// 장르 선택시 책 진열
	private void getCartegoryBook() {
		BookDAO dao = new BookDAO();
		bookList = dao.searchBook(listViewSelectItem, "category");
		bookCount = bookList.size();
		bookTitleList.addAll(FXCollections.observableArrayList(lbTitle11, lbTitle12, lbTitle13, lbTitle21, lbTitle22,
				lbTitle23, lbTitle31, lbTitle32, lbTitle33));
		bookImageVList.addAll(FXCollections.observableArrayList(imgV11, imgV12, imgV13, imgV21, imgV22, imgV23, imgV31,
				imgV32, imgV33));

		for (int j = 0; j < bookImageVList.size(); j++) {
			bookTitleList.get(j).setText("");
			bookImageVList.get(j).setImage(null);
			bookImageVList.get(j).setDisable(true);
		}

		for (int i = 0; i < bookCount; i++) {

			Book b = bookList.get(i);
			selectFileName = b.getFileimg();
			localUrl = "file:/C:/images/Library_BookData/" + selectFileName;
			bookTitleList.get(i).setText(b.getTitle());
			bookImageVList.get(i).setImage(new Image(localUrl));
			bookImageVList.get(i).setDisable(false);
		}

	}

	private void getBookInformationPopup(Book b) {
		try {
			Parent userModifyView = FXMLLoader.load(getClass().getResource("/view/user_bookInformation.fxml"));
			Scene scene = new Scene(userModifyView);
			Stage userModifyStage = new Stage(StageStyle.UTILITY);
			Label lbTitle = (Label) scene.lookup("#lbTitle");
			Label lbISBN = (Label) scene.lookup("#lbISBN");
			Label lbWriter = (Label) scene.lookup("#lbWriter");
			Label lbCompany = (Label) scene.lookup("#lbCompany");
			Label lbDate = (Label) scene.lookup("#lbDate");
			TextArea txaInformation = (TextArea) scene.lookup("#txaInformation");
			Button btnClose = (Button) scene.lookup("#btnClose");
			Button btnRental = (Button) scene.lookup("#btnRental");
			ImageView imgV = (ImageView) scene.lookup("#imgV");
			lbTitle.setText(b.getTitle());
			lbISBN.setText(b.getIsbn());
			lbWriter.setText(b.getWriter());
			lbCompany.setText(b.getCompany());
			lbDate.setText(b.getDate());
			txaInformation.setText(b.getInformation());
			selectFileName = b.getFileimg();
			localUrl = "file:/C:/images/Library_BookData/" + selectFileName;
			imgV.setImage(new Image(localUrl));
			userModifyStage.initModality(Modality.WINDOW_MODAL);
			userModifyStage.initOwner(this.stage);
			userModifyStage.setScene(scene);
			userModifyStage.setResizable(false);
			userModifyStage.setTitle("책 정보");
			userModifyStage.show();
			btnClose.setOnAction(e -> userModifyStage.close());
			btnRental.setOnAction(e -> {
				MemberDAO dao = new MemberDAO();
				if (!(b.isRental())) {
					if (dao.m.getRentalBook() == null) {
						Connection con1 = null;
						PreparedStatement preparedStatement1 = null;
						PreparedStatement preparedStatement2 = null;
						try {

							con1 = DBUtil.getConnection(); //
							String query1 = "update memberTBL set rentalBook=? where Id=?";
							String query2 = "update BookTBL set rental=? where ISBN=?";
							preparedStatement1 = con1.prepareStatement(query1);
							preparedStatement2 = con1.prepareStatement(query2);

							preparedStatement1.setString(1, b.getTitle());
							preparedStatement1.setString(2, dao.m.getId());
							preparedStatement2.setBoolean(1, true);
							preparedStatement2.setString(2, b.getIsbn());

							if (preparedStatement1.executeUpdate() != 0 && preparedStatement2.executeUpdate() != 0) {
								dao.m.setRentalBook(b.getTitle());
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setHeaderText("대여완료");
								alert.showAndWait();
								userModifyStage.close();
							} else {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setHeaderText("등록 실패");
								alert.showAndWait();
								throw new Exception();
							}

						} catch (Exception e1) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setHeaderText("등록 실패 DB에러");
							alert.showAndWait();
						}

					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("대여중인 책이 있습니다.");
						alert.setContentText("책을 반납하고 도서를 대여하세요.");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("이미 대여된 책입니다.");
					alert.showAndWait();
				}
			});
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("계정관리창 부르기 오류");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

}
