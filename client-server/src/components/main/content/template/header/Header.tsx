import * as React from 'react';
import { HamburgerMenu } from '../../../fragment/HamburgerMenu';
import Logo from '../../../fragment/Logo';
import ActiveRequests from './requests/ActiveRequests';
import "../../../../style/Header.css";



interface Props {
    onMenuButtonClick: Function,
    menuVisibility: boolean
}

class Header extends React.Component<Props> {
    render() {
        return (
            <div className="Header">
                <HamburgerMenu
                    onMenuButtonClick={() => this.props.onMenuButtonClick()}
                    menuVisibility={this.props.menuVisibility}
                />
                <ActiveRequests />
                <Logo />
                
            </div>
        );
    }
}

export default Header;

