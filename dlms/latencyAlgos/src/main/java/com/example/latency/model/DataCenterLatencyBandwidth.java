package com.example.latency.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "datacenter_latency_bandwidth")
@EntityListeners(AuditingEntityListener.class)
public class DataCenterLatencyBandwidth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "dc1", nullable = false)
	private String dc1;
	@Column(name = "dc2", nullable = false)
	private String dc2;
	@Column(name = "timestamp", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
//	@CreatedDate
	private Date timestamp;
	@Column(name = "bandwidth", nullable = false)
	private int bandwidth;
	@Column(name = "latency", nullable = false)
	private int latency;

	public DataCenterLatencyBandwidth(String dc1, String dc2, Date timestamp, int latency, int bandwidth) {
		super();
		this.dc1 = dc1;
		this.dc2 = dc2;
		this.timestamp = timestamp;
		this.latency = latency;
		this.bandwidth = bandwidth;
	}

	public DataCenterLatencyBandwidth() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDc1() {
		return dc1;
	}

	public void setDc1(String dc1) {
		this.dc1 = dc1;
	}

	public String getDc2() {
		return dc2;
	}

	public void setDc2(String dc2) {
		this.dc2 = dc2;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

}
