import * as React from "react";
import {RequestState} from "./Request";
import RequestList from "./RequestList";
import {AppContext} from "../../../../MediaHunterApi";
import "../../../../../style/ActiveRequests.css";

interface State {
    activeRequest: boolean;
    visible: boolean;
}

class ActiveRequests extends React.Component<{}, State> {
    constructor(props: React.Props<any>) {
        super(props);

        this.state = {
            activeRequest: false,
            visible: false
        };

        this.onDropdownButtonClick = this.onDropdownButtonClick.bind(this);
    }

    render() {
        return (
            <React.Fragment>
                {this.state.visible? <div onClick={this.onDropdownButtonClick} className="overlay"/> : <></>}
                <div className="ActiveRequests">
                    <AppContext.Consumer>
                        {ctx => {
                            return (
                                <div
                                    onClick={() => this.onDropdownButtonClick()}
                                    className={
                                        "DropdownButton" +
                                        (ctx.requests.filter((element: RequestState) => {
                                            return !element.done;
                                        }).length > 0
                                            ? " active"
                                            : "")
                                    }
                                >
                                    &#9660;
                                </div>
                            );
                        }}
                    </AppContext.Consumer>
                    <RequestList visible={this.state.visible}/>
                </div>
            </React.Fragment>
        );
    }

    onDropdownButtonClick() {
        let state = {...this.state};
        state.visible = !state.visible;
        this.setState(state);
    }
}

export default ActiveRequests;
