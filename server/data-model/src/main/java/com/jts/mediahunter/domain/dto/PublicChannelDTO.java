package com.jts.mediahunter.domain.dto;

import lombok.Data;
import java.net.URI;

@Data
public class PublicChannelDTO {
    private String id;
    private String name;
    private String mcpName;
    private URI uri;
}
