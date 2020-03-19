import UserService from "../service/UserService"


let storage = {
    _state: {
        login: '',
        password: '',
        user: null
    },

    getState() {
        return this._state
    },

    _rerender() {
    },

    inputLogin(login) {
        this._state.login = login
        this._rerender(this._state)
    },


    inputPassword(pwd) {
        this._state.password = pwd
        this._rerender(this._state)
    },

    loginClicked() {
        const userAutorizationData = { login: this._state.login, password: this._state.password }
        UserService.login(userAutorizationData).then(respose => {
            let user = {
                id: respose.data.id,
                name: respose.data.name,
                surname: respose.data.surname,
                login: respose.data.login,
                role: respose.data.role
            }
            this._state.user = respose.data
        })
        this._state.login = ''
        this._state.password = ''
        this._rerender(this._state)
    },

    logoutClicked() {
        this._state.user= null
    },

    subscribe(observer) {
        this._rerender = observer
    }

}

export default storage;