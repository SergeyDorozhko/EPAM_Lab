import React, { Component } from "react";
import { Redirect, withRouter } from "react-router-dom";


class LogInPage extends Component {

    constructor(props) {
        super(props)
        this.state = {
            login: '',
            password: '',

            loginButton: props.loginClicked,

            autorizedUser: props.userStorage.user

        }

        this.onChangeLogin = this.onChangeLogin.bind(this);
        this.onChangePwd = this.onChangePwd.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }


    onChangeLogin(event) {
        this.setState({
            login: event.target.value
        })
    }


    onChangePwd(event) {
        this.setState({
            password: event.target.value
        })
    }

    loginClicked() {
        this.state.loginButton(this.state.login, this.state.password);
    }

    render() {
        let loginValue = this.state.login;
        let password = this.state.password;

        if (this.state.autorizedUser) {
            return <Redirect to="/" />
        }

        return (
            <div className="container-my">
                <div className="row">

                    <div className="col-sm-4" />
                    <div className="col-sm-4">
                        <h1 align="center">Login</h1>
                        <div className="form-group">
                            <div>Login</div>
                            <div>
                                <input type="text" className="form-control" value={loginValue} onChange={this.onChangeLogin} autoFocus />
                            </div>
                        </div>
                        <div className="form-group">
                            <div>Password</div>
                            <div>
                                <input type="password" className="form-control" value={password} onChange={this.onChangePwd} />
                            </div>
                        </div>
                        <div className="form-group">
                            <div className="row">
                                <div className="col-sm-3" />
                                <div className="col-sm-3">
                                    <button className="btn btn-primary" onClick={this.loginClicked}>Login</button>
                                </div>
                                <div className="col-sm-3">
                                    <button className="btn btn-danger">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-4" />
                </div>
            </div>
        )
    }
}
export default LogInPage;
