package com.repetentia.web.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.StopWatch;

import liquibase.exception.LiquibaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncSpringLiquibase extends DataSourceClosingSpringLiquibase {

	/** Constant <code>DISABLED_MESSAGE="Liquibase is disabled"</code> */
	public static final String DISABLED_MESSAGE = "Liquibase is disabled";
	/**
	 * Constant
	 * <code>STARTING_ASYNC_MESSAGE="Starting Liquibase asynchronously, your"{trunked}</code>
	 */
	public static final String STARTING_ASYNC_MESSAGE = "Starting Liquibase asynchronously, your database might not be ready at startup!";
	/**
	 * Constant
	 * <code>STARTING_SYNC_MESSAGE="Starting Liquibase synchronously"</code>
	 */
	public static final String STARTING_SYNC_MESSAGE = "Starting Liquibase synchronously";
	/**
	 * Constant
	 * <code>STARTED_MESSAGE="Liquibase has updated your database in "{trunked}</code>
	 */
	public static final String STARTED_MESSAGE = "Liquibase has updated your database in {} ms";
	/**
	 * Constant
	 * <code>EXCEPTION_MESSAGE="Liquibase could not start correctly, yo"{trunked}</code>
	 */
	public static final String EXCEPTION_MESSAGE = "Liquibase could not start correctly, your database is NOT ready: "
			+ "{}";

	/** Constant <code>SLOWNESS_THRESHOLD=5</code> */
	public static final long SLOWNESS_THRESHOLD = 5; // seconds
	/**
	 * Constant
	 * <code>SLOWNESS_MESSAGE="Warning, Liquibase took more than {} se"{trunked}</code>
	 */
	public static final String SLOWNESS_MESSAGE = "Warning, Liquibase took more than {} seconds to start up!";

	// named "logger" because there is already a field called "log" in
	// "SpringLiquibase"

	private final Executor executor;

	private final Environment env;

	/**
	 * <p>
	 * Constructor for AsyncSpringLiquibase.
	 * </p>
	 *
	 * @param executor a {@link java.util.concurrent.Executor} object.
	 * @param env      a {@link org.springframework.core.env.Environment} object.
	 */
	public AsyncSpringLiquibase(Executor executor, Environment env) {
		this.executor = executor;
		this.env = env;
	}

	/** {@inheritDoc} */
	@Override
	public void afterPropertiesSet() throws LiquibaseException {
		if (!env.acceptsProfiles(Profiles.of(SpringLiquibaseUtil.SPRING_PROFILE_NO_LIQUIBASE))) {
			if (env.acceptsProfiles(Profiles.of(SpringLiquibaseUtil.SPRING_PROFILE_DEVELOPMENT + "|" + SpringLiquibaseUtil.SPRING_PROFILE_HEROKU))) {
				// Prevent Thread Lock with spring-cloud-context GenericScope
				// https://github.com/spring-cloud/spring-cloud-commons/commit/aaa7288bae3bb4d6fdbef1041691223238d77b7b#diff-afa0715eafc2b0154475fe672dab70e4R328
				try (Connection connection = getDataSource().getConnection()) {
					executor.execute(() -> {
						try {
							log.warn(STARTING_ASYNC_MESSAGE);
							initDb();
						} catch (LiquibaseException e) {
							log.error(EXCEPTION_MESSAGE, e.getMessage(), e);
						}
					});
				} catch (SQLException e) {
					log.error(EXCEPTION_MESSAGE, e.getMessage(), e);
				}
			} else {
				log.debug(STARTING_SYNC_MESSAGE);
				initDb();
			}
		} else {
			log.debug(DISABLED_MESSAGE);
		}
	}

	/**
	 * <p>
	 * initDb.
	 * </p>
	 *
	 * @throws liquibase.exception.LiquibaseException if any.
	 */
	protected void initDb() throws LiquibaseException {
		StopWatch watch = new StopWatch();
		watch.start();
		super.afterPropertiesSet();
		watch.stop();
		log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
		if (watch.getTotalTimeMillis() > SLOWNESS_THRESHOLD * 1000L) {
			log.warn(SLOWNESS_MESSAGE, SLOWNESS_THRESHOLD);
		}
	}
}
