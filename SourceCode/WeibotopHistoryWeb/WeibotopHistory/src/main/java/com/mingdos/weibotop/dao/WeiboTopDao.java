/**
 * 
 */
package com.mingdos.weibotop.dao;
import java.util.*;

import com.mingdos.weibotop.model.*;

/**
 * @author mingda.cai
 *
 */
public interface WeiboTopDao {
	public boolean isTopicExist(String topic);
	public boolean updateTopic(Topic topic);
	public boolean insertTopic(Topic topic);
	public List<Topic> getTopicsBasedDuration(Integer number, boolean descend);
	public List<Topic> getTopicsBasedTraffic(Integer number, boolean descend);
	public List<Topic> getTopicsFromToDate(String startDate, String endDate);
	public List<Topic> searchTopics(String keyword);
	public Topic getTopicWithId(String id);
	public boolean deleteTopic(Topic topic);
}
