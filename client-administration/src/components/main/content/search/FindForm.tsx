import * as React from "react";
import ButtonLink from "../../fragment/ButtonLink";
import * as Router from "react-router";
import "../../../style/FindForm.css";

class FindForm<T> extends React.Component<Router.RouteComponentProps<any>, T> {

    constructor(props: Router.RouteComponentProps<any>) {
        super(props);
    }

    render() {
        return (
            <div>
                <form
                    onSubmit={event => this.handleFormSubmit(event)}
                    className="FindForm"
                >
                    <input
                        className="TextInput"
                        type="text"
                        placeholder={this.getPlaceholder()}
                        value={this.getValue()}
                        onChange={event => this.onTextInputChange(event.target.value)}
                        autoFocus
                    />
                    <ButtonLink onClick={this.onSearchButtonClick} to={this.props.match.url + "/" + this.getValue()}>
                        Search
                    </ButtonLink>
                </form>
                <Router.Route
                    path={this.props.match.url + "/:eid"}
                    render={this.renderRoute}
                />
            </div>
        );
    }

    renderRoute = (props: Router.RouteComponentProps<{ eid: string }>) => <></>;

    onSearchButtonClick() {
    }

    getPlaceholder() {
        return "";
    }

    onTextInputChange(value: string) {
    }

    getValue(): string {
        return "";
    }

    handleFormSubmit(event: React.FormEvent) {
        event.preventDefault();
        this.props.history.push(this.props.match.url + "/" + this.getValue());
    }
}

export default FindForm;
