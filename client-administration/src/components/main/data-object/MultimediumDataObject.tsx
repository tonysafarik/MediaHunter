interface Thumbnail {
    lowResolution?: string;
    highResolution?: string;
}

export interface MultimediumDataObject {
    id: string;
    externalId: string;
    uploaderExternalId: string;
    name: string;
    mcpName: string;
    uri: string;
    thumbnail: Thumbnail;
    uploadTime: Date;
    description: string;
    stage: Stage;
}

export class Stage {
    static UNKNOWN = "UNKNOWN";
    static ACCEPTED = "ACCEPTED";
    static REJECTED = "REJECTED";
    static WAITING = "WAITING";
}