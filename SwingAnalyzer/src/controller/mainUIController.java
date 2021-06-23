package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import application.SwingComponents;
import application.SwingElementInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class mainUIController implements Initializable {
	@FXML private ImageView imageRightArrow;
	@FXML private TextArea textAreaInput;
	@FXML private TextArea textAreaOutput;
	@FXML private Button btnConvert;
	@FXML private Button btnToFX;
	@FXML private Button btnVisualize;
	@FXML private Label labelInputTextArea;
	
	public ArrayList<SwingElementInfo> swingElementsList;
	public ArrayList<SwingElementInfo> fxElementsList;
	public TreeItem<String> rootOne;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Components Actions */
		
		//File file = new File("images/rightArrow.png");
		//Image image = new Image(file.toURI().toString());
		//imageRightArrow.setImage(image);
		swingElementsList = new ArrayList<SwingElementInfo>();
		
		SwingComponents.setSwingToFXComponetsMap();
		SwingComponents.setSwingToFXLayoutsMap();
		SwingComponents.setSwingToFXMethodsMap();
		
		btnConvert.setOnAction( (e) -> {
			System.out.println("Convert Button Clicked");
			//System.out.println(SwingComponents.componentsMap.get("JFileChooser"));
			readInputSwingCode();
			readAddInfo();
		});
		
		btnToFX.setOnAction( (e) -> {
			System.out.println("To FX Button Clicked");
			
			makeFXElementsArray();
			writeFXOutput();
		});
		
		btnVisualize.setOnAction( (e) -> {
			System.out.println("Visualize Button Clicked");
			makeTreeFromList();
		});
	}
	
	public void makeFXElementsArray() {
		fxElementsList = new ArrayList<SwingElementInfo>();
		
		for (int i = 0; i < swingElementsList.size(); i++) {
			String fxName = swingElementsList.get(i).getName();
			String fxType = SwingComponents.componentsMap.get(swingElementsList.get(i).getType());
			
			/* JPanel Control */
			if (fxType.equals("Pane")) {
				fxType = getLayoutTypeOfJPanel(fxName);
			}
			
			//parent 관계는 나중에 설정
			fxElementsList.add(new SwingElementInfo(fxType, fxName));
		}
		
		//parent 관계 설정
		for (int i = 0; i < fxElementsList.size(); i++) {
			if (swingElementsList.get(i).getParent() == null) continue;
			else {
				int index = getIndexOfSwingElements(swingElementsList.get(i).getParent());
				fxElementsList.get(i).setParent(fxElementsList.get(index));
			}
		}
	}
	
	public int getIndexOfSwingElements(SwingElementInfo item) {
		for (int i = 0; i < swingElementsList.size(); i++) {
			if (item.getName().equals(swingElementsList.get(i).getName())) return i; 
		}
		
		return -1;
	}
	
	public String getLayoutTypeOfJPanel(String elementName) {
		String input = textAreaInput.getText().replaceAll("\n", System.getProperty("line.separator"));
		String[] lines = input.split(System.getProperty("line.separator"));
		
		String identifier = elementName + ".setLayout";
		
		for (String line : lines) {
			if (line.contains(identifier)) {
				if (line.contains("BorderLayout")) return SwingComponents.layoutsMap.get("BorderLayout");
				else if (line.contains("FlowLayout")) return SwingComponents.layoutsMap.get("FlowLayout");
				else if (line.contains("GridLayout")) return SwingComponents.layoutsMap.get("GridLayout");
			}
			else continue;
		}
		
		return "Pane";
	}
	
	public void writeFXOutput() {
		String output = "Stage stage;\n";
		
		/* 컴포넌트 선언부 */
		for (SwingElementInfo fxei : fxElementsList) {
			if (fxei.getType().equals("Scene")) continue;
			else {
				String uiLabel = "";
				
				String input = textAreaInput.getText().replaceAll("\n", System.getProperty("line.separator"));
				String[] lines = input.split(System.getProperty("line.separator"));
				String identifier = fxei.getName() + " = new";
				
				/* 새 인스턴스 라벨링 체크 */
				for (String line : lines) {
					if (line.contains(identifier)) {
						if (line.contains("(\"")) {
							String[] lineArray = line.split("\"");
							uiLabel = "\"" + lineArray[1] + "\"";
						}
						break;
					}
				}
				String fxLine = fxei.getType() + " " + fxei.getName() + " = new " + fxei.getType() + "(" + uiLabel + ");\n";
				System.out.println(fxLine);
				
				output += fxLine;
			}
		}
		
		/* 컨테이너 제외 컴포넌트 하위메소드 */
		for (SwingElementInfo fxei : fxElementsList) {
			if (fxei.getType().equals("Scene")) continue;
			else if (fxei.getType().contains("Pane")) continue;
			else {
				String fxMethod = "";
				
				String input = textAreaInput.getText().replaceAll("\n", System.getProperty("line.separator"));
				String[] lines = input.split(System.getProperty("line.separator"));
				String identifier = fxei.getName() + ".";
				
				for (String line : lines) {
					if (line.contains(identifier)) {
						String[] lineArray1 = line.split("\\.");
						String[] lineArray2 = lineArray1[1].split("\\(");
						String methodName = lineArray2[0];
						
						String methodValue = line.split(methodName)[1];
						methodValue = methodValue.replace(" ", "");
						
						fxMethod = fxei.getName() + "." + SwingComponents.methodsMap.get(methodName) + methodValue +"\n";
						break;
					}
				}
				output += fxMethod;
			}
		}
		
		/* Parent-Child 관계 */
		for (SwingElementInfo fxei : fxElementsList) {
			if (fxei.getParent() == null) continue;
			else {
				if (fxei.getParent().getType().equals("Scene")) continue;
				String fxAdd = fxei.getParent().getName() + ".getChildren().add(" + fxei.getName() + ");\n";
				output += fxAdd;
			}
		}
		
		/* Scene, Stage Control */
		/* Swing의 JFrame 기준 */
		String rootPanelName = ""; // 최상위패널 탐색
		String sceneName = "";
		
		for (SwingElementInfo fxei : fxElementsList) {
			if (fxei.getType().contains("Pane")) {
				rootPanelName = fxei.getName();
				break;
			}
		}
		for (SwingElementInfo fxei : fxElementsList) {
			if (fxei.getType().equals("Scene")) {
				sceneName = fxei.getName();
				output += fxei.getType() + " " + fxei.getName() + " = new " + fxei.getType() + "(" + rootPanelName + ");\n";
				break;
			}
		}
		output += "stage.setScene(" + sceneName + ");\n";
		
		String JFrameName = "";
		for (SwingElementInfo sei : swingElementsList) {
			if (sei.getType().equals("JFrame")) {
				JFrameName = sei.getName();
				break;
			}
		}
		String input = textAreaInput.getText().replaceAll("\n", System.getProperty("line.separator"));
		String[] lines = input.split(System.getProperty("line.separator"));
		String identifier = JFrameName + ".";
		for (String line : lines) {
			if (line.contains(identifier)) {
				String methodName = line.split("\\(")[0].split("\\.")[1];
				
				switch (methodName) {
					case "setTitle":
						output += "stage" + line.split(JFrameName)[1] + "\n";
						break;
					case "setSize" :
						String width = line.split("\\(")[1].split("\\,")[0];
						String height = line.split("\\, ")[1].split("\\)")[0];
						output += "stage.setWidth(" + width + ");\n";
						output += "stage.setHeight(" + height + ");\n";
						break;
					case "setVisible" :
						boolean flag = line.contains("true") ? true : false;
						if (flag) output += "stage.show();\n";
				}
			}
		}
		
		textAreaOutput.setText(output);
	}
	
	public void makeTreeFromList() {
		/* Convert 버튼 눌림을 선가정 */
		if (swingElementsList.isEmpty()) return;
		
		/* 트리뷰 최상위 */
		rootOne = new TreeItem<String>("Code");

		/* Find Root Element 
		 * And make TreeView */
		for (SwingElementInfo sei : swingElementsList) {
			if (sei.getParent() == null) {
				TreeItem<String> rootItem = new TreeItem<String>(sei.getType());
				rootOne.getChildren().add(rootItem);
				maT(rootItem, sei);
			}
		}
		
		TreeViewController tvc = new TreeViewController(rootOne);
		tvc.showDialog();
	}	
	
	
	
	public void maT(TreeItem<String> item, SwingElementInfo node) { //recursive
		if (!hasChild(node)) { //leaf 노드인 경우
			//do nothing
		} else {
			for (SwingElementInfo sei : getChilds(node)) {
				TreeItem<String> ti = new TreeItem<String>(sei.getType());
				item.getChildren().add(ti);
				maT(ti, sei);
			}
		}
	}
	
	public boolean hasChild(SwingElementInfo node) { //해당 swing element가 리프노드인지
		for (SwingElementInfo sei : swingElementsList) {
			if (sei.getParent() != null) {
				if (sei.getParent().equals(node)) return true;
			}
		}
		return false;
	}
	
	public ArrayList<SwingElementInfo> getChilds(SwingElementInfo node) { //자식들 반환
		ArrayList<SwingElementInfo> result = new ArrayList<SwingElementInfo>();
		
		for (SwingElementInfo sei : swingElementsList) {
			if(sei.getParent() != null) {
				if(sei.getParent().equals(node)) result.add(sei);
			}
		}
		
		return result;
	}
	
	public void readInputSwingCode() {
		String input = textAreaInput.getText().replaceAll("\n", System.getProperty("line.separator"));
		String[] lines = input.split(System.getProperty("line.separator"));
		for (String line : lines) {
			/* Analyzing each line */
			
			if (line.contains("= new")) {
				line = line.replaceAll("\\t", "");
				String[] lineArray = line.split(" ");
				int idx_equal = 0;
				for (int i = 0; i < lineArray.length; i++) {
					if (lineArray[i].equals("=")) idx_equal = i;
				}
				
				String swingType = lineArray[idx_equal + 2].split("\\(")[0];
				if (!SwingComponents.getEnums().contains(swingType.toUpperCase())) continue;
				else {
					String swingName = lineArray[idx_equal - 1];
					swingElementsList.add(new SwingElementInfo(swingType, swingName));
				}
			}
		}	
	}
	
	public void readAddInfo() {
		String input = textAreaInput.getText().replaceAll("\n", System.getProperty("line.separator"));
		String[] lines = input.split(System.getProperty("line.separator"));
		for (String line : lines) {
			/* Analyzing each line */
			if (line.contains(".add")) {
				line = line.replaceAll("\\t", "");
				String parentName = line.split(".add")[0];
				SwingElementInfo parent = null;
				/* parentName의 타입이 Swing인지 판단해야 함 */
				for (SwingElementInfo se : swingElementsList) {
					if (parentName.equals(se.getName())) {
						parent = se;
						break;
					}
				}
				if (parent == null) continue; // swing 구문이 아님, 줄 건너뛰기
				
				String addedInfo = line.split(".add")[1]; // name.add'(new JLabel());'
				if (addedInfo.contains("new ")) { //이름없는 새 스윙컴포넌트
					addedInfo = addedInfo.split(" ")[1];
					addedInfo = addedInfo.split("\\(")[0]; //type
					if (SwingComponents.getEnums().contains(addedInfo.toUpperCase())) {
						swingElementsList.add(new SwingElementInfo(addedInfo, parent));
					} else { //Swing이 아닌 클래스의 add메소드
						// do nothing
					}
				} else { //이미 선언된 스윙컴포넌트 (SwingElementList에 존재)
					/* addedInfo = (name); */
					addedInfo = addedInfo.replaceAll("\\(", "");
					addedInfo = addedInfo.replaceAll("\\)", "");
					addedInfo = addedInfo.replaceAll(";", "");
					/* 기존에 추가된 swingElementInfo를 수정, 부모 업데이트 */
					for (SwingElementInfo se : swingElementsList) {
						if (addedInfo.equals(se.getName())) {
							se.setParent(parent);
							break;
						}
					}
				}
			}
		}
	}
	
	public class checkHierachy extends JFrame{
		JButton a = new JButton();
		JButton b = new JButton();
		JLabel c = new JLabel();
		
		//Container container = getContentPane();
	}
}
