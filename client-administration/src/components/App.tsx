import * as React from "react";
import * as Router from "react-router-dom";
import Home from "./main/content/Home";
import UITemplate from "./main/content/template/UITemplate";
import FindForm from "./main/content/search/FindForm";
import RecordList from "./main/content/search/record/RecordList";
import Login from "./main/content/Login";
import PageNotFound from "./main/content/PageNotFound";
import FindChannelForm from "./main/content/search/channel/FindChannelForm";
import ChannelList from "./main/content/search/channel/ChannelList";
import Channel from "./main/content/Channel";
import { RequestState } from "./main/content/template/header/requests/Request";
import {
  AppContext,
  defaultRequestStorage,
  RequestStorage
} from "./main/MediaHunterApi";

export interface RouteState {
  key: string;
  exact: boolean;
  path: string;
  render: (props: Router.RouteComponentProps) => React.ReactNode;
}

interface Props {}

interface State {
  routes: RouteState[];
}

class MediaHunterApp extends React.Component<Props, RequestStorage> {
  public constructor(props: Props) {
    super(props);
    this.state = {
      requests: [],
      addRequest: (request: RequestState) => {
        let state = { ...this.state };
        state.requests.push(request);
        this.setState(state);
      },
      markDone: (request: RequestState) => {
        let state = {...this.state};
        let newRequest = state.requests.find(oldRequest => request === oldRequest);
        if(newRequest != undefined) {
          newRequest.done = true;
        }
        this.setState(state);
      },
      routes: [
        {
          key: "Home",
          exact: true,
          path: "/(|home)",
          render: () => <Home />
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
          key: "Record Search",
          exact: true,
          path: "/records",
          render: (props: Router.RouteComponentProps) => <FindForm {...props} />
        },
        {
          key: "Record Queue",
          exact: true,
          path: "/records/queue",
          render: () => <RecordList />
        },
        {
          key: "Login",
          exact: true,
          path: "/login",
          render: () => <Login />
        },
        {
          key: "Channel",
          exact: false,
          path: "/channel",
          render: () => <Channel />
        }
      ]
    };
  }

  render() {
    return (
      <Router.BrowserRouter>
        <AppContext.Provider value={this.state}>
          <UITemplate>
            <Router.Switch>
              {this.state.routes.map(route => (
                <Router.Route {...route} />
              ))}
            </Router.Switch>
          </UITemplate>
        </AppContext.Provider>
      </Router.BrowserRouter>
    );
  }
}

export default MediaHunterApp;
