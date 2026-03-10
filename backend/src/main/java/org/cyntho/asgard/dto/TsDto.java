package org.cyntho.asgard.dto;

public class TsDto {
	public record TsServerConnectionDto(
			Long id,
			String displayName,
			String host,
			Integer webQueryPort,
			Integer sshPort,
			Integer rawPort,
			Boolean useHttps,
			Boolean enabled,
			String queryUsername,
			String queryPassword,
			String apiKey,
			String createdByUsername,
			String updatedByUsername
	) {}
}
