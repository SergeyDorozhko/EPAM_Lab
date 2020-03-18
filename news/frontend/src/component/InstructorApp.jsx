import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import NewsComponent from "./news/NewsComponent";
import News from "./news/News";
import Header from "./Header";
import Footer from "./Footer";
import Authors from "./author/Authors"
import Tags from "./tags/Tags"

class InstructorApp extends Component {

    render() {
        return (
            <Router>
                <Header />
                <Route path="/" exact component={News} />
                <Route path="/edit/:id" component={NewsComponent} />
                <Route path="/add" component={NewsComponent} />
                <Route path="/authors" component={Authors} />
                <Route path="/tags" component={Tags} />
                <Footer />
            </Router>
        )
    }
}

export default InstructorApp