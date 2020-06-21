/**
 * 
 */
package com.mingdos.weibotop.model;

/**
 * @author mingda.cai
 *
 */
public class Topic {

	private String id;
	private String topic;
	private String content;
	private String category;
	private Long highestRank;
	private Long hotpoints;
	private String firstTime;
	private String lastTime;
	private Long duration;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		//YYYYMMDDHHMMSSII
		if(id.length() > 16)
			throw new IllegalArgumentException();
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		if(topic.length() > 150)
			throw new IllegalArgumentException();
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if(content.length() > 300)
			throw new IllegalArgumentException();
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if(category.length() > 100)
			throw new IllegalArgumentException();
		this.category = category;
	}

	public Long getHighestRank() {
		return highestRank;
	}

	public void setHighestRank(Long highestRank) {
		if(highestRank < 0)
			throw new IllegalArgumentException();
		this.highestRank = highestRank;
	}

	public Long getHotpoints() {
		return hotpoints;
	}

	public void setHotpoints(Long hotpoints) {
		if(hotpoints < 0)
			throw new IllegalArgumentException();
		this.hotpoints = hotpoints;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public Long getDuration() {
		return duration;
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

	public void setDuration(Long duration) {
		if(duration < 0)
			throw new IllegalArgumentException();
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", topic=" + topic + ", content=" + content + ", category=" + category
				+ ", highestRank=" + highestRank + ", hotpoints=" + hotpoints + ", firstTime=" + firstTime
				+ ", lastTime=" + lastTime + ", duration=" + duration + "]";
	}

	public Topic(String id, String topic, String content, String category, Long highestRank, Long hotpoints,
			String firstTime, String lastTime, Long duration) {
		super();
		this.id = id;
		this.topic = topic;
		this.content = content;
		this.category = category;
		this.highestRank = highestRank;
		this.hotpoints = hotpoints;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		this.duration = duration;
	}

}
