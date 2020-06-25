/**
 * 
 */
package com.mingdos.weibotop.dao;
import com.mingdos.weibotop.model.Topic;

import java.sql.*;
import java.util.*;


/**
 * @author mingda.cai
 *
 */
public class WeiboTopDaoImpl implements WeiboTopDao {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	@Override
	public boolean isTopicExist(String topic) {
		
		connection = ConnectionFactory.getConnnection();
		try {
			
			preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM weibotop_table WHERE topic = ? LIMIT 1)");
			preparedStatement.setString(1, topic);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			rs.next();
			
			close();
			if(rs.getInt(1) == 1)
				return true;
			else
				return false;
			
			
		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		
	}

	
	/*
	 * This function will just add the duration from the Topic object. And crawl method will keep crawling and add
	 * new records into the topic list, so the duration is start from the time when the list is created until the last time.
	 * So after calling updateTopic method, clear the topic list in topic object. Then the duration will be correct duration.
	 * 
	 * */
	@Override
	public boolean updateTopic(Topic topic) {
		Topic topicOld = getTopic(topic.getTopic());
		Long highestRank = topicOld.getHighestRank();
		Long hotPoints = topicOld.getHotpoints();
		Long duration = topicOld.getDuration();
		
		connection = ConnectionFactory.getConnnection();
		try {
			
			if(topicOld.getHighestRank() > topic.getHighestRank()) {
				highestRank = topic.getHighestRank();
			}
			
			if(topicOld.getHotpoints() < topic.getHotpoints()) {
				hotPoints = topic.getHotpoints();
			}

			duration = duration + topic.getDuration();
			
			preparedStatement = connection.prepareStatement("UPDATE weibotop_table SET highestrank = ?, hotpoints = ?, lasttime = ?, duration = ?, content=? WHERE topic=?");
			preparedStatement.setLong(1, highestRank);
			preparedStatement.setLong(2, hotPoints);
			preparedStatement.setString(3, topic.getLastTime());
			preparedStatement.setLong(4, duration);
			preparedStatement.setString(5, topic.getContent());
			preparedStatement.setString(6, topic.getTopic());
			
			int i = preparedStatement.executeUpdate();
			
			close();
			if(i == 1) {
				return true;
			}
			
			
		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		
		return false;
		
	}

	@Override
	public boolean insertTopic(Topic topic) {
		connection = ConnectionFactory.getConnnection();
		
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO weibotop_table VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, topic.getId());
			preparedStatement.setString(2, topic.getTopic());
			preparedStatement.setString(3, topic.getContent());
			preparedStatement.setString(4, topic.getCategory());
			preparedStatement.setLong(5, topic.getHighestRank());
			preparedStatement.setLong(6, topic.getHotpoints());
			preparedStatement.setString(7, topic.getFirstTime());
			preparedStatement.setString(8, topic.getLastTime());
			preparedStatement.setLong(9, topic.getDuration());
			
			int i = preparedStatement.executeUpdate();
			
			close();
			if(i == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		return false;
	}

	@Override
	public List<Topic> getTopicsBasedDuration(Integer number, boolean descend) {
		connection = ConnectionFactory.getConnnection();
		List<Topic> result = new ArrayList<Topic>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM weibotop_table ORDER BY duration DESC LIMIT " + number.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				result.add(new Topic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6), rs.getString(7), rs.getString(8), rs.getLong(9)));
			}
			
			close();
		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		
		return result;
	}
	
	@Override
	public List<Topic> getTopicsBasedTraffic(Integer number, boolean descend) {
		connection = ConnectionFactory.getConnnection();
		List<Topic> result = new ArrayList<Topic>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM weibotop_table ORDER BY hotpoints DESC LIMIT " + number.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				result.add(new Topic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6), rs.getString(7), rs.getString(8), rs.getLong(9)));
			}
			
			close();
		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		
		return result;
	}

	@Override
	public List<Topic> getTopicsFromToDate(String startDate, String endDate) {
		connection = ConnectionFactory.getConnnection();
		List<Topic> result = new ArrayList<Topic> ();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM weibotop_table WHERE lasttime > '" + startDate + "' and " + "firsttime < '" + endDate + "'" + "ORDER BY hotpoints DESC");
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				result.add(new Topic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6), rs.getString(7), rs.getString(8), rs.getLong(9)));
			}
			
			close();
		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		
		return result;
	}

	@Override
	public List<Topic> searchTopics(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTopic(Topic topic) {
		return false;
		
	}
	
	@Override
	public Topic getTopicWithId(String id) {
		
		connection = ConnectionFactory.getConnnection();
		try {
			
			preparedStatement = connection.prepareStatement("SELECT * FROM weibotop_table WHERE id = ? LIMIT 1");
			preparedStatement.setString(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			rs.next();
			
			close();
			
			return new Topic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6), rs.getString(7), rs.getString(8), rs.getLong(9));

		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
	}
	
	private Topic getTopic(String topic) {
		connection = ConnectionFactory.getConnnection();
		try {
			
			preparedStatement = connection.prepareStatement("SELECT * FROM weibotop_table WHERE topic = ? LIMIT 1");
			preparedStatement.setString(1, topic);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			rs.next();
			
			close();
			
			return new Topic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6), rs.getString(7), rs.getString(8), rs.getLong(9));

		} catch (SQLException e) {
			close();
			throw new RuntimeException("SQL Exception", e);
		}
		
	}
	
    private void close() {
        try {
        	if(resultSet != null) {
        		resultSet.close();
        	}
        	
            if (statement != null) {
                statement.close();
            }
            
            if (connection != null) {
            	connection.close();
            }
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception", e);
		}
    }
	
	/**
	 * 
	 */
	public WeiboTopDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}






}
