import * as React from "react";
import Request, {RequestState} from "./Request";
import {AppContext} from "../../../../MediaHunterApi";
import "../../../../../style/RequestList.css";

export interface RequestListProps {
    visible: boolean;
}

export interface RequestListState {
    requests: RequestState[];
}

class RequestList extends React.Component<RequestListProps, RequestListState> {
    constructor(props: RequestListProps) {
        super(props);
        this.state = {
            requests: []
        };
    }

    render() {
        return (
            <div className={"RequestList" + (this.props.visible ? " visible" : "")}>
                <AppContext.Consumer>
                    {(appState) => {
                        if (appState.requests.length < 1) {
                            return (
                                <span>Nothing to see here. Move on.</span>
                            );
                        }
                        return appState.requests.map(request => {
                            let key = Array.from(request.content.keys()).find((key) => key == "EID");
                            if (key === undefined) {
                                key = request.content.get(Array.from(request.content.keys())[1]);
                            } else {
                                key = request.content.get(key);
                            }
                            return (
                                <Request
                                    requestState={request}
                                    key={key}/>);
                        });
                    }}
                </AppContext.Consumer>
            </div>
        );
    }
}

export default RequestList;
