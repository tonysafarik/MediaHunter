import * as React from 'react';
import Header from './header/header';
import Menu from './menu/menu';
import ChannelList from './channel/ChannelList';

class MediaHunterApp extends React.Component {
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
                />
                <ChannelList />
            </React.Fragment>
        );
    }

    handleMenuButtonClick() {
        const menuVisibility = !this.state.menuVisibility;
        this.setState({ menuVisibility });
    }

}

export default MediaHunterApp;