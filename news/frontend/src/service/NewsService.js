import axios from 'axios'


const NEWS_API_URL = 'http://localhost:8080/news_app/news/'

class NewsService {

    findAllNews(currentPage, pageSize, searchByTags, searchByAuthor ) {
        console.log(searchByAuthor)
        var search = new URLSearchParams()
        if (searchByTags) {
            console.log(searchByTags)
            searchByTags.map(x => search.append('tags', x.name))
            console.log(search.toString())
            
        } 
        if (searchByAuthor) {
            console.log(searchByAuthor)
            const author = searchByAuthor.split(' ')
            search.append('authorName', author[0])
            search.append('authorSurname', author[1])
            console.log(search.toString())
            
        } 

        search.append('page', currentPage)
        search.append('pageSize', pageSize)
        return axios.get(`${NEWS_API_URL}search?` + search.toString());
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