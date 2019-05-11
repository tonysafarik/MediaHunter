import * as React from "react";
import YTLogo from "../../../../img/youtube_social_circle_red.png";
import TrustedBadge from "../../../fragment/TrustedBadge";
import {Link} from "react-router-dom";
import "../../../../style/ChannelListItem.css";
import {ChannelDataObject} from "../../../data-object/ChannelDataObject";

interface Props {
    channelInfo: ChannelDataObject;
    onClick: Function;
}

class ChannelListItem extends React.Component<Props> {

    constructor(props: Props) {
        super(props);
    }

    render() {
        return (
            <div className={"ChannelListItem" + this.notRegisteredClassName()}>
                <Link onClick={() => this.props.onClick()} to={this.getItemLinkPath()}>
                    <img className="logo" src={YTLogo}/>
                    <div className="content">
                        <span className="name">{this.props.channelInfo.name}</span>
                        {this.props.channelInfo.trusted ? <TrustedBadge/> : null}
                    </div>
                </Link>
            </div>
        );
    }

    notRegisteredClassName() {
        return this.props.channelInfo.id != null ? "" : " not-registered";
    }

    getItemLinkPath() {
        return (this.props.channelInfo.id != null
            ? "/channel/" + this.props.channelInfo.id
            : "/search/channel/" + this.props.channelInfo.externalId + "/register");
    }

}

export default ChannelListItem;
