import React, { Component } from "react";
import NewsComponent from "./NewsComponent";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import News from "./News";
import Main from "./Main";
import Header from "./Header";
import Footer from "./Footer";

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
                </Switch>
                <Footer />
            </Router>
        )
    }
}

export default InstructorApp