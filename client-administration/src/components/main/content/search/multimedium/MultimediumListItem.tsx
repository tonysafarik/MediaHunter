import * as React from 'react';
import multimediaThumbnail from '../../../../img/rectangle.jpg';
import StageBadge from '../../../fragment/StageBadge';
import {Link} from 'react-router-dom';
import "../../../../style/MultimediumListItem.css";
import {MultimediumDataObject} from "../../../data-object/MultimediumDataObject";

interface Props {
    multimedium: MultimediumDataObject;
    onClick: Function;
}

class MultimediumListItem extends React.Component<Props> {

    constructor(props: Props) {
        super(props);
    }

    render() {
        return (
            <div className={"MultimediumListItem" + this.notRegisteredClassName()}>
                <Link onClick={() => this.props.onClick()} to={this.getItemLinkPath()}>
                    <img className="thumbnail" src={multimediaThumbnail}/>
                    <div className="content">
                        <div className="names">
                            <div className="flex">
                                <span className="name">{this.props.multimedium.name}</span>
                                <span className="mcpName">{this.props.multimedium.mcpName}</span>
                            </div>
                        </div>
                        <StageBadge stage={this.props.multimedium.stage} />
                    </div>
                </Link>
            </div>
        );
    }

    notRegisteredClassName() {
        return this.props.multimedium.id != null ? "" : " not-registered";
    }

    getItemLinkPath() {
        return (this.props.multimedium.id != null
            ? "/multimedium/" + this.props.multimedium.id
            : "/search/multimedium/" + this.props.multimedium.externalId + "/register");
    }

}

export default MultimediumListItem;

