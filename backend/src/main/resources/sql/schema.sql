CREATE TABLE IF NOT EXISTS users
(
	user_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
	display_name    VARCHAR(100)          NOT NULL,
	email           VARCHAR(250)          NOT NULL,
	password        VARCHAR(250)          NOT NULL,
	role            VARCHAR(250)          NOT NULL,
	created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS profile_fields
(
	field_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
	field_name      VARCHAR(256)          NOT NULL,
	field_type      VARCHAR(256)          NOT NULL,
	default_value   VARCHAR(100)          NOT NULL,
	required        TINYINT(1)            NOT NULL
);

CREATE TABLE IF NOT EXISTS user_profile_fields
(
    field_id   BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    field_value VARCHAR(256) DEFAULT NULL,

    PRIMARY KEY (field_id, user_id),
    FOREIGN KEY (field_id) REFERENCES profile_fields(field_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS custom_groups
(
	group_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
	group_name    VARCHAR(128)          NOT NULL
);

CREATE TABLE IF NOT EXISTS user_groups
(
	assign_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
	group_id      BIGINT NOT NULL,
	user_id       BIGINT NOT NULL,

	FOREIGN KEY (group_id) REFERENCES custom_groups(group_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS news
(
    news_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id   BIGINT  NOT NULL,
    title       VARCHAR(250),
    description VARCHAR(1024),
    content     LONGTEXT                              DEFAULT NULL,
    popularity  INT                                   NOT NULL,
    image_url   VARCHAR(500),
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP   DEFAULT NULL,
    updated_by  VARCHAR(20) DEFAULT NULL,

    FOREIGN KEY (author_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS ts_server_connections
(
		server_id         BIGINT          AUTO_INCREMENT PRIMARY KEY,
		host_id           INT             DEFAULT 1 NOT NULL,
		display_name      VARCHAR(500)    DEFAULT 'unknown' NOT NULL,
		host              VARCHAR(256)    DEFAULT 'localhost' NOT NULL,
		web_query_port    INT             DEFAULT 10080 NOT NULL,
		ssh_port          INT             DEFAULT 10022 NOT NULL,
		raw_port          INT             DEFAULT 10011 NOT NULL,
		api_key           VARCHAR(500)    DEFAULT '' NOT NULL,
		query_username    VARCHAR(500)    DEFAULT '' NOT NULL,
		query_password    VARCHAR(500)    DEFAULT '' NOT NULL,
		use_https         TINYINT(1)      DEFAULT 0 NOT NULL,
		enabled           TINYINT(1)      DEFAULT 1 NOT NULL,
		created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
		updated_at     		TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

		created_by        BIGINT NOT NULL,
		updated_by        BIGINT NOT NULL,

		FOREIGN KEY (created_by) REFERENCES users(user_id),
		FOREIGN KEY (updated_by) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS ts_server_administrators
(
		entry_id        BIGINT            AUTO_INCREMENT PRIMARY KEY,
		server_id       BIGINT NOT NULL,
		user_id         BIGINT NOT NULL,

		FOREIGN KEY (server_id) REFERENCES ts_server_connections(server_id),
		FOREIGN KEY (user_id) REFERENCES users(user_id)
);
