import UserService from "../service/UserService"


let storage = {
    _state: {
        user: null
    },

    getState() {
        return this._state
    },

    _rerender() {
    },


    loginClicked(login, password) {
        const userAutorizationData = { login: login, password: password }
        UserService.login(userAutorizationData).then(respose => {
            let user = {
                id: respose.data.id,
                name: respose.data.name,
                surname: respose.data.surname,
                login: respose.data.login,
                role: respose.data.role
            }
                this._state.user = respose.data    
            this._rerender(this._state)
        })
    },

    logoutClicked() {
        this._state.user = null
    },

    subscribe(observer) {
        this._rerender = observer
    }

}

export default storage;