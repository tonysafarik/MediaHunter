import * as React from "react";
import TrustedBadge from "../fragment/TrustedBadge";
import {RouteComponentProps} from "react-router-dom";
import "../../style/Channel.css";
import {ChannelDataObject} from "../data-object/ChannelDataObject";
import BackendApi from "../MediaHunterApi";
import LoadingCircle from "../fragment/LoadingCircle";
import SettingsButton from "../fragment/SettingsButton";

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
        this.channelShouldUpdate = this.channelShouldUpdate.bind(this);
        this.channelShouldDelete = this.channelShouldDelete.bind(this);
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
                    <SettingsButton>
                        <div className={"SettingsDialog"}>
                            <div onClick={this.channelShouldUpdate}>Update Channel</div>
                            <div onClick={this.channelShouldDelete}>Delete Channel</div>
                        </div>
                    </SettingsButton>
                    <div className="EntityDetail">
                        <span>External ID: {channel.externalId}</span>
                        <span>Accepted Record Count: {channel.acceptedMultimediumCount}/{channel.multimediumCount}</span>
                        <span>Last record upload time: {channel.lastMultimediumUpload}</span>
                    </div>
                    {channel.trusted? <TrustedBadge /> : <></>}
                </div>
            </div>
        );
    }

    channelShouldDelete() {
        if (this.state.channel !== undefined && this.state.channel.id !== undefined) {
            BackendApi.channel.delete(this.state.channel.id).then(() => {
                this.props.history.push("/");
            }).catch(() => {
                console.log("error");
            });
        }
    }

    channelShouldUpdate() {
        if (this.state.channel !== undefined && this.state.channel.id !== undefined) {
            BackendApi.channel.update(this.state.channel.id).then((promise) => {
                let state = {...this.state};
                state.channel = promise.data;
                this.setState(state);
            }).catch(() => {
                console.log("error");
            });
        }

    }

}

export default Channel;
