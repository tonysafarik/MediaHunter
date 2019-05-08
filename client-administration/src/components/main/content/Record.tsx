import * as React from 'react';
import './style/Record.css';
import StageBadge from '../fragment/StageBadge';

export interface Props {

}

class Record extends React.Component<Props> {
    render() {
        return (
            // TODO: add thumbnail (if there is one).
            // TODO: make name link open in new window so this app stays opened.
            <div className="Record">
                <a href="" target="_blank" rel="noreferrer nofollow"><span className="ItemName">Record name</span></a>
                <span className="McpName">Name of MCP</span>
                <div className="EntityDetail">
                    <span>External ID: somesample</span>
                    <span>Uploader external ID: </span>
                    <span>Upload time: </span>
                    <span>Description: </span>
                </div>
                <StageBadge />
            </div>
        );
    }
}

export default Record;