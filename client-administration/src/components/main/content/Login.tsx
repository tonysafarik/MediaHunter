import * as React from 'react';
import ButtonLink from '../fragment/ButtonLink';
import Logo from '../fragment/Logo';
import "../../style/Login.css";
import {ChangeEvent} from "react";
import BackendApi from "../MediaHunterApi";
import {RouteComponentProps} from "react-router";

interface Props extends RouteComponentProps {}

interface State {
    username: string;
    password: string;
    correct: boolean;
}

class Login extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            correct: true
        }
        this.onUsernameInputChange = this.onUsernameInputChange.bind(this);
        this.onPasswordInputChange = this.onPasswordInputChange.bind(this);
        this.onLoginButtonClick = this.onLoginButtonClick.bind(this);
    }

    onUsernameInputChange(event: ChangeEvent<HTMLInputElement>) {
        let state = {...this.state};
        state.username = event.target.value;
        this.setState(state);
    }

    onPasswordInputChange(event: ChangeEvent<HTMLInputElement>) {
        let state = {...this.state};
        state.password = event.target.value;
        this.setState(state);
    }

    render() {
        return (
            <div className="Login">
                <Logo/>
                {this.state.correct? null : <div>Bad credentials</div>}
                <form>
                    <input className="Input" onChange={this.onUsernameInputChange} placeholder="USERNAME" type="text"/>
                    <input className="Input" onChange={this.onPasswordInputChange} placeholder="PASSWORD" type="password"/>
                    <ButtonLink onClick={this.onLoginButtonClick} to="/">Login</ButtonLink>
                </form>
            </div>
        );
    }

    async onLoginButtonClick() {
        BackendApi.login.attempt(this.state.username, this.state.password).then(() => {
            localStorage.setItem("token", btoa(this.state.username + ":" + this.state.password));
            this.props.history.push("/");
        }).catch(() => {
            console.log("Username and/or password is not correct");
            let state = {...this.state};
            state.correct = false;
            this.setState(state);
            console.log(this.state.correct);
            console.log(state.correct);
        });

    }
}

export default Login;