import React, { Component } from "react";
import NewsService from "../../service/NewsService";

class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
            message: null
        }
        this.findAllNews = this.findAllNews.bind(this);
        this.deleteNewsClicked = this.deleteNewsClicked.bind(this)
        this.editNewsClicked = this.editNewsClicked.bind(this)

    }


    componentDidMount() {
        this.findAllNews();
    }

    findAllNews() {
        NewsService.findAllNews()
            .then(
                response => {
                    console.log(response);
                    this.setState({ news: response.data })
                })
    }

    deleteNewsClicked(id) {
        NewsService.deleteNews(id)
        .then( response => {
            this.setState({message: `News ${id} successfully deleted!`})
            this.findAllNews()
        })
    }

    editNewsClicked(id) {
        this.props.history.push(`/news/${id}`)
    }
    

    render() {
        return (
            <div className="container-my">
                {this.state.message && <div className="alert alert-success">{this.state.message}</div>}

                {this.state.news.map(oneNews =>
                    <div className="card" key={oneNews.id}>
                        <div className="card-body">
                            <h4 className="card-title">{oneNews.title}</h4>
                            <div className="row">
                                <div className="col-sm-9">{oneNews.author ? (oneNews.author.name + ' ' + oneNews.author.surname) : 'No author'}</div>
                                <div className="col-sm-3">{oneNews.modificationDate}</div>
                            </div>
                            <p className="card-text">{oneNews.shortText}</p>
                            <p className="card-tags">{oneNews.tags.length > 0 ? (oneNews.tags.map(tag => tag.name + ' ')) : ''}</p>
                            <button className="btn btn-primary" onClick={() => this.editNewsClicked(oneNews.id)}>Edit</button>
                            <button className="btn btn-danger" onClick={() => this.deleteNewsClicked(oneNews.id)}>Delete</button>
                        </div>
                    </div>
                )
                }
                
                
            </div>
        )
    }
}

export default News