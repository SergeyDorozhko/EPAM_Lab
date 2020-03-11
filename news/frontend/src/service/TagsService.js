import axios from 'axios'

const TAG_API_URL = 'http://localhost:8080/news_app/tag/'

class TagService {
    findAll() {
        return axios.get(`${TAG_API_URL}`)
    }

    updateTag(tag) {
        return axios.put(`${TAG_API_URL}`, tag)
    }

    addTag(tag) {
        return axios.post(`${TAG_API_URL}`, tag)
    }
}

export default new TagService()