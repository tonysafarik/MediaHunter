import * as React from "react";
import ButtonLink from "../fragment/ButtonLink";
import "../../style/Home.css";

interface Props {
}

class Home extends React.Component<Props> {

    render() {
        return (
            <div className="Home">
                <div>Action items:</div>
                <ButtonLink to="/search/channel" onClick={() => {
                }}>Find Channel</ButtonLink>
                <ButtonLink to="/search/multimedium" onClick={() => {
                }}>Find Multimedium</ButtonLink>
                <ButtonLink to="/queue" onClick={() => {
                }}>Multimedium Queue</ButtonLink>
            </div>
        );
    }

}

export default Home;
