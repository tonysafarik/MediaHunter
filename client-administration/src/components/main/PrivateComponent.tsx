import React from 'react';
import Home from "./content/Home";
import * as Router from "react-router";
import FindChannelForm from "./content/search/channel/FindChannelForm";
import FindMultimediumForm from "./content/search/multimedium/FindMultimediumForm";
import QueueList from "./content/QueueList";
import Login from "./content/Login";
import Channel from "./content/Channel";
import Multimedium from "./content/Multimedium";
import {RouteState} from "../App";
import {Redirect} from "react-router";
import UITemplate from "./content/template/UITemplate";
import {AppContext} from "./MediaHunterApi";

interface Props {
}

interface State {
    routes: RouteState[];
}

class PrivateComponent extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            routes: [
                {
                    key: "Home",
                    exact: true,
                    path: "/(|home)",
                    render: () => <Home/>
                },
                {
                    key: "Channel Search",
                    exact: false,
                    path: "/search/channel",
                    render: (props: Router.RouteComponentProps) => (
                        <FindChannelForm {...props} />
                    )
                },
                {
                    key: "MultiMedium Search",
                    exact: false,
                    path: "/search/multimedium",
                    render: (props: Router.RouteComponentProps) => <FindMultimediumForm {...props} />
                },
                {
                    key: "Record Queue",
                    exact: true,
                    path: "/queue",
                    render: (props: Router.RouteComponentProps<any>) => <QueueList {...props}/>
                },
                {
                    key: "Channel",
                    exact: false,
                    path: "/channel/:eid",
                    render: (props: Router.RouteComponentProps<any>) => <Channel {...props} />
                },
                {
                    key: "Multimedium",
                    exact: false,
                    path: "/multimedium/:eid",
                    render: (props: Router.RouteComponentProps<any>) => <Multimedium {...props} />
                }
            ]
        };
    }

    render() {
        if (localStorage.getItem("token") === null) {
            return (<Redirect to="/login"/>)
        }
        return (
            <React.Fragment>
                <UITemplate>
                    <Router.Switch>
                        {this.state.routes.map(route => (
                            <Router.Route {...route} />
                        ))}
                    </Router.Switch>
                </UITemplate>
            </React.Fragment>
        );
    }
}

export default PrivateComponent;