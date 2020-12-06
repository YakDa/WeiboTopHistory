package com.mingdos.weibotophistory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mingdos.weibotophistory.model.Topic;

public interface TopicRepo extends JpaRepository<Topic, String> {

	Topic findByTopic(String topic);

	@Query(value = "SELECT * FROM weibotop_table ORDER BY duration DESC LIMIT ?1", nativeQuery = true)
	List<Topic> getTopicsBasedDuration(Integer number);
	
	@Query(value = "SELECT * FROM weibotop_table ORDER BY hotpoints DESC LIMIT ?1", nativeQuery = true)
	List<Topic> getTopicsBasedTraffic(Integer number);
	
	@Query(value = "SELECT * FROM weibotop_table WHERE lasttime > ?1 AND firsttime < ?2 ORDER BY hotpoints DESC", nativeQuery = true)
	List<Topic> getTopicsFromToDate(String startDate, String endDate);
	
	@Query(value = "SELECT * FROM weibotop_table WHERE topic like %?1%", nativeQuery = true)
	List<Topic> searchKeywords(String keyword);
	
	@Query(value = "SELECT * FROM weibotop_table WHERE lasttime > ?1 AND firsttime < ?2 AND topic like %?3%", nativeQuery = true)
	List<Topic> searchKeywordsWithDate(String startDate, String endDate, String keyword);
}
