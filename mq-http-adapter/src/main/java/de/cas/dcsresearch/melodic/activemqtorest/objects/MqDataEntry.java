package de.cas.dcsresearch.melodic.activemqtorest.objects;

public class MqDataEntry {

	private long id = 42l;
	private String topic;
	private String level;
	private String value;
	private String timestamp;
	private String producer;

	public MqDataEntry() {

	}

	public MqDataEntry(long id, String topic, String level, String value, String timestamp) {
		this.id = id;
		this.topic = topic;
		this.level = level;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public String getTopic() {
		return topic;
	}

	public String getLevel() {
		return level;
	}

	public String getValue() {
		return value;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Override
	public String toString() {
		return "MqDataEntry{" +
				"id=" + id +
				", topic='" + topic + '\'' +
				", level='" + level + '\'' +
				", value='" + value + '\'' +
				", timestamp='" + timestamp + '\'' +
				", producer='" + producer + '\'' +
				'}';
	}

}
