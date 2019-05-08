import * as React from "react";
import YTLogo from "../../../../img/youtube_social_circle_red.png";
import TrustedBadge from "../../../fragment/TrustedBadge";
import {Link} from "react-router-dom";
import "../../../../style/ChannelListItem.css";

interface Props {
    channelInfo: FindChannelDTO;
    onClick: Function;
    key: string;
}

export interface FindChannelDTO {
    externalId: string;
    id?: string;
    mcpName: string;
    name: string;
    trusted: boolean;
    uri: string;
}

class ChannelListItem extends React.Component<Props> {

    public constructor(props: Props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                <div className={"ChannelListItem" + this.notRegisteredClassName()}>
                    <Link
                        onClick={() => this.props.onClick()}
                        to={
                            this.props.channelInfo.id != null
                                ? "/channel/" + this.props.channelInfo.id
                                : "/search/channel/" +
                                this.props.channelInfo.externalId +
                                "/register"
                        }
                    >
                        <img className="logo" src={YTLogo}/>
                        <div className="content">
                            <span className="name">{this.props.channelInfo.name}</span>
                            {this.props.channelInfo.trusted ? <TrustedBadge/> : null}
                        </div>
                    </Link>
                </div>
            </React.Fragment>
        );
    }

    private notRegisteredClassName() {
        return this.props.channelInfo.id != null? "" : " not-registered";
    }

}

export default ChannelListItem;
