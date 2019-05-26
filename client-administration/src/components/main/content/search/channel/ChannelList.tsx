import * as React from "react";
import {Route, RouteComponentProps} from "react-router-dom";
import BackendApi from "../../../MediaHunterApi";
import ChannelRegisterForm from "./register/ChannelRegisterForm";
import ChannelListItem from "./ChannelListItem";
import {ChannelDataObject} from "../../../data-object/ChannelDataObject";

interface Props extends RouteComponentProps<{ eid: string }> {
}

interface State {
    channels: ChannelDataObject[];
    selectedChannel?: ChannelDataObject;
    eid: string;
    empty: boolean;
    loading: boolean;
}

class ChannelList extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            channels: [],
            selectedChannel: undefined,
            eid: this.props.match.params.eid,
            empty: false,
            loading: true
        };
        this.channelShouldUpdate = this.channelShouldUpdate.bind(this);
    }

    componentDidMount() {
        this.getChannels(this.props.match.params.eid);
    }

    async getChannels(id: string) {
        let state = {...this.state};
        state.loading = true;
        this.setState(state);
        BackendApi.channel.getPreviewsByExternalId(id).then((promise) => {
            state.channels = promise.data;
            if (state.channels.length == 0) {
                state.empty = true;
            } else {
                state.empty = false;
            }
            state.loading = false;
            this.setState(state);
        });

    }

    componentWillReceiveProps(nextProps: Readonly<Props>, nextContext: any): void {
        if (this.props.match.url !== nextProps.match.url) {
            let state = {...this.state};
            state.channels = [];
            this.setState(state);
            this.getChannels(nextProps.match.params.eid);
        }
    }

    render() {
        if (this.state.loading) {
            return <span>Loading</span>;
        } else if (this.state.empty) {
            return <span>Can't find channel with this ID</span>;
        } else {
            return (
                <React.Fragment>
                    <div>
                        {this.state.channels.map(channel => (
                            <ChannelListItem key={channel.mcpName} channelInfo={channel}
                                             onClick={() => this.onChannelListItemClick(channel)}
                            />
                        ))}
                    </div>
                    <Route
                        path={"/search/channel/:eid/register"}
                        render={(props: RouteComponentProps) => (
                            <ChannelRegisterForm channelShouldUpdate={this.channelShouldUpdate}
                                                 channel={this.state.selectedChannel} url={this.props.match.url}/>
                        )}
                    />
                </React.Fragment>
            );
        }
    }

    channelShouldUpdate(id: string, trusted: boolean) {
        let state = {...this.state};
        let channel = state.channels.find(channel => channel === state.selectedChannel);
        if (channel !== undefined) {
            channel.id = id;
            channel.trusted = trusted;
            console.log("channel disable should be false", channel)
        }
        this.setState(state);
        console.log(state);
    }

    onChannelListItemClick(channel: ChannelDataObject) {
        let state = {...this.state};
        state.selectedChannel = channel;
        this.setState(state);
    }
}

export default ChannelList;
