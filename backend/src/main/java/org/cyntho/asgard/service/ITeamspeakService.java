package org.cyntho.asgard.service;

import org.cyntho.asgard.dto.TsDto;

import java.util.List;

public interface ITeamspeakService {

	TsDto.TsServerConnectionDto getServerConnection(long id);

	List<TsDto.TsServerConnectionDto> getAllConnections();
}
