import React, { Component } from 'react'
import NewsService from '../../service/NewsService';
import mainLogo from '../../image/plus_PNG42.png';
import TagsService from '../../service/TagsService';
import { Redirect, withRouter } from 'react-router-dom';

class NewsComponent extends Component {

    constructor(props) {
        super(props)
        debugger
        this.state = {
            id: this.props.match.params.id,
            title: '',
            shortText: '',
            fullText: '',
            author: null,
            tags: [],
            newTag: null,

            tagsOption: [],

            autorizedUser: props.userStorage.user

        }

        this.onSubmit = this.onSubmit.bind(this)
        this.handleInputChange = this.handleInputChange.bind(this)
        this.handleAuthorInputChange = this.handleAuthorInputChange.bind(this)
        this.handleTagDelete = this.handleTagDelete.bind(this)
        this.handleTagAdd = this.handleTagAdd.bind(this)
        this.handleNewTagInput = this.handleNewTagInput.bind(this)



    }

    componentDidMount() {

        NewsService.findNewsById(this.state.id)
            .then(response => this.setState({
                title: response.data.title,
                shortText: response.data.shortText,
                fullText: response.data.fullText,
                author: response.data.author,
                tags: response.data.tags
            }))

        TagsService.findAll().then(response => this.setState({
            tagsOption: response.data
        }))
    }

    handleInputChange(event) {
        const target = event.target;
        console.log(target.type);
        const name = target.name;
        const value = target.value;

        this.setState({
            [name]: value
        })
    }



    handleAuthorInputChange(event) {
        const target = event.target;
        const name = target.name;
        const value = target.value;

        let newAuthor;
        if (this.state.author) {
            newAuthor = this.state.author;

        } else {
            newAuthor = {
                name: '',
                surname: ''
            }
        }

        if (name === 'name') {
            newAuthor.name = value;
        } else if (name === 'surname') {
            newAuthor.surname = value;
        }

        this.setState({
            author: newAuthor
        })
    }

    handleTagDelete(event) {

        const delTag = event.target.id;
        const tags = this.state.tags;

        let updateTags = tags.filter(x => x.name !== delTag);
        this.setState({
            tags: updateTags
        })
    }

    handleNewTagInput(event) {
        const creatingTag = { id: '', name: event.target.value }
        this.setState({
            newTag: creatingTag
        })
    }

    handleTagAdd(event) {
        console.log(event)
        if (this.state.newTag) {
            let newTags = this.state.tags;
            newTags.push(this.state.newTag)
            this.setState({
                tags: newTags,
                newTag: null
            })
        }
    }

    onSubmit(event) {

        if (this.state.author && this.state.author.name.length == 0 && this.state.author.surname.length == 0) {
            this.setState({
                author: null
            })
        }

        let news = {
            id: (this.state.id ? this.state.id : 0),
            title: this.state.title,
            shortText: this.state.shortText,
            fullText: this.state.fullText,
            author: this.state.author,
            tags: this.state.tags
        }

        console.log(news.id);

        debugger

        if (event.target.name == 'cancel') {
            this.props.history.push('/')
        } else if (event.target.name == 'add' && news.id !== 0) {
            NewsService.updateNews(news)
            debugger
            this.props.history.push('/')
        } else {
            NewsService.createNews(news)
            debugger
            this.props.history.push('/')
        }
    }


    render() {

        if (!this.state.autorizedUser) {
            return <Redirect to="/login" />
        }

        const author = this.state.author;
        let authorField;
        console.log(this.state.id);
        if (author && author.id) {
            authorField = <p> {author.name + ' ' + author.surname} </p>
        } else {
            authorField = <div>
                <label>Name:</label>
                <input type="text" className="form-control" name="name" value={author ? author.name : ''} onChange={this.handleAuthorInputChange} />
                <div>Surname:</div>
                <input type="text" className="form-control" name="surname" value={author ? author.surname : ''} onChange={this.handleAuthorInputChange} />
            </div>
        }

        const tags = this.state.tags;
        let tagsField;
        if (tags) {
            tagsField = (
                tags.map(tag =>
                    <div className="form-group" key={tag.name}>
                        <div className="row">
                            <div className="col-sm-3"></div>

                            <div className="tags">
                                <p> {tag.name}
                                    <button id={tag.name} type="button" className="btn" onClick={this.handleTagDelete}>X</button>
                                </p>
                            </div>
                        </div>
                    </div>
                )
            )
        }

        return (
            <div className="container-my" >
                <div className="row">

                    <div className="col-sm-3"></div>

                    <div className="col-sm-6">

                        <h3 align="center">Add/Edit News</h3>
                        <form>
                            <div className="form-group">
                                <div>Author:</div>

                                {authorField}

                            </div>

                            <div className="form-group">

                                <div>News title:</div>
                                <input type="text" className="form-control" name="title" value={this.state.title} onChange={this.handleInputChange} />
                            </div>
                            <div className="form-group">
                                <div>News Short Text:</div>
                                <textarea type="text" className="form-control" name="shortText" value={this.state.shortText} onChange={this.handleInputChange} />
                            </div>

                            <div className="form-group">
                                <div>
                                    News Full Text:
                        </div>
                                <textarea type="text" className="form-control" name="fullText" value={this.state.fullText} onChange={this.handleInputChange} />
                            </div>

                            <div className="form-group">
                                <div className="row">
                                    <div className="col-sm-2"></div>

                                    <div className="col-sm-1">
                                        <div onClick={this.handleTagAdd}>
                                            <img className="tagLogo" alt="X" src={mainLogo} />
                                        </div>
                                    </div>
                                    <div className="col-sm-3">
                                        <input type="text" list="tagList" className="form-control" id="newTag" value={this.state.newTag ? this.state.newTag.name : ''} onChange={this.handleNewTagInput} />

                                        <datalist id="tagList">
                                            {this.state.tagsOption.map(x => {
                                                return <option value={x.name} />

                                            })}

                                        </datalist>
                                    </div>
                                </div>
                            </div>
                            {tagsField}

                            <div className="row justify-content-center" >
                                <div className="col-sm-3">
                                    <button className="btn btn-primary" name="add" onClick={this.onSubmit}>Add</button>
                                </div>
                                <div className="col-sm-3">
                                    <button className="btn btn-danger" name="cancel" onClick={this.onSubmit}>Cancel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div >
        )
    }


}

export default withRouter(NewsComponent)
