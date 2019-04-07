import * as React from 'react';
import YTLogo from '../img/youtube_social_circle_red.png';

interface ChannelProps {

}

interface ChannelState {

}

class Channel extends React.Component<ChannelProps, ChannelState> {
    state = {}
    render() {
        return (
            <div className="Channel">
                <img className="logo" src={YTLogo} />
                <div className="content">
                    <span className="name">Name of Channel</span>
                    <TrustedBadge />
                </div>
            </div>
        );
    }
}

export default Channel;

class TrustedBadge extends React.Component {
    render(){
        return (
            <div className="TrustedBadge">Trusted</div>
        );
    }
}