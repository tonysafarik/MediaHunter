import * as React from 'react';
import {MultimediumDataObject, Stage} from "../data-object/MultimediumDataObject";
import {RouteComponentProps} from "react-router";
import "../../style/Multimedium.css"
import BackendApi from "../MediaHunterApi";
import LoadingCircle from "../fragment/LoadingCircle";
import {Link} from "react-router-dom";

export interface Props extends RouteComponentProps<{ eid: string }> {
}

interface State {
    multimedium?: MultimediumDataObject;
}

class Multimedium extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            multimedium: undefined
        };
    }

    async componentDidMount() {
        let response = await BackendApi.multimedium.getById(this.props.match.params.eid);
        let state = {...this.state};
        state.multimedium = response.data;
        this.setState(state);
    }

    resolveStage(stage: Stage, nextStage: Stage) {
        let state = {...this.state};
        if (state.multimedium !== undefined) {

            if (stage === nextStage) {
                return;
            }

            if ((stage === Stage.WAITING && nextStage === Stage.ACCEPTED) || stage === Stage.REJECTED) {
                BackendApi.multimedium.accept(state.multimedium.id);
                state.multimedium.stage = Stage.ACCEPTED;
            } else if ((stage === Stage.WAITING && nextStage === Stage.REJECTED) || stage === Stage.ACCEPTED) {
                BackendApi.multimedium.reject(state.multimedium.id);
                state.multimedium.stage = Stage.REJECTED;
            }
            this.setState(state);
        }
    }

    renderStage(stage: Stage) {
        return (
            <div className="StageStatus">
                <span onClick={() => this.resolveStage(stage, Stage.ACCEPTED)}
                      className={"Stage ACCEPTED" + (stage === Stage.ACCEPTED ? " Selected" : "")}>&#10003; Accept</span>
                <span onClick={() => this.resolveStage(stage, Stage.REJECTED)}
                      className={"Stage REJECTED" + (stage === Stage.REJECTED ? " Selected" : "")}>&#10007; Reject</span>
            </div>);
    }

    render() {
        if (this.state.multimedium === undefined) {
            return (
                <div className="MultimediumBackground">
                    <LoadingCircle/>
                </div>);
        }
        const multimedium = this.state.multimedium;
        return (
            <div className="MultimediumBackground">
                <div className="Multimedium">
                    <img src={multimedium.thumbnail.highResolution} alt="Thumbnail"/>
                    {this.renderStage(multimedium.stage)}
                    <a href={multimedium.uri} target="_blank" rel="noreferrer nofollow">
                        <span className="ItemName">{multimedium.name}</span>
                    </a>
                    <span className="McpName">{multimedium.mcpName}</span>
                    <div className="EntityDetail">
                        <span>External ID: {multimedium.externalId}</span>
                        <span>Uploader external ID: <Link
                            to={"/search/channel/" + multimedium.uploaderExternalId}>{multimedium.uploaderExternalId}</Link>
                        </span>
                        <span>Upload time: {multimedium.uploadTime}</span>
                        <span>Description: {multimedium.description}</span>
                    </div>
                </div>
            </div>
        );
    }
}

export default Multimedium;