import React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";

import NewsComponent from "./news/NewsComponent";
import News from "./news/News";
import Header from "./Header";
import Footer from "./Footer";
import Authors from "./author/Authors"
import Tags from "./tags/Tags"
import LogInPage from "./logIn/LogInPage"

const InstructorApp = (props) => {
    return (
        <Router>
            <Header userStorage={props.userStorage}
                logoutClicked={props.logoutClicked}
            />
            <Route path="/" exact component={() =>
                <News userStorage={props.userStorage} />}
            />
            <Route path="/edit/:id" component={() =>
                <NewsComponent userStorage={props.userStorage} />}
            />
            <Route path="/add" component={() =>
                <NewsComponent userStorage={props.userStorage} />}
            />
            <Route path="/authors" component={() =>
                <Authors userStorage={props.userStorage}
                />}
            />
            <Route path="/tags" component={() =>
                <Tags userStorage={props.userStorage} />}
            />
            <Route path="/login" component={() => <LogInPage
                userStorage={props.userStorage}
                loginClicked={props.loginClicked}
            />} />
            <Footer />
        </Router>
    )
}


export default InstructorApp