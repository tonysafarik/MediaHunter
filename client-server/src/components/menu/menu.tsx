import * as React from 'react';
import './menu.css';

interface MenuProps {
    visibility: boolean;
}

class Menu extends React.Component<MenuProps> {
    render() {
        let visible = "";
        if (this.props.visibility) visible = " visible";
        return (
            <div className={"Menu".concat(visible)}>
                <ul>
                    <li><a>Home</a></li>
                    <li><a>Find Channel</a></li>
                    <li><a>Find Record</a></li>
                    <li><a>Record Queue</a></li>
                </ul>
            </div>
        );
    }
}

export default Menu;
