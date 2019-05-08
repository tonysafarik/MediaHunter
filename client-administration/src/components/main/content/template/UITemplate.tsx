import * as React from 'react';
import Header from './header/Header';
import Menu from './Menu';

interface Props {

}

class UITemplate extends React.Component<Props> {
    state = {
        menuVisibility: false
    }
    render() {
        return (
            <React.Fragment>
                <Header
                    onMenuButtonClick={() => this.handleMenuButtonClick()}
                    menuVisibility={this.state.menuVisibility}
                />
                <Menu
                    visibility={this.state.menuVisibility}
                    onMenuItemClick={() => this.handleMenuButtonClick()}
                />
                {this.props.children}
            </React.Fragment>
        );
    }

    handleMenuButtonClick() {
        const menuVisibility = !this.state.menuVisibility;
        this.setState({ menuVisibility });
    }
}

export default UITemplate;