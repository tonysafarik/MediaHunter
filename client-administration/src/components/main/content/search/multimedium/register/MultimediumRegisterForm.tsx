import React from 'react';
import {MultimediumDataObject} from "../../../../data-object/MultimediumDataObject";
import {Redirect} from "react-router";
import {ChannelDataObject} from "../../../../data-object/ChannelDataObject";
import {Link} from "react-router-dom";
import {HamburgerMenu} from "../../../../fragment/HamburgerMenu";
import CheckBox from "../../../../fragment/CheckBox";
import BackendApi, {AppContext} from "../../../../MediaHunterApi";
import {RequestState} from "../../../template/header/requests/Request";

interface Props {
    multimedium?: MultimediumDataObject;
    multimediumShouldUpdate: Function;
    url: string;
}

interface State {
    redirect: boolean;
}

class MultimediumRegisterForm extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            redirect: false
        };
    }

    render() {
        return (this.props.multimedium === undefined ?
            <Redirect to={this.getURLBack()}/> : this.channelRegisterFormComponent(this.props.multimedium));
    }

    getURLBack() {
        return this.props.url;
    }


    channelRegisterFormComponent(multimedium: MultimediumDataObject) {
        const urlBack = this.getURLBack();
        if (this.state.redirect) {
            return <Redirect to={urlBack}/>;
        }
        return (
            <div className="overlay">
                <Link className="overlay" to={urlBack}/>
                <div className="ChannelRegisterForm">
                    <HamburgerMenu menuVisibility onMenuButtonClick={() => this.closeRegisterForm()}/>
                    <a className="ItemName" href={multimedium.uri} target="_blank" rel="noreferrer nofollow">
                        {multimedium.name}
                    </a>
                    <span className="McpName">{multimedium.mcpName}</span>
                    <div className="detail">
                        <AppContext.Consumer>
                            {ctx => {
                                return (
                                    <div className="register"
                                         onClick={() => this.registerEntity(ctx.addRequest, ctx.markDone)}>
                                        <span>Register MultiMedium</span>
                                    </div>
                                );
                            }}
                        </AppContext.Consumer>
                    </div>
                </div>
            </div>
        );
    }

    closeRegisterForm() {
        let state = {...this.state};
        state.redirect = !state.redirect;
        this.setState(state);
    }

    async registerEntity(addRequest: Function, markDone: Function) {
        if (window.confirm("Are you sure?")) {
            if (this.props.multimedium !== undefined) {
                const request: RequestState = {
                    httpMethod: "POST",
                    content: new Map([
                        ["Type", "Multimedium"],
                        ["EID", this.props.multimedium.externalId],
                        ["MCP", this.props.multimedium.mcpName]
                    ]),
                    done: false
                };
                addRequest(request);
                this.setState({redirect: true});
                let response = await BackendApi.multimedium.register(
                    this.props.multimedium.externalId,
                    this.props.multimedium.mcpName
                );
                console.log(response);
                markDone(request);
                this.props.multimediumShouldUpdate(response.data, "ACCEPTED");
            }
        }
    }

}

export default MultimediumRegisterForm;