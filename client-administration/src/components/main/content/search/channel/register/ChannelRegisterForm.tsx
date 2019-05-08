import * as React from "react";
import { Link, Redirect } from "react-router-dom";
import { HamburgerMenu } from "../../../../fragment/HamburgerMenu";
import CheckBox from "../../../../fragment/CheckBox";
import BackendApi, { AppContext } from "../../../../MediaHunterApi";
import { RequestState } from "../../../template/header/requests/Request";
import { FindChannelDTO } from "../ChannelListItem";
import "../../../../../style/ChannelRegisterForm.css";

interface Props {
  channel?: FindChannelDTO;
}

class ChannelRegisterForm extends React.Component<Props> {
  state = {
    redirect: false
  };
  trustInput = React.createRef<HTMLInputElement>();
  render() {
    if (this.props.channel === undefined) {
      return <div>Loading</div>;
    } else {
      const urlBack =
        "/search/channel" +
        (this.props.channel != null ? "/" + this.props.channel.externalId : "");
      if (this.state.redirect) {
        return <Redirect to={urlBack} />;
      }
      return (
        <div className="overlay">
          <Link className="overlay" to={urlBack} />
          <div className="ChannelRegisterForm">
            <HamburgerMenu
              menuVisibility
              onMenuButtonClick={() => this.closeChannelRegisterForm()}
            />
            <a href="#" target="_blank" rel="noreferrer nofollow">
              <span className="ItemName">
                {this.props.channel != null ? this.props.channel.name : ""}
              </span>
            </a>
            <span className="McpName">
              {this.props.channel != null ? this.props.channel.mcpName : ""}
            </span>
            <div className="detail">
              <div className="trust">
                <span>Trustworthy?</span>
                <CheckBox reference={this.trustInput} />
              </div>
              <AppContext.Consumer>
                {ctx => {
                  return (
                    <div
                      className="register"
                      onClick={() => this.registerChannel(ctx.addRequest, ctx.markDone)}
                    >
                      <span>Register Channel</span>
                    </div>
                  );
                }}
              </AppContext.Consumer>
            </div>
          </div>
        </div>
      );
    }
  }

  async registerChannel(
    addRequest: (request: RequestState) => void,
    markDone: (request: RequestState) => void
  ) {
    if (confirm("Are you sure?")) {
      if (this.props.channel != null) {
        const request: RequestState = {
          httpMethod: "POST",
          content: new Map([
            ["EID", this.props.channel.externalId],
            ["MCP", this.props.channel.mcpName]
          ]),
          done: false
        };
        addRequest(request);
        this.setState({ redirect: true });
        let response = await BackendApi.channel.registerChannel(
          this.props.channel.externalId,
          this.props.channel.mcpName,
          this.trustInput.current != null
            ? this.trustInput.current.checked
            : false
        );
        console.log(response);
        markDone(request);
      }
    }
  }

  closeChannelRegisterForm() {
    const redirect = !this.state.redirect;
    this.setState({ redirect });
  }
}

export default ChannelRegisterForm;
