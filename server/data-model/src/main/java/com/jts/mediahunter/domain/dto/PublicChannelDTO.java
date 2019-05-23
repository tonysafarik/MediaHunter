package com.jts.mediahunter.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.net.URI;
import java.time.LocalDateTime;

@Data
public class PublicChannelDTO {
    private String id;
    private String name;
    private String mcpName;
    private URI uri;
}
