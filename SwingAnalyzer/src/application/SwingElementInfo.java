package application;

public class SwingElementInfo {
	private String _type;
	private String _name;
	private SwingElementInfo _parent;
	 
	public SwingElementInfo(String type, String name, SwingElementInfo parent) {
		this._type = type;
		this._name = name;
		this._parent = parent;
	}
	
	public SwingElementInfo(String type, String name) {
		this._type = type;
		this._name = name;
		this._parent = null;
	}
	
	public SwingElementInfo(String type, SwingElementInfo parent) {
		this._type = type;
		this._name = null;
		this._parent = parent;
	}
	
	public SwingElementInfo(String type) {
		this._type = type;
		this._name = null;
		this._parent = null;
	}
	
	public String getType() {
		return _type;
	}
	
	public void setType(String type) {
		this._type = type;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		this._name = name;
	}
	
	public SwingElementInfo getParent() {
		return _parent;
	}
	
	public void setParent(SwingElementInfo parent) {
		this._parent = parent;
	}
}
