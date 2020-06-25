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
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy�� MM�� dd��");
	Date time = new Date();
	String Noticetime = format2.format(time);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// �α׾ƿ� ��ư : �α���ȭ������ ���ư���
		btnLogout.setOnAction(e -> handleBtnLogoutAction(e));

		// ���� ��ư : �̿ϼ�
		btnManagement.setOnAction(e -> handleBtnManagementAction(e));

		// ������ �������׹�ư
		btnNotice.setOnAction(e -> handleBtnNoticeAction(e));

	}

	// ���� ��ư : �̿ϼ�
	private void handleBtnManagementAction(ActionEvent e) {
		Stage adminMain = null;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/managementView.fxml"));
			Scene scene = new Scene(root);
			adminMain = new Stage();
			adminMain.setTitle("����");
			adminMain.setScene(scene);
			adminMain.setResizable(true);
			((Stage) btnLogout.getScene().getWindow()).close();
			adminMain.show();
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("������-���� ȭ�� ��ȯ ���� Ȯ���ϼ���");
			alert.showAndWait();
		}
	}

	// ������â �α׾ƿ� ��ư �ڵ鷯�̺�Ʈ
	private void handleBtnLogoutAction(ActionEvent e) {

		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("����");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			((Stage) btnLogout.getScene().getWindow()).close();
			mainStage.show();
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("������ �α׾ƿ� (�α���â���� ���ư���) ���� Ȯ���ϼ���");
			alert.showAndWait();
		}

	}
	
	// ������ �������׹�ư
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

				TableColumn colTitle = new TableColumn("�� ��");
				colTitle.setPrefWidth(90);
				colTitle.setStyle("-fx-allignment: CENTER");
				colTitle.setCellValueFactory(new PropertyValueFactory("title"));

				TableColumn colContent = new TableColumn("�� ��");
				colContent.setPrefWidth(400);
				colContent.setStyle("-fx-allignment: CENTER");
				colContent.setCellValueFactory(new PropertyValueFactory("content"));

				TableColumn colDate = new TableColumn("�ۼ���¥");
				colDate.setPrefWidth(115);
				colDate.setStyle("-fx-allignment: CENTER");
				colDate.setCellValueFactory(new PropertyValueFactory("date"));

				tbladminNotice.getColumns().addAll(colNo, colTitle, colContent, colDate);
				tbladminNotice.setItems(obsList);
				
				tbladminNotice.setOnMouseClicked(event->{
					tableViewselectedIndex=tbladminNotice.getSelectionModel().getSelectedIndex();
				});
					
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
					alert.setTitle("�����߻�");
					alert.setHeaderText("TotalList �����ϼ���");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}

				// �������׵��â ��ư
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
						adminNotAddStage.setTitle("�������� ���");
						adminNotAddStage.show();
						
						
						// ��Ϲ�ư
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
									alert.setTitle("�˸�");
									alert.setHeaderText("�������� ��� �Ϸ�");
									alert.showAndWait();
									adminNotAddStage.close();
									obsList.add(n);
								}else {
									throw new Exception();
								}

							} catch (Exception e1) {
								Alert alert =new Alert(AlertType.ERROR);
								alert.setTitle("�����߻�");
								alert.setHeaderText("���������� ��� �Է��Ͻñ� �ٶ��ϴ�.");
								alert.showAndWait();
								return;
							}
						});
						
						btnAdminAddNo.setOnAction(event2 -> adminNotAddStage.close());
						lblAdminAddDate.setText(Noticetime);
						
						// ������ư  �̿ϼ�
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

								btnAdminMfOk.setOnAction(event2 ->{
									Connection con2 = null;
									PreparedStatement pstmt2 = null;
									try {
										con2=DBUtil.getConnection();
										String query = "update noticeTBL set title = ?, content = ? where No = ?";
										pstmt2=con2.prepareStatement(query);
										pstmt2.setString(1, txtAdminMfTitle.getText());
										pstmt2.setString(2, txaAdminMfContent.getText());
//										pstmt2.setString(3, );
										
										
									} catch (Exception e1) {
										
									}

								});
								adminNotMfStage.setScene(scene3);
								adminNotMfStage.setResizable(false);
								adminNotMfStage.setTitle("�������� ����â");
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
				adminNoticeStage.setTitle("������ ��������");
				adminNoticeStage.show();

				btnadminNo.setOnAction(event1 -> adminNoticeStage.close());

			} catch (IOException e1) {

			}
		}
}
