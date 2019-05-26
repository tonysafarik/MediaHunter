export interface ChannelDataObject {
    id?: string;
    externalId: string;
    name: string;
    mcpName: string;
    uri: string;
    multimediumCount?: Number;
    acceptedMultimediumCount?: Number;
    lastMultimediumUpload?: Date;
    trusted?: boolean;
}