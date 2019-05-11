import * as React from 'react';
import {Route, RouteComponentProps, RouterChildContext} from "react-router";
import {MultimediumDataObject} from "../../../data-object/MultimediumDataObject";
import BackendApi from "../../../MediaHunterApi";
import MultimediumListItem from "./MultimediumListItem";
import MultimediumRegisterForm from "./register/MultimediumRegisterForm";


interface Props extends RouteComponentProps<{ eid: string }> {
}

interface State {
    multimedia: MultimediumDataObject[];
    selectedMultimedium?: MultimediumDataObject;
    eid: string;
}

class MultimediumList extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            multimedia: [],
            selectedMultimedium: undefined,
            eid: this.props.match.params.eid
        }
        this.multimediumShouldUpdate = this.multimediumShouldUpdate.bind(this);
    }

    componentDidMount(): void {
        this.getMultimedia(this.props.match.params.eid);
    }

    componentWillReceiveProps(nextProps: Readonly<Props>, nextContext: any): void {
        if (this.props.match.url !== nextProps.match.url) {
            let state = {...this.state};
            state.multimedia = [];
            this.setState(state);
            this.getMultimedia(nextProps.match.params.eid);
        }
    }

    async getMultimedia(id: string) {
        console.log(id);
        let response = await BackendApi.multimedium.getPreviewsByExternalId(id);
        let state = {...this.state};
        state.multimedia = response.data;
        console.log("response that i got", response);
        this.setState(state);
    }

    render() {
        if (this.state.multimedia.length < 1) {
            return <span>loading</span>;
        }
        return (
            <React.Fragment>
                <div>
                    {this.state.multimedia.map(multimedium => (
                        <MultimediumListItem key={multimedium.mcpName} multimedium={multimedium}
                                             onClick={() => this.onMultimediumClick(multimedium)}/>
                    ))}
                </div>
                <Route path="/search/multimedium/:eid/register" render={(props: RouteComponentProps) => (
                    <MultimediumRegisterForm multimedium={this.state.selectedMultimedium} url={this.props.match.url} multimediumShouldUpdate={this.multimediumShouldUpdate} />
                )}/>
            </React.Fragment>
        );
    }

    onMultimediumClick(multimedium: MultimediumDataObject) {
        let state = {...this.state};
        state.selectedMultimedium = multimedium;
        this.setState(state);
    }

    multimediumShouldUpdate(id: string, stage: string) {
        let state = {...this.state};
        let multimedium = state.multimedia.find(multimedium => multimedium === state.selectedMultimedium);
        if (multimedium !== undefined) {
            multimedium.id = id;
            multimedium.stage = stage;
        }
        this.setState(state);
        console.log(state);
    }

}

export default MultimediumList;