var app = new Vue({
	el: '#app',
	data() {
		return {
			articles: []
		}
	},
	mounted() {
		this.loadArticles();
	},
	methods: {
		loadArticles() {
			axios.get('/public/articles')
			.then(response => {
				this.articles = response.data.data;
			});
		},
		goToArticle(article) {
			window.location = '/user/article.html?code=' + article.code;
		}
	}
});