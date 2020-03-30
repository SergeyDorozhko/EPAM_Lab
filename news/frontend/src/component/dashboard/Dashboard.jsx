
import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './Dashboard.css';

class Dashboard extends Component {

    constructor(props) {
        super(props)
        this.addEditAuthorsClicked = this.addEditAuthorsClicked.bind(this)
        this.addNewsClicked = this.addNewsClicked.bind(this)
        this.addEditTagsClicked = this.addEditTagsClicked.bind(this)
        
        this.state = {
            autorizedUser: props.user
        }
    }

    addEditAuthorsClicked() {
        this.props.history.push(`/authors`)
    }

    addNewsClicked() {
        this.props.history.push(`/add`);
    }

    addEditTagsClicked() {
        this.props.history.push(`/tags`);
    }

    render() {
        const isAdmin = this.state.autorizedUser.ERole == 'ADMIN'
        return (
            <div className="container" >
                <div className="row-auto bg-info text-white" >
                    <div className="col text-center">Dashboard</div >
                    <div className="col-auto pad">
                        <div className="row" >
                            <button className="btn btn-primary btn-block" onClick={this.addEditAuthorsClicked}>Add/Edit Authors</button >
                        </div >
                        <div className="row" >
                            <button className="btn btn-primary btn-block" onClick={this.addNewsClicked} >Add News</button >
                        </div >
                        <div className="row" >
                            <button className="btn btn-primary btn-block"
                                onClick={this.addEditTagsClicked}
                                disabled={!isAdmin}
                            >Add/Edit Tags</button >
                        </div >
                    </div >
                </div >
            </div >
        )
    }
}


export default withRouter(Dashboard)