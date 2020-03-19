import React from 'react'
import ReactDOM from 'react-dom'
import App from './App'
import  storage from './storage/UserStorage';
import * as serviceWorker from './serviceWorker'


let render = (state) => {
    ReactDOM.render(<App userStorage={state}
        inputLogin={storage.inputLogin.bind(storage)}
        inputPassword={storage.inputPassword.bind(storage)}
        loginClicked={storage.loginClicked.bind(storage)}
        logoutClicked={storage.logoutClicked.bind(storage)}

        />, document.getElementById('root'));
}

render(storage.getState())

storage.subscribe(render);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
