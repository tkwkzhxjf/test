/*
 * package controller;
 * 
 * import java.net.URL; import java.util.ResourceBundle;
 * 
 * import javafx.event.ActionEvent; import javafx.event.EventHandler; import
 * javafx.fxml.FXML; import javafx.fxml.FXMLLoader; import
 * javafx.fxml.Initializable; import javafx.scene.Parent; import
 * javafx.scene.Scene; import javafx.scene.control.Alert; import
 * javafx.scene.control.Button; import javafx.scene.control.Alert.AlertType;
 * import javafx.stage.Stage;
 * 
 * public class User_MainController implements Initializable{
 * 
 * @FXML private Button btnUserModify;
 * 
 * @FXML private Button btnOut;
 * 
 * @FXML private Button btnBookSearch;
 * 
 * @FXML private Button btnBookApply;
 * 
 * @FXML private Button btnBookRental;
 * 
 * @FXML private Button btnNotice;
 * 
 * @FXML private Button btnCalendar; public Stage stage;
 * 
 * @Override public void initialize(URL location, ResourceBundle resources) { //
 * 로그인창으로 돌아감 btnOut.setOnAction(event -> handlerBtnOut(event));
 * 
 * }
 * 
 * 
 * // 로그인창으로 돌아감 private void handlerBtnOut(ActionEvent event) { Parent mainView
 * = null; Stage mainStage = null; try { mainView =
 * FXMLLoader.load(getClass().getResource("/view/login.fxml")); Scene scene =
 * new Scene(mainView); mainStage = new Stage(); mainStage.setTitle("메인");
 * mainStage.setScene(scene); mainStage.setResizable(true); ((Stage)
 * btnOut.getScene().getWindow()).close(); mainStage.show(); }catch(Exception e)
 * { Alert alert = new Alert(AlertType.ERROR); alert.setTitle("에러발생");
 * alert.setHeaderText("메인창 부르기 오류"); alert.showAndWait(); } }
 * 
 * }
 */

package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import model.Member;
import model.Notice;
import model.RequestBook;

public class User_MainController implements Initializable {
	@FXML
	private Button btnUserModify;
	@FXML
	private Button btnOut;
	@FXML
	private Button btnBookSearch;
	@FXML
	private Button btnBookApply;
	@FXML
	private Button btnBookRental;
	@FXML
	private Button btnNotice;
	@FXML
	private Button btnCalendar;
	public Stage stage;
	@FXML
	private ImageView imgV;
	@FXML
	private AnchorPane A;
	MemberDAO memberDao = new MemberDAO();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일");
	Date time = new Date();
	String BookApplytime = format1.format(time);
	ArrayList<Book> bookList;
	private ObservableList<Notice> obsList = FXCollections.observableArrayList();
	private ObservableList<Member> obsList2;
	private int tableViewselectedIndex;

	// String selectFileName;
	public User_MainController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 로그인창으로 돌아감
		btnOut.setOnAction(event -> handlerBtnOut(event));

		// 대여중인 도서 확인(반납)
		btnBookRental.setOnAction(e -> setReturnRentalBook());

		// 회원정보 수정
		btnUserModify.setOnAction(event -> handlerBtnUserModify(event));

		// 자료 검색
		btnBookSearch.setOnAction(event -> handlerBtnBookSearch(event));

		// 일정
		btnCalendar.setOnAction(event -> handlerBtnCalendar(event));

		// 공지사항
		btnNotice.setOnAction(event -> handlerBtnNotice(event));
		
		// 자료신청
		btnBookApply.setOnAction(event -> handlerBtnBookApply(event));

	}

	
	private void setReturnRentalBook() {
		try {
			BookDAO dao = new BookDAO();
			bookList = dao.searchBook(memberDao.m.getRentalBook(), "title");
			getRentalBookInformationPopup(bookList.get(0));

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("대여중인 도서가 없습니다.");
			alert.showAndWait();
		}

	}

	private void setProfilImage() {
		try {
			String selectFileName = memberDao.m.getFileimg();
			String localUrl = "file:/C:/images/Library_MemberData/" + selectFileName;
			imgV.setImage(new Image(localUrl));

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("이미지오류");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

	// 대여중인 도서 정보(반납)
	private void getRentalBookInformationPopup(Book b) {
		try {

			Parent userModifyView = FXMLLoader.load(getClass().getResource("/view/user_bookInformation.fxml"));
			Scene scene = new Scene(userModifyView);
			Stage userModifyStage = new Stage(StageStyle.UTILITY);
			Label lbTitle = (Label) scene.lookup("#lbTitle");
			Label lbISBN = (Label) scene.lookup("#lbISBN");
			Label lbWriter = (Label) scene.lookup("#lbWriter");
			Label lbCompany = (Label) scene.lookup("#lbCompany");
			Label lbDate = (Label) scene.lookup("#lbDate");
			Label lbInformation = (Label) scene.lookup("#lbInformation");
			Button btnClose = (Button) scene.lookup("#btnClose");
			Button btnRental = (Button) scene.lookup("#btnRental");
			ImageView imgV = (ImageView) scene.lookup("#imgV");
			btnRental.setText("반납");
			lbTitle.setText(b.getTitle());
			lbISBN.setText(b.getIsbn());
			lbWriter.setText(b.getWriter());
			lbCompany.setText(b.getCompany());
			lbDate.setText(b.getDate());
			lbInformation.setText(b.getInformation());
			String selectFileName = b.getFileimg();
			String localUrl = "file:/C:/images/Library_BookData/" + selectFileName;
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

				Connection con1 = null;
				PreparedStatement preparedStatement1 = null;
				PreparedStatement preparedStatement2 = null;
				try {

					con1 = DBUtil.getConnection(); //
					String query1 = "update memberTBL set rentalBook=? where Id=?";
					String query2 = "update BookTBL set rental=? where ISBN=?";
					preparedStatement1 = con1.prepareStatement(query1);
					preparedStatement2 = con1.prepareStatement(query2);

					preparedStatement1.setString(1, null);
					preparedStatement1.setString(2, dao.m.getId());
					preparedStatement2.setBoolean(1, false);
					preparedStatement2.setString(2, b.getIsbn());

					if (preparedStatement1.executeUpdate() != 0 && preparedStatement2.executeUpdate() != 0) {
						memberDao.m.setRentalBook(null);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("반납 완료");
						alert.showAndWait();
						userModifyStage.close();
						bookList.clear();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("반납 실패");
						alert.showAndWait();
						throw new Exception();
					}

				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("등록 실패 DB에러");
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

	// 자료검색
	private void handlerBtnBookSearch(ActionEvent event) {

		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_BookSearch.fxml"));
			Scene scene = new Scene(mainView);

			mainStage = new Stage();
			mainStage.setTitle("자료 검색");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			((Stage) btnOut.getScene().getWindow()).close();
			mainStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("자료검색창 부르기 오류");
			alert.showAndWait();
		}

	}

	// 로그인창으로 돌아감
	private void handlerBtnOut(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(mainView);

			mainStage = new Stage();
			mainStage.setTitle("메인");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			((Stage) btnOut.getScene().getWindow()).close();
			mainStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("메인창 부르기 오류");
			alert.showAndWait();
		}
	}

	// 회원정보 수정
	private void handlerBtnUserModify(ActionEvent event) {
		try {

			Parent userModifyView = FXMLLoader.load(getClass().getResource("/view/user_changingInformation.fxml"));
			Scene scene = new Scene(userModifyView);
			Stage userModifyStage = new Stage(StageStyle.UTILITY);
			PasswordField txtPass = (PasswordField) scene.lookup("#txtPass");
			Button btnUserModifyNo = (Button) scene.lookup("#btnNo");
			Button btnUserModifyOk = (Button) scene.lookup("#btnOk");

			btnUserModifyOk.setOnAction(event1 -> {
				if (txtPass.getText().equals(memberDao.m.getPass())) {
					userModifyStage.close();
					try {
						Parent userModifyView2 = FXMLLoader.load(getClass().getResource("/view/user_changingInformation2.fxml"));
						Scene scene1 = new Scene(userModifyView2);
						Stage userModifyStage2 = new Stage(StageStyle.UTILITY);
						
						Label lblId2 = (Label) scene1.lookup("#lblId");
						Label lblBirth2 = (Label) scene1.lookup("#lblBirth");
						Label lbPhone2 = (Label) scene1.lookup("#lbPhone");
						TextField txtPass2 = (TextField) scene1.lookup("#txtPass");
						TextField txtName2 = (TextField) scene1.lookup("#txtName");
						// TextField txtPhoneNumber2=(TextField) scene1.lookup("txtPhoneNumber");
						//TextField txtPhoneNumber2=(TextField) scene1.lookup("txtPhoneNumber");
						ImageView imgView = (ImageView) scene1.lookup("#imgView");
						Button btnModifyNo2 = (Button) scene1.lookup("#btnNo");
						Button btnModifyAdd2 = (Button) scene1.lookup("#btnAdd");
						
						imgView.setImage(new Image("file:/C:/images/Library_MemberData/" + memberDao.m.getFileimg()));
						lblId2.setText(memberDao.m.getId());
						lblBirth2.setText(memberDao.m.getBirth());
						txtPass2.setText(memberDao.m.getPass());
						txtName2.setText(memberDao.m.getName());
						lbPhone2.setText(memberDao.m.getPhoneNumber());
						//txtPhoneNumber2.setText(memberDao.m.getPhoneNumber());
						
						btnModifyAdd2.setOnAction(event2 -> {
							//Member m = new Member(txtName2.getText(), txtPass2.getText());
							Connection con = null;
							PreparedStatement pstmt = null;
							try {
								con = DBUtil.getConnection();
							
								String query = "update memberTBL set name = ? ,pass = ? where id = ?";
								
								pstmt = con.prepareStatement(query);
								
								pstmt.setString(1, txtName2.getText());
								pstmt.setString(2, txtPass2.getText());
								pstmt.setString(3, memberDao.m.getId());
								
								int userModify = pstmt.executeUpdate();
								
								if(userModify != 0) {
									memberDao.m.setName(txtName2.getText());
									memberDao.m.setPass(txtPass2.getText());
								
									Alert alert =new Alert(AlertType.INFORMATION);
									alert.setTitle("계정관리");
									alert.setHeaderText("회원정보 수정이 완료되었습니다");
									alert.showAndWait();
									userModifyStage2.close();
								}else {
									throw new Exception();
								}
							} catch (Exception e) {
								Alert alert =new Alert(AlertType.ERROR);
								alert.setTitle("에러발생");
								alert.setHeaderText("수정창 점검");
								alert.setContentText(e.getMessage());
								alert.showAndWait();
							}
						});
						
						userModifyStage2.initModality(Modality.WINDOW_MODAL);
						userModifyStage2.initOwner(stage);
						userModifyStage2.setScene(scene1);
						userModifyStage2.setResizable(false);
						userModifyStage2.setTitle("계정관리");
						userModifyStage2.show();

						btnModifyNo2.setOnAction(event2 -> userModifyStage2.close());

					} catch (IOException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("에러발생");
						alert.setHeaderText("계정관리창 부르기 오류");
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("회원정보 진입불가");
					alert.setHeaderText("비밀번호를 확인하세요");
					alert.showAndWait();
				}
			});
			userModifyStage.initModality(Modality.WINDOW_MODAL);
			userModifyStage.initOwner(stage);
			userModifyStage.setScene(scene);
			userModifyStage.setResizable(false);
			userModifyStage.setTitle("본인확인");
			userModifyStage.show();
			btnUserModifyNo.setOnAction(event1 -> userModifyStage.close());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("계정관리창 부르기 오류");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	// 일정표
	private void handlerBtnCalendar(ActionEvent event) {
		try {
			Parent userScheduleView = FXMLLoader.load(getClass().getResource("/view/user_schedule.fxml"));
			Scene scene = new Scene(userScheduleView);
			Stage userScheduleStage = new Stage(StageStyle.UTILITY);

			TableView tblUserSchedule = (TableView) scene.lookup("#tblSchedule");
			Button btnUserScheduleNo = (Button) scene.lookup("#btnNo");
			userScheduleStage.initModality(Modality.WINDOW_MODAL);
			userScheduleStage.initOwner(stage);
			userScheduleStage.setScene(scene);
			userScheduleStage.setResizable(false);
			userScheduleStage.setTitle("일정표");
			userScheduleStage.show();
			btnUserScheduleNo.setOnAction(event1 -> userScheduleStage.close());
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("일정표 부르기 오류");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	// 공지사항
	private void handlerBtnNotice(ActionEvent event) {
		try {
			Parent userNoticeView = FXMLLoader.load(getClass().getResource("/view/user_notice.fxml"));
			Scene scene = new Scene(userNoticeView);
			Stage userNoticeStage = new Stage(StageStyle.UTILITY);

			TableView tblUserNotice = (TableView) scene.lookup("#tblNotice");
			Button btnUserNoticeNo = (Button) scene.lookup("#btnNo");
			Button btnUserNoticeAdd = (Button) scene.lookup("#btnAdd");
			Button btnUserNoticeDelete = (Button) scene.lookup("#btnDelete");
			
			TableColumn colNo = new TableColumn("No");
			colNo.setMaxWidth(30);
			colNo.setStyle("-fx-allignment: CENTER");
			colNo.setCellValueFactory(new PropertyValueFactory("no"));

			TableColumn colTitle = new TableColumn("제 목");
			colTitle.setPrefWidth(90);
			colTitle.setStyle("-fx-allignment: CENTER");
			colTitle.setCellValueFactory(new PropertyValueFactory("title"));

			TableColumn colContent = new TableColumn("내 용");
			colContent.setPrefWidth(400);
			colContent.setStyle("-fx-allignment: CENTER");
			colContent.setCellValueFactory(new PropertyValueFactory("content"));

			TableColumn colDate = new TableColumn("작성날짜");
			colDate.setPrefWidth(115);
			colDate.setStyle("-fx-allignment: CENTER");
			colDate.setCellValueFactory(new PropertyValueFactory("date"));

			tblUserNotice.getColumns().addAll(colNo, colTitle, colContent, colDate);
			tblUserNotice.setItems(obsList);
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();

				String query = "select * from noticeTBL";

				pstmt = con.prepareStatement(query);

				rs = pstmt.executeQuery();

				ArrayList<Notice> arrayList = new ArrayList<Notice>();
				while (rs.next()) {
					Notice notice = new Notice(rs.getString("title"),
							rs.getString("content"),
							rs.getString("date"),
							rs.getInt("No"));
					arrayList.add(notice);
				}

				for (int i = 0; i < arrayList.size(); i++) {
					Notice n = arrayList.get(i);
					obsList.add(n);
				}

			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("에러발생");
				alert.setHeaderText("DB 점검하세요");
				alert.setContentText(e1.getMessage());
				alert.showAndWait();
			}
			
			userNoticeStage.initModality(Modality.WINDOW_MODAL);
			userNoticeStage.initOwner(stage);
			userNoticeStage.setScene(scene);
			userNoticeStage.setResizable(false);
			userNoticeStage.setTitle("공지사항");
			userNoticeStage.show();
			btnUserNoticeNo.setOnAction(event1 -> userNoticeStage.close());
			
			btnUserNoticeAdd.setVisible(false);
			btnUserNoticeAdd.setDisable(true);
			btnUserNoticeDelete.setVisible(false);
			btnUserNoticeDelete.setDisable(true);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("공지사항 부르기 오류");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	// 자료신청
	private void handlerBtnBookApply(ActionEvent event) {
		try {
			Parent userRequestPopUpView = FXMLLoader.load(getClass().getResource("/view/requestPopUp.fxml"));
			Scene scene = new Scene(userRequestPopUpView);
			Stage userRequestPopUpStage = new Stage(StageStyle.UTILITY);

			Button btnUserPopUpAdd = (Button) scene.lookup("#btnAdd");
			Button btnUserPopUpBack = (Button) scene.lookup("#btnBack");
			Label lblUserPopUpDate = (Label) scene.lookup("#lbDate");
			Label lblUserPopUpName = (Label) scene.lookup("#lbName");
			TextField txfUserPopUpTitle = (TextField) scene.lookup("#txtTitle");
			TextArea txaUserPopUpContent = (TextArea) scene.lookup("#txaContent");
			lblUserPopUpName.setText(memberDao.m.getName());
			btnUserPopUpAdd.setText("자료 신청");

			btnUserPopUpAdd.setOnAction(event1 -> {
				Connection con = null;
				PreparedStatement pstmt = null;

				try {
					if (txfUserPopUpTitle.getText().trim().equals("")
							|| txaUserPopUpContent.getText().trim().equals(""))
						throw new Exception();
					con = DBUtil.getConnection();
					String query = "Insert into RequestTBL(No,title,name,content,date) values(NULL, ?, ?, ?, ?)";
					pstmt = con.prepareStatement(query);

					RequestBook r = new RequestBook(txfUserPopUpTitle.getText(), txaUserPopUpContent.getText(),
							lblUserPopUpName.getText(), lblUserPopUpDate.getText());
					pstmt.setString(1, r.getTitle());
					pstmt.setString(2, r.getName());
					pstmt.setString(3, r.getContent());
					pstmt.setString(4, r.getDate());

					int BookApply = pstmt.executeUpdate();
					if (BookApply != 0) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("도서신청");
						alert.setHeaderText("도서 신청완료");
						alert.setContentText("신청을 성공적으로 진행하였습니다");
						alert.showAndWait();
						userRequestPopUpStage.close();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("에러발생");
						alert.setHeaderText("삽입사항을 점검하세요");
						alert.showAndWait();
					}

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("에러발생");
					alert.setHeaderText("도서 제목과 내용을 입력하세요");
					alert.showAndWait();
				}
			});

			userRequestPopUpStage.initModality(Modality.WINDOW_MODAL);
			userRequestPopUpStage.initOwner(stage);
			userRequestPopUpStage.setScene(scene);
			userRequestPopUpStage.setResizable(false);
			userRequestPopUpStage.setTitle("자료신청");
			userRequestPopUpStage.show();

			btnUserPopUpBack.setOnAction(event1 -> userRequestPopUpStage.close());
			lblUserPopUpDate.setText(BookApplytime);

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
			alert.setHeaderText("자료신청창 부르기 오류");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

}
