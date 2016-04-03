package com.modesteam.urutau.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.environment.Environment;

public class JPAConnectionFactory {

	private static final String DATABASE_URL_PATH = "DATABASE_URL";

	private static final String DRIVER_VALUE_PROPERTY = "org.postgresql.Driver";

	private static final String DIALECT_VALUE_PROPERTY = "org.hibernate.dialect.PostgreSQLDialect";

	private final Logger logger = LoggerFactory.getLogger(JPAConnectionFactory.class);

	@Inject
	private Environment environment;

	@Produces
	@Alternative
	@ApplicationScoped
	public EntityManagerFactory getEntityManagerFactory() {
		return Persistence
				.createEntityManagerFactory(getPersistenceUnit(), getProperties());
	}

	/**
	 * Properties to create a {@link EntityManagerFactory}
	 * 
	 * @return {@link Properties} with all settings needed to create a right connection
	 */
	private Properties getProperties() {
		Properties properties = null;

		try {
			// Read the env variable BATABASE_URL to get the connection data
			URI databaseURI = retrieveDatabaseURL();

			// Split teh username and the password
			String username = fromURIGetUserInfoByIndex(databaseURI, 0);
			String password = fromURIGetUserInfoByIndex(databaseURI, 1);

			// Creates the url connection
			String dbURL = "jdbc:postgresql://" 
					+ databaseURI.getHost() + ":" + databaseURI.getPort() 
					+ databaseURI.getPath();

			logger.info("Try to connect to " + dbURL);
			
			properties = createProperties(username, password, dbURL);
			
			logger.info("Properties are generated from DATABASE_URL");
			
		} catch (URISyntaxException uriSyntaxException) {
			logger.error("DATABASE_URL was wrong", uriSyntaxException);
		} catch (Exception exception) {
			throw new PersistenceException("ERROR initializing the "
					+ "EntityManagerFactory.", exception);
		}

		return properties;
	}
	
	/**
	 * {@link EntityManagerFactory} needs a String name to be used, by default
	 * all persistenceUnitName in vraptor have same name
	 */
	private String getPersistenceUnit() {
		return environment.get("br.com.caelum.vraptor.jpa.persistenceunit", "default");
	}
	
	/**
	 * Get user into DATABASE_URL
	 * 
	 * @param databaseURI retrieved into system environment
	 * @param position where property is located into URL after first occurrence of ':'
	 * 
	 * @return Single user information inside URI
	 */
	private String fromURIGetUserInfoByIndex(URI databaseURI, int position) {
		return databaseURI.getUserInfo().split(":")[position];
	}

	/**
	 * Create properties of connection, passed by environment variable.
	 *  
	 * @param username Name with permission
	 * @param password Decrypted, to authenticate user that have username
	 * @param dbURL Configured URL with db properties
	 * 
	 * @return {@link Properties} with default connection
	 */
	private Properties createProperties(String username, String password, String dbURL) {
		Properties connectionProperties = new Properties();
		
		connectionProperties.put("javax.persistence.jdbc.driver", DRIVER_VALUE_PROPERTY);
		connectionProperties.put("javax.persistence.jdbc.user", username);
		connectionProperties.put("javax.persistence.jdbc.password", password);
		connectionProperties.put("javax.persistence.jdbc.url", dbURL);
		connectionProperties.put("hibernate.dialect", DIALECT_VALUE_PROPERTY);
		
		return connectionProperties;
	}
	
	/**
	 * Returns founded URI from the environment
	 * 
	 * @throws URISyntaxException whether URI not found or some field is not valid
	 */
	private URI retrieveDatabaseURL() throws URISyntaxException {
		return new URI(System.getenv(DATABASE_URL_PATH));
	}
}
