import React from 'react';
import FindForm from "../FindForm";
import Router from "react-router-dom";
import MultimediumList from "./MultimediumList";

interface State {
    eid: string;
    placeholder: string;
}

class FindMultimediumForm extends FindForm<State> {
    constructor(props: Router.RouteComponentProps<any>) {
        super(props);
        this.state = {
            eid: "",
            placeholder: "MultiMedium ID"
        };
        this.onSearchButtonClick = this.onSearchButtonClick.bind(this);
    }


    renderRoute = (props: Router.RouteComponentProps<{ eid: string }>) => (
        <MultimediumList {...props} />
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

export default FindMultimediumForm;