import * as React from "react";
import FindForm from "../FindForm";
import * as Router from "react-router-dom";
import ChannelList from "./ChannelList";
import axios from "axios";
import { FindChannelDTO } from "./ChannelListItem";
import BackendApi from "../../../MediaHunterApi";

interface FindChannelFormState {
  info: FindChannelDTO[];
  eid: string;
  placeholder: string;
}

class FindChannelForm extends FindForm<FindChannelFormState> {
  state: Readonly<FindChannelFormState> = {
    info: [],
    eid: "",
    placeholder: "Channel Id"
  };

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
