var app = new Vue({
	el: '#app',
	data() {
		return {
			articles: [],
			article: {},
			displayArticleForm: false
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
		sendArticle() {
			axios.post('/admin/article', this.article)
			.then(response => {
				if (response.data.success) {
					this.loadArticles();
					this.displayArticleForm = false;
					this.article = {};
				}
			});
		}
	}
});