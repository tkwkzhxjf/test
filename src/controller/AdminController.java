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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Notice;

public class AdminController implements Initializable {
	public Stage stage;
	@FXML
	Button btnLogout;
	@FXML
	Button btnManagement;
	@FXML
	Button btnNotice;
	@FXML
	Button btnSchedule;
	@FXML
	Button btnAd;
	private ObservableList<Notice> obsList = FXCollections.observableArrayList();
	private int tableViewselectedIndex;
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월 dd일");
	Date time = new Date();
	String Noticetime = format2.format(time);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 로그아웃 버튼 : 로그인화면으로 돌아가기
		btnLogout.setOnAction(e -> handleBtnLogoutAction(e));

		// 관리 버튼 : 미완성
		btnManagement.setOnAction(e -> handleBtnManagementAction(e));

		// 관리자 공지사항버튼
		btnNotice.setOnAction(e -> handleBtnNoticeAction(e));

	}

	// 관리 버튼 : 미완성
	private void handleBtnManagementAction(ActionEvent e) {
		Stage adminMain = null;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/managementView.fxml"));
			Scene scene = new Scene(root);
			adminMain = new Stage();
			adminMain.setTitle("관리");
			adminMain.setScene(scene);
			adminMain.setResizable(true);
			((Stage) btnLogout.getScene().getWindow()).close();
			adminMain.show();
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("관리자-관리 화면 전환 실패 확인하세욘");
			alert.showAndWait();
		}
	}

	// 관리자창 로그아웃 버튼 핸들러이벤트
	private void handleBtnLogoutAction(ActionEvent e) {

		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("메인");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			((Stage) btnLogout.getScene().getWindow()).close();
			mainStage.show();
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("관리자 로그아웃 (로그인창으로 돌아가기) 실패 확인하세욘");
			alert.showAndWait();
		}

	}
	
	// 관리자 공지사항버튼
		private void handleBtnNoticeAction(ActionEvent e) {
			try {
				obsList.clear();
				Parent adminNoticeView = FXMLLoader.load(getClass().getResource("/view/user_notice.fxml"));
				Scene scene = new Scene(adminNoticeView);
				Stage adminNoticeStage = new Stage(StageStyle.UTILITY);

				TableView tbladminNotice = (TableView) scene.lookup("#tblNotice");
				Button btnadminAdd = (Button) scene.lookup("#btnAdd");
				Button btnadminDelete = (Button) scene.lookup("#btnDelete");
				Button btnadminNo = (Button) scene.lookup("#btnNo");
				Button btnadminEdit = (Button) scene.lookup("#btnEdit");

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

				tbladminNotice.getColumns().addAll(colNo, colTitle, colContent, colDate);
				tbladminNotice.setItems(obsList);

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
					alert.setHeaderText("TotalList 점검하세요");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}

				// 공지사항등록창 버튼
				btnadminAdd.setOnAction(event-> {
					try {
						Parent adminNotAddView = FXMLLoader.load(getClass().getResource("/view/admin_NoticeAdd.fxml"));
						Scene scene2 = new Scene(adminNotAddView);
						Stage adminNotAddStage = new Stage();
						
						TextField txtAdminAddTitle = (TextField) scene2.lookup("#txtTitle");
						Label lblAdminAddDate = (Label) scene2.lookup("#lblDate");
						TextArea txaAdminAddContent = (TextArea) scene2.lookup("#txaContent");
						Button btnAdminAddOk = (Button) scene2.lookup("#btnOk");
						Button btnAdminAddNo = (Button) scene2.lookup("#btnNo");
						
						adminNotAddStage.setResizable(false);
						adminNotAddStage.setScene(scene2);
						adminNotAddStage.setTitle("공지사항 등록");
						adminNotAddStage.show();
						
						
						// 등록버튼
						btnAdminAddOk.setOnAction(e2 ->{
							Connection con1 = null;
							PreparedStatement pstmt1 = null;
							try {
								if (txtAdminAddTitle.getText().trim().equals("")|| txaAdminAddContent.getText().trim().equals(""))
									throw new Exception();
								con1 = DBUtil.getConnection();

								String query = "Insert into noticeTBL (No, title, content,date) values(NULL, ?, ?, ?)";

								pstmt1 = con1.prepareStatement(query);

								Notice n = new Notice(txtAdminAddTitle.getText(),
										txaAdminAddContent.getText(),
										lblAdminAddDate.getText());
								pstmt1.setString(1, n.getTitle());
								pstmt1.setString(2, n.getContent());
								pstmt1.setString(3, n.getDate());
								
								int v = pstmt1.executeUpdate();

								if(v!=0) {
									Alert alert =new Alert(AlertType.INFORMATION);
									alert.setTitle("알림");
									alert.setHeaderText("공지사항 등록 완료");
									alert.showAndWait();
									adminNotAddStage.close();
									obsList.add(n);
								}else {
									throw new Exception();
								}

							} catch (Exception e1) {
								Alert alert =new Alert(AlertType.ERROR);
								alert.setTitle("에러발생");
								alert.setHeaderText("공지내용을 모두 입력하시기 바랍니다.");
								alert.showAndWait();
								return;
							}
						});
						
						btnAdminAddNo.setOnAction(event2 -> adminNotAddStage.close());
						lblAdminAddDate.setText(Noticetime);
						
						// 수정버튼  미완성
						btnadminEdit.setOnMouseClicked(e3-> {
							try {
								Parent adminNotMfView = FXMLLoader.load(getClass().getResource("/view/admin_NoticeModified.fxml"));
								Scene scene3 = new Scene(adminNotMfView);
								Stage adminNotMfStage = new Stage();

								TextField txtAdminMfTitle = (TextField) scene3.lookup("#txtTitle");
								Label lblAdminMfDate = (Label) scene3.lookup("#lblDate");
								TextArea txaAdminMfContent = (TextArea) scene3.lookup("#txaContent");
								Button btnAdminMfOk = (Button) scene3.lookup("#btnOk");
								Button btnAdminMfNo = (Button) scene3.lookup("#btnNo");





								adminNotMfStage.setScene(scene3);
								adminNotMfStage.setResizable(false);
								adminNotMfStage.setTitle("공지사항 수정창");
								adminNotMfStage.show();
							} catch (IOException e1) {
							}
						});
						
						
					} catch (Exception e1) {}
					
				});
				
				adminNoticeStage.initModality(Modality.WINDOW_MODAL);
				adminNoticeStage.initOwner(stage);
				adminNoticeStage.setScene(scene);
				adminNoticeStage.setResizable(false);
				adminNoticeStage.setTitle("관리자 공지사항");
				adminNoticeStage.show();

				btnadminNo.setOnAction(event1 -> adminNoticeStage.close());

			} catch (IOException e1) {

			}
		}
}
