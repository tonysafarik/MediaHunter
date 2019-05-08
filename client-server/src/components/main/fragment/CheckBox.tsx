import * as React from "react";

interface Props {
  reference: React.RefObject<HTMLInputElement>;
}

class CheckBox extends React.Component<Props> {
  render() {
    return (
      <div className="CheckBox">
        <input ref={this.props.reference} type="checkbox" id="cbx"/>
        <label htmlFor="cbx" className="check">
          <span />
        </label>
      </div>
    );
  }
}

export default CheckBox;
