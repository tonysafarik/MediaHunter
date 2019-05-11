import * as React from "react";
import FindForm from "../FindForm";
import * as Router from "react-router-dom";
import ChannelList from "./ChannelList";
import {ChannelDataObject} from "../../../data-object/ChannelDataObject";

interface FindChannelFormState {
  eid: string;
  placeholder: string;
}

class FindChannelForm extends FindForm<FindChannelFormState> {
  constructor(props: Router.RouteComponentProps<any>) {
    super(props);
    this.state = {
      eid: "",
      placeholder: "Channel Id"
    };
    this.onSearchButtonClick = this.onSearchButtonClick.bind(this);
  }


  renderRoute = (props: Router.RouteComponentProps<{ eid: string }>) => (
    <ChannelList {...props} />
  );

  getValue() {
    return this.state.eid;
  }

  onTextInputChange(value: string) {
    let state = { ...this.state };
    state.eid = value;
    this.setState(state);
  }

  getPlaceholder() {
    return this.state.placeholder;
  }
}

export default FindChannelForm;
