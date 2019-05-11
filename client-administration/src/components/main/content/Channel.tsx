import * as React from "react";
import TrustedBadge from "../fragment/TrustedBadge";
import {Redirect, RouteComponentProps} from "react-router-dom";
import "../../style/Channel.css";
import {ChannelDataObject} from "../data-object/ChannelDataObject";
import BackendApi from "../MediaHunterApi";
import LoadingCircle from "../fragment/LoadingCircle";

interface Props extends RouteComponentProps<{ eid: string }> {
    info?: ChannelDataObject;
}

interface State {
    channel?: ChannelDataObject;
}

class Channel extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            channel: undefined
        }
    }

    async componentDidMount() {
        let response = await BackendApi.channel.getById(this.props.match.params.eid);
        let state = {...this.state};
        state.channel = response.data;
        this.setState(state);
    }

    render() {
        if (this.state.channel === undefined) {
            return (
                <div className="ChannelBackground">
                    <LoadingCircle/>
                </div>);
        }
        let channel = this.state.channel;
        return (
            <div className="ChannelBackground">
                <div className="Channel">
                    <a href={channel.uri} target="_blank" rel="noreferrer nofollow">
                        <span className="ItemName">{channel.name}</span>
                    </a>
                    <span className="McpName">{channel.mcpName}</span>
                    <div className="EntityDetail">
                        <span>External ID: {channel.externalId}</span>
                        <span>Accepted Record Count: {channel.acceptedRecordCount}/{channel.recordCount}</span>
                        <span>Last record upload time: {channel.lastRecordUpload}</span>
                    </div>
                    {channel.trusted? <TrustedBadge /> : <></>}
                </div>
            </div>
        );
    }
}

export default Channel;
