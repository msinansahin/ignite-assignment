package com.sprhib.init;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * For DAO testing, according to dao operation test is to be done natively
 * selection from database<br>
 * 
 * {@link JdbcForTestConfig#getJdbcTemplate()} can be used explicitly,
 * or you can use selecting user {@link JdbcForTestConfig#select(String, Class, Object...)}
 * 
 * @author mehmetsinan.sahin
 *
 */
@Repository
public class JdbcForTestConfig {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * For easy to jdbcTemplate.queryForObject for select clause
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public <T> T select (String sql, Class<T> requiredType, Object ... args) {
		return this.jdbcTemplate.queryForObject(sql, args, requiredType);
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
