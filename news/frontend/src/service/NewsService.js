import axios from 'axios'

const NEWS_API_URL = 'http://localhost:8080/news_app/news/'

class NewsService {

    findAllNews() {
        return axios.get(`${NEWS_API_URL}search`);
    }

    deleteNews(id) {
        return axios.delete(`${NEWS_API_URL}${id}`);
    }

    findNewsById(id) {
        return axios.get(`${NEWS_API_URL}${id}`);
    }

    updateNews(news){
        return axios.put(`${NEWS_API_URL}`, news);
    }

    createNews(news) {
        return axios.post(`${NEWS_API_URL}`, news);
    }
}

export default new NewsService()