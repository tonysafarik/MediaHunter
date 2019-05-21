import * as React from "react";
import * as Router from "react-router-dom";
import Home from "./main/content/Home";
import UITemplate from "./main/content/template/UITemplate";
import Login from "./main/content/Login";
import FindChannelForm from "./main/content/search/channel/FindChannelForm";
import Channel from "./main/content/Channel";
import {RequestState} from "./main/content/template/header/requests/Request";
import {AppContext, RequestStorage} from "./main/MediaHunterApi";
import FindMultimediumForm from "./main/content/search/multimedium/FindMultimediumForm";
import Multimedium from "./main/content/Multimedium";
import QueueList from "./main/content/QueueList";
import PrivateComponent from "./PrivateComponent";
import {RouteComponentProps} from "react-router-dom";

export interface RouteState {
    key: string;
    exact: boolean;
    path: string;
    render: (props: Router.RouteComponentProps<any>) => React.ReactNode;
}

interface Props {
}

class MediaHunterApp extends React.Component<Props, RequestStorage> {
    constructor(props: Props) {
        super(props);
        this.state = {
            requests: [],
            addRequest: (request: RequestState) => {
                let state = {...this.state};
                state.requests.push(request);
                this.setState(state);
            },
            markDone: (request: RequestState) => {
                let state = {...this.state};
                let newRequest = state.requests.find(oldRequest => request === oldRequest);
                if (newRequest != undefined) {
                    newRequest.done = true;
                }
                this.setState(state);
            }
        };
    }

    render() {
        return (
            <Router.BrowserRouter>
                <AppContext.Provider value={this.state}>
                    <Router.Switch>
                        <Router.Route exact path="/login" render={(props: RouteComponentProps) => <Login {...props}/>}/>
                        <Router.Route path="/" render={() => <PrivateComponent/>}/>
                    </Router.Switch>
                </AppContext.Provider>
            </Router.BrowserRouter>
        );
    }
}

export default MediaHunterApp;
