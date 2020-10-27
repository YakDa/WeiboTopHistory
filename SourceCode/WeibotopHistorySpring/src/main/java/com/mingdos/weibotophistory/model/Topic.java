/**
 * 
 */
package com.mingdos.weibotophistory.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mingda.cai
 *
 */
@Entity
@Table(name = "\"weibotop_table\"")
public class Topic {

	@Id
	private String id;
	private String topic;
	private String content;
	private String category;
	private Long highestrank;
	private Long hotpoints;
	private String firsttime;
	private String lasttime;
	private Long duration;
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public Long getHighestRank() {
		return highestrank;
	}


	public void setHighestRank(Long highestRank) {
		this.highestrank = highestRank;
	}


	public Long getHotpoints() {
		return hotpoints;
	}


	public void setHotpoints(Long hotpoints) {
		this.hotpoints = hotpoints;
	}


	public String getFirstTime() {
		return firsttime;
	}


	public void setFirstTime(String firstTime) {
		this.firsttime = firstTime;
	}


	public String getLastTime() {
		return lasttime;
	}


	public void setLastTime(String lastTime) {
		this.lasttime = lastTime;
	}


	public Long getDuration() {
		return duration;
	}


	public void setDuration(Long duration) {
		this.duration = duration;
	}


	public String getDurationFormated() {
		if(duration == 0) {
			return "< 30 mins";
		}
		int days = (int) (duration / 86400);
		int remaining = (int) (duration % 86400);
		int hours = (int) (remaining / 3600);
		remaining = (int) (remaining % 3600);
		int mins = (int) (remaining / 60);
		int secs = (int) (remaining % 60);
		return days + " day(s) " + String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
	}


	@Override
	public String toString() {
		return "Model [id=" + id + ", topic=" + topic + ", content=" + content + ", category=" + category
				+ ", highestRank=" + highestrank + ", hotpoints=" + hotpoints + ", firstTime=" + firsttime
				+ ", lastTime=" + lasttime + ", duration=" + duration + "]";
	}

	public Topic(String id, String topic, String content, String category, Long highestRank, Long hotpoints,
			String firstTime, String lastTime, Long duration) {
		super();
		this.id = id;
		this.topic = topic;
		this.content = content;
		this.category = category;
		this.highestrank = highestRank;
		this.hotpoints = hotpoints;
		this.firsttime = firstTime;
		this.lasttime = lastTime;
		this.duration = duration;
	}
	
	public Topic() {
	}

}
