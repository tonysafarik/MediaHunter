import React from 'react';
import BackendApi from "../MediaHunterApi";
import {MultimediumDataObject} from "../data-object/MultimediumDataObject";
import MultimediumListItem from "./search/multimedium/MultimediumListItem";

interface Props {
}

interface State {
    multimedia: MultimediumDataObject[];
}

class QueueList extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            multimedia: []
        };
        this.onItemClick = this.onItemClick.bind(this);
    }

    componentDidMount(): void {
        this.getMultimedia();
    }

    async getMultimedia() {
        let response = await BackendApi.multimedium.getQueue();
        let state = {...this.state};
        state.multimedia = response.data;
        console.log("response that i got", response);
        this.setState(state);
    }

    onItemClick() {
    }

    render() {
        return (
            <div>
                {this.state.multimedia.map(multimedium => (
                    <MultimediumListItem key={multimedium.id} multimedium={multimedium} onClick={this.onItemClick} />
                ))}
            </div>
        );
    }
}

export default QueueList;