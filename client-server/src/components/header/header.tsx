import * as React from 'react';
import './header.css'



interface HeaderProps {
    onMenuButtonClick: Function,
    menuVisibility: boolean
}

class Header extends React.Component<HeaderProps> {
    state = {
        className: 'Header'
    };
    render() {
        return (
            <div className={this.state.className}>
                <HamburgerMenu
                    onMenuButtonClick={() => this.props.onMenuButtonClick()}
                    menuVisibility={this.props.menuVisibility}
                />
                <span className="logo"><b>Media</b>Hunter</span>
            </div>
        );
    }
}

export default Header;

class HamburgerMenu extends React.Component<HeaderProps> {
    render() {
        let toggled = "";
        if (this.props.menuVisibility) toggled = " toggled";
        return (
            <React.Fragment>
                <div className={"HamburgerMenu".concat(toggled)} onClick={() => this.props.onMenuButtonClick()}>
                    <div className="bar"></div>
                    <div className="bar"></div>
                    <div className="bar"></div>
                </div>
            </React.Fragment>
        );
    }
}