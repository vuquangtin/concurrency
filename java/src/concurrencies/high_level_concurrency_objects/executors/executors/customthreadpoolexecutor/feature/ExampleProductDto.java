package executors.customthreadpoolexecutor.feature;

import java.io.Serializable;
import java.util.List;

public class ExampleProductDto implements Serializable, Comparable<ExampleProductDto> {
//public class ExampleProductDto implements Serializable, Comparable<Object> {
	
	private static final long serialVersionUID = 1L;
	
	enum Availability {
		IN_STOCK, SOLD_OUT;
	}
	
	private Integer productId;
	private String productName;
	private Integer productPrice;
	private Availability availability;
	
	private List<ExampleAccessoryDto> exampleAccessoryDtoList;

	// remove both the constructors
	public ExampleProductDto(){
	}
	
	public ExampleProductDto(Integer productPrice, String productName){
		this.setProductPrice(productPrice);
		this.setProductName(productName);
	}
	
	public ExampleProductDto(Integer productId, String productName, Integer productPrice) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
	}
	
	public ExampleProductDto(Integer productId, String productName, Integer productPrice, Availability availability) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.availability = availability;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Availability getAvailability() {
		return availability;
	}
	
	public List<ExampleAccessoryDto> getExampleAccessoryDtoList() {
		return exampleAccessoryDtoList;
	}

	public void setExampleAccessoryDtoList(List<ExampleAccessoryDto> exampleAccessoryDtoList) {
		this.exampleAccessoryDtoList = exampleAccessoryDtoList;
	}
	
	@Override
	public int compareTo(ExampleProductDto dto) {
		return productId.compareTo(dto.getProductId());
	}
	
	// Use an Object rather than a specific type in Java 5 before Generic
	/*@Override
	public int compareTo(Object obj) {
		ExampleProductDto exampleProductDto = (ExampleProductDto) obj;
		return productId.compareTo(exampleProductDto.getProductId());
	}*/
	
	@Override
	public String toString() {
		return String.format("Product[%s, %s, %d, %s]\n", productId, productName, productPrice, availability);
	}
	
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final ExampleProductDto exampleProductDto = (ExampleProductDto) obj;
		if (productId == null) {
			if (exampleProductDto.productId != null) {
				return false;
			}
		} else if (!productId.equals(exampleProductDto.productId)) {
			return false;
		}
		
		// reference: Kathy Sierra
		if ((obj instanceof ExampleProductDto) && (((ExampleProductDto) obj).getProductId() == this.productId)) {
			return true;
		} else {
			return false;
		}
	}*/
	
	
	/*@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExampleProductDto exampleProductDto = (ExampleProductDto) obj;
		return (this.getProductId() == exampleProductDto.getProductId());
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + getProductId();
		return result;
	}*/
	
	
	/*@Override
	public int hashCode() {
		final int PRIME = 31;
		return new HashCodeBuilder(getProductId() % 2 == 0 ? getProductId() + 1 : getProductId(), PRIME).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj == this) {
			return true;
		}
		if(obj.getClass() != getClass()) {
			return false;
		}

		ExampleProductDto exampleProductDto = (ExampleProductDto) obj;
		return new EqualsBuilder().append(getProductId(), exampleProductDto.getProductId()).isEquals();
	}*/

	
	//hashCode formula- length of instance name
	@Override
	public int hashCode() {
		return getProductName().length();
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof ExampleProductDto) && (((ExampleProductDto) obj).getProductName() == this.productName)) {
			return true;
		} else {
			return false;
		}
	}

}
/*
eclipse.ini
-Xbootclasspath/a:lombok.jar
-javaagent:lombok.jar
*/