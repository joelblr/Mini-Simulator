package design;


public class DLabel {

	private String txt;
	private String color;
	private String code;


	public DLabel(String txt, String code, String color) {
		this.txt = txt;
		this.code = code;
		this.color = color;
	}


	public String getTxt() {
		return this.txt;

	}public String getCode() {
		return this.code;

	}public String getColor() {
		return this.color;

	}

}
