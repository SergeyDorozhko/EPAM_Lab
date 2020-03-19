import React, { Component } from "react";
import NewsService from "../../service/NewsService";
import { Multiselect } from 'multiselect-react-dropdown';
import AuthorService from "../../service/AuthorService";
import TagsService from "../../service/TagsService";
import Select from 'react-select';
import Dashboard from "../dashboard/Dashboard";
import { NavLink, withRouter } from "react-router-dom";
import Pagination from "./pagination/Pagination"
import PageSize from "./pageSize/PageSize";

class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
            currentPage: 1,
            totalCount: 0,
            pageSize: 5,
            message: null,

            tags: [],
            selectedTags: [],

            authors: [],
            seletedAuthor: null,

            autorizedUser: props.userStorage.user
        }
        this.findAllNews = this.findAllNews.bind(this);
        this.findAllAuthors = this.findAllAuthors.bind(this);
        this.findAllTags = this.findAllTags.bind(this);
        this.deleteNewsClicked = this.deleteNewsClicked.bind(this)
        this.editNewsClicked = this.editNewsClicked.bind(this)
        this.handleMultiSelectChange = this.handleMultiSelectChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.onPageClick = this.onPageClick.bind(this);
        this.onChangePageSize = this.onChangePageSize.bind(this);
    }


    componentDidMount() {
        this.findAllNews(this.state.currentPage, this.state.pageSize);
        this.findAllAuthors();
        this.findAllTags();
    }

    findAllNews(currentPage, pageSize, tags, author) {
        NewsService.findAllNews(currentPage, pageSize, tags, author)
            .then(
                response => {
                    console.log(response);
                    this.setState({
                        news: response.data.items,
                        totalCount: response.data.totalCount
                    })
                })
    }

    findAllAuthors() {
        AuthorService.findAll()
            .then(
                response => {
                    console.log(response);
                    let allAuthors = response.data.map(x => {
                        let author = { value: (x.name + ' ' + x.surname), label: (x.name + ' ' + x.surname) }
                        return author;
                    })
                    console.log(allAuthors)
                    this.setState({ authors: allAuthors })
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
                this.findAllNews(this.state.currentPage,
                    this.state.pageSize,
                    this.state.selectedTags,
                    (this.state.seletedAuthor ? this.state.seletedAuthor.value : null))
            })
    }

    editNewsClicked(id) {
        debugger
        this.props.history.push(`/edit/${id}`)
    }

    handleMultiSelectChange(selectedTagsValues) {
        this.findAllNews(1,
            this.state.pageSize,
            selectedTagsValues,
            (this.state.seletedAuthor ? this.state.seletedAuthor.value : null))
        this.setState({
            selectedTags: selectedTagsValues,
            currentPage: 1
        })
    }


    handleSelectChange(seletedAuthor) {
        this.findAllNews(1,
            this.state.pageSize,
            this.state.selectedTags,
            seletedAuthor.value);
        this.setState({
            seletedAuthor: seletedAuthor,
            currentPage: 1
        });
        console.log(`Option selected:`, seletedAuthor);

    };

    onPageClick(page) {
        console.log(page)
        this.findAllNews(page,
            this.state.pageSize,
            this.state.selectedTags,
            (this.state.seletedAuthor ? this.state.seletedAuthor.value : null));
        this.setState({
            currentPage: page
        })
    }

    onChangePageSize(size) {
        console.log(size);
        this.findAllNews(this.state.page,
            size.value,
            this.state.selectedTags,
            (this.state.seletedAuthor ? this.state.seletedAuthor.value : null));
        this.setState({
            pageSize: size.value
        })
    }

    render() {


        console.log(this.state.autorizedUser)

        const { seletedAuthor } = this.state;
        console.log(this.state.authors)
        return (
            <div className="container-my">
                <div className="row">
                    <div className="col-sm-3">
                        {this.state.autorizedUser &&
                            <Dashboard user={this.state.autorizedUser}/>
                        }
                    </div>

                    <div className="col-sm-7">
                        {this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                        <div className="form-group">
                            <div className="row">
                                <div className="col-sm-1" />

                                <div className="col-sm-10">
                                    <Multiselect
                                        options={this.state.tags} // Options to display in the dropdown
                                        selectedValues={this.state.selectedTags} // Preselected value to persist in dropdown
                                        onSelect={selectedValues => this.handleMultiSelectChange(selectedValues)} // Function will trigger on select event
                                        onRemove={selectedValues => this.handleMultiSelectChange(selectedValues)} // Function will trigger on remove event
                                        displayValue="name" // Property name to display in the dropdown options
                                        hideSelectedOptions={true}
                                    />
                                </div>
                                <form>
                                    <button className="btn btn-danger">Reset</button>
                                </form>
                            </div>
                            <div className="row">
                                <div className="col-sm-1" />

                                <div className="col-sm-10">

                                    <Select
                                        value={seletedAuthor}
                                        onChange={seletedAuthor => this.handleSelectChange(seletedAuthor)}
                                        options={this.state.authors}
                                    />
                                </div>
                            </div>

                        </div>
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
                                    {this.state.autorizedUser &&
                                        <div>
                                            <button className="btn btn-primary" onClick={() => this.editNewsClicked(oneNews.id)}>Edit</button>
                                            <button className="btn btn-danger" onClick={() => this.deleteNewsClicked(oneNews.id)}>Delete</button>
                                        </div>
                                    }
                                </div>
                            </div>
                        )
                        }
                        <div className="form-group">
                            <div className="row">
                                <div className="col-sm-3" >

                                    <PageSize
                                        perPage={this.state.pageSize}
                                        onChange={this.onChangePageSize}
                                    />
                                </div>
                                <div className="col-sm-9">
                                    <Pagination
                                        pageSize={this.state.pageSize}
                                        totalNews={this.state.totalCount}
                                        currentPage={this.state.currentPage}
                                        onClick={this.onPageClick}
                                    />
                                </div>
                            </div>
                        </div>

                    </div>
                    <div className="col-sm-3" />
                </div>
            </div>
        )
    }
}

export default withRouter(News)