import * as React from "react";
import * as Router from "react-router-dom";
import "../../../style/Menu.css";

interface MenuProps {
    visibility: boolean;
    onMenuItemClick: Function;
}

class Menu extends React.Component<MenuProps> {
    state = {
        items: [
            {
                name: "Home",
                path: "/home"
            },
            {
                name: "Find Channel",
                path: "/search/channel"
            },
            {
                name: "Find Record",
                path: "/records"
            },
            {
                name: "Record Queue",
                path: "/records/queue"
            }
        ]
    };

    render() {
        let visible = "";
        if (this.props.visibility) visible = " visible";
        return (
            <div className={"Menu".concat(visible)}>
                <ul>
                    {this.state.items.map(item => (
                        <li key={item.name}>
                            <Router.Link
                                className="MenuLink"
                                to={item.path}
                                onClick={() => this.props.onMenuItemClick()}
                            >
                                {item.name}
                            </Router.Link>
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}

export default Menu;
