import React, { Component } from 'react';
import { withLocalize, Translate } from "react-localize-redux";
import LanguageToggle from './Locale/LanguageToggle';
import { NavLink } from 'react-router-dom/cjs/react-router-dom.min';
import { withRouter, Redirect } from 'react-router-dom';


const Header = (props) => {

  let onLoginClicked = () => {
    props.history.push(`/login`);
  }

  let onLogoutClicked = () => {
    props.logoutClicked()
    props.history.push(`/login`);
  }

  return (
    <nav className="navbar navbar-expand-sm bg-primary navbar-dark fixed-top ">
      <div className="container">

        <NavLink className="navbar-brand" to="/">News Portal</NavLink>
        <LanguageToggle />

        <ul className="nav justify-content-right">

          {props.userStorage.user ?
              <button type="button" className="btn" onClick={onLogoutClicked}>{props.userStorage.user.login}: log out</button>
              :
            <div>
              <button type="button" className="btn" onClick={onLoginClicked}><Translate id="header.logIn" /></button>
              <button type="button" className="btn"><Translate id="header.registration" /></button>

            </div>
          }
        </ul>
      </div>
    </nav>
  )
}


export default withRouter(withLocalize(Header))