import * as React from "react";
import "../../style/CheckBox.css";

interface Props {
    reference: React.RefObject<HTMLInputElement>;
}

class CheckBox extends React.Component<Props> {
    constructor(props: Props) {
        super(props);
    }
    render() {
        return (
            <div className="CheckBox">
                <input ref={this.props.reference} type="checkbox" id="cbx"/>
                <label htmlFor="cbx" className="check">
                    <span/>
                </label>
            </div>
        );
    }
}

export default CheckBox;
