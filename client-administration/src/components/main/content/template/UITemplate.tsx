import * as React from 'react';
import Header from './header/Header';
import Menu from './Menu';

interface Props {
}

interface State {
    menuVisibility: boolean;
}

class UITemplate extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            menuVisibility: false
        };
        this.handleMenuButtonClick = this.handleMenuButtonClick.bind(this);
    }

    render() {
        return (
            <React.Fragment>
                <Header menuVisibility={this.state.menuVisibility} onMenuButtonClick={this.handleMenuButtonClick}/>
                <Menu visibility={this.state.menuVisibility} onMenuItemClick={this.handleMenuButtonClick}/>
                {this.props.children}
            </React.Fragment>
        );
    }

    handleMenuButtonClick() {
        const state = {...this.state};
        state.menuVisibility = !state.menuVisibility;
        this.setState(state);
    }
}

export default UITemplate;