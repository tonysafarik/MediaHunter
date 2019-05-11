export interface ChannelDataObject {
    id?: string;
    externalId: string;
    name: string;
    mcpName: string;
    uri: string;
    recordCount?: Number;
    acceptedRecordCount?: Number;
    lastRecordUpload?: Date;
    trusted?: boolean;
}