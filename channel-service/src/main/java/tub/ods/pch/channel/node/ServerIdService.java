package tub.ods.pch.channel.node;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServerIdService {
    private static final Logger log = LoggerFactory.getLogger(ServerIdService.class);
    
    private UUID runId = UUID.randomUUID();

    public ServerIdService() {
        log.info("Server run id: {}", runId);
    }

    public UUID getRunId() {
        return runId;
    }
}
