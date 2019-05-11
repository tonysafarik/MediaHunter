import * as React from "react";
import "../../style/PageNotFound.css";

interface Props {
}

class PageNotFound extends React.Component<Props> {

    constructor(props: Props) {
        super(props);
    }

    render() {
        return (
            <div className="PageNotFound">
                Page not found.
                <br/>
                If you think you are on the right URL, please create a GH Issue.
            </div>
        );
    }
}

export default PageNotFound;
