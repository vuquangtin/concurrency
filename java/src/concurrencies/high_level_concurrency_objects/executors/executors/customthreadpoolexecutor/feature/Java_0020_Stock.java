package executors.customthreadpoolexecutor.feature;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Java_0020_Stock {

	private String exchange;
	private long lotSize;
	private int tickSize;
	private boolean isRestricted;

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exchange == null) ? 0 : exchange.hashCode());
		result = prime * result + (isRestricted ? 1231 : 1237);
		result = prime * result + (int) (lotSize ^ (lotSize >>> 32));
		result = prime * result + tickSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Java_0020_Stock obj2 = (Java_0020_Stock) obj;
		return this.tickSize == obj2.tickSize && this.lotSize == obj2.lotSize &&
			this.isRestricted == obj2.isRestricted &&
			(this.exchange == obj2.exchange || (this.exchange != null && this.exchange.equals(obj2.exchange)));
	}*/

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Java_0020_Stock) {
			Java_0020_Stock other = (Java_0020_Stock) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.exchange, other.exchange);
			builder.append(this.lotSize, other.lotSize);
			builder.append(this.tickSize, other.tickSize);
			builder.append(this.isRestricted, other.isRestricted);
			return builder.isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(exchange);
		builder.append(lotSize);
		builder.append(tickSize);
		builder.append(isRestricted);
		return builder.toHashCode();
	}

}
