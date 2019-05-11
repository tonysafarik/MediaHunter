import * as React from "react";
import {Link, Redirect} from "react-router-dom";
import {HamburgerMenu} from "../../../../fragment/HamburgerMenu";
import CheckBox from "../../../../fragment/CheckBox";
import BackendApi, {AppContext} from "../../../../MediaHunterApi";
import {RequestState} from "../../../template/header/requests/Request";
import "../../../../../style/ChannelRegisterForm.css";
import {ChannelDataObject} from "../../../../data-object/ChannelDataObject";

interface Props {
    channel?: ChannelDataObject;
    channelShouldUpdate: Function;
    url: string;
}

interface State {
    redirect: boolean;
}


class ChannelRegisterForm extends React.Component<Props, State> {

    trustInput: React.RefObject<HTMLInputElement>;

    constructor(props: Props) {
        super(props);
        this.state = {
            redirect: false
        };
        this.trustInput = React.createRef<HTMLInputElement>();
        this.channelRegisterFormComponent = this.channelRegisterFormComponent.bind(this);
    }

    render() {
        return (this.props.channel === undefined ?
            <Redirect to={this.getURLBack()}/> : this.channelRegisterFormComponent(this.props.channel));
    }

    getURLBack() {
        return this.props.url;
    }

    channelRegisterFormComponent(channel: ChannelDataObject) {
        const urlBack = this.getURLBack();
        if (this.state.redirect) {
            return <Redirect to={urlBack}/>;
        }
        return (
            <div className="overlay">
                <Link className="overlay" to={urlBack}/>
                <div className="ChannelRegisterForm">
                    <HamburgerMenu menuVisibility onMenuButtonClick={() => this.closeChannelRegisterForm()}/>
                    <a className="ItemName" href="#" target="_blank" rel="noreferrer nofollow">
                        {channel.name}
                    </a>
                    <span className="McpName">{channel.mcpName}</span>
                    <div className="detail">
                        <div className="trust">
                            <span>Trustworthy?</span>
                            <CheckBox reference={this.trustInput}/>
                        </div>
                        <AppContext.Consumer>
                            {ctx => {
                                return (
                                    <div className="register"
                                         onClick={() => this.registerChannel(ctx.addRequest, ctx.markDone)}>
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

    async registerChannel(addRequest: (request: RequestState) => void, markDone: (request: RequestState) => void) {
        if (window.confirm("Are you sure?")) {
            if (this.props.channel != undefined && this.trustInput.current != null) {
                const trusted = this.trustInput.current.checked;
                const request: RequestState = {
                    httpMethod: "POST",
                    content: new Map([
                        ["Type", "Channel"],
                        ["EID", this.props.channel.externalId],
                        ["MCP", this.props.channel.mcpName]
                    ]),
                    done: false
                };
                addRequest(request);
                this.setState({redirect: true});
                let response = await BackendApi.channel.register(
                    this.props.channel.externalId,
                    this.props.channel.mcpName,
                    trusted
                );
                console.log(response);
                markDone(request);
                this.props.channelShouldUpdate(response.data, trusted);
            }
        }
    }

    closeChannelRegisterForm() {
        const redirect = !this.state.redirect;
        this.setState({redirect});
    }
}

export default ChannelRegisterForm;
