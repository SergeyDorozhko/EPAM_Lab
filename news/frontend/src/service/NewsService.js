import axios from 'axios'


const NEWS_API_URL = 'http://localhost:8080/news_app/news/'

class NewsService {

    findAllNews(searchParams) {
        var search5 = new URLSearchParams()
        if (searchParams) {
            searchParams.map(x => search5.append('tags', x.name))
            console.log(search5.toString())
            
        } 
        return axios.get(`${NEWS_API_URL}search?` + search5.toString());
    }

    deleteNews(id) {
        return axios.delete(`${NEWS_API_URL}${id}`);
    }

    findNewsById(id) {
        return axios.get(`${NEWS_API_URL}${id}`);
    }

    updateNews(news) {
        return axios.put(`${NEWS_API_URL}`, news);
    }

    createNews(news) {
        return axios.post(`${NEWS_API_URL}`, news);
    }
}

export default new NewsService()