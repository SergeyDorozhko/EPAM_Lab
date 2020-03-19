import React from 'react';
import './App.css';
import InstructorApp from './component/InstructorApp';
import { LocalizeProvider } from "react-localize-redux";
import Localizer from './component/Locale/Localizer';

function App(props) {
  return (
    <LocalizeProvider>
      <Localizer />
      <InstructorApp userStorage={props.userStorage}
        inputLogin={props.inputLogin}
        inputPassword={props.inputPassword} 
        loginClicked={props.loginClicked}
        logoutClicked={props.logoutClicked}

        />
    </LocalizeProvider>

  );
}

export default App;
