import React from "react";
import { Redirect } from "react-router-dom";


const LogInPage = (props) => {
    let loginValue = props.userStorage.login;
    let password = props.userStorage.password;

    let onChangeLogin = (event) => {
        let text = event.target.value;
        props.inputLogin(text)
    }

    let onChangePwd = (event) => {
        let text = event.target.value;
        props.inputPassword(text)
    }

    let login = () => {
        props.loginClicked()
    }
    
    if(props.userStorage.user) {
        return <Redirect to="/"/>
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
                            <input type="text" className="form-control"  value={loginValue} onChange={onChangeLogin} autoFocus/>
                        </div>
                    </div>
                    <div className="form-group">
                        <div>Password</div>
                        <div>
                            <input type="password" className="form-control" value={password} onChange={onChangePwd}/>
                        </div>
                    </div>
                    <div className="form-group">
                        <div className="row">
                            <div className="col-sm-3" />
                            <div className="col-sm-3">
                                <button className="btn btn-primary" onClick={login}>Login</button>
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

export default LogInPage;
