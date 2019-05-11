import * as React from "react";
import * as Router from "react-router-dom";
import "../../../style/Menu.css";

interface Props {
    visibility: boolean;
    onMenuItemClick: Function;
}

interface State {
    items: MenuItem[];
}

class Menu extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
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
                    name: "Find MultiMedium",
                    path: "/search/multimedium"
                },
                {
                    name: "Record Queue",
                    path: "/queue"
                }
            ]
        }
    }

    render() {
        return (
            <div className={"Menu" + this.getMenuVisibleClassName()}>
                <ul>{this.state.items.map(item => (
                    <li key={item.name}>
                        <Router.Link className="MenuLink" to={item.path} onClick={() => this.props.onMenuItemClick()}>
                            {item.name}
                        </Router.Link>
                    </li>))}
                </ul>
            </div>
        );
    }

    getMenuVisibleClassName() {
        return this.props.visibility ? " visible" : "";
    }

}

export default Menu;

interface MenuItem {
    name: string;
    path: string;
}