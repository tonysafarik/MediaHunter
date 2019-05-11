import * as React from 'react';
import "../../style/HamburgerMenu.css";

interface Props {
    onMenuButtonClick: Function,
    menuVisibility: boolean
}

export class HamburgerMenu extends React.Component<Props> {
    render() {
        let toggled = "";
        if (this.props.menuVisibility)
            toggled = " toggled";
        return (
            <div className={"HamburgerMenu".concat(toggled)} onClick={() => this.props.onMenuButtonClick()}>
                <div className="bar" />
                <div className="bar" />
                <div className="bar" />
            </div>);
    }
}
