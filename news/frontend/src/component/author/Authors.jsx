import React, { Component } from "react";
import AuthorService from "../../service/AuthorService";
import { Redirect } from "react-router-dom";

export default class Authors extends Component {

    constructor(props) {
        super(props);
        this.state = {
            authors: [],
            editAuthor: '',
            newAuthor: null,

            autorizedUser: props.userStorage.user
        }

        this.findAll = this.findAll.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this)
        this.editClicked = this.editClicked.bind(this)
        this.discardClicked = this.discardClicked.bind(this)
        this.saveClicked = this.saveClicked.bind(this)
        this.handleNewAuthorInputChange = this.handleNewAuthorInputChange.bind(this)
        this.addClicked = this.addClicked.bind(this)
    }

    componentDidMount() {
        this.findAll()
    }

    findAll() {
        AuthorService.findAll()
            .then(response =>
                this.setState({
                    authors: response.data
                })
            )
    }

    handleInputChange(event) {
        const authorId = event.target.id
        let updatedAuthor = event.target.value.split(' ')

        let updateAuthors = this.state.authors.map(author => {
            if (author.id == authorId) {
                author.id = authorId
                author.name = updatedAuthor[0] ? updatedAuthor[0] : ''
                author.surname = updatedAuthor[1] ? updatedAuthor[1] : ''
            }
            return author
        })

        this.setState({
            authors: updateAuthors
        })
        console.log(updateAuthors)

    }

    handleNewAuthorInputChange(event) {
        let updatedAuthor = event.target.value.split(' ')

        const nAuthor = {
            name: updatedAuthor[0] ? updatedAuthor[0] : '',
            surname: updatedAuthor[1] ? updatedAuthor[1] : ''
        }
        console.log(nAuthor)

        this.setState({
            newAuthor: nAuthor
        })
    }

    discardClicked() {
        this.setState({
            editAuthor: ''
        })
        this.findAll()
    }

    editClicked(id) {
        console.log(id)
        this.setState({
            editAuthor: id
        })
    }

    saveClicked(id) {
        const author = this.state.authors.filter(x => x.id == id)
        console.log(author)
        AuthorService.updateAuthor(author[0])
        this.setState({
            editAuthor: ''
        })
    }

    addClicked() {

        if (this.state.newAuthor && this.state.newAuthor.name.length && this.state.newAuthor.surname.length) {
            AuthorService.addAuthor(this.state.newAuthor)
            this.setState({
                newAuthor: null
            })

        }
    }

    render() {


        if (this.state.autorizedUser) {

            let authors = this.state.authors.map(author => {
                let authorView
                if (this.state.editAuthor == author.id) {
                    authorView = <div className="form-group">
                        <div className="row">
                            <div className="col-sm-3" />

                            <div className="col-sm-1">
                                <div>Author:</div>
                            </div>
                            <div className="col-sm-3">
                                <input type="text" className="form-control" id={author.id} value={author.name + ' ' + author.surname} onChange={this.handleInputChange} />
                            </div>
                            <div className="col-sm-5">
                                <button className="btn btn-primary btn-sm" onClick={() => this.saveClicked(author.id)}>SAVE CHANGES</button>
                                <button className="btn btn-danger btn-sm" onClick={this.discardClicked}>DISCARD CHANGES</button>
                            </div>
                        </div>
                    </div>
                } else {
                    authorView = <div className="form-group">
                        <div className="row">
                            <div className="col-sm-3"></div>

                            <div className="col-sm-1">
                                <div>Author:</div>
                            </div>
                            <div className="col-sm-3">
                                <input type="text" className="form-control" id={author.id} value={author.name + ' ' + author.surname} disabled />
                            </div>
                            <div className="col-sm-3">
                                <button className="btn btn-primary" onClick={() => this.editClicked(author.id)}>Edit</button>
                            </div>


                        </div>
                    </div>
                }

                return authorView

            }

            )




            return (
                <div className="container-my ">
                    <h3 align="center">Add/Edit Authors</h3>
                    {authors}

                    <div className="form-group">
                        <form>
                            <div className="row">
                                <div className="col-sm-2" />

                                <div className="col-sm-2">
                                    <div>Add Author:</div>
                                </div>
                                <div className="col-sm-3">
                                    <input type="text" className="form-control" onChange={this.handleNewAuthorInputChange} />
                                </div>
                                <div className="col-sm-5">
                                    <button className="btn btn-primary" onClick={this.addClicked}>ADD</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            )
        } else {
            return <Redirect to="/login" />
        }
    }
}
