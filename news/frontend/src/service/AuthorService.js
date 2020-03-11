import axios from 'axios'

const AUTHOR_API_URL = 'http://localhost:8080/news_app/author/'

class AuthorService {
    findAll() {
        return axios.get(`${AUTHOR_API_URL}`)
    }

    updateAuthor(author) {
        return axios.put(`${AUTHOR_API_URL}`, author)
    }

    addAuthor(author) {
        return axios.post(`${AUTHOR_API_URL}`, author)
    }
}

export default new AuthorService()