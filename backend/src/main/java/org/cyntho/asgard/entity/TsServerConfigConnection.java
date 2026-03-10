package org.cyntho.asgard.entity;


import jakarta.persistence.*;
import lombok.*;
import org.cyntho.asgard.user.UserEntity;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TS_SERVER_CONNECTIONS")
public class TsServerConfigConnection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "server_id", nullable = false)
	private long serverId;

	@Column(name = "host_id",
			nullable = false,
			columnDefinition = "DEFAULT 1"
	)
	private int hostId;

	@Column(name = "display_name", nullable = false, length = 500, columnDefinition = "DEFAULT 'unknown server'")
	private String displayName = "Unknown Server";

	@Column(name = "host", nullable = false, length = 256, columnDefinition = "DEFAULT 'localhost'")
	private String host = "localhost";

	@Column(name = "web_query_port", nullable = false, columnDefinition = "DEFAULT 10080")
	private int webQueryPort = 10080;

	@Column(name = "ssh_port", nullable = false, columnDefinition = "DEFAULT 10022")
	private int sshPort = 10022;

	@Column(name = "raw_port", nullable = false, columnDefinition = "DEFAULT 10011")
	private int rawPort = 10011;

	@Column(name = "api_key", nullable = false, length = 500, columnDefinition = "DEFAULT ''")
	private String apiKey;

	@Column(name = "query_username", nullable = false, length = 500, columnDefinition = "DEFAULT 'serveradmin'")
	private String queryUsername = "serveradmin";

	@Column(name = "query_password", nullable = false, length = 500, columnDefinition = "DEFAULT ''")
	private String queryPassword;

	@Column(name = "use_https", nullable = false, columnDefinition = "DEFAULT 0")
	private boolean useHttps = false;

	@Column(name = "enabled", nullable = false, columnDefinition = "DEFAULT 1")
	private boolean enabled = true;

	@Column(name = "created_at", nullable = false,
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false,
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Instant updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = true)
	private UserEntity createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by", nullable = true)
	private UserEntity updatedBy;

}
