import React, { Component } from "react";
import TagsService from "../../service/TagsService";
import { withLocalize, Translate } from "react-localize-redux";
import { Redirect } from "react-router-dom";

class Tags extends Component {

    constructor(props) {
        super(props);


        this.state = {
            tags: [],
            editTag: '',
            newTag: null,

            autorizedUser: props.userStorage.user

        }

        this.findAll = this.findAll.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this)
        this.editClicked = this.editClicked.bind(this)
        this.discardClicked = this.discardClicked.bind(this)
        this.saveClicked = this.saveClicked.bind(this)
        this.handleNewTagInputChange = this.handleNewTagInputChange.bind(this)
        this.addClicked = this.addClicked.bind(this)
    }

    componentDidMount() {
        this.findAll()
    }

    findAll() {
        TagsService.findAll()
            .then(response =>
                this.setState({
                    tags: response.data
                })
            )
    }

    handleInputChange(event) {
        const tagId = event.target.id

        let updateTags = this.state.tags.map(tag => {
            if (tag.id == tagId) {
                tag.id = tagId
                tag.name = event.target.value
            }
            return tag
        })

        this.setState({
            tags: updateTags
        })
        console.log(updateTags)

    }

    handleNewTagInputChange(event) {

        const nTag = {
            name: event.target.value,
        }
        console.log(nTag)

        this.setState({
            newTag: nTag
        })
    }

    discardClicked() {
        this.setState({
            editTag: ''
        })
        this.findAll()
    }

    editClicked(id) {
        console.log(id)
        this.setState({
            editTag: id
        })
    }

    saveClicked(id) {
        const tag = this.state.tags.filter(x => x.id == id)
        console.log(tag)
        TagsService.updateTag(tag[0])
        this.setState({
            editTag: ''
        })
    }

    addClicked() {

        if (this.state.newTag && this.state.newTag.name.length) {
            TagsService.addTag(this.state.newTag)
            this.setState({
                newTag: null
            })

        }
    }

    makeView() {
        return this.state.tags.map(tag => {
            let tagView
            if (this.state.editTag == tag.id) {
                tagView = <div className="form-group">
                    <div className="row">
                        <div className="col-sm-3" />

                        <div className="col-sm-1">
                            <div><Translate id="tags.tag" />:</div>
                        </div>
                        <div className="col-sm-3">
                            <input type="text" className="form-control" id={tag.id} value={tag.name} onChange={this.handleInputChange} />
                        </div>
                        <div className="col-sm-5">
                            <button className="btn btn-primary btn-sm" onClick={() => this.saveClicked(tag.id)}><Translate id="saveChanges" /></button>
                            <button className="btn btn-danger btn-sm" onClick={this.discardClicked}><Translate id="discardChanges" /></button>
                        </div>
                    </div>
                </div>
            } else {
                tagView = <div className="form-group">
                    <div className="row">
                        <div className="col-sm-3"></div>

                        <div className="col-sm-1">
                            <div><Translate id="tags.tag" />:</div>
                        </div>
                        <div className="col-sm-3">
                            <input type="text" className="form-control" id={tag.id} value={tag.name} disabled />
                        </div>
                        <div className="col-sm-3">
                            <button className="btn btn-primary" onClick={() => this.editClicked(tag.id)}><Translate id="edit" /></button>
                        </div>


                    </div>
                </div>
            }
            return tagView

        }

        )
    }

    render() {

        if (this.state.autorizedUser && this.state.autorizedUser.role == 'ADMIN') {
            let tags = this.makeView();

            return (
                <div className="container-my ">
                    <h3 align="center"><Translate id="tags.add/edit" /></h3>
                    {tags}

                    <div className="form-group">
                        <form>
                            <div className="row">
                                <div className="col-sm-2" />

                                <div className="col-sm-2">
                                    <div><Translate id="tags.addTag" />:</div>
                                </div>
                                <div className="col-sm-3">
                                    <input type="text" className="form-control" onChange={this.handleNewTagInputChange} />
                                </div>
                                <div className="col-sm-5">
                                    <button className="btn btn-primary" onClick={this.addClicked}><Translate id="add" /></button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            )
        } else {
            return (
                <Redirect to="/login" />
            )
        }
    }
}

export default withLocalize(Tags);