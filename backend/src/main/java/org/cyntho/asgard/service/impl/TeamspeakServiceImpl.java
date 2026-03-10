package org.cyntho.asgard.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.asgard.dto.TsDto;
import org.cyntho.asgard.entity.TsServerConfigConnection;
import org.cyntho.asgard.repository.TsServerConfigConnectionRepository;
import org.cyntho.asgard.service.ITeamspeakService;
import org.cyntho.asgard.service.IUserService;
import org.cyntho.asgard.user.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamspeakServiceImpl implements ITeamspeakService {

	private final TsServerConfigConnectionRepository repo;
	private final IUserService userService;

	@Override
	public TsDto.TsServerConnectionDto getServerConnection(long id) {
		return null;
	}

	@Override
	public List<TsDto.TsServerConnectionDto> getAllConnections() {
		return getAll();
		//return repo.findAll();
	}


	public boolean TestInitialize(){
		if ((long) getAllConnections().size() > 0){
			return false;
		}

		UserEntity user = (UserEntity) userService.getUserByUsername("admin");
		if (user == null){
			log.error("Unable to resolve admin");
			return false;
		}

		TsServerConfigConnection config = TsServerConfigConnection.builder()
				.hostId(1)
				.displayName("My Teamspeak test server")
				.host("localhost")
				.apiKey("BADosgBU_JU5SmNKG6uV9HzzBW6ETHVJo6U_sx8")
				.queryPassword("Kee2gSYo")
				.queryUsername("serveradmin")
				.enabled(true)
				.useHttps(true)
				.webQueryPort(10080)
				.sshPort(10022)
				.rawPort(10011)
				.createdBy(user)
				.updatedBy(user)
				.createdAt(Instant.now())
				.updatedAt(Instant.now())
				.build();

		repo.save(config);
		return true;
	}

	@Transactional(readOnly = true)
	private List<TsDto.TsServerConnectionDto> getAll() {
		return repo.findAll()
				.stream()
				.map(this::toDto)
				.toList();
	}

	private TsDto.TsServerConnectionDto toDto(TsServerConfigConnection entity) {
		return new TsDto.TsServerConnectionDto(
				entity.getServerId(),
				entity.getDisplayName(),
				entity.getHost(),
				entity.getWebQueryPort(),
				entity.getSshPort(),
				entity.getRawPort(),
				entity.isEnabled(),
				entity.isUseHttps(),
				entity.getQueryUsername(),
				entity.getQueryPassword(),
				entity.getApiKey(),
				entity.getCreatedBy().getUsername(),
				entity.getUpdatedBy().getUsername()
		);
	}


}
