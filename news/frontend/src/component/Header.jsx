import React, { Component } from 'react';
import { withLocalize, Translate } from "react-localize-redux";
import LanguageToggle from './Locale/LanguageToggle';
import { NavLink } from 'react-router-dom/cjs/react-router-dom.min';


class Header extends Component {
  render() {


    return (
      <nav className="navbar navbar-expand-sm bg-primary navbar-dark fixed-top ">
        <div className="container">

          <NavLink className="navbar-brand" to="/">News Portal</NavLink>
          <LanguageToggle/>

          <ul className="nav justify-content-right">
            <button type="button" className="btn"><Translate id="header.logIn"/></button>
            <button type="button" className="btn"><Translate id="header.registration"/></button>
          </ul>
        </div>
      </nav>
    )
  }
}

export default withLocalize(Header)