import * as React from "react";
import { Route, RouteComponentProps } from "react-router-dom";
import BackendApi from "../../../MediaHunterApi";
import ChannelRegisterForm from "./register/ChannelRegisterForm";
import ChannelListItem, { FindChannelDTO } from "./ChannelListItem";

interface Props extends RouteComponentProps<{ eid: string }> {}

interface State {
  channels: FindChannelDTO[];
  selectedChannel?: FindChannelDTO;
  eid: string;
}

class ChannelList extends React.Component<Props, State> {
  public constructor(props: Props) {
    super(props);
    this.state = {
      channels: [],
      selectedChannel: undefined,
      eid: this.props.match.params.eid
    };
  }

  async componentDidMount() {
    let id = this.props.match.params.eid;
    console.log(id);
    let promise = await BackendApi.channel.getChannelPreviewByExternalId(id);
    let state = { ...this.state };
    state.channels = promise.data;
    this.setState(state);
    console.log(this.state.channels[0]);
  }

  render() {
    if (this.state.channels.length < 1) {
      return <span>Loading</span>;
    } else {
      return (
        <React.Fragment>
          <div className="ChannelList">
            {this.state.channels.map(channel => (
              <ChannelListItem
                key={channel.mcpName}
                channelInfo={channel}
                onClick={() => this.onChannelListItemClick(channel)}
              />
            ))}
          </div>
          <Route
            path={"/search/channel/:eid/register"}
            render={(props: RouteComponentProps) => (
              <ChannelRegisterForm channel={this.state.selectedChannel} />
            )}
          />
        </React.Fragment>
      );
    }
  }

  onChannelListItemClick(channel: FindChannelDTO) {
    BackendApi.channel.getChannelByExternalId("");
    let state = {...this.state};
    state.selectedChannel = channel;
    this.setState(state);
  }
}

export default ChannelList;
