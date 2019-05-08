import * as React from 'react';
import YtThumbnail from '../../../../img/youtube_social_square_red.png';
import StageBadge from '../../../fragment/StageBadge';
import { Link } from 'react-router-dom';

interface RecordProps {

}

class RecordListItem extends React.Component<RecordProps> {
    state = {}
    render() {
        return (
            <div className="RecordListItem">
                <Link to="/Record">
                    <img className="thumbnail" src={YtThumbnail} />
                    <div className="content">
                        <span className="name">Name of Record</span>
                        <span className="mcpName">Name of MCP</span>
                        <span className="description">Description: something soooo long it won't even fit on</span>
                        <StageBadge />
                    </div>
                </Link>
            </div>
        );
    }
}

export default RecordListItem;

