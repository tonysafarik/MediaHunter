import * as React from 'react';
import Channel from './Channel';
import './channel.css';
interface ChannelListProps {

}

interface ChannelListState {

}

class ChannelList extends React.Component<ChannelListProps, ChannelListState> {
    state = {}
    render() {
        return (
            <div className="ChannelList">
                <Channel />
                <Channel />
                <Channel />
            </div>
        );
    }
}

export default ChannelList;