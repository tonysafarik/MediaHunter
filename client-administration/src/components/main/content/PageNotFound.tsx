import * as React from "react";
import "./style/PageNotFound.css";
import UITemplate from "./template/UITemplate";

interface Props {}

class PageNotFound extends React.Component<Props> {
  render() {
    return (
        <div className="PageNotFound">
          Page not found.
          <br />
          If you think you are on the right URL, please create a GH Issue.
        </div>
    );
  }
}

export default PageNotFound;
