import React from 'react';
import "../../style/SettingsButton.css";

interface Props {
}

interface State {
    visibility: boolean;
}

class SettingsButton extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            visibility: false
        };
        this.onButtonClick = this.onButtonClick.bind(this);
    }

    onButtonClick() {
        let state = {...this.state};
        state.visibility = !state.visibility;
        this.setState(state);
    }

    render() {
        let visibility = this.state.visibility? "" : " invisible";
        return (
            <div onClick={this.onButtonClick} className={"SettingsButton" + visibility}>
                &#9881;
                {this.props.children}
            </div>
        );
    }
}

export default SettingsButton;