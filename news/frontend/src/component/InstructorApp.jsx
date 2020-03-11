import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import NewsComponent from "./news/NewsComponent";
import News from "./news/News";
import Main from "./Main";
import Header from "./Header";
import Footer from "./Footer";
import Authors from "./author/Authors"
import Tags from "./tags/Tags"

class InstructorApp extends Component {

    render() {
        return (
            <Router>

                <Header />
                <Switch>
                    <Route path="/" exact component={Main} />
                    <Route path="/news" exact component={News} />
                    <Route path="/news/:id" component={NewsComponent} />
                    <Route path="/news/add" component={NewsComponent} />
                    <Route path="/authors" component={Authors} />
                    <Route path="/tags" component={Tags} />

                </Switch>
                <Footer />
            </Router>
        )
    }
}

export default InstructorApp