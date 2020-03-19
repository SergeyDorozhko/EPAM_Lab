import axios from 'axios'

const USER_API_URL = 'http://localhost:8080/news_app/user/singIn'

class UserService {

    login(userLoginPwd) {
        return axios.post(`${USER_API_URL}`, userLoginPwd)
    }
}

export default new UserService()