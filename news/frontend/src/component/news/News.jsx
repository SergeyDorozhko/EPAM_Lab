import React, { Component } from "react";
import NewsService from "../../service/NewsService";
import { Multiselect } from 'multiselect-react-dropdown';
import AuthorService from "../../service/AuthorService";
import TagsService from "../../service/TagsService";

class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
            message: null,

            tags:[],
            selectedTags:[],

            authors:[],
            seletedAuthor: null,
        }
        this.findAllNews = this.findAllNews.bind(this);
        this.findAllAuthors = this.findAllAuthors.bind(this);
        this.findAllTags = this.findAllTags.bind(this);
        this.deleteNewsClicked = this.deleteNewsClicked.bind(this)
        this.editNewsClicked = this.editNewsClicked.bind(this)
        this.onSelect = this.onSelect.bind(this);
        this.onRemove = this.onRemove.bind(this);
    }


    componentDidMount() {
        this.findAllNews();
        this.findAllAuthors();
        this.findAllTags();
    }

    findAllNews(params) {
        NewsService.findAllNews(params)
            .then(
                response => {
                    console.log(response);
                    this.setState({ news: response.data })
                })
    }

    findAllAuthors() {
        AuthorService.findAll()
            .then(
                response => {
                    console.log(response);
                    this.setState({ authors: response.data })
                })
    }

    findAllTags() {
       TagsService.findAll()
            .then(
                response => {
                    console.log(response);
                    this.setState({ tags: response.data })
                })
    }
    deleteNewsClicked(id) {
        NewsService.deleteNews(id)
            .then(response => {
                this.setState({ message: `News ${id} successfully deleted!` })
                this.findAllNews(this.state.selectedTags)
            })
    }

    editNewsClicked(id) {
        this.props.history.push(`/news/${id}`)
    }

    onSelect(selectedValues) {
        this.findAllNews(selectedValues)
        this.setState({selectedTags : selectedValues})
    }

    onRemove(selectedValues) {
        this.findAllNews(selectedValues)
        this.setState({selectedTags : selectedValues})
    }

    render() {
        return (
            <div className="container-my">

                <Multiselect
                    options={this.state.tags} // Options to display in the dropdown
                    selectedValues={this.state.selectedTags} // Preselected value to persist in dropdown
                    onSelect={selectedValues => this.onSelect(selectedValues)} // Function will trigger on select event
                    onRemove={selectedValues => this.onRemove(selectedValues)} // Function will trigger on remove event
                    displayValue="name" // Property name to display in the dropdown options
                />

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