import * as React from "react";
import {Link} from "react-router-dom";
import "../../style/ButtonLink.css";

interface Props {
    to: string;
    onClick: Function;
}

class ButtonLink extends React.Component<Props> {
    render() {
        return (
            <Link onClick={() => this.props.onClick()} className="ButtonLink" to={this.props.to}>
                <span>{this.props.children}</span>
            </Link>
        );
    }
}

export default ButtonLink;
