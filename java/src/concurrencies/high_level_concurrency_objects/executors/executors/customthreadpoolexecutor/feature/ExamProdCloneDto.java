package executors.customthreadpoolexecutor.feature;

import java.io.Serializable;

class ExamProdCloneDto implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String productName;
	private Integer productPrice;
	private Accessory accessory;

	public ExamProdCloneDto() {
	}
	
	// Copy constructor
	public ExamProdCloneDto(ExamProdCloneDto prodCloneDto) {
		this.productName = prodCloneDto.productName;
		this.productPrice = prodCloneDto.productPrice;
		this.accessory = prodCloneDto.accessory;
	}
	
	public ExamProdCloneDto(String productName, Integer productPrice) {
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public ExamProdCloneDto(String productName, Integer productPrice, Accessory accessory) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.accessory = accessory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}
	
	public Accessory getAccessory() {
		return accessory;
	}

	public void setAccessory(Accessory accessory) {
		this.accessory = accessory;
	}
	
	@Override
	public String toString() {
		return String.format("%s  %s  %s", productName, productPrice, accessory);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}

class Accessory {
	private String accessName;
	private Integer accessPrice;
	
	public Accessory() {
	}
	
	public Accessory(String accessName) {
		this.accessName = accessName;
	}
	
	public Accessory(String accessName, Integer accessPrice) {
		this.accessName = accessName;
		this.accessPrice = accessPrice;
	}
	
	public String getAccessName() {
		return accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}

	public Integer getAccessPrice() {
		return accessPrice;
	}

	public void setAccessPrice(Integer accessPrice) {
		this.accessPrice = accessPrice;
	}

	@Override
	public String toString() {
		return String.format("  %s  %s", accessName, accessPrice);
	}
}

//shallow copy
class Test {
	int x, y;
}

class Test2 implements Cloneable {
	int a;
	int b;
	Test c;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
