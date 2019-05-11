import * as React from 'react';
import "../../style/StageBadge.css";
import {Stage} from "../data-object/MultimediumDataObject";

interface Props {
    stage: Stage;
}

class StageBadge extends React.Component<Props> {
    render() {
        return (<div className={"StageBadge " + this.props.stage.toString().toLowerCase()}>{this.props.stage}</div>);
    }

}

export default StageBadge;