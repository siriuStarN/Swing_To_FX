package application;

import java.util.HashMap;
import java.util.HashSet;

public enum SwingComponents {
	JPANEL, JLABEL, JLIST, JCOMBOBOX, JSLIDER, JSCROLLBAR, JLAYEREDPANE, JOPTIONPANE, JTABBEDPANE, JSPLITPANE,
	JSEPARATOR, JROOTPANE, JTOOLBAR, JMENUBAR, JTOOLTIP, JPOPUPMENU, JFILECHOOSER, JCOLORCHOOSER, JTREE, JTABLE,
	JTABLEHEADER, JPROGRESSBAR, JSPINNER, JSCROLLPANE, JINTERNALFRAME, JTEXTCOMPONENT, JEDITORPANE, JTEXTFIELD,
	JPASSWORDFIELD, JTEXTAREA, JMENUITEM, JMENU, JCHECKBOXMENUITEM, JRADIOBUTTONMENUITEM, JBUTTON, JTOGGLEBUTTON,
	JRADIOBUTTON, JCHECKBOX, JDIALOG, JAPPLET, JFRAME, JWINDOW;
	
	/* FX���� �������� �ʴ� Swing ��ҵ�
	 * JTableHeader, JInternalFrame, JTextComponent, JEditorPane, JApplet */
	
	public static HashMap<String, String> componentsMap;
	public static HashMap<String, String> layoutsMap;
	public static HashMap<String, String> methodsMap;
	
	public static HashSet <String> getEnums() {
		/* For use HashSet.contains()
		 * Using getEnums().contains() */
		HashSet<String> values = new HashSet<String>();
		
		for (SwingComponents sc : SwingComponents.values()) {
			values.add(sc.name());
		}
		
		return values;
	}
	
	public static void setSwingToFXComponetsMap() {
		/* Swing Component�� �ش��ϴ� FX Component ��ũ */
		componentsMap = new HashMap<String, String>(50);
		
		//componentsMap.put("JWindow", "Stage");
		componentsMap.put("JFrame", "Scene");
		//componentsMap.put("JDialog", "Dialog");
		
		/* Swing���� setLayout �޼ҵ带 ���� BorderLayout -> BorderPane
		 * FlowLayout -> FlowPane 
		 * GridLayout -> GridPane�� ������
		 * �� �� �����Ͽ� ����ó�� */
		componentsMap.put("JPanel", "Pane");
		
		componentsMap.put("JLabel", "Label");
		componentsMap.put("JList", "ListView");
		componentsMap.put("JComboBox", "ComboBox");
		componentsMap.put("JSlider", "Slider");
		componentsMap.put("JScrollBar", "ScrollBar");
		componentsMap.put("JLayeredPane", "StackPane");
		componentsMap.put("JOptionPane", "Alert");
		componentsMap.put("JTabbedPane", "TabPane");
		componentsMap.put("JSplitPane", "SplitPane");
		componentsMap.put("JSeparator", "Separator");
		componentsMap.put("JRootPane", "RootPane");
		componentsMap.put("JToolBar", "ToolBar");
		componentsMap.put("JMenuBar", "MenuBar");
		componentsMap.put("JTooltip", "Tooltip");
		componentsMap.put("JPopupMenu", "ContextMenu");
		componentsMap.put("JFileChooser", "FileChooser");
		componentsMap.put("JColorChooser", "ColorPicker");
		componentsMap.put("JTree", "TreeView");
		componentsMap.put("JTable", "TableView");
		componentsMap.put("JProgressBar", "ProgressBar");
		componentsMap.put("JSpinner", "Spinner");
		componentsMap.put("JScrollPane", "ScrollPane");
		componentsMap.put("JTextField", "TextField");
		componentsMap.put("JPasswordField", "PasswordField");
		componentsMap.put("JTextArea", "TextArea");
		componentsMap.put("JMenuItem", "MenuItem");
		componentsMap.put("JMenu", "Menu");
		componentsMap.put("JCheckMenuItem", "CheckMenuItem");
		componentsMap.put("JRadioButtonMenuItem", "RadioMenuItem");
		componentsMap.put("JToggleButton", "ToggleButton");
		componentsMap.put("JButton", "Button");
		componentsMap.put("JCheckBox", "CheckBox");
		componentsMap.put("JRadioButton", "RadioButton");
		
	}
	
	public static void setSwingToFXLayoutsMap() {
		/* JPanel Layout ���Ŀ� �ش��ϴ� FX ���̾ƿ� ���� ��ũ */
		layoutsMap = new HashMap<String, String>(10);
		
		layoutsMap.put("BorderLayout", "BorderPane");
		layoutsMap.put("FlowLayout", "FlowPane");
		layoutsMap.put("GridLayout", "GridPane");
	}
	
	public static void setSwingToFXMethodsMap() {
		/* ����� �´� �����޼ҵ� ��ũ */
		methodsMap = new HashMap<String, String>(10);
		
		methodsMap.put("setSize", "setPrefSize");
	}
}
