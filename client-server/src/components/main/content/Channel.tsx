import * as React from "react";
import TrustedBadge from "../fragment/TrustedBadge";
import { Redirect } from "react-router-dom";

interface Props {
  info?: ChannelInfoDTO;
}

interface State {
  redirect: boolean;
}

export interface ChannelInfoDTO {
  id?: string;
  externalId: string;
  mcpName: string;
  name: string;
  uri: string;
  recordCount?: BigInt;
  acceptedRecordCount?: BigInt;
  lastRecordUpload?: Date;
  trusted?: boolean;
}

class Channel extends React.Component<Props, State> {
  state = {
    redirect: false
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to="/channels/search" />;
    }
    return (
        <div className="Channel">
          <a href="#" target="_blank" rel="noreferrer nofollow">
            <span className="ItemName">Channel name</span>
          </a>
          <span className="McpName">Name of MCP</span>
          <div className="EntityDetail">
            <span>External ID: somesample</span>
            <span>Accepted Record Count: x/X </span>
            <span>Last record upload time: </span>
          </div>
          <TrustedBadge />
        </div>
    );
  }

  closeChannelInfo() {
    const redirect: boolean = true;
    this.setState({ redirect });
  }
}

export default Channel;
