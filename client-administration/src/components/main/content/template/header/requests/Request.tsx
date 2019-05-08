import * as React from "react";
import "../../../../../style/Request.css";

interface Props {
    requestState: RequestState;
}


export interface RequestState {
    httpMethod: string;
    content: Map<string, string>;
    done: boolean;
}

class Request extends React.Component<Props> {
  render() {
    return (
      <div className="Request">
        <div className="content">
          <span>
            <b>Request {this.props.requestState.httpMethod}:</b>
          </span>

          {Array.from(this.props.requestState.content.keys()).map((key: string) => (
            <span key={key}>
              <b>{key}: </b>
              {this.props.requestState.content.get(key)}
            </span>
          ))}
        </div>
        {this.props.requestState.done? <div className="done"> &#9989; </div> : <div className="loading" />}
      </div>
    );
  }
}

export default Request;
